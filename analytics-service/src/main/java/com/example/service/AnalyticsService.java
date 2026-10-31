package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    private int totalShipments;

    private int created;
    private int paid;
    private int packed;
    private int shipped;
    private int delivered;
    private int cancelled;
    private int failed;
    private int deliveryAssigned;
    private int deliveryFailed;

    public synchronized void processEvent(
            String eventType) {

        switch (eventType) {

            case "SHIPMENT_CREATED":
                totalShipments++;
                created++;
                break;

            case "SHIPMENT_PAID":
                paid++;
                break;

            case "SHIPMENT_PACKED":
                packed++;
                break;

            case "SHIPMENT_SHIPPED":
                shipped++;
                break;

            case "SHIPMENT_DELIVERED":
                delivered++;
                break;

            case "SHIPMENT_CANCELLED":
                cancelled++;
                break;

            case "SHIPMENT_FAILED":
                failed++;
                break;

            case "DELIVERY_ASSIGNED":
                deliveryAssigned++;
                break;

            case "DELIVERY_FAILED":
                deliveryFailed++;
                break;
        }

        System.out.println();
        System.out.println("=================================");
        System.out.println("ANALYTICS DASHBOARD");
        System.out.println("=================================");
        System.out.println("Total Shipments   : " + totalShipments);
        System.out.println("Created           : " + created);
        System.out.println("Paid              : " + paid);
        System.out.println("Packed            : " + packed);
        System.out.println("Shipped           : " + shipped);
        System.out.println("Delivered         : " + delivered);
        System.out.println("Cancelled         : " + cancelled);
        System.out.println("Failed            : " + failed);
        System.out.println("Delivery Assigned : " + deliveryAssigned);
        System.out.println("Delivery Failed   : " + deliveryFailed);
        System.out.println("=================================");
    }

    public int getTotalShipments() {
        return totalShipments;
    }

    public int getCreated() {
        return created;
    }

    public int getPaid() {
        return paid;
    }

    public int getPacked() {
        return packed;
    }

    public int getShipped() {
        return shipped;
    }

    public int getDelivered() {
        return delivered;
    }

    public int getCancelled() {
        return cancelled;
    }

    public int getFailed() {
        return failed;
    }

    public int getDeliveryAssigned() {
        return deliveryAssigned;
    }

    public int getDeliveryFailed() {
        return deliveryFailed;
    }
}
