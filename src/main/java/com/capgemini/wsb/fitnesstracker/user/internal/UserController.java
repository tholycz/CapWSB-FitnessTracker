package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling requests related to users.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    /**
     * Retrieves all users.
     *
     * @return A list of {@link UserDto} objects representing all users.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    /**
     * Adds a new user.
     *
     * @param userDto The user data to add.
     * @return The added user.
     * @throws InterruptedException If the operation is interrupted.
     */
    @PostMapping
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // Demonstracja how to use @RequestBody
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
        User user = userMapper.toEntity(userDto);
        return userService.createUser(user);
    }

    /**
     * Retrieves basic information about all users.
     *
     * @return A list of {@link UserBasicInfo} objects containing basic information about each user.
     */
    @GetMapping("/basic-info")
    public List<UserBasicInfo> getAllUserBasicInfo() {
        return userService.getAllUserBasicInfo();
    }

    /**
     * Retrieves basic information about all users.
     *
     * @return A list of {@link UserBasicInfo} objects containing basic information about each user.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<UserDto> userDto = userService.getUser(id).map(userMapper::toDto);
        return userDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return HTTP status 204 if successful, or 404 if the user is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Searches for users by email address.
     *
     * @param emailFragment The fragment of the email address to search for.
     * @return A list of {@link UserEmailInfo} objects containing matching users' email addresses.
     */
    @GetMapping("/search")
    public List<UserEmailInfo> searchUsersByEmail(@RequestParam String emailFragment) {
        return userService.searchUsersByEmail(emailFragment);
    }

    /**
     * Searches for users older than the specified age.
     *
     * @param age The minimum age of users to search for.
     * @return A list of {@link UserDto} objects representing users older than the specified age.
     */
    @GetMapping("/searchByAge")
    public List<UserDto> searchUsersOlderThan(@RequestParam int age) {
        return userService.searchUsersOlderThan(age);
    }

    /**
     * Updates the email address of a user.
     *
     * @param id       The ID of the user to update.
     * @param emailData data with the new email address.
     * @return The updated user, or 404 if the user is not found.
     */
    @PutMapping("/{id}/email")
    public ResponseEntity<UserDto> updateUserEmail(@PathVariable Long id, @RequestBody UserEmailInfo emailData) {
        Optional<User> updatedUser = userService.updateUserEmail(id, emailData.email());
        if (updatedUser.isPresent()) {
            return ResponseEntity.ok(updatedUser.map(userMapper::toDto).get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}