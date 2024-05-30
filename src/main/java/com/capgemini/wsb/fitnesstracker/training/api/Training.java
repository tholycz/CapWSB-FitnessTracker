package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Represents a training session of a user.
 */
@Entity
@Table(name = "trainings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Training {

    /**
     * The unique identifier of the training session.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who performed the training.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The start time of the training session.
     */
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    /**
     * The end time of the training session.
     */
    @Column(name = "end_time", nullable = false)
    private Date endTime;

    /**
     * The type of activity performed during the training session.
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;

    /**
     * The distance covered during the training session, in kilometers.
     */
    @Column(name = "distance")
    @Setter
    private double distance;

    /**
     * The average speed during the training session, in kilometers per hour.
     */
    @Column(name = "average_speed")
    private double averageSpeed;

    /**
     * Constructs a new Training instance.
     *
     * @param user the user who performed the training
     * @param startTime the start time of the training session
     * @param endTime the end time of the training session
     * @param activityType the type of activity performed
     * @param distance the distance covered during the training session
     * @param averageSpeed the average speed during the training session
     */
    public Training(
            final User user,
            final Date startTime,
            final Date endTime,
            final ActivityType activityType,
            final double distance,
            final double averageSpeed) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }
}