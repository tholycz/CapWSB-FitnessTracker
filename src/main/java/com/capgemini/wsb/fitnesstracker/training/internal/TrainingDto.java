package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Data Transfer Object for the Training entity.
 */
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainingDto {

    /**
     * The unique identifier of the training.
     */
    private Long id;

    /**
     * The user associated with the training.
     */
    private User user;

    /**
     * The start time of the training.
     */
    private Date startTime;

    /**
     * The end time of the training.
     */
    private Date endTime;

    /**
     * The type of activity performed during the training.
     */
    private ActivityType activityType;

    /**
     * The distance covered during the training.
     */
    private double distance;

    /**
     * The average speed during the training.
     */
    private double averageSpeed;
}
