package org.example.service.parallel_approach;

import org.example.domain.checkout.Cart;
import org.example.domain.checkout.CartItem;
import org.example.domain.checkout.CheckoutResponse;
import org.example.domain.checkout.CheckoutStatus;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.util.CommonUtil.startTimer;
import static org.example.util.CommonUtil.timeTaken;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/6/2023
 */
public class CheckoutService
{
    PriceValidatorService priceValidatorService;

    public CheckoutService(PriceValidatorService priceValidatorService)
    {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkOut(Cart cart){
        startTimer();
        List<CartItem> priceValidationList = cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> {
                    boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isPriceInvalid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());
        if (priceValidationList.size() > 0){
            return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
        }
        timeTaken();
        return new CheckoutResponse(CheckoutStatus.SUCCESS);
    }

}
