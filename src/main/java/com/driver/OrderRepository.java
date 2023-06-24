package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    Map<String , Order> ordersDB = new HashMap<>();
    Map<String , DeliveryPartner> deliveryPartnerDB = new HashMap<>();
    Map<String , String> orderPartnerDB = new HashMap<>();
    Map<String , List<String>> partnerOrderDB = new HashMap<>();
    public void addOrder(Order order) {
        ordersDB.put(order.getId(), order);
    }

    public void addPartner(String partnerId) {
        deliveryPartnerDB.put(partnerId , new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(ordersDB.containsKey(orderId) && deliveryPartnerDB.containsKey(partnerId))
            orderPartnerDB.put(orderId , partnerId);

        List<String> currOrders = new ArrayList<>();
        if(partnerOrderDB.containsKey(partnerId)){
            currOrders = partnerOrderDB.get(partnerId);
        }
    }
}
