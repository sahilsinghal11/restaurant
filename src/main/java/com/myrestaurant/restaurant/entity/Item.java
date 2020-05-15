package com.myrestaurant.restaurant.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;
}
