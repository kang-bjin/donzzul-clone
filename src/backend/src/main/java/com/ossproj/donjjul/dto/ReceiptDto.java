// dto/ReceiptDto.java
package com.ossproj.donjjul.dto;

public class ReceiptDto {
    private String storeName;
    private int totalAmount;
    // 필요한 필드 추가

    // Getter/Setter
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public int getTotalAmount() { return totalAmount; }
    public void setTotalAmount(int totalAmount) { this.totalAmount = totalAmount; }
}
