package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.UserType;
import co.edu.usbcali.bank.domain.Users;

// indica que es una calse Junit combinada con Spring
@SpringBootTest
// el rollback false, indica que no debe de hacer rollback al finalzar el metodo
@Rollback(false)
class UserRepositoryTest {

	//Siempre se recomienda usar el Log de slf4j, para el manejo de los LOGS
	final static Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);

	// El AutoWired indica al Spting que debe realizar internamente el new inicializar la variable
	@Autowired
	UsersRepository userRepository;
	String userId = "amgrim07@hotmail.com";
	@Autowired
	UserTypeRepository userTypeRepository;

	// el @Test indica al JUnit que es un metod Test que se debe de ejecutar
	@Test
	// el @DisplayName indica al JUnit el nombre de la prueba que se debe desplegar
	@DisplayName("Save")
	@Transactional(readOnly = false)
	void atest() {
		log.info("Se ejecuto el Test Save");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<Users> usersOptional = userRepository.findById(userId);
		// funcional
		assertFalse(usersOptional.isPresent(), "El usuario ya existe");
		
		Users users = new Users();
		users.setEnable("S");
		users.setName("amgrim07");
		users.setUserEmail(userId);
		

		Optional<UserType> userTypeOptional = userTypeRepository.findById(1L);
		assertTrue(userTypeOptional.isPresent(), "El userType existe");

		users.setUserType(userTypeOptional.get());
		
		userRepository.save(users);

	}

	@Test
	@DisplayName("findByID")
	@Transactional(readOnly = false)
	void btest() {

		log.info("Se ejecuto el Test find_by_ID");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<Users> usersOptional = userRepository.findById(userId);
		// funcional
		assertTrue(usersOptional.isPresent(), "El Users no existe");
	}

	@Test
	@DisplayName("Update")
	@Transactional(readOnly = false)
	void ctest() {

		log.info("Se ejecuto el Test Update");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<Users> usersOptional = userRepository.findById(userId);
		// funcional
		assertTrue(usersOptional.isPresent(), "El Users no existe");

		// Programacacion interactiva
		usersOptional.ifPresent(client ->{
			client.setName("Perensejo Pendejo jr");
			userRepository.save(client);
		}
				);
	}

	@Test
	@DisplayName("Delete")
	@Transactional(readOnly = false)
	void dtest() {

		log.info("Se ejecuto el Test Delete");

		// el java.util.Optional se adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<Users> usersOptional = userRepository.findById(userId);
		// funcional
		assertTrue(usersOptional.isPresent(), "El users no existe");

		// Programacacion interactiva
		usersOptional.ifPresent(users ->{
			userRepository.delete(users);
		});
	}

	// el beforeEach indica al JUnit que de ejecutar este metodo antes de cada metodo @test que se ejecute
	@BeforeEach
	void beforeEach()
	{
		log.info("Se ejecuto el metodo beforeEach");
		assertNotNull(userRepository, "el userRepository es null");
		assertNotNull(userTypeRepository, "el userTypeRepository es null");
	}

	// el afterEach indica al JUnit que de ejecutar este metodo despues de cada metodo @test que se ejecute
	@AfterEach
	void afterEach()
	{	
		log.info("Se ejecuto el metodo afterEach");
	}

}
