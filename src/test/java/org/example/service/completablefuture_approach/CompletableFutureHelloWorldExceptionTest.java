package org.example.service.completablefuture_approach;

import org.example.service.HelloWorldService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/17/2023
 */
@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest
{
    @Mock
    HelloWorldService helloWorldService=mock(HelloWorldService.class);

    @InjectMocks
    CompletableFutureHelloWorldException service;


    @Test
    @DisplayName("Testing service with simulated exception for hello() method")
    void helloWorld_3_async_calls_handle()
    {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occurred"));
        when(helloWorldService.world()).thenCallRealMethod();
        String result = service.helloWorld_3_async_calls_handle();
        assertEquals(" WORLD! HI COMPLETABLEFUTURE!",result);
    }

    @Test
    @DisplayName("Testing service with simulated exception for hello() and world() methods")
    void helloWorld_3_async_calls_handle_2()
    {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception occurred"));
        String result = service.helloWorld_3_async_calls_handle();
        assertEquals(" HI COMPLETABLEFUTURE!",result);
    }

    @Test
    @DisplayName("Testing service without any exception for hello() and world() methods")
    void helloWorld_3_async_calls_handle_3()
    {
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();
        String result = service.helloWorld_3_async_calls_handle();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!",result);
    }

    @Test
    @DisplayName("Testing service with exceptionally method")
    void helloWorld_3_async_calls_exceptionally()
    {
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();
        String result = service.helloWorld_3_async_calls_exceptionally();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!",result);
    }
    @Test
    @DisplayName("Testing service with simulated exception for hello() and world() methods with exceptionally approach")
    void helloWorld_3_async_calls_exceptionally_2()
    {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception occurred"));
        String result = service.helloWorld_3_async_calls_exceptionally();
        assertEquals(" HI COMPLETABLEFUTURE!",result);
    }

    @Test
    @DisplayName("Testing service without any exception for hello() and world() methods")
    void helloWorld_3_async_calls_whenComplete()
    {
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();
        String result = service.helloWorld_3_async_calls_whenComplete();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!",result);
    }
    @Test
    @DisplayName("Testing service with simulated exception for hello() and world() methods with whenComplete approach")
    void helloWorld_3_async_calls_whenComplete_2()
    {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception occurred"));
        String result = service.helloWorld_3_async_calls_whenComplete();
        assertEquals(" HI COMPLETABLEFUTURE!",result);
    }
}