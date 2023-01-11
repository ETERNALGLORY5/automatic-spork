package evon.tech.learning.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import evon.tech.learning.repository.UserRepository;
import evon.tech.learning.services.UserService;
//import lombok.Value;
import springfox.documentation.swagger2.mappers.ModelMapper;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository ;
    
    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String imagePath;
}
