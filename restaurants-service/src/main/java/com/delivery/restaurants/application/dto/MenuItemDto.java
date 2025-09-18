package com.delivery.restaurants.application.dto;

import com.delivery.restaurants.domain.model.MenuItem;
import com.delivery.restaurants.domain.model.MenuCategory;

import java.math.BigDecimal;

public record MenuItemDto(
    String id,
    String name,
    String description,
    BigDecimal price,
    MenuCategory category,
    boolean available,
    String imageUrl
) {
    public static MenuItemDto from(MenuItem item) {
        return new MenuItemDto(
            item.id().toString(),
            item.name(),
            item.description(),
            item.price(),
            item.category(),
            item.available(),
            item.imageUrl()
        );
    }
}

