package com.delivery.delivery.domain.model;

public enum DayOfWeek {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");
    
    private final String displayName;
    
    DayOfWeek(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public boolean isWeekend() {
        return this == SATURDAY || this == SUNDAY;
    }
    
    public boolean isWeekday() {
        return !isWeekend();
    }
}

