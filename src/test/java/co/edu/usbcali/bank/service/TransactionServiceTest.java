package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
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

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Transaction;
import co.edu.usbcali.bank.domain.TransactionType;
import co.edu.usbcali.bank.domain.Users;

// indica que es una calse Junit combinada con Spring
@SpringBootTest
// el rollback false, indica que no debe de hacer rollback al finalzar el metodo
@Rollback(false)
class TransactionServiceTest {

	//Siempre se recomienda usar el Log de slf4j, para el manejo de los LOGS
	final static Logger log = LoggerFactory.getLogger(TransactionServiceTest.class);

	@Autowired
	TransactionService transactionService;
	static Long transactionID = 0L;

	@Autowired
	TransactionTypeService transactionTypeService;
	Long transactionTypeID = 1L;

	@Autowired
	AccountService accountService;
	String accountId = "4640-0341-9387-5781";

	@Autowired
	UserService userService;
	String userId = "cfaier0@cafepress.com";

	// el @Test indica al JUnit que es un metod Test que se debe de ejecutar
	@Test
	// el @DisplayName indica al JUnit el nombre de la prueba que se debe desplegar
	@DisplayName("Save")
	@Transactional(readOnly = false)
	void atest() {
		log.info("Se ejecuto el Test Save");

		Optional<Transaction> transactionOptional = transactionService.findById(transactionID);
		assertFalse(transactionOptional.isPresent(), "El registeredAccount ya existe");

		Optional<TransactionType> transactionTypeOptional = transactionTypeService.findById(transactionTypeID);
		assertTrue(transactionTypeOptional.isPresent(), "El TransactionType existe");

		Optional<Account> accountOptional = accountService.findById(accountId);
		assertTrue(accountOptional.isPresent(), "La Cuenta no existe");

		Optional<Users> usersOptional = userService.findById(userId);
		assertTrue(usersOptional.isPresent(), "El Users no existe");

		Transaction transaction = new Transaction();

		transaction.setAccount(accountOptional.get());
		transaction.setAmount(200000D);
		transaction.setDate(new Date());
		transaction.setTransactionType(transactionTypeOptional.get());
		transaction.setUsers(usersOptional.get());

		try {
			transaction = transactionService.save(transaction);
			transactionID = transaction.getTranId();
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
		log.info("Id "+transactionID);
	}

	@Test
	@DisplayName("findByID")
	@Transactional(readOnly = false)
	void btest() {

		log.info("Se ejecuto el Test find_by_ID");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<Transaction> transactionOptional = transactionService.findById(transactionID);
		// funcional
		assertTrue(transactionOptional.isPresent(), "El Transaction no existe");
	}

	@Test
	@DisplayName("Update")
	@Transactional(readOnly = false)
	void ctest() {

		log.info("Se ejecuto el Test Update");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<Transaction> transactionOptional = transactionService.findById(transactionID);
		// funcional
		assertTrue(transactionOptional.isPresent(), "La Transaction no existe");

		Transaction transaction = transactionOptional.get();
		transaction.setAmount(10000D);
		transaction.setDate(new Date());
		try {
			transactionService.update(transaction);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("Delete")
	@Transactional(readOnly = false)
	void dtest() {

		log.info("Se ejecuto el Test Delete");

		Optional<Transaction> transactionOptional = transactionService.findById(transactionID);
		// funcional
		assertTrue(transactionOptional.isPresent(), "La Transaction no existe");

		Transaction transaction = transactionOptional.get();
//		try {
//			transactionService.delete(transaction);
//		} catch (Exception e) {
//			assertNull(e, e.getMessage());
//		}
	}

	// el beforeEach indica al JUnit que de ejecutar este metodo antes de cada metodo @test que se ejecute
	@BeforeEach
	void beforeEach()
	{
		log.info("Se ejecuto el metodo beforeEach");
		assertNotNull(transactionService, "el transactionService es null");
	}

	// el afterEach indica al JUnit que de ejecutar este metodo despues de cada metodo @test que se ejecute
	@AfterEach
	void afterEach()
	{	
		log.info("Se ejecuto el metodo afterEach");
	}

}
