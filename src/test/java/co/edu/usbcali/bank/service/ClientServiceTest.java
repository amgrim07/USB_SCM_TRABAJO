package co.edu.usbcali.bank.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.domain.DocumentType;

@SpringBootTest
@Rollback(false)
class ClientServiceTest {

	static final Logger log = LoggerFactory.getLogger(ClientServiceTest.class);

	Long clientId = 6060L;

	@Autowired
	ClientService clientService;
	
	@Autowired
	DocumentTypeService documentTypeService;
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Test
	void context() {
		assertNotNull(applicationContext);
		ClientService cliSer=(ClientService)applicationContext.getBean(ClientServiceImpl.class);
		assertNotNull(cliSer, "El cliSer es nulo");
	}

	
	@Test
	@DisplayName("Validate")
	void fail() {
		log.info("Se ejecuto el Test Validate");
		
		assertThrows(Exception.class, ()->{
				Client client = new Client();
				client.setAdress("CRA");
				client.setClieId(clientId);
				client.setEmail("correo@correo.com");
				client.setEnable("S");
				client.setName("");
				client.setPhone("");
				
				clientService.save(client);
			}
		);
	}
	
	@Test
	@DisplayName("Save")
	void aTest()
	{
		Client client = new Client();
		client.setAdress("CALLE C # 34-89");
		client.setClieId(clientId);
		client.setEmail("correo@correo.com");
		client.setEnable("S");
		client.setName("Perensejo cejo");
		client.setPhone("7775555");
		
		Optional<DocumentType> documentTypeOptoinal = documentTypeService.findById(1L);
		
		assertTrue(documentTypeOptoinal.isPresent(), "El documento no existe");
		
		client.setDocumentType(documentTypeOptoinal.get());
		try {
			clientService.save(client);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}
	
	@Test
	@DisplayName("findById")
	void bTest()
	{
		Optional<Client> clientOptional = clientService.findById(clientId);
		assertTrue(clientOptional.isPresent(), "El cliente No existe");
	} 
	
	@Test
	@DisplayName("Upate")
	void cTest()
	{
		Optional<Client> clientOptional = clientService.findById(clientId);
		assertTrue(clientOptional.isPresent(), "El cliente No existe");
		
		Client client = clientOptional.get();
		client.setAdress("CALLE C # 34-89 La Hacieda Norteña");
		client.setEmail("amgrim07@correo.com");
		client.setEnable("S");
		client.setPhone("+57 7775555");
		
		try {
			clientService.update(client);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}
	
	@Test
	@DisplayName("delete")
	void dTest()
	{
		Optional<Client> clientOptional = clientService.findById(clientId);
		assertTrue(clientOptional.isPresent(), "El cliente No existe");
		
		Client client = clientOptional.get();
		try {
			clientService.delete(client);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	} 

	/**
	 * Con el @BeforeAll indicamos al Junit que solo se debe ejecutar Una sola vez al inicio de la ejecucion
	 * de la prueba.
	 */
	/*@BeforeAll
	void before()
	{
		assertNotNull(clientService, "el clientService es nulo");
	}*/


}
