package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import co.edu.usbcali.bank.domain.TransactionType;

// indica que es una calse Junit combinada con Spring
@SpringBootTest
// el rollback false, indica que no debe de hacer rollback al finalzar el metodo
@Rollback(false)
class TransactionTypeServiceTest {

	//Siempre se recomienda usar el Log de slf4j, para el manejo de los LOGS
	final static Logger log = LoggerFactory.getLogger(TransactionTypeServiceTest.class);

	@Autowired
	TransactionTypeService transactionTypeService;
	static Long transactionTypeID = 0L;

	// el @Test indica al JUnit que es un metod Test que se debe de ejecutar
	@Test
	// el @DisplayName indica al JUnit el nombre de la prueba que se debe desplegar
	@DisplayName("Save")
	@Transactional(readOnly = false)
	void atest() {
		log.info("Se ejecuto el Test Save");

		Optional<TransactionType> transactionTypeOptional = transactionTypeService.findById(transactionTypeID);
		assertFalse(transactionTypeOptional.isPresent(), "El registeredAccount ya existe");

		TransactionType transactionType = new TransactionType();

		transactionType.setTrtyId(transactionTypeID);
		transactionType.setEnable("S");
		transactionType.setName("Transaccion Banck");

		try {
			transactionType = transactionTypeService.save(transactionType);
			transactionTypeID = transactionType.getTrtyId();
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
		log.info("Id "+transactionTypeID);
	}

	@Test
	@DisplayName("findByID")
	@Transactional(readOnly = false)
	void btest() {

		log.info("Se ejecuto el Test find_by_ID");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<TransactionType> transactionTypeOptional = transactionTypeService.findById(transactionTypeID);
		// funcional
		assertTrue(transactionTypeOptional.isPresent(), "El TransactionType existe");
	}

	@Test
	@DisplayName("Update")
	@Transactional(readOnly = false)
	void ctest() {

		log.info("Se ejecuto el Test Update");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<TransactionType> transactionTypeOptional = transactionTypeService.findById(transactionTypeID);
		// funcional
		assertTrue(transactionTypeOptional.isPresent(), "La TransactionType no existe");

		TransactionType transactionType = transactionTypeOptional.get();
		transactionType.setEnable("N");
		transactionType.setName("Modificacion de Transaccion");
		try {
			transactionTypeService.update(transactionType);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("Delete")
	@Transactional(readOnly = false)
	void dtest() {

		log.info("Se ejecuto el Test Delete");

		Optional<TransactionType> transactionTypeOptional = transactionTypeService.findById(transactionTypeID);
		// funcional
		assertTrue(transactionTypeOptional.isPresent(), "La TransactionType no existe");
		TransactionType transactionType = transactionTypeOptional.get();
		try {
			transactionTypeService.delete(transactionType);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	// el beforeEach indica al JUnit que de ejecutar este metodo antes de cada metodo @test que se ejecute
	@BeforeEach
	void beforeEach()
	{
		log.info("Se ejecuto el metodo beforeEach");
		assertNotNull(transactionTypeService, "el transactionTypeService es null");
	}

	// el afterEach indica al JUnit que de ejecutar este metodo despues de cada metodo @test que se ejecute
	@AfterEach
	void afterEach()
	{	
		log.info("Se ejecuto el metodo afterEach");
	}

}
