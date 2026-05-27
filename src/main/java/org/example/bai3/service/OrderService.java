package org.example.bai3.service;





import org.example.bai3.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final List<Order> orders = new ArrayList<>();

    private Long currentId = 1L;

    public List<Order> getAllOrders() {
        return orders;
    }

    public Order getOrderById(Long id) {

        return orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }

    public Order addOrder(Order order) {

        order.setId(currentId++);

        orders.add(order);

        return order;
    }

    public Order updateOrder(Long id, Order newOrder) {

        Order oldOrder = getOrderById(id);

        oldOrder.setCustomerName(newOrder.getCustomerName());
        oldOrder.setProduct(newOrder.getProduct());
        oldOrder.setQuantity(newOrder.getQuantity());
        oldOrder.setTotalAmount(newOrder.getTotalAmount());

        return oldOrder;
    }

    public void deleteOrder(Long id) {

        Order order = getOrderById(id);

        orders.remove(order);
    }
}
