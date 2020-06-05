package co.edu.usbcali.bank.service;

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

// indica que es una calse Junit combinada con Spring
@SpringBootTest
// el rollback false, indica que no debe de hacer rollback al finalzar el metodo
@Rollback(false)
class AccountServiceTest {

	//Siempre se recomienda usar el Log de slf4j, para el manejo de los LOGS
	final static Logger log = LoggerFactory.getLogger(AccountServiceTest.class);

	@Autowired
	AccountService accountService;
	String accountId = "9999-9999-9999-9999";

	@Autowired
	ClientService clientService;
	Long clientId = 1L;

	@Test
	@DisplayName("Validate")
	void fail() {
		log.info("Se ejecuto el Test Validate");

		assertThrows(Exception.class, ()->{
			Account account = new Account();
			account.setAccoId(accountId);
			account.setBalance(3000000D);
			account.setEnable("S");
			account.setPassword("0000");
			account.setVersion(1L);

			accountService.save(account);
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

		Account account = new Account();
		account.setAccoId(accountId);
		account.setBalance(3000000D);
		account.setEnable("S");
		account.setPassword("0000");
		account.setVersion(1L);

		Optional<Client> clientOptional = clientService.findById(clientId);
		assertTrue(clientOptional.isPresent(), "El client existe");

		account.setClient(clientOptional.get());

		try {
			account = accountService.save(account);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
		log.info("Id "+account.getAccoId());
	}

	@Test
	@DisplayName("findByID")
	@Transactional(readOnly = false)
	void btest() {

		log.info("Se ejecuto el Test find_by_ID");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<Account> AccountOptional = accountService.findById(accountId);
		// funcional
		assertTrue(AccountOptional.isPresent(), "La Cuenta no existe");
	}

	@Test
	@DisplayName("Update")
	@Transactional(readOnly = false)
	void ctest() {

		log.info("Se ejecuto el Test Update");

		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<Account> accountOptional = accountService.findById(accountId);
		// funcional
		assertTrue(accountOptional.isPresent(), "El Accounte no existe");
		
		Account account = accountOptional.get();
		account.setPassword("NUIII");
		
		try {
			accountService.update(account);
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
		Optional<Account> AccountOptional = accountService.findById(accountId);
		// funcional
		assertTrue(AccountOptional.isPresent(), "El Accounte no existe");

		Account account = AccountOptional.get();
		try {
			accountService.delete(account);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	// el beforeEach indica al JUnit que de ejecutar este metodo antes de cada metodo @test que se ejecute
	@BeforeEach
	void beforeEach()
	{
		log.info("Se ejecuto el metodo beforeEach");
		assertNotNull(accountService, "el accountRepository es null");
	}

	// el afterEach indica al JUnit que de ejecutar este metodo despues de cada metodo @test que se ejecute
	@AfterEach
	void afterEach()
	{	
		log.info("Se ejecuto el metodo afterEach");
	}

}
