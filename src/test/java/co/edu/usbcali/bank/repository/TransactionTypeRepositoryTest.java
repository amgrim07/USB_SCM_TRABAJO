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

import co.edu.usbcali.bank.domain.TransactionType;

// indica que es una calse Junit combinada con Spring
@SpringBootTest
// el rollback false, indica que no debe de hacer rollback al finalzar el metodo
@Rollback(false)
class TransactionTypeRepositoryTest {

	//Siempre se recomienda usar el Log de slf4j, para el manejo de los LOGS
	final static Logger log = LoggerFactory.getLogger(TransactionTypeRepositoryTest.class);

	@Autowired
	TransactionTypeRepository transactionTypeRepository;
	static Long transactionTypeID = 0L;

	// el @Test indica al JUnit que es un metod Test que se debe de ejecutar
	@Test
	// el @DisplayName indica al JUnit el nombre de la prueba que se debe desplegar
	@DisplayName("Save")
	@Transactional(readOnly = false)
	void atest() {
		log.info("Se ejecuto el Test Save");

		Optional<TransactionType> transactionTypeOptional = transactionTypeRepository.findById(transactionTypeID);
		assertFalse(transactionTypeOptional.isPresent(), "El registeredAccount ya existe");

		TransactionType transactionType = new TransactionType();

		transactionType.setTrtyId(transactionTypeID);
		transactionType.setEnable("S");
		transactionType.setName("Transaccion Banck");

		transactionType = transactionTypeRepository.save(transactionType);
		transactionTypeID = transactionType.getTrtyId();

		log.info("Id "+transactionTypeID);
	}

	@Test
	@DisplayName("findByID")
	@Transactional(readOnly = false)
	void btest() {

		log.info("Se ejecuto el Test find_by_ID");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<TransactionType> transactionTypeOptional = transactionTypeRepository.findById(transactionTypeID);
		// funcional
		assertTrue(transactionTypeOptional.isPresent(), "El TransactionType existe");
	}

	@Test
	@DisplayName("Update")
	@Transactional(readOnly = false)
	void ctest() {

		log.info("Se ejecuto el Test Update");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<TransactionType> transactionTypeOptional = transactionTypeRepository.findById(transactionTypeID);
		// funcional
		assertTrue(transactionTypeOptional.isPresent(), "La TransactionType no existe");

		// Programacacion interactiva
		transactionTypeOptional.ifPresent(transactionType ->{
			transactionType.setEnable("N");
			transactionType.setName("Modificacion de Transaccion");
			transactionTypeRepository.save(transactionType);
		}
				);
	}

	@Test
	@DisplayName("Delete")
	@Transactional(readOnly = false)
	void dtest() {

		log.info("Se ejecuto el Test Delete");

		Optional<TransactionType> transactionTypeOptional = transactionTypeRepository.findById(transactionTypeID);
		// funcional
		assertTrue(transactionTypeOptional.isPresent(), "La TransactionType no existe");

		// Programacacion interactiva
		transactionTypeOptional.ifPresent(transactionType ->{
			transactionTypeRepository.delete(transactionType);
		}
				);
	}

	// el beforeEach indica al JUnit que de ejecutar este metodo antes de cada metodo @test que se ejecute
	@BeforeEach
	void beforeEach()
	{
		log.info("Se ejecuto el metodo beforeEach");
		assertNotNull(transactionTypeRepository, "el transactionTypeRepository es null");
	}

	// el afterEach indica al JUnit que de ejecutar este metodo despues de cada metodo @test que se ejecute
	@AfterEach
	void afterEach()
	{	
		log.info("Se ejecuto el metodo afterEach");
	}

}
