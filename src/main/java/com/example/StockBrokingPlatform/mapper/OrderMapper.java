package com.example.StockBrokingPlatform.mapper;

import com.example.StockBrokingPlatform.DTO.OrderDTO;
import com.example.StockBrokingPlatform.model.Client;
import com.example.StockBrokingPlatform.model.Instrument;
import com.example.StockBrokingPlatform.model.Order;

public class OrderMapper {
    public static OrderDTO toDTO(Order entity) {
        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());
        dto.setClientId(entity.getClient().getId());
        dto.setInstrumentId(entity.getInstrument().getId());
        dto.setOrderType(entity.getOrderType());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        dto.setStatus(entity.getStatus());
        dto.setOrderDate(entity.getOrderDate());
        dto.setValidity(entity.getValidity());
        return dto;
    }

    public static Order toEntity(OrderDTO dto, Client client, Instrument instrument) {
        Order entity = new Order();
        entity.setId(dto.getId());
        entity.setClient(client);
        entity.setInstrument(instrument);
        entity.setOrderType(dto.getOrderType());
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());
        entity.setStatus(dto.getStatus());
        entity.setOrderDate(dto.getOrderDate());
        entity.setValidity(dto.getValidity());
        return entity;
    }
}
