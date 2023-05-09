package org.example.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    Optional<User> findUserById(Long id);

    Page<User> findUsersByRolesIsIn(Set<Role> roles, Pageable pageable);

    Page<User> findUsersByRolesInAndUsernameContainsIgnoreCaseOrRolesInAndFullnameContainsIgnoreCase(Set<Role> roles, String username, Set<Role> roles2, String fullname, Pageable pageable);

    Optional<User> findUserByActivationCode(String activationCode);
}
