package org.example.service.completablefuture_approach;

import org.example.domain.Inventory;
import org.example.domain.Product;
import org.example.domain.ProductInfo;
import org.example.domain.ProductOption;
import org.example.domain.Review;
import org.example.service.blocking_approach.InventoryService;
import org.example.service.blocking_approach.ProductInfoService;
import org.example.service.blocking_approach.ProductService;
import org.example.service.blocking_approach.ReviewService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.example.util.CommonUtil.stopWatch;
import static org.example.util.LoggerUtil.log;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/16/2023
 */
public class ProductServiceUsingCompletableFuture
{
    private ProductInfoService productInfoService;
    private ReviewService reviewService;
    private InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService)
    {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService,
                                                InventoryService inventoryService)
    {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    /*Asynchronously calling 2 services and then combine and join their response to a unique response
        this method prefer if you want to make client base application, because the join() method will block main thread until other 2 call respond*/
    public Product retrieveProductDetails(String productId)
    {
        stopWatch.start();

        //ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
        //Review review = reviewService.retrieveReviews(productId); // blocking call
        CompletableFuture<ProductInfo> productInfoCf =
                CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCf =
                CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        Product product = productInfoCf.thenCombine(reviewCf,
                                                    (productInfo, review) -> new Product(
                                                            productId, productInfo,
                                                            review))
                .join(); //join() will blocking the main thread until their response is ready
        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return product;
    }

    /*Asynchronously calling 2 services and then combine and join their response to a unique response.
      this method prefer if you want to make server-side base application, since we do not have a join() method it will not block main thread and response will return as CompletableFuture*/
    public CompletableFuture<Product> retrieveProductDetails_approach2(String productId)
    {
        CompletableFuture<ProductInfo> productInfoCf =
                CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCf =
                CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        return productInfoCf.thenCombine(reviewCf,
                                                    (productInfo, review) -> new Product(
                                                            productId, productInfo,
                                                            review))
                ; //there is no join() here anymore

    }

    //This method needs the InventoryService to fetch inventory status for each product info, so we added the InventoryService into productInfoService once retrieveProductInfo() method step is done.
    public Product retrieveProductDetailsWithInventory(String productId)
    {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCf =
                CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId)).
                        thenApply(productInfo -> {
                            productInfo.setProductOptions(updateInventory(productInfo));
                            return productInfo;
                        });
        CompletableFuture<Review> reviewCf =
                CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        Product product = productInfoCf.thenCombine(reviewCf,
                                                    (productInfo, review) -> new Product(
                                                            productId, productInfo,
                                                            review))
                .join(); //join() will blocking the main thread until their response is ready
        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return product;
    }

    //This method needs the InventoryService to fetch inventory status for each product info, so we added the InventoryService into productInfoService once retrieveProductInfo() method step is done.
    public Product retrieveProductDetailsWithInventory_approach2(String productId)
    {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCf =
                CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId)).
                        thenApply(productInfo -> {
                            productInfo.setProductOptions(updateInventory_approach2_withBetterPerformance(productInfo));
                            return productInfo;
                        });
        CompletableFuture<Review> reviewCf =
                CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId))
                        .exceptionally( exception -> {
                            log("Handled the exception in reviewService:  "+ exception.getMessage());
                            return Review.builder().noOfReviews(0).overallRating(0.0).build();
                        });

        Product product = productInfoCf
                .thenCombine(reviewCf,(productInfo, review) -> new Product(productId, productInfo,review))
                .whenComplete((product1, exception)-> {
                    log("Inside whenComplete: " +product1+ "and the exception is: "+ exception);
                })
                .join(); //join() will blocking the main thread until their response is ready
        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return product;
    }

    public Product retrieveProductDetailsWithInventory_approach2_withBetterPerformance_exceptionally(String productId)
    {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCf =
                CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId)).
                        thenApply(productInfo -> {
                            productInfo.setProductOptions(updateInventory_approach2_withBetterPerformance_exceptionally(productInfo));
                            return productInfo;
                        });
        CompletableFuture<Review> reviewCf =
                CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId))
                        .exceptionally( exception -> {
                            log("Handled the exception in reviewService:  "+ exception.getMessage());
                            return Review.builder().noOfReviews(0).overallRating(0.0).build();
                        });

        Product product = productInfoCf
                .thenCombine(reviewCf,(productInfo, review) -> new Product(productId, productInfo,review))
                .whenComplete((product1, exception)-> {
                    log("Inside whenComplete: " +product1+ " and the exception is: "+ exception);
                })
                .join(); //join() will blocking the main thread until their response is ready
        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return product;
    }

    //A method for get and set the value for each productOption's inventory status, but still we have latency here about the latency in InventoryService process.
    private List<ProductOption> updateInventory(ProductInfo productInfo)
    {
        List<ProductOption> productOptions = productInfo.getProductOptions().stream().map(productOption -> {
            Inventory inventory = inventoryService.retrieveInventory(productOption);
            productOption.setInventory(inventory);
            return productOption;
        }).collect(Collectors.toList());

        return productOptions;
    }


    private List<ProductOption> updateInventory_approach2_withBetterPerformance(ProductInfo productInfo)
    {
        List<CompletableFuture<ProductOption> > productOptions = productInfo.getProductOptions().stream()
                .map(productOption -> {
            return CompletableFuture.supplyAsync( () -> inventoryService.retrieveInventory(productOption))
                            .thenApply(inventory -> {
                                productOption.setInventory(inventory);
                                return productOption;
                            });
        })
                .collect(Collectors.toList());

        return productOptions.stream().map(productOptionCompletableFuture -> productOptionCompletableFuture.join()).collect(
                Collectors.toList());
    }

    private List<ProductOption> updateInventory_approach2_withBetterPerformance_exceptionally(ProductInfo productInfo)
    {
        List<CompletableFuture<ProductOption> > productOptions = productInfo.getProductOptions().stream()
                .map(productOption -> {
                    return CompletableFuture.supplyAsync( () -> inventoryService.retrieveInventory(productOption))
                            .exceptionally( exception -> {
                                log("Handled the exception in updateInventory call:  "+ exception.getMessage());
                                return Inventory.builder().count(1).build();
                            })
                            .thenApply(inventory -> {
                                productOption.setInventory(inventory);
                                return productOption;
                            });
                })
                .collect(Collectors.toList());

        return productOptions.stream().map(CompletableFuture::join).collect(
                Collectors.toList());
    }

    public static void main(String[] args)
    {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductService productService = new ProductService(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);
    }
}
