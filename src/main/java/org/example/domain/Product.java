package org.example.domain;
import lombok.*;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 1/29/2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @NonNull
    private String productId;
    @NonNull
    private ProductInfo productInfo;
    @NonNull
    private Review review;
}
