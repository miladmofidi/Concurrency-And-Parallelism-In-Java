package org.example.service.parallel_approach;


import org.example.domain.checkout.CartItem;

import static org.example.util.CommonUtil.delay;

public class PriceValidatorService
{

    public boolean isCartItemInvalid(CartItem cartItem){
        int cartId = cartItem.getItemId();
        delay(500);
        if (cartId == 7 || cartId == 9 || cartId == 11) {
            return true;
        }
        return false;
    }
}
