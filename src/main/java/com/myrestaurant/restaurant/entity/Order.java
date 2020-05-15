package com.myrestaurant.restaurant.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "num_items")
    private Integer numItems;

    @Column(name = "orderTime")
    private String orderTime;

    @Column(name = "status")
    private String status;

    @Column(name = "expected_delivery_time")
    private String expectedDeliveryTime;

    @Column(name = "actual_delivery_time")
    private String actualDeliveryTime;

    @Column(name = "delivery_agent_id")
    private String deliveryAgentId;

    public Order(String id, String itemId, Integer numItems, String orderTime, String status, String expectedDeliveryTime){
        this.id = id;
        this.itemId = itemId;
        this.numItems = numItems;
        this.orderTime = orderTime;
        this.status = status;
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    public String getStatus(){
        return status;
    }

    public String getId(){
        return id;
    }

    public String getExpectedDeliveryTime(){
        return expectedDeliveryTime;
    }

    public String getDeliveryAgentId(){
        return deliveryAgentId;
    }
}
