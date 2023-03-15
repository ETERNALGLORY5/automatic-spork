package evon.microservices.userauth.services;

import evon.api.userauth.models.User;
import evon.microservices.userauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppUserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetails = userRepository.findUserByUsername(username);

        if(userDetails.isEmpty())
            throw new UsernameNotFoundException("User with username: " + username +" not found !");
        else {
            User user = userDetails.get();
            System.out.println("user.getRoles() : " + user.getRoles());
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRoles()
                            .stream()
                            .map(role-> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toSet())
            );
        }
    }
}
