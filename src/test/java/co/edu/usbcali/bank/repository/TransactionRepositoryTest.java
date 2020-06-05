package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
class TransactionRepositoryTest {

	//Siempre se recomienda usar el Log de slf4j, para el manejo de los LOGS
	final static Logger log = LoggerFactory.getLogger(TransactionRepositoryTest.class);

	@Autowired
	TransactionRepository transactionRepository;
	static Long transactionID = 0L;

	@Autowired
	TransactionTypeRepository transactionTypeRepository;
	Long transactionTypeID = 1L;
	
	@Autowired
	AccountRepository accountRepository;
	String accountId = "4640-0341-9387-5781";
	
	@Autowired
	UsersRepository userRepository;
	String userId = "cfaier0@cafepress.com";

	// el @Test indica al JUnit que es un metod Test que se debe de ejecutar
	@Test
	// el @DisplayName indica al JUnit el nombre de la prueba que se debe desplegar
	@DisplayName("Save")
	@Transactional(readOnly = false)
	void atest() {
		log.info("Se ejecuto el Test Save");

		Optional<Transaction> transactionOptional = transactionRepository.findById(transactionID);
		assertFalse(transactionOptional.isPresent(), "El registeredAccount ya existe");
		
		Optional<TransactionType> transactionTypeOptional = transactionTypeRepository.findById(transactionTypeID);
		assertTrue(transactionTypeOptional.isPresent(), "El TransactionType existe");
		
		Optional<Account> accountOptional = accountRepository.findById(accountId);
		assertTrue(accountOptional.isPresent(), "La Cuenta no existe");
		
		Optional<Users> usersOptional = userRepository.findById(userId);
		assertTrue(usersOptional.isPresent(), "El Users no existe");

		Transaction transaction = new Transaction();

		transaction.setAccount(accountOptional.get());
		transaction.setAmount(200000D);
		transaction.setDate(new Date());
		transaction.setTransactionType(transactionTypeOptional.get());
		transaction.setUsers(usersOptional.get());
		
		transaction = transactionRepository.save(transaction);
		transactionID = transaction.getTranId();
		
		log.info("Id "+transactionID);
	}

	@Test
	@DisplayName("findByID")
	@Transactional(readOnly = false)
	void btest() {

		log.info("Se ejecuto el Test find_by_ID");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<Transaction> transactionOptional = transactionRepository.findById(transactionID);
		// funcional
		assertTrue(transactionOptional.isPresent(), "El Transaction no existe");
	}

	@Test
	@DisplayName("Update")
	@Transactional(readOnly = false)
	void ctest() {

		log.info("Se ejecuto el Test Update");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<Transaction> transactionOptional = transactionRepository.findById(transactionID);
		// funcional
		assertTrue(transactionOptional.isPresent(), "La Transaction no existe");

		// Programacacion interactiva
		transactionOptional.ifPresent(transaction ->{
			transaction.setAmount(10000D);
			transaction.setDate(new Date());
			transactionRepository.save(transaction);
		}
				);
	}

	@Test
	@DisplayName("Delete")
	@Transactional(readOnly = false)
	void dtest() {

		log.info("Se ejecuto el Test Delete");

		Optional<Transaction> transactionOptional = transactionRepository.findById(transactionID);
		// funcional
		assertTrue(transactionOptional.isPresent(), "La Transaction no existe");

		// Programacacion interactiva
		transactionOptional.ifPresent(transaction ->{
			transactionRepository.delete(transaction);
		}
				);
	}

	// el beforeEach indica al JUnit que de ejecutar este metodo antes de cada metodo @test que se ejecute
	@BeforeEach
	void beforeEach()
	{
		log.info("Se ejecuto el metodo beforeEach");
		assertNotNull(transactionRepository, "el transactionRepository es null");
	}

	// el afterEach indica al JUnit que de ejecutar este metodo despues de cada metodo @test que se ejecute
	@AfterEach
	void afterEach()
	{	
		log.info("Se ejecuto el metodo afterEach");
	}

}
