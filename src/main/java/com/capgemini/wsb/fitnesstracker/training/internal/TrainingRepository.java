package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Repository interface for {@link Training} entities.
 * Extends {@link JpaRepository} to provide CRUD operations.
 */
interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Finds all trainings for a specific user.
     *
     * @param user the user whose trainings are to be found
     * @return a list of trainings for the specified user
     */
    List<Training> findByUser(User user);

    /**
     * Finds all trainings that were completed after a specific date.
     *
     * @param date the date after which trainings were completed
     * @return a list of trainings completed after the specified date
     */
    List<Training> findByEndTimeAfter(Date date);

    /**
     * Finds all trainings of a specific activity type.
     *
     * @param activityType the activity type of the trainings to be found
     * @return a list of trainings of the specified activity type
     */
    List<Training> findByActivityType(ActivityType activityType);
}
