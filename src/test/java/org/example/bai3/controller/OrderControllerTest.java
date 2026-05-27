package org.example.bai3.controller;


import org.example.bai3.model.Order;
import org.example.bai3.service.OrderService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllOrders() throws Exception {

        List<Order> orders = List.of(
                new Order(1L,
                        "Chiến",
                        "Laptop",
                        1,
                        2000.0)
        );

        when(orderService.getAllOrders())
                .thenReturn(orders);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName")
                        .value("Chiến"));
    }

    @Test
    void testGetOrderById_Found() throws Exception {

        Order order = new Order(
                1L,
                "Chiến",
                "Laptop",
                1,
                2000.0
        );

        when(orderService.getOrderById(1L))
                .thenReturn(order);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product")
                        .value("Laptop"));
    }

    @Test
    void testGetOrderById_NotFound() throws Exception {

        when(orderService.getOrderById(99L))
                .thenThrow(new RuntimeException("Order not found"));

        mockMvc.perform(get("/api/orders/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPostOrder() throws Exception {

        Order order = new Order(
                1L,
                "Admin",
                "Mouse",
                2,
                100.0
        );

        when(orderService.addOrder(org.mockito.ArgumentMatchers.any(Order.class)))
                .thenReturn(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id")
                        .value(1));
    }
}
