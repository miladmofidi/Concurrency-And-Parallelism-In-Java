package org.example.service.thread_approach;

import org.example.domain.Product;
import org.example.domain.ProductInfo;
import org.example.domain.Review;
import org.example.service.blocking_approach.ProductInfoService;
import org.example.service.blocking_approach.ReviewService;

import static org.example.util.CommonUtil.stopWatch;
import static org.example.util.LoggerUtil.log;

public class ProductServiceUsingThread
{
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingThread(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException
    {
        stopWatch.start();

        //Creating a Runnable for product info service and creating a new thread to run it.
        ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);
        Thread productInfoThread = new Thread(productInfoRunnable);

        //Creating a Runnable for review service and creating a new thread to run it.
        ReviewRunnable reviewRunnable = new ReviewRunnable(productId);
        Thread reviewThread = new Thread(reviewRunnable);

        productInfoThread.start();
        reviewThread.start();

        productInfoThread.join();
        reviewThread.join();

        ProductInfo productInfo = productInfoRunnable.getProductInfo();
        Review review = reviewRunnable.getReview();

/*
        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
        Review review = reviewService.retrieveReviews(productId); // blocking call
*/

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) throws InterruptedException
    {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingThread productService = new ProductServiceUsingThread(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }

    private class ProductInfoRunnable implements Runnable
    {
        private ProductInfo productInfo;
        private String productId;
        public ProductInfoRunnable(String productId)
        {
            this.productId=productId;
        }

        public ProductInfo getProductInfo()
        {
            return productInfo;
        }

        @Override
        public void run()
        {
            productInfo = productInfoService.retrieveProductInfo(productId);

        }
    }
    private class ReviewRunnable implements Runnable
    {
        private Review review;
        private String productId;
        public ReviewRunnable(String productId)
        {
            this.productId=productId;
        }

        public Review getReview()
        {
            return review;
        }

        @Override
        public void run()
        {
            review = reviewService.retrieveReviews(productId);
        }
    }


}
