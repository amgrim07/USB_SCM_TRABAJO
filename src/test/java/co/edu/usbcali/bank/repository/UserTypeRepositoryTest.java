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

// indica que es una calse Junit combinada con Spring
@SpringBootTest
// el rollback false, indica que no debe de hacer rollback al finalzar el metodo
@Rollback(false)
class UserTypeRepositoryTest {

	//Siempre se recomienda usar el Log de slf4j, para el manejo de los LOGS
	final static Logger log = LoggerFactory.getLogger(UserTypeRepositoryTest.class);

	@Autowired
	UserTypeRepository userTypeRepository;
	static Long userTypeID = 0L;

	// el @Test indica al JUnit que es un metod Test que se debe de ejecutar
	@Test
	// el @DisplayName indica al JUnit el nombre de la prueba que se debe desplegar
	@DisplayName("Save")
	@Transactional(readOnly = false)
	void atest() {
		log.info("Se ejecuto el Test Save");

		Optional<UserType> userTypeOptional = userTypeRepository.findById(userTypeID);
		assertFalse(userTypeOptional.isPresent(), "El registeredAccount ya existe");

		UserType UserType = new UserType();

		UserType.setUstyId(userTypeID);
		UserType.setEnable("S");
		UserType.setName("User Bank");

		UserType = userTypeRepository.save(UserType);
		userTypeID = UserType.getUstyId();

		log.info("Id "+userTypeID);
	}

	@Test
	@DisplayName("findByID")
	@Transactional(readOnly = false)
	void btest() {

		log.info("Se ejecuto el Test find_by_ID");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<UserType> userTypeOptional = userTypeRepository.findById(userTypeID);
		// funcional
		assertTrue(userTypeOptional.isPresent(), "El UserType existe");
	}

	@Test
	@DisplayName("Update")
	@Transactional(readOnly = false)
	void ctest() {

		log.info("Se ejecuto el Test Update");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<UserType> userTypeOptional = userTypeRepository.findById(userTypeID);
		// funcional
		assertTrue(userTypeOptional.isPresent(), "La UserType no existe");

		// Programacacion interactiva
		userTypeOptional.ifPresent(UserType ->{
			UserType.setEnable("N");
			UserType.setName("Modificacion de User Type");
			userTypeRepository.save(UserType);
		}
				);
	}

	@Test
	@DisplayName("Delete")
	@Transactional(readOnly = false)
	void dtest() {

		log.info("Se ejecuto el Test Delete");

		Optional<UserType> UserTypeOptional = userTypeRepository.findById(userTypeID);
		// funcional
		assertTrue(UserTypeOptional.isPresent(), "La UserType no existe");

		// Programacacion interactiva
		UserTypeOptional.ifPresent(UserType ->{
			userTypeRepository.delete(UserType);
		}
				);
	}

	// el beforeEach indica al JUnit que de ejecutar este metodo antes de cada metodo @test que se ejecute
	@BeforeEach
	void beforeEach()
	{
		log.info("Se ejecuto el metodo beforeEach");
		assertNotNull(userTypeRepository, "el userTypeRepository es null");
	}

	// el afterEach indica al JUnit que de ejecutar este metodo despues de cada metodo @test que se ejecute
	@AfterEach
	void afterEach()
	{	
		log.info("Se ejecuto el metodo afterEach");
	}

}
