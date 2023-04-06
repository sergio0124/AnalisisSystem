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
    List<User> findUsersByUsernameContainsIgnoreCase(String loginPart);

    Optional<User> findUserById(Long id);

    List<User> findUsersByRolesIsIn(Set<Role> roles);

    List<User> findUsersByRolesIsInAndUsernameContainsIgnoreCase(Set<Role> roles, String username);

    Optional<User> findUserByActivationCode(String activationCode);
}
