package org.example.domain;
import lombok.*;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 1/29/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOption {
    private Integer productionOptionId;
    private String size;
    private String  color;
    private double  price;
    private Inventory inventory;

    public ProductOption(Integer productionOptionId, String size, String color, double price) {
        this.productionOptionId = productionOptionId;
        this.size = size;
        this.color = color;
        this.price = price;
    }
}