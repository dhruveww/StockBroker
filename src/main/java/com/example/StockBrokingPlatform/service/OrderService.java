// OrderService.java
package com.example.StockBrokingPlatform.service;

import com.example.StockBrokingPlatform.DTO.OrderDTO;
import com.example.StockBrokingPlatform.exception.ResourceNotFoundException;
import com.example.StockBrokingPlatform.mapper.OrderMapper;
import com.example.StockBrokingPlatform.model.Client;
import com.example.StockBrokingPlatform.model.Instrument;
import com.example.StockBrokingPlatform.model.Order;
import com.example.StockBrokingPlatform.repository.ClientRepository;
import com.example.StockBrokingPlatform.repository.InstrumentRepository;
import com.example.StockBrokingPlatform.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    public OrderDTO placeOrder(OrderDTO dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        Instrument instrument = instrumentRepository.findById(dto.getInstrumentId())
                .orElseThrow(() -> new ResourceNotFoundException("Instrument not found"));

        dto.setOrderDate(LocalDateTime.now());
        dto.setStatus("PENDING");
        Order order = OrderMapper.toEntity(dto, client, instrument);
        return OrderMapper.toDTO(orderRepository.save(order));
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public OrderDTO modifyOrder(Long id, OrderDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!"PENDING".equals(order.getStatus())) {
            throw new IllegalStateException("Only pending orders can be modified");
        }

        order.setPrice(dto.getPrice());
        order.setQuantity(dto.getQuantity());
        return OrderMapper.toDTO(orderRepository.save(order));
    }

    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!"PENDING".equals(order.getStatus())) {
            throw new IllegalStateException("Only pending orders can be cancelled");
        }

        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }

    public List<OrderDTO> getOrdersByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId).stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getPendingOrders() {
        return orderRepository.findByStatus("PENDING").stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO updateStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatus(status);
        return OrderMapper.toDTO(orderRepository.save(order));
    }
}