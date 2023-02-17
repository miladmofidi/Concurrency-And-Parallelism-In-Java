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
 * user: miladm on 2/17/2023
 */
public class CompletableFutureHelloWorldException
{
    HelloWorldService helloWorldService;

    public CompletableFutureHelloWorldException(HelloWorldService helloWorldService)
    {
        this.helloWorldService = helloWorldService;
    }

    public String helloWorld_3_async_calls_handle()
    {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });
        String result = hello
                .handle((res, exception) -> { //handle exception for hello completable future here if there is a exception from hello() method since we have returned the empty string for hello cf so the result would be=""
                    log("res is: " + res);
                    if (exception != null)
                    {
                        log("Exception is: " + exception.getMessage());
                        return "";
                    }
                    else
                    {
                        return res;
                    }
                })
                .thenCombine(world, (h, w) -> h + w)
                .handle((res, exception) -> { //handle exception for world completable future
                    log("res is: " + res);
                    if (exception != null)
                    {
                        log("Exception after world is: " + exception.getMessage());
                        return "";
                    }
                    else
                    {
                        return res;
                    }
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous +
                                                                         current) //if there is exception in world() method the result of this code will be "  Hi CompletableFuture!"
                .thenApply(String::toUpperCase) //the result of this code will be "HELLO WORLD! HI COMPLETABLEFUTURE!"
                .join();
        timeTaken();
        return result;
    }

    public String helloWorld_3_async_calls_exceptionally()
    {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });
        String result = hello
                .exceptionally(
                        exception -> { //handle exception for hello completable future here if there is a exception from hello() method since we have returned the empty string for hello cf so the result would be=""
                            log("Exception is: " + exception.getMessage());
                            return "";
                        })
                .thenCombine(world, (h, w) -> h + w)
                .exceptionally(exception -> { //handle exception for world completable future
                    log("Exception after world is: " + exception.getMessage());
                    return "";
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous +
                                                                         current) //if there is exception in world() method the result of this code will be "  Hi CompletableFuture!"
                .thenApply(String::toUpperCase) //the result of this code will be "HELLO WORLD! HI COMPLETABLEFUTURE!"
                .join();
        timeTaken();
        return result;
    }

    public String helloWorld_3_async_calls_whenComplete()
    {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });
        String result = hello
                .whenComplete(
                        (res, exception) -> { //handle exception for hello completable future here if there is a exception from hello() method since we have returned the empty string for hello cf so the result would be=""
                            log("res is: " + res);
                            if (exception != null)
                            {
                                log("Exception is: " + exception.getMessage());
                            }
                        })
                .thenCombine(world, (h, w) -> h + w)
                .whenComplete((res, exception) -> { //handle exception for world completable future
                    log("res is: " + res);
                    if (exception != null)
                    {
                        log("Exception after world is: " + exception.getMessage());
                    }
                })
                .exceptionally(
                        exception -> {
                            log("Exception after thenCombine is: " + exception.getMessage());
                            return "";
                        })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous +
                                                                         current) //if there is exception in world() method the result of this code will be "  Hi CompletableFuture!"
                .thenApply(String::toUpperCase) //the result of this code will be "HELLO WORLD! HI COMPLETABLEFUTURE!"
                .join();
        timeTaken();
        return result;
    }
}
