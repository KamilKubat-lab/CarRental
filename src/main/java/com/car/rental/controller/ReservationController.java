package com.car.rental.controller;


import com.car.rental.dto.ReservationRequest;
import com.car.rental.exception.NoAvailableCarException;
import com.car.rental.model.Reservation;
import com.car.rental.service.CarRentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final CarRentalService service;


    public ReservationController(CarRentalService service) {
        this.service = service;
    }

    @Operation(
            summary = "Car reservation",
            description = "Reserves a car of a given type for a specified number of days",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reservation created",
                            content = @Content(schema = @Schema(implementation = Reservation.class))),
                    @ApiResponse(responseCode = "409", description = "No available cars"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    public ResponseEntity<Reservation> reserve(@RequestBody ReservationRequest req) {
        Reservation r = service.reserve(req.getCarType(), req.getStart(), req.getDays());
        return ResponseEntity.status(HttpStatus.CREATED).body(r);
    }


    @ExceptionHandler(NoAvailableCarException.class)
    public ResponseEntity<String> handleNoAvailable(NoAvailableCarException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadArg(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}