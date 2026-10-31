package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AnalyticsService;

@RestController
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(
            AnalyticsService analyticsService) {

        this.analyticsService = analyticsService;
    }

    @GetMapping("/analytics")
    public Map<String, Integer> getAnalytics() {

        Map<String, Integer> data =
                new HashMap<>();

        data.put(
                "totalShipments",
                analyticsService.getTotalShipments());

        data.put(
                "created",
                analyticsService.getCreated());

        data.put(
                "paid",
                analyticsService.getPaid());

        data.put(
                "packed",
                analyticsService.getPacked());

        data.put(
                "shipped",
                analyticsService.getShipped());

        data.put(
                "delivered",
                analyticsService.getDelivered());

        data.put(
                "cancelled",
                analyticsService.getCancelled());

        data.put(
                "failed",
                analyticsService.getFailed());

        data.put(
                "deliveryAssigned",
                analyticsService.getDeliveryAssigned());

        data.put(
                "deliveryFailed",
                analyticsService.getDeliveryFailed());

        return data;
    }
}
