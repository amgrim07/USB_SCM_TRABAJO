package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.domain.RegisteredAccount;

// indica que es una calse Junit combinada con Spring
@SpringBootTest
// el rollback false, indica que no debe de hacer rollback al finalzar el metodo
@Rollback(false)
class RegisteredAccountServiceTest {

	//Siempre se recomienda usar el Log de slf4j, para el manejo de los LOGS
	final static Logger log = LoggerFactory.getLogger(RegisteredAccountServiceTest.class);

	@Autowired
	RegisteredAccountService registeredAccountService;
	static Long registeredAccountId = 0L;

	@Autowired
	AccountService accountService;
	String accountId = "4640-0341-9387-5781";

	@Autowired
	ClientService clientService;
	Long clientId = 4L;

	@Test
	@DisplayName("Validate")
	void fail() {
		log.info("Se ejecuto el Test Validate");

		assertThrows(Exception.class, ()->{
			RegisteredAccount registeredAccount = new RegisteredAccount();
			registeredAccount.setEnable("S");
			registeredAccount.setReacId(registeredAccountId);

			registeredAccountService.save(registeredAccount);
		}
				);
	}

	// el @Test indica al JUnit que es un metod Test que se debe de ejecutar
	@Test
	// el @DisplayName indica al JUnit el nombre de la prueba que se debe desplegar
	@DisplayName("Save")
	@Transactional(readOnly = false)
	void atest() {
		log.info("Se ejecuto el Test Save");

		Optional<RegisteredAccount> registeredAccountOptional = registeredAccountService.findById(registeredAccountId);
		assertFalse(registeredAccountOptional.isPresent(), "El registeredAccount ya existe");

		Optional<Client> clientOptional = clientService.findById(clientId);
		assertTrue(clientOptional.isPresent(), "El client no existe");

		Optional<Account> accountOptional = accountService.findById(accountId);
		assertTrue(accountOptional.isPresent(), "La Cuenta no existe");

		RegisteredAccount registeredAccount = new RegisteredAccount();

		registeredAccount.setAccount(accountOptional.get());
		registeredAccount.setClient(clientOptional.get());
		registeredAccount.setEnable("S");
		registeredAccount.setReacId(registeredAccountId);

		try {
			registeredAccount = registeredAccountService.save(registeredAccount);
			registeredAccountId = registeredAccount.getReacId();
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}

		log.info("Id "+registeredAccount.getReacId());
	}

	@Test
	@DisplayName("findByID")
	@Transactional(readOnly = false)
	void btest() {

		log.info("Se ejecuto el Test find_by_ID");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<RegisteredAccount> registeredAccountOptional = registeredAccountService.findById(registeredAccountId);
		// funcional
		assertTrue(registeredAccountOptional.isPresent(), "La RegisteredAccount no existe");
	}

	@Test
	@DisplayName("Update")
	@Transactional(readOnly = false)
	void ctest() {

		log.info("Se ejecuto el Test Update");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<RegisteredAccount> registeredAccountOptional = registeredAccountService.findById(registeredAccountId);
		// funcional
		assertTrue(registeredAccountOptional.isPresent(), "La RegisteredAccount no existe");
		
		RegisteredAccount registeredAccount = registeredAccountOptional.get();
		registeredAccount.setEnable("N");
		
		try {
			registeredAccountService.update(registeredAccount);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	@Test
	@DisplayName("Delete")
	@Transactional(readOnly = false)
	void dtest() {

		log.info("Se ejecuto el Test Delete");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<RegisteredAccount> registeredAccountOptional = registeredAccountService.findById(registeredAccountId);
		// funcional
		assertTrue(registeredAccountOptional.isPresent(), "La RegisteredAccount no existe");

		RegisteredAccount registeredAccount = registeredAccountOptional.get();
		
		try {
			registeredAccountService.delete(registeredAccount);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	// el beforeEach indica al JUnit que de ejecutar este metodo antes de cada metodo @test que se ejecute
	@BeforeEach
	void beforeEach()
	{
		log.info("Se ejecuto el metodo beforeEach");
		assertNotNull(registeredAccountService, "el registeredAccountService es null");
		assertNotNull(clientService, "el clientService es null");
		assertNotNull(accountService, "el accountService es null");
	}

	// el afterEach indica al JUnit que de ejecutar este metodo despues de cada metodo @test que se ejecute
	@AfterEach
	void afterEach()
	{	
		log.info("Se ejecuto el metodo afterEach");
	}

}
