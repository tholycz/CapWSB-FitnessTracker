package com.capgemini.wsb.fitnesstracker.training.internal;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between {@link Training} and {@link TrainingDto}.
 */
@Component
class TrainingMapper {

    /**
     * Converts a {@link Training} entity to a {@link TrainingDto}.
     *
     * @param training the {@link Training} entity to convert
     * @return the converted {@link TrainingDto}
     */
    TrainingDto toDto (Training training){
        return new TrainingDto(training.getId(), training.getUser(), training.getStartTime(), training.getEndTime(), training.getActivityType(), training.getDistance(), training.getAverageSpeed());
    }

    /**
     * Converts a {@link TrainingDto} to a {@link Training} entity.
     *
     * @param trainingDto the {@link TrainingDto} to convert
     * @return the converted {@link Training} entity
     */
    Training toEntity(TrainingDto trainingDto){
        return new Training(trainingDto.getUser(), trainingDto.getStartTime(), trainingDto.getEndTime(), trainingDto.getActivityType(), trainingDto.getDistance(), trainingDto.getAverageSpeed());
    }
}
