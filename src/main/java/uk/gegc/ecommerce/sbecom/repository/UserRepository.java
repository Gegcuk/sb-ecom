package uk.gegc.ecommerce.sbecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gegc.ecommerce.sbecom.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
}
