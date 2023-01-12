package evon.tech.learning.repositories;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import evon.tech.learning.entities.User;




public interface UserRepository extends JpaRepository<User, String>
{
    Optional<User> findByEmail(String email);
    Optional<User> findBYEmailAndPassword(String email,String password);
    
    List<User> findByNameContaining(String keywords);
   
}
