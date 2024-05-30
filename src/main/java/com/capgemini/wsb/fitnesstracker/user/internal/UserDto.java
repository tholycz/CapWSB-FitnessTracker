package com.capgemini.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Record representing a DTO (Data Transfer Object) for a user.
 */
record UserDto(@Nullable Long Id, String firstName, String lastName,
               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
               String email) {

}
