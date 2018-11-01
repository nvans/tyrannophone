package com.nvans.tyrannophone.model.cart;

import com.nvans.tyrannophone.model.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class OrderCart {

    private List<Order> orders;


    public OrderCart() {
        orders = new ArrayList<>();
    }

    public void addOrder(Order order) {

        if (order == null) return;

        order.setOrderDate(LocalDateTime.now());
        orders.add(order);
    }

    public Order getOrder(int index) {
        return orders.get(index);
    }

    public void deleteOrder(int index) {

        orders.remove(index);
    }

    public Integer getTotalPrice() {

        return orders.stream().mapToInt(Order::getPrice).sum();
    }


    public boolean isCartEmpty() {
        return orders.isEmpty();
    }

    public void clear() {
        orders.clear();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


}
