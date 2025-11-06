package com.car.rental.service;

import com.car.rental.exception.NoAvailableCarException;
import com.car.rental.model.CarType;
import com.car.rental.model.Reservation;
import com.car.rental.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CarRentalService {
    private final ReservationRepository reservationRepository;
    private final CarInventory inventory;


    public CarRentalService(ReservationRepository reservationRepository, CarInventory inventory) {
        this.reservationRepository = reservationRepository;
        this.inventory = inventory;
    }


    public Reservation reserve(CarType type, LocalDateTime start, int numberOfDays) {
        if (numberOfDays <= 0){
            throw new IllegalArgumentException("Number of days must be positive");
        }
        if (start.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }

        LocalDateTime end = start.plusDays(numberOfDays);


        List<Reservation> overlapping = reservationRepository.findByTypeAndOverlap(type, start, end);
        if (overlapping.size() >= inventory.getCount(type)) {
            throw new NoAvailableCarException("No " + type + " available for the requested period");
        }
        Reservation r = new Reservation(type, start, end);
        reservationRepository.save(r);
        return r;

    }


    public void clearAllReservations() {
        reservationRepository.clear();
    }
}
