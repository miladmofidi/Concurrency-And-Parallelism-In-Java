package org.example.service.executer_approach;

import org.example.domain.Product;
import org.example.domain.ProductInfo;
import org.example.domain.Review;
import org.example.service.blocking_approach.ProductInfoService;
import org.example.service.blocking_approach.ReviewService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.example.util.CommonUtil.stopWatch;
import static org.example.util.LoggerUtil.log;

public class ProductServiceUsingExecuter
{
    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingExecuter(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws ExecutionException, InterruptedException
    {
        stopWatch.start();

        Future<ProductInfo> productInfoFuture  = executorService.submit(() -> productInfoService.retrieveProductInfo(productId) );
        Future<Review> reviewFuture = executorService.submit( () -> reviewService.retrieveReviews(productId) );

        ProductInfo productInfo = productInfoFuture.get();
        Review review = reviewFuture.get();

/*
        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
        Review review = reviewService.retrieveReviews(productId); // blocking call
*/

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException
    {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingExecuter productService = new ProductServiceUsingExecuter(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);
        executorService.shutdown();

    }
}
