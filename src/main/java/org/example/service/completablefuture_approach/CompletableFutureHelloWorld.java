package org.example.service.completablefuture_approach;

import org.example.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static org.example.util.CommonUtil.delay;
import static org.example.util.CommonUtil.startTimer;
import static org.example.util.CommonUtil.timeTaken;
import static org.example.util.LoggerUtil.log;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/8/2023
 */
public class CompletableFutureHelloWorld
{
    HelloWorldService helloWorldService;

    public CompletableFutureHelloWorld(HelloWorldService helloWorldService)
    {
        this.helloWorldService = helloWorldService;
    }

    public CompletableFuture<String> helloWorld(){
        return CompletableFuture.supplyAsync( () -> helloWorldService.helloWorld() )
                .thenApply(result -> result.toUpperCase());
    }

    public String helloWorldWithTraditionalApproach(){
        String hello = helloWorldService.hello();
        String world = helloWorldService.world();
        return hello + world;
    }
    public String helloWorldWithMultipleAsyncCall(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        String result = hello.thenCombine(world, (h,w) -> h+w)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return result;
    }
    public String helloWorldWith3AsyncCall(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });
        String result = hello
                .thenCombine(world, (h,w) -> h+w)
                .thenCombine(hiCompletableFuture, (previous,current) -> previous+current)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return result;
    }

    public String helloWorld_4_async_calls() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        });
        // Add the 4th CompletableFuture that returns a String "  Bye!"
        CompletableFuture<String> byeCompletableFuture = CompletableFuture.supplyAsync(() -> " Bye!");

        String hw = hello
                .thenCombine(world, (h, w) -> h + w) // (first,second)
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                // Combine the fourth CompletableFuture
                .thenCombine(byeCompletableFuture, (previous, current) -> previous+current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return hw;
    }

    public CompletableFuture<String> helloWorldThenCompose(){
        return CompletableFuture.supplyAsync(() -> helloWorldService.hello())
                .thenCompose((previous) -> helloWorldService.worldFuture(previous))
                .thenApply(String::toUpperCase);
    }
    public CompletableFuture<String> helloWorld_withSize(){
        return CompletableFuture.supplyAsync( () -> helloWorldService.helloWorld())
                .thenApply(string -> {
                    StringBuilder sb = new StringBuilder(string.toUpperCase());
                    sb.insert(0,string.length()+" - ");
                    return sb.toString();
                });
    }



    public static void main(String[] args)
    {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFuture.supplyAsync( () -> helloWorldService.helloWorld() )
                .thenApply(result -> result.toUpperCase())
                .thenAccept( (result) -> {
                    log("Result is: "+ result);
                }).join();
        //without making delay for main thread or withou joining threads you will see just "Done from main thread." message in console,
        //but if you make a delay or join them you will see the HelloWorld service body as well
        log("Done from main thread.");
        //delay(2000);

    }
}
