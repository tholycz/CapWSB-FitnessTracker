package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing trainings.
 * Provides methods for retrieving and manipulating training data.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    /**
     * Retrieves a training based on its ID.
     * Not implemented yet.
     *
     * @param trainingId the ID of the training to retrieve
     * @return an {@link Optional} containing the training if found, or {@link Optional#empty()} if not found
     */
    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    /**
     * Finds all trainings for a specific user.
     *
     * @param user the user whose trainings are to be found
     * @return a list of trainings for the specified user
     */
    @Override
    public List<Training> findTrainingsByUser(User user) {
        return trainingRepository.findByUser(user);
    }

    /**
     * Finds all trainings that were completed after a specific date.
     *
     * @param date the date after which trainings were completed
     * @return a list of trainings completed after the specified date
     */
    @Override
    public List<Training> findTrainingsCompletedAfterDate(Date date) {
        return  trainingRepository.findByEndTimeAfter(date);
    }

    /**
     * Finds all trainings of a specific activity type.
     *
     * @param activityType the activity type of the trainings to be found
     * @return a list of trainings of the specified activity type
     */
    @Override
    public List<Training> findTrainingsByActivity(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }

    /**
     * Retrieves all trainings.
     *
     * @return a list of all trainings
     */
    public List<Training> findAllTrainings() { return trainingRepository.findAll(); }

    /**
     * Creates a new training.
     *
     * @param training the training to create
     * @return the created training
     * @throws IllegalArgumentException if the training already has a database ID
     */
    public Training createTraining(Training training) {
        if (training.getId() != null){
            throw new IllegalArgumentException("Training has already DB ID, update is not permitted!");
        }
        return trainingRepository.save(training);
    }

    /**
     * Updates the distance of a training.
     *
     * @param trainingId the ID of the training to update
     * @param newDistance the new distance to set
     * @return an {@link Optional} containing the updated training if successful, or {@link Optional#empty()} if the training was not found
     */
    @Override
    public Optional<Training> updateTrainingDistance(Long trainingId, double newDistance) {
        Optional<Training> trainingOptional = trainingRepository.findById(trainingId);
        if (trainingOptional.isPresent()) {
            Training training = trainingOptional.get();
            training.setDistance(newDistance);
            trainingRepository.save(training);
            return Optional.of(training);
        }
        return Optional.empty();
    }

}
