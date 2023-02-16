package org.example.service.blocking_approach;

import org.example.domain.Inventory;
import org.example.domain.ProductOption;

import static org.example.util.CommonUtil.delay;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/16/2023
 */
public class InventoryService
{
    public Inventory retrieveInventory(ProductOption productOption){
        delay(500);
        return Inventory.builder().count(2).build();
    }
}
