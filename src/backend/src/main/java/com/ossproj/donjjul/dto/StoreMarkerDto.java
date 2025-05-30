package com.ossproj.donjjul.dto;

import com.ossproj.donjjul.domain.Store;

public class StoreMarkerDto {
    private String name;
    private double lat;
    private double lng;
    private String category; // 현재 Store 클래스에는 없음 (필요 시 추가해야 함)

    public StoreMarkerDto(Store store) {
        this.name = store.getName();
        this.lat = store.getLatitude();   // 수정됨
        this.lng = store.getLongitude();  // 수정됨
        this.category = "UNKNOWN";        // ← Store에 category 필드 없으므로 임시 처리
    }

    public String getName() { return name; }
    public double getLat() { return lat; }
    public double getLng() { return lng; }
    public String getCategory() { return category; }
}
