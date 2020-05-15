package com.myrestaurant.restaurant.repository;

import com.myrestaurant.restaurant.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long> {

    List<Order> findById(String id);

    @Modifying
    @Query("Update Order o set o.status = :updatedStatus where o.id = :orderId")
    void updateStatus(@Param("orderId") String orderId,
                      @Param("updatedStatus") String updatedStatus);

    List<Order> findByStatus(String status);

}
