package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.bank.domain.UserType;
import co.edu.usbcali.bank.domain.Users;

@SpringBootTest
@Rollback(false)
class UserServiceTest {

	static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

	String userEmail = "amgrim07@hotmail.com";

	@Autowired
	UserService userService;
	
	@Autowired
	UserTypeService userTypeService;
	
	@Test
	@DisplayName("Validate")
	void fail() {
		log.info("Se ejecuto el Test Validate");
		
		assertThrows(Exception.class, ()->{
				Users users = new Users();
				users.setEnable("S");
				users.setName("a");
				users.setUserEmail(userEmail);
				users.setUserType(null);
				
				userService.save(users);
			}
		);
	}
	
	@Test
	@DisplayName("Save")
	void aTest()
	{
		Users users = new Users();
		users.setEnable("S");
		users.setName("amgrim07");
		users.setUserEmail(userEmail);
		
		Optional<UserType> usersTypeOptoinal = userTypeService.findById(1L);
		
		assertTrue(usersTypeOptoinal.isPresent(), "El UserType no existe");
		
		users.setUserType(usersTypeOptoinal.get());
		try {
			userService.save(users);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}
	
	@Test
	@DisplayName("findById")
	void bTest()
	{
		Optional<Users> UsersOptional = userService.findById(userEmail);
		assertTrue(UsersOptional.isPresent(), "El Users No existe");
	} 
	
	@Test
	@DisplayName("Upate")
	void cTest()
	{
		Optional<Users> usersOptional = userService.findById(userEmail);
		assertTrue(usersOptional.isPresent(), "El Users No existe");
		
		Users users = usersOptional.get();
		users.setName("amgrim1987");
		
		try {
			userService.update(users);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}
	
	@Test
	@DisplayName("delete")
	void dTest()
	{
		Optional<Users> UsersOptional = userService.findById(userEmail);
		assertTrue(UsersOptional.isPresent(), "El Users No existe");
		
		Users Users = UsersOptional.get();
		try {
			userService.delete(Users);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	} 
}
