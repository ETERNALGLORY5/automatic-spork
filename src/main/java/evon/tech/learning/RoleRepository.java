package evon.microservices.userauth.repository;

import evon.api.userauth.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findRoleByName(String name);
}
