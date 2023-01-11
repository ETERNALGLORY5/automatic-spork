package evon.tech.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import evon.tech.learning.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{

    User findByEmail(String username);
    
    
}
