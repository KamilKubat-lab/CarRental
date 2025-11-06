package com.car.rental.service;

import com.car.rental.model.CarType;

import org.springframework.stereotype.Component;
import java.util.EnumMap;
import java.util.Map;

@Component
public class CarInventory {
    private final Map<CarType, Integer> inventory = new EnumMap<>(CarType.class);


    public CarInventory() {
        inventory.put(CarType.SEDAN, 3);
        inventory.put(CarType.SUV, 2);
        inventory.put(CarType.VAN, 1);
    }


    public int getCount(CarType type) {
        return inventory.getOrDefault(type, 0);
    }


    public void setCount(CarType type, int count) {
        inventory.put(type, count);
    }
}
