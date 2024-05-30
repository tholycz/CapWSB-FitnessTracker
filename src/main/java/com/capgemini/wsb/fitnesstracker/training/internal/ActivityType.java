package com.capgemini.wsb.fitnesstracker.training.internal;

/**
 * Enumeration representing different types of activities for training.
 */
public enum ActivityType {

    /**
     * Activity type representing running.
     */
    RUNNING("Running"),
    /**
     * Activity type representing cycling.
     */
    CYCLING("Cycling"),


    /**
     * Activity type representing walking.
     */
    WALKING("Walking"),

    /**
     * Activity type representing swimming.
     */
    SWIMMING("Swimming"),
    /**
     * Activity type representing tennis.
     */
    TENNIS("Tenis");

    private final String displayName;

    /**
     * Constructor for ActivityType.
     *
     * @param displayName the display name of the activity type
     */
    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the activity type.
     *
     * @return the display name of the activity type
     */
    public String getDisplayName() {
        return displayName;
    }

}
