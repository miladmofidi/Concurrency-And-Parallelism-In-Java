package org.example.service.parallel_approach;

import org.example.domain.checkout.Cart;
import org.example.domain.checkout.CheckoutResponse;
import org.example.domain.checkout.CheckoutStatus;
import org.example.util.DataSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/6/2023
 */
class CheckoutServiceTest
{
    PriceValidatorService priceValidatorService = new PriceValidatorService();
    CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    @DisplayName("getting number of cpu cores")
    void no_of_cores()
    {
        System.out.println("no of CPU cores: " + Runtime.getRuntime().availableProcessors());
    }

    @Test
    @DisplayName("checking performance with just 6 items that means all cases are going to be a success")
    void checkOut_6_items()
    {
        //given
        //create and populate 6 cart item into cart
        Cart cart = DataSet.createCart(6);
        //when
        CheckoutResponse checkoutResponse = checkoutService.checkOut(cart);
        //then
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
    }

    @Test
    @DisplayName("checking performance with 13 items that means we have failure items")
    void checkOut_13_items()
    {
        //given
        //create and populate 6 cart item into cart
        Cart cart = DataSet.createCart(13);
        //when
        CheckoutResponse checkoutResponse = checkoutService.checkOut(cart);
        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }

    @Test
    @DisplayName("checking performance with 25 items that means definitely we will have failure items")
    void checkOut_25_items()
    {
        //given
        //create and populate 6 cart item into cart
        Cart cart = DataSet.createCart(25);
        //when
        CheckoutResponse checkoutResponse = checkoutService.checkOut(cart);
        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }
}