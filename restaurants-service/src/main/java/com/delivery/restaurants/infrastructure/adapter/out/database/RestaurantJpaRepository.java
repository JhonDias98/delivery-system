package com.delivery.restaurants.infrastructure.adapter.out.database;

import com.delivery.restaurants.infrastructure.adapter.out.persistence.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, UUID> {
    
    List<RestaurantEntity> findByStatus(String status);
    
    @Query("SELECT r FROM RestaurantEntity r WHERE r.status = 'ACTIVE'")
    List<RestaurantEntity> findActiveRestaurants();
    
    @Query("SELECT r FROM RestaurantEntity r WHERE r.name ILIKE %:name%")
    List<RestaurantEntity> findByNameContaining(@Param("name") String name);
    
    @Query("""
        SELECT r FROM RestaurantEntity r 
        WHERE r.latitude IS NOT NULL AND r.longitude IS NOT NULL
        AND (6371 * acos(cos(radians(:latitude)) * cos(radians(r.latitude)) * 
        cos(radians(r.longitude) - radians(:longitude)) + 
        sin(radians(:latitude)) * sin(radians(r.latitude)))) <= :radiusKm
        AND r.status = 'ACTIVE'
        ORDER BY (6371 * acos(cos(radians(:latitude)) * cos(radians(r.latitude)) * 
        cos(radians(r.longitude) - radians(:longitude)) + 
        sin(radians(:latitude)) * sin(radians(r.latitude))))
        """)
    List<RestaurantEntity> findByLocationRadius(
        @Param("latitude") BigDecimal latitude,
        @Param("longitude") BigDecimal longitude,
        @Param("radiusKm") double radiusKm
    );
    
    long countByStatus(String status);
}

