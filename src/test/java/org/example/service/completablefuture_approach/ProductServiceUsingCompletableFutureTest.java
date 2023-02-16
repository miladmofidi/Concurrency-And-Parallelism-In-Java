package org.example.service.completablefuture_approach;

import org.example.domain.Product;
import org.example.service.blocking_approach.InventoryService;
import org.example.service.blocking_approach.ProductInfoService;
import org.example.service.blocking_approach.ReviewService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.example.util.CommonUtil.startTimer;
import static org.example.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/16/2023
 */
class ProductServiceUsingCompletableFutureTest
{
    private ProductInfoService productInfoService= new ProductInfoService();
    private ReviewService reviewService = new ReviewService();
    private InventoryService inventoryService= new InventoryService();

    private ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture = new ProductServiceUsingCompletableFuture(productInfoService,reviewService, inventoryService);

    @Test
    void retrieveProductDetails()
    {
        String productId="ABC123";
        Product product = productServiceUsingCompletableFuture.retrieveProductDetails(productId);
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetails_approach2() {

        //given
        String productId = "ABC123";
        startTimer();

        //when
        CompletableFuture<Product> cfProduct = productServiceUsingCompletableFuture.retrieveProductDetails_approach2(productId);

        //then
        cfProduct
                .thenAccept((product -> {
                    assertNotNull(product);
                    assertTrue(product.getProductInfo().getProductOptions().size() > 0);
                    assertNotNull(product.getReview());
                }))
                .join();

        timeTaken();

    }

    @Test
    void retrieveProductDetailsWithInventory()
    {
        String productId="ABC123";
        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory(productId);
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions().forEach(productOption -> {
            assertNotNull(productOption.getInventory());
        });
        assertNotNull(product.getReview());

    }

    @Test
    void retrieveProductDetailsWithInventory_approach2()
    {
        String productId="ABC123";
        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory_approach2(productId);
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions().forEach(productOption -> {
            assertNotNull(productOption.getInventory());
        });
        assertNotNull(product.getReview());
    }
}