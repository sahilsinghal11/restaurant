package com.myrestaurant.restaurant.services;

import com.myrestaurant.restaurant.entity.Item;
import com.myrestaurant.restaurant.entity.Order;
import com.myrestaurant.restaurant.repository.ItemRepository;
import com.myrestaurant.restaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PrimaryService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    public HashMap<String, Object> createOrder(String itemId, Integer numItems){

        HashMap<String, Object> response = new HashMap<>();
        response.put("error", false);
        response.put("message", "success");
        HashMap<String, Object> data = new HashMap<>();

        List<Item> itemList = itemRepository.findByItemId(itemId);
        if(itemList.size() == 0){
            response.put("error", true);
            response.put("message", "Invalid Item Id");
            return response;
        }

        String newOrderId = UUID.randomUUID().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String orderTime = formatter.format(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,30);

        String expectedDeliveryTime = formatter.format(calendar.getTime());
        Order newOrder = new Order(newOrderId, itemId, numItems, orderTime, "Accepted", expectedDeliveryTime);
        orderRepository.save(newOrder);

        data.put("order_id" , newOrderId);
        response.put("data", data);

        return response;
    }

    public HashMap<String, Object> updateOrderStatus(String orderId, String updatedStatus){
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", false);
        response.put("message", "success");

        List<Order> orderList = orderRepository.findById(orderId);
        if(orderList.size() == 0){
            response.put("error", true);
            response.put("message", "Order does not exist");
            return response;
        }

        try{
            orderRepository.updateStatus(orderId, updatedStatus);
        }
        catch(Exception e){
            response.put("error", true);
            response.put("message", e.getMessage());
            return response;
        }
        return response;
    }

    public HashMap<String, Object> getOrderStatus(String orderId) throws ParseException {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", false);
        response.put("message", "success");
        HashMap<String, Object> data = new HashMap<>();

        List<Order> orderList = orderRepository.findById(orderId);
        if(orderList.size() == 0){
            response.put("error", true);
            response.put("message", "Order does not exist");
            return response;
        }

        String orderStatus = orderList.get(0).getStatus();
        if("InProgress".equalsIgnoreCase(orderStatus)){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date currDate = new Date();
            Date firstParsedDate = formatter.parse(currDate.toString());
            Date secondParsedDate = formatter.parse(orderList.get(0).getExpectedDeliveryTime());
            long diff = secondParsedDate.getTime() - firstParsedDate.getTime();
            long diffMins = diff/60000;
            if(diffMins < 0){
                data.put("status", "order is " + diffMins + " late");
            }
            else{
                data.put("status", "order will reach in " + diffMins);
            }
        }
        else {
            data.put("status", orderStatus);
        }
        response.put("data", data);
        return response;
    }

    public HashMap<String, Object> getActiveDeliveryAgents() throws ParseException {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", false);
        response.put("message", "success");

        List<Order> orderList = orderRepository.findByStatus("InProgress");
        List<HashMap<String, Object>> listOfAgents = new ArrayList<>();
        for(Order order : orderList){
            HashMap<String, Object> agentDetails = new HashMap<>();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date currDate = new Date();
            Date firstParsedDate = formatter.parse(currDate.toString());
            Date secondParsedDate = formatter.parse(order.getExpectedDeliveryTime());
            long diff = secondParsedDate.getTime() - firstParsedDate.getTime();
            long diffMins = diff/60000;
            agentDetails.put("agent_id", order.getDeliveryAgentId());
            agentDetails.put("order_id", order.getId());
            if(diffMins < 0){
                agentDetails.put("status", "order is " + diffMins + " late");
            }
            else{
                agentDetails.put("status", "order will reach in " + diffMins);
            }
        }

        response.put("data", listOfAgents);
        return response;
    }
}
