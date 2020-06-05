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

import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.domain.DocumentType;

// indica que es una calse Junit combinada con Spring
@SpringBootTest
// el rollback false, indica que no debe de hacer rollback al finalzar el metodo
@Rollback(false)
class ClientRepositoryTest {

	//Siempre se recomienda usar el Log de slf4j, para el manejo de los LOGS
	final static Logger log = LoggerFactory.getLogger(ClientRepositoryTest.class);

	// El AutoWired indica al Spting que debe realizar internamente el new inicializar la variable
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	DocumentTypeRepository documentTypeRepository;
	Long clientId = 4040L;
	

	// el @Test indica al JUnit que es un metod Test que se debe de ejecutar
	@Test
	// el @DisplayName indica al JUnit el nombre de la prueba que se debe desplegar
	@DisplayName("Save")
	void atest() {
		log.info("Se ejecuto el Test Save");
		
		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<Client> clientOptional = clientRepository.findById(clientId);
		// funcional
		assertFalse(clientOptional.isPresent(), "El cliente ya existe");
		
		Client client = new Client();
		client.setClieId(clientId);
		client.setAdress("Calle C");
		client.setName("Hernesto Perez");
		client.setEmail("amgrim@hotmail.com");
		client.setEnable("S");
		client.setPhone("5550000");
		
		Optional<DocumentType> documentTypeOptional = documentTypeRepository.findById(1L);
		assertTrue(documentTypeOptional.isPresent(), "El documento existe");
		
		client.setDocumentType(documentTypeOptional.get());
		
		clientRepository.save(client);
	}
	
	@Test
	@DisplayName("findByID")
	@Transactional(readOnly = false)
	void btest() {
		
		log.info("Se ejecuto el Test find_by_ID");
		
		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<Client> clientOptional = clientRepository.findById(clientId);
		// funcional
		assertTrue(clientOptional.isPresent(), "El cliente existe");
	}
	
	@Test
	@DisplayName("Update")
	@Transactional(readOnly = false)
	void ctest() {
		
		log.info("Se ejecuto el Test Update");
		
		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<Client> clientOptional = clientRepository.findById(clientId);
		// funcional
		assertTrue(clientOptional.isPresent(), "El cliente no existe");
		
		// Programacacion interactiva
		clientOptional.ifPresent(client ->{
			client.setName("Perensejo Pendejo jr");
			clientRepository.save(client);
		}
		);
	}
	
	@Test
	@DisplayName("Delete")
	@Transactional(readOnly = false)
	void dtest() {
		
		log.info("Se ejecuto el Test Delete");
		
		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<Client> clientOptional = clientRepository.findById(clientId);
		// funcional
		assertTrue(clientOptional.isPresent(), "El cliente no existe");
		
		// Programacacion interactiva
		clientOptional.ifPresent(client ->{
			clientRepository.delete(client);
		});
	}

	// el beforeEach indica al JUnit que de ejecutar este metodo antes de cada metodo @test que se ejecute
	@BeforeEach
	void beforeEach()
	{
		log.info("Se ejecuto el metodo beforeEach");
		assertNotNull(clientRepository, "el clientRepository es null");
		assertNotNull(documentTypeRepository, "el DocumentTypeRepository es null");
	}

	// el afterEach indica al JUnit que de ejecutar este metodo despues de cada metodo @test que se ejecute
	@AfterEach
	void afterEach()
	{	
		log.info("Se ejecuto el metodo afterEach");
	}

}
