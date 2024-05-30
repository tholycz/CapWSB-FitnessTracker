package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * Repository interface for accessing and managing User entities.
 */
interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();
    }

    /**
     * Finds all users and returns their basic information.
     *
     * @return A list of {@link UserBasicInfo} objects containing basic information about each user.
     */
    default List<UserBasicInfo> findAllUserBasicInfo(){
        return findAll().stream()
                .map(user -> new UserBasicInfo(user.getId(), user.getLastName()))
                .toList();
    }

    /**
     * Finds users whose email addresses contain the specified fragment.
     *
     * @param emailFragment The fragment of the email address to search for.
     * @return A list of {@link UserEmailInfo} objects containing information about users whose email addresses match the fragment.
     */
    default List<UserEmailInfo> findByEmailContaining(String emailFragment) {
        return findAll().stream()
                .filter(user -> user.getEmail().contains(emailFragment))
                .map(user -> new UserEmailInfo(user.getId(), user.getEmail()))
                .toList();
    }

}
