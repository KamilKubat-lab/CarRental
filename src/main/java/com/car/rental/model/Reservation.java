package com.car.rental.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Reservation {
    private final String id = UUID.randomUUID().toString();
    private final CarType carType;
    private final LocalDateTime start;
    private final LocalDateTime end;


    public Reservation(CarType carType, LocalDateTime start, LocalDateTime end) {
        this.carType = carType;
        this.start = start;
        this.end = end;
    }

    public boolean overlaps(LocalDateTime otherStart, LocalDateTime otherEnd) {
        return this.start.isBefore(otherEnd) && otherStart.isBefore(this.end);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
