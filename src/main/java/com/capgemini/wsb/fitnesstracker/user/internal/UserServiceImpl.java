package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Implementation of the UserService and UserProvider interfaces.
 */
@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Creates a new user in the system.
     *
     * @param user The user to create.
     * @return The created user.
     * @throws IllegalArgumentException If the user already has a database ID.
     */
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return An {@link Optional} containing the retrieved user, or {@link Optional#empty()} if not found.
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return An {@link Optional} containing the retrieved user, or {@link Optional#empty()} if not found.
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieves all users in the system.
     *
     * @return A list of all users.
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves basic information about all users in the system.
     *
     * @return A list of {@link UserBasicInfo} objects containing basic information about each user.
     */
    public List<UserBasicInfo> getAllUserBasicInfo() {
        return userRepository.findAllUserBasicInfo();
    }

    /**
     * Deletes a user from the system.
     *
     * @param id The ID of the user to delete.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Searches for users whose email addresses contain the specified fragment.
     *
     * @param emailFragment The fragment of the email address to search for.
     * @return A list of {@link UserEmailInfo} objects containing information about matching users.
     */
    public List<UserEmailInfo> searchUsersByEmail(String emailFragment) {
        return userRepository.findByEmailContaining(emailFragment);
    }

    /**
     * Searches for users older than the specified age.
     *
     * @param age The age threshold.
     * @return A list of {@link UserDto} objects representing users older than the specified age.
     */
    public List<UserDto> searchUsersOlderThan(int age) {
        LocalDate dateThreshold = LocalDate.now().minusYears(age);
        List<User> users = userRepository.findAll();
        List<User> filteredUsers = users.stream()
                .filter(user -> user.getBirthdate().isBefore(dateThreshold))
                .toList();
        return filteredUsers.stream()
                .map(user -> new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getBirthdate(), user.getEmail()))
                .collect(Collectors.toList());
    }

    /**
     * Updates the email address of a user.
     *
     * @param id       The ID of the user to update.
     * @param newEmail The new email address.
     * @return An {@link Optional} containing the updated user, or {@link Optional#empty()} if not found.
     */
    public Optional<User> updateUserEmail(Long id, String newEmail) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmail(newEmail);
            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }


}