package com.ossproj.donjjul.dto;

import com.ossproj.donjjul.domain.Store;

public class StoreMarkerDto {
    private String name;
    private double lat;
    private double lng;
    private String category;

    public StoreMarkerDto(Store store) {
        this.name = store.getName();
        this.lat = store.getLatitude() != null ? store.getLatitude() : 0.0;
        this.lng = store.getLongitude() != null ? store.getLongitude() : 0.0;
        this.category = store.getCategory() != null ? store.getCategory() : "UNKNOWN";
    }

    public String getName() { return name; }
    public double getLat() { return lat; }
    public double getLng() { return lng; }
    public String getCategory() { return category; }
}
