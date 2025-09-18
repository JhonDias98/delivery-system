package com.delivery.restaurants.infrastructure.adapter.in.web.mapper;

import com.delivery.restaurants.application.dto.CreateRestaurantCommand;
import com.delivery.restaurants.domain.model.*;
import com.delivery.restaurants.infrastructure.adapter.in.web.dto.CreateRestaurantRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantWebMapper {
    
    public CreateRestaurantCommand toCommand(CreateRestaurantRequest request) {
        var address = new Address(
            request.address().street(),
            request.address().number(),
            request.address().complement(),
            request.address().neighborhood(),
            request.address().city(),
            request.address().state(),
            request.address().zipCode()
        );
        
        var contactInfo = new ContactInfo(
            request.contactInfo().phone(),
            request.contactInfo().email(),
            request.contactInfo().website()
        );
        
        var operatingHours = request.operatingHours().stream()
            .map(dto -> new OperatingHours(
                dto.openTime(),
                dto.closeTime(),
                List.of(DayOfWeek.valueOf(dto.dayOfWeek()))
            ))
            .toList();
        
        var menu = request.menu() != null ? request.menu().stream()
            .map(itemDto -> new MenuItem(
                MenuItemId.generate(),
                itemDto.name(),
                itemDto.description(),
                itemDto.price(),
                MenuCategory.valueOf(itemDto.category()),
                itemDto.available(),
                itemDto.imageUrl()
            ))
            .toList() : List.<MenuItem>of();
        
        return new CreateRestaurantCommand(
            request.name(),
            request.description(),
            address,
            contactInfo,
            operatingHours,
            menu
        );
    }
}

