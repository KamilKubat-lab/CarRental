package com.car.rental.dto;

import com.car.rental.model.CarType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@Schema(description = "Car reservation request")
public class ReservationRequest {

    @Schema(description = "Type of car to reserve", example = "SEDAN")
    private CarType carType;

    @Schema(description = "Start date and time of the reservation", example = "2025-11-10T10:00:00")
    private LocalDateTime start;

    @Schema(description = "Number of days for the reservation", example = "3")
    private int days;
}