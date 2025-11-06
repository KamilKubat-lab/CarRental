package com.car.rental.repository;


import com.car.rental.model.CarType;
import com.car.rental.model.Reservation;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;


@Repository
public class ReservationRepository {
    private final Queue<Reservation> reservations = new ConcurrentLinkedQueue<>();


    public List<Reservation> findByTypeAndOverlap(CarType type, LocalDateTime start, LocalDateTime end) {
        synchronized (reservations) {
            return reservations.stream()
                    .filter(r -> r.getCarType() == type && r.overlaps(start, end))
                    .collect(Collectors.toList());
        }
    }


    public void save(Reservation r) {
        reservations.add(r);
    }


    public void clear() {
        reservations.clear();
    }
}
