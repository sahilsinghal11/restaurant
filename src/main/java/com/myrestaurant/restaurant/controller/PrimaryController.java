package com.myrestaurant.restaurant.controller;

import com.myrestaurant.restaurant.services.PrimaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class PrimaryController {

    @Autowired
    PrimaryService primaryService;

    @RequestMapping(value = "/create_order", method = RequestMethod.POST)
    public HashMap<String, Object> createOrder(@RequestParam(value = "item_id") String itemId,
                                               @RequestParam(value = "num_items") Integer numItems){
        return primaryService.createOrder(itemId, numItems);
    }

    @RequestMapping(value = "/get_order_status", method = RequestMethod.GET)
    public HashMap<String, Object> getOrderStatus(@RequestParam(value = "order_id") String orderId){
        return primaryService.getOrderStatus(orderId);
    }

    @RequestMapping(value = "/update_order_status", method = RequestMethod.POST)
    public HashMap<String, Object> updateOrderStatus(@RequestParam(value = "order_id") String orderId,
                                                     @RequestParam(value = "updated_status") String updatedStatus) {
        return primaryService.updateOrderStatus(orderId, updatedStatus);
    }

    @RequestMapping(value = "/get_active_delivery_agents", method = RequestMethod.GET)
    public HashMap<String, Object> getActiveDeliveryAgents() throws ParseException {
        return primaryService.getActiveDeliveryAgents();
    }
}
