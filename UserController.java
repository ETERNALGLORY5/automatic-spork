package evon.tech.learning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import evon.tech.learning.dtos.ApiResponseMessage;
import evon.tech.learning.dtos.UserDto;
import evon.tech.learning.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
 
	// create
	/*
	 * we use ResponseEntity bcoz with data we also share the response, we use UserDto
	 *  to send the data and it's name will be createUser. since we require data so we
	 *  use UserDto with name userDto with @RequestBody.
	 *  
	 *  the method we require to create user is held with UserService so @Autowired it.
	 *  
	 *  
	 */
	
	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto)
	{
		UserDto user = userService.createUser(userDto);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
		//
	}
	
	//update
	/*
	 * we get data and userId which we have to update.
	 * we return what we update means UserDto.
	 * @PathVariable is used to fetch the value of {userId} into the variable "Sting
	 * userId" .
	 * the
	 * we get the new data in form of JSON in @RequestBody
	 */
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser
	     (@PathVariable("userId") String userId,
	    	@RequestBody UserDto userDto	 )
	{
		UserDto updatedUserDto = userService.updateUser(userDto, userId);
		return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
	}
	
	
	
	// DELETE
	/*we should not take String data type in ResponseEntity, will handle it later.
	 * since normal string is not the JSON data.
	 * since java method variable String userId and path variable {userId} are same 
	 * so we don't provide variable for PathVariable.
	 * 
	 */
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId)
	{
		userService.deleteUser(userId);
		ApiResponseMessage message =  ApiResponseMessage
				.builder()
				.message("User Is deleted successfully ")
				.success(true)
				.status(HttpStatus.OK)
				.build();
		return new ResponseEntity<ApiResponseMessage>(message ,HttpStatus.OK);
	}
	
	
	
	//GETALL
	/*we use list bcoz we get whole list of data
	 * 
	 */
	
	@GetMapping		
	public ResponseEntity<List<UserDto>> getAllUsers()
	{
		return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
	}
			
	
	
	//GET SINGLE USER BY ID
	/*
	 * 
	 */
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable String userId)
	{
		return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
	}
	
	
	
	
	// get Single by  Email
	
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email)
	{
		return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
	}
	
	
	//Search user
	/*
	 * if String and URL request value are same we don't required to provide value with
	 * @Pathvariable
	 * 
	 * when we search we get list of userDto so we use List<> .
	 */
	
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords)
	{
		return new ResponseEntity<>(userService.searchUser(keywords),HttpStatus.OK);
	}
}
