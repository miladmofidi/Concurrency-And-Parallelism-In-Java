package org.example.domain;
import lombok.*;

import java.util.List;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 1/29/2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInfo
{
    private String productId;
    private List<ProductOption> productOptions;

}
