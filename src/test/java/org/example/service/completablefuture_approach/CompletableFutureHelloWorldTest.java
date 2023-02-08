package org.example.service.completablefuture_approach;

import org.example.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.example.util.CommonUtil.startTimer;
import static org.example.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/8/2023
 */
class CompletableFutureHelloWorldTest
{
    HelloWorldService helloWorldService =new HelloWorldService();
    CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);

    @Test
    void helloWorld()
    {
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld();
        completableFuture.thenAccept(s -> assertEquals("HELLO WORLD" , s))
                .join();
    }
    @Test
    void helloWorldWithMultipleAsyncCall()
    {
        String helloWorld = completableFutureHelloWorld.helloWorldWithMultipleAsyncCall();
        assertEquals("HELLO WORLD!" , helloWorld);
    }
    @Test
    void helloWorldWith3AsyncCall()
    {
        String helloWorld = completableFutureHelloWorld.helloWorldWith3AsyncCall();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!" , helloWorld);
    }
    @Test
    void helloWorld_4_async_calls()
    {
        String helloWorld = completableFutureHelloWorld.helloWorld_4_async_calls();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE! BYE!" , helloWorld);
    }
    @Test
    void helloWorldThenCompose()
    {
        startTimer();
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorldThenCompose();
        completableFuture.thenAccept(s -> assertEquals("HELLO WORLD!" , s))
                .join();
        timeTaken();
    }

    @Test
    void helloWorld_withSize()
    {
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld_withSize();
        completableFuture.thenAccept(s -> assertEquals("11 - HELLO WORLD" , s))
                .join();
    }
}