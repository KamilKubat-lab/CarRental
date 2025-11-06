package com.car.rental.service;

import com.car.rental.exception.NoAvailableCarException;
import com.car.rental.model.CarType;
import com.car.rental.model.Reservation;
import com.car.rental.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CarRentalServiceTest {
    private CarRentalService service;


    @BeforeEach
    public void setup() {
        ReservationRepository repo = new ReservationRepository();
        CarInventory inventory = new CarInventory();
        inventory.setCount(CarType.SEDAN, 2);
        inventory.setCount(CarType.SUV, 1);
        inventory.setCount(CarType.VAN, 1);
        service = new CarRentalService(repo, inventory);
        service.clearAllReservations();
    }


    @Test
    public void reserveSucceedsWhenAvailable() {
        LocalDateTime start = LocalDateTime.of(2025, 11, 10, 10, 0);
        Reservation r = service.reserve(CarType.SEDAN, start, 3);
        assertNotNull(r.getId());
        assertEquals(CarType.SEDAN, r.getCarType());
        assertEquals(start, r.getStart());
        assertEquals(start.plusDays(3), r.getEnd());
    }


    @Test
    public void cannotReserveWhenInventoryExceeded() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        service.reserve(CarType.SEDAN, start, 2);
        service.reserve(CarType.SEDAN, start.plusDays(1), 2); // overlapping


        assertThrows(NoAvailableCarException.class, () -> service.reserve(CarType.SEDAN, start.plusHours(3), 1));
    }


    @Test
    public void nonOverlappingReservationsAllowed() {
        LocalDateTime start1 = LocalDateTime.now().plusDays(1);
        LocalDateTime start2 = LocalDateTime.now().plusDays(4);
        service.reserve(CarType.SEDAN, start1, 3);
        Reservation r2 = service.reserve(CarType.SEDAN, start2, 2);
        assertNotNull(r2);
    }


    @Test
    public void differentTypesDoNotConflict() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);;
        service.reserve(CarType.SUV, start, 2);
        Reservation r = service.reserve(CarType.SEDAN, start, 2);
        assertNotNull(r);
    }


    @Test
    public void invalidDaysThrows() {
        LocalDateTime start = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class, () -> service.reserve(CarType.SEDAN, start, 0));
    }

    @Test
    void shouldRejectReservationWithPastStartDate() {
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);

        assertThrows(IllegalArgumentException.class, () -> {
            service.reserve(CarType.SEDAN, pastDate, 2);
        });
    }
}