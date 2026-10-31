package com.example.entity;

public class Shipment {

    private String shipmentId;
    private Long customerId;
    private String customerName;
    private String pickupAddress;
    private String deliveryAddress;
    private Double weight;
    private String status;

    public Shipment() {
    }

    public Shipment(
            String shipmentId,
            Long customerId,
            String customerName,
            String pickupAddress,
            String deliveryAddress,
            Double weight,
            String status) {

        this.shipmentId = shipmentId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.pickupAddress = pickupAddress;
        this.deliveryAddress = deliveryAddress;
        this.weight = weight;
        this.status = status;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
