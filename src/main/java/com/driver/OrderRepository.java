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

        currOrders.add(orderId);
        partnerOrderDB.put(partnerId , currOrders);

        DeliveryPartner deliveryPartner = deliveryPartnerDB.get(partnerId);
        deliveryPartner.setNumberOfOrders(currOrders.size());
    }

    public Order getOrderById(String orderId) {
        return ordersDB.get(orderId);
    }

    public DeliveryPartner getPartnerByID(String partnerId) {
        return deliveryPartnerDB.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return partnerOrderDB.get(partnerId).size();
    }

    public List<String> getOrdersbyPartnerId(String partnerId) {
        return partnerOrderDB.get(partnerId);
    }

    public List<String> getAllOrders() {
        List<String> orders = new ArrayList<>();
        for(String order : ordersDB.keySet()){
            orders.add(order);
        }
        return orders;
    }

    public Integer getCountOfUnassignedOrders() {
        return ordersDB.size() - orderPartnerDB.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int newTime, String partnerId) {
        int count =0;
        List<String> orders = partnerOrderDB.get(partnerId);

        for(String orderId : orders){
            int dTime = ordersDB.get(orderId).getDeliveryTime();
            if(dTime > newTime){
                count++;
            }
        }
        return count;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        int maxTime =0;
        List<String> orders = partnerOrderDB.get(partnerId);

        for(String orderId : orders){
            int dTime = ordersDB.get(orderId).getDeliveryTime();
            maxTime = Math.max(maxTime , dTime);
        }
        return maxTime;
    }

    public void deletePartnerById(String partnerId) {
        deliveryPartnerDB.remove(partnerId);
        List<String> listOfOrders = partnerOrderDB.get(partnerId);
        partnerOrderDB.remove(partnerId);

        for(String order : listOfOrders){
            orderPartnerDB.remove(order);
        }
    }

    public void deleteOrderById(String orderId) {
        ordersDB.remove(orderId);

        String partner = orderPartnerDB.get(orderId);
        orderPartnerDB.remove(orderId);

        partnerOrderDB.get(partner).remove(orderId);

        deliveryPartnerDB.get(partner).setNumberOfOrders(partnerOrderDB.get(partner).size());
    }
}
