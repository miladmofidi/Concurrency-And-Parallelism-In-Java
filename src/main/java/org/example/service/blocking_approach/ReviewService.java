package org.example.service.blocking_approach;

import org.example.domain.Review;

import static org.example.util.CommonUtil.delay;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        delay(1000);
        return new Review(200, 4.5);
    }
}
