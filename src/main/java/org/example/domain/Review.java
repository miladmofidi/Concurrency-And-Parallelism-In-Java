package org.example.domain;

import lombok.*;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 1/29/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Review {
    private int noOfReviews;
    private double overallRating;
}
