package com.driver;

public class Order {

    private String id;
    private int deliveryTime;



    public Order(String id, String deliveryTime) {
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        int HH = 0;
        int MM = 0;
        int i;
        for(i= 0 ; i < 2 ; i++){
            int temp = deliveryTime.charAt(i) - '0';
            HH = HH*10 + temp;
        }
        for(i = 3; i< 5;i++){
            int temp2 = deliveryTime.charAt(i)-'0';
            MM = MM*10+temp2;
        }
        int time = HH*60 + MM;
        setDeliveryTime(time);
    }
    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
