package org.example.bai3.service;



import org.example.bai3.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();

        orderService.addOrder(
                new Order(null,
                        "Chiến",
                        "Laptop",
                        1,
                        2000.0)
        );
    }

    // 1
    @Test
    void getAllOrders_ReturnNonEmptyList() {

        List<Order> orders = orderService.getAllOrders();

        assertFalse(orders.isEmpty());
    }

    // 2
    @Test
    void getOrderById_Found() {

        Order order = orderService.getOrderById(1L);

        assertEquals("Chiến", order.getCustomerName());
    }

    // 3
    @Test
    void getOrderById_NotFound_ThrowException() {

        assertThrows(RuntimeException.class,
                () -> orderService.getOrderById(99L));
    }

    // 4
    @Test
    void addOrder_Success() {

        Order order = new Order(
                null,
                "Admin",
                "Mouse",
                2,
                100.0
        );

        Order saved = orderService.addOrder(order);

        assertNotNull(saved.getId());

        assertEquals(2,
                orderService.getAllOrders().size());
    }

    // 5
    @Test
    void updateOrder_Success() {

        Order newOrder = new Order(
                null,
                "Updated",
                "Keyboard",
                3,
                300.0
        );

        Order updated =
                orderService.updateOrder(1L, newOrder);

        assertEquals("Updated",
                updated.getCustomerName());

        assertEquals("Keyboard",
                updated.getProduct());
    }

    // 6
    @Test
    void deleteOrder_RemovesElement() {

        orderService.deleteOrder(1L);

        assertEquals(0,
                orderService.getAllOrders().size());
    }
}
