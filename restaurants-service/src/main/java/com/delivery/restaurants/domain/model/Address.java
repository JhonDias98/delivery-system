package com.delivery.restaurants.domain.model;

public record Address(
    String street,
    String number,
    String complement,
    String neighborhood,
    String city,
    String state,
    String zipCode
) {
    public Address {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street cannot be null or blank");
        }
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Number cannot be null or blank");
        }
        if (neighborhood == null || neighborhood.isBlank()) {
            throw new IllegalArgumentException("Neighborhood cannot be null or blank");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be null or blank");
        }
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("State cannot be null or blank");
        }
        if (zipCode == null || zipCode.isBlank()) {
            throw new IllegalArgumentException("Zip code cannot be null or blank");
        }
    }
    
    public String getFullAddress() {
        var fullAddress = new StringBuilder()
            .append(street).append(", ").append(number);
        
        if (complement != null && !complement.isBlank()) {
            fullAddress.append(", ").append(complement);
        }
        
        fullAddress.append(" - ").append(neighborhood)
                  .append(", ").append(city)
                  .append(" - ").append(state)
                  .append(", ").append(zipCode);
        
        return fullAddress.toString();
    }
}

