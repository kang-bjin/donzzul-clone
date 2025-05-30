package com.ossproj.donjjul.dto;

import com.ossproj.donjjul.domain.Store;

public class StoreMarkerDto {
    private String name;
    private double lat;
    private double lng;
    private String category;

    public StoreMarkerDto(Store store) {
        this.name = store.getName();
        this.lat = store.getLat();
        this.lng = store.getLng();
        this.category = store.getCategory();
    }

    // Getter만 필요
    public String getName() { return name; }
    public double getLat() { return lat; }
    public double getLng() { return lng; }
    public String getCategory() { return category; }
}
