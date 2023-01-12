package evon.tech.learning.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import evon.tech.learning.dtos.UserDto;
import evon.tech.learning.entities.User;
import evon.tech.learning.repositories.UserRepository;
import evon.tech.learning.services.UserService;

@Service

public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;

	public UserDto createUser(UserDto userDto) {
		//generate unique id in string format
		String userId = UUID.randomUUID().toString();
		userDto.setUserId(userId);
		//dto -> entity
		User user =dtoToEntity(userDto);
	    User savedUser = userRepository.save(user);
	    //entity -> dto
	    UserDto newDto = entityToDto(savedUser);
		return newDto;
	}

	
	public UserDto updateUser(UserDto userDto, String userId) {
		
		User user = userRepository.findById(userId).orElseThrow(( ) -> new RuntimeException("User not found by thi s Id"));
		
		user.setName(userDto.getName());
		user.setGender(userDto.getGender());
		user.setPassword(userDto.getPassword());
		user.setImagename(userDto.getImagename());
		
		
		//save data
		
		User updatedUser = userRepository.save(user);
		UserDto updatedDto = entityToDto(updatedUser);
		return updatedDto;
	}

	@Override
	public void deleteUser(String userId) {
	  
		User user = userRepository.findById(userId).orElseThrow(( ) -> new RuntimeException("User not found by thi s Id"));
		//delete user
		
		userRepository.delete(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
     List<User> users  = userRepository.findAll();
    List<UserDto>  dtoList =  users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
     
		return dtoList;
	}

	@Override
	public UserDto getUserById(String userId) {
		User user = userRepository.findById(userId).orElseThrow(( ) -> new RuntimeException("User not found by thi s Id"));
		return entityToDto(user);
	}

	//very imp topic
	@Override
	public UserDto getUserByEmail(String email) {
	
	User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user not with given email ID and password"));
		return entityToDto(user);
	}

	public List<UserDto> searchUser(String keywords) {
	List<User> users=   userRepository.findByNameContaining(keywords);
	List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
		return dtoList;
	}
		
	
	private UserDto entityToDto(User savedUser) {
		
//	    UserDto userDto = UserDto.builder() 
//	             .userId(savedUser.getUserId())
//	             .name(savedUser.getName())
//	             .email(savedUser.getEmail())
//	             .password(savedUser.getPassword())
//	             .gender(savedUser.getGender())
//	             .imagename(savedUser.getImagename())
//	             .build();
		//return userDto;
		return mapper.map(savedUser,UserDto.class);
	}

	private User dtoToEntity(UserDto userDto) {
	
//		User user =  User.builder()
//		      .userId(userDto.getUserId())
//		      .name(userDto.getName())
//		      .email(userDto.getEmail())
//		      .password(userDto.getPassword())
//		      .gender(userDto.getGender())
//		      .imagename(userDto.getImagename())
//		      .build();
//		
//		return user;
		
		return mapper.map(userDto, User.class);   //(source , destination)
		
		
	}

}
