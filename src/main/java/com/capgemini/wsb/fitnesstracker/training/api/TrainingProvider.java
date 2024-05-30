package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<User> getTraining(Long trainingId);

    /**
     * Retrieves a list of trainings associated with a specific user.
     *
     * @param user the user whose trainings are to be retrieved
     * @return a list of trainings associated with the specified user
     */
    List<Training> findTrainingsByUser(User user);

    /**
     * Retrieves a list of trainings that were completed after a specified date.
     *
     * @param date the date after which completed trainings should be retrieved
     * @return a list of trainings completed after the specified date
     */
    List<Training> findTrainingsCompletedAfterDate(Date date);

    /**
     * Retrieves a list of trainings for a specified activity type.
     *
     * @param activityType the type of activity for which trainings are to be retrieved
     * @return a list of trainings for the specified activity type
     */
    List<Training> findTrainingsByActivity(ActivityType activityType);

    /**
     * Updates the distance of a specific training.
     *
     * @param trainingId the ID of the training to be updated
     * @param newDistance the new distance to be set for the training
     * @return an {@link Optional} containing the updated Training, or {@link Optional#empty()} if the training was not found
     */
    Optional<Training> updateTrainingDistance(Long trainingId, double newDistance);


}
