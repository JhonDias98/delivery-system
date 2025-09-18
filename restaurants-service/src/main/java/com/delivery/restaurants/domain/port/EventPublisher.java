package com.delivery.restaurants.domain.port;

import com.delivery.restaurants.domain.event.*;

public interface EventPublisher {
    
    void publishRestaurantCreated(RestaurantCreatedEvent event);
    
    void publishRestaurantActivated(RestaurantActivatedEvent event);
    
    void publishRestaurantDeactivated(RestaurantDeactivatedEvent event);
    
    void publishMenuUpdated(MenuUpdatedEvent event);
    
    void publishRestaurantRatingUpdated(RestaurantRatingUpdatedEvent event);
}

