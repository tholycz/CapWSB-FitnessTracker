package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.exception.api.NotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing trainings.
 */
@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;

    private final TrainingMapper trainingMapper;
    private final UserServiceImpl userService;


    /**
     * Retrieves all trainings.
     *
     * @return a list of {@link TrainingDto} representing all trainings
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings().stream().map(trainingMapper::toDto).toList();
    }

    /**
     * Creates a new training.
     *
     * @param trainingDto the training data to create a new training
     * @return the created {@link TrainingDto}
     * @throws InterruptedException if the thread is interrupted
     */
    @PostMapping
    public TrainingDto addTraining(@RequestBody TrainingDto trainingDto) throws InterruptedException {
    Training training = trainingMapper.toEntity(trainingDto);
    Training createdTraining = trainingService.createTraining(training);
        return new TrainingDto(createdTraining.getId(), createdTraining.getUser(), createdTraining.getStartTime(), createdTraining.getEndTime(), createdTraining.getActivityType(), createdTraining.getDistance(), createdTraining.getAverageSpeed());
    }

    /**
     * Retrieves all trainings for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of {@link TrainingDto} representing the trainings of the user
     */
    @GetMapping("/user/{userId}")
    public List<TrainingDto> getTrainingsByUserId(@PathVariable Long userId) {
        User user = userService.getUser(userId).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        List<Training> trainings = trainingService.findTrainingsByUser(user);

        return trainings.stream().map(trainingMapper::toDto).toList();
    }


    /**
     * Retrieves all trainings completed after a specific date.
     *
     * @param date the date to filter trainings
     * @return a list of {@link TrainingDto} representing the trainings completed after the date
     */
    @GetMapping("/completed")
    public List<TrainingDto> getTrainingsCompletedAfterDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        List<Training> trainings = trainingService.findTrainingsCompletedAfterDate(date);

        return trainings.stream().map(trainingMapper::toDto).toList();
    }

    /**
     * Retrieves all trainings for a specific activity type.
     *
     * @param activityType the type of activity
     * @return a list of {@link TrainingDto} representing the trainings of the specified activity type
     */
    @GetMapping("/activity")
    public List<TrainingDto> getTrainingsByActivity(@RequestParam("activityType") ActivityType activityType) {
        List<Training> trainings = trainingService.findTrainingsByActivity(activityType);

        return trainings.stream().map(trainingMapper::toDto).toList();
    }

    /**
     * Updates the distance of a specific training.
     *
     * @param trainingId the ID of the training to update
     * @param trainingDto the training data with the new distance
     * @return a {@link ResponseEntity} containing the updated {@link TrainingDto}, or a {@link ResponseEntity} with status 404 if the training is not found
     */
    @PutMapping("/{trainingId}/distance")
    public ResponseEntity<TrainingDto> updateTrainingDistance(@PathVariable Long trainingId, @RequestBody TrainingDto trainingDto) {
        Optional<Training> updatedTrainingOptional = trainingService.updateTrainingDistance(trainingId, trainingDto.getDistance());
        if (updatedTrainingOptional.isPresent()) {
            TrainingDto updatedTrainingDto = trainingMapper.toDto(updatedTrainingOptional.get());
            return ResponseEntity.ok(updatedTrainingDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
