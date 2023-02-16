package org.example.service.blocking_approach;

import org.example.domain.ProductInfo;
import org.example.domain.ProductOption;

import java.util.List;

import static org.example.util.CommonUtil.delay;

public class ProductInfoService {

    public ProductInfo retrieveProductInfo(String productId) {
        delay(1000);
        List<ProductOption> productOptions = List.of(new ProductOption(1, "64GB", "Black", 699.99),
                                                     new ProductOption(2, "128GB", "Black", 749.99),
                                                     new ProductOption(3, "256GB", "Black", 850.99),
                                                     new ProductOption(4, "512GB", "Black", 119.99));
        return ProductInfo.builder().productId(productId)
                .productOptions(productOptions)
                .build();
    }
}
