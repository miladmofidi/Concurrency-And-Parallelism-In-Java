package org.example.service.completablefuture_approach;

import org.example.domain.Product;
import org.example.service.blocking_approach.InventoryService;
import org.example.service.blocking_approach.ProductInfoService;
import org.example.service.blocking_approach.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/17/2023
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceUsingCompletableFutureExceptionTest
{
    @Mock
    private ProductInfoService productInfoService;
    @Mock
    private ReviewService reviewService;
    @Mock
    private InventoryService inventoryService;
    @InjectMocks
    ProductServiceUsingCompletableFuture psucf;

    @Test
    void retrieveProductDetailsWithInventory_approach2()
    {
        String productId= "ABC123";
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        when(reviewService.retrieveReviews(any())).thenThrow(new RuntimeException("Exception occurred: "));
        when(inventoryService.retrieveInventory(any())).thenCallRealMethod();

        Product product = psucf.retrieveProductDetailsWithInventory_approach2(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions().forEach(productOption -> {
            assertNotNull(productOption.getInventory());
        });
        assertNotNull(product.getReview());
        assertEquals(0,product.getReview().getNoOfReviews());

    }

    @Test
    void retrieveProductDetailsWithInventory_productInfoServiceError()
    {
        String productId= "ABC123";
        when(productInfoService.retrieveProductInfo(any())).thenThrow(new RuntimeException("Exception occurred: "));
        when(reviewService.retrieveReviews(any())).thenCallRealMethod();

        Assertions.assertThrows(RuntimeException.class , ()-> psucf.retrieveProductDetailsWithInventory_approach2(productId));

    }

    @Test
    void updateInventory_approach2_withBetterPerformance_withExceptionally()
    {
        String productId= "ABC123";
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        //when(reviewService.retrieveReviews(any())).thenCallRealMethod();

        Assertions.assertThrows(RuntimeException.class , ()-> psucf.retrieveProductDetailsWithInventory_approach2_withBetterPerformance_exceptionally(productId));

    }
}