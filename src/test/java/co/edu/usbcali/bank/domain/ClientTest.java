package co.edu.usbcali.bank.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Rollback(false)
class ClientTest {
	
	@PersistenceContext
	EntityManager entityManager;
	Long clientId = 4040L;

	@Test
	@DisplayName("Save")
	
	// Transactional indica que dede hacer el beginTransaction, Commit; rollback, closeTransaction
	@Transactional(readOnly = false)
	void aTest() {
		
		assertNotNull(entityManager);
		Client client = entityManager.find(Client.class, clientId);
		assertNull(client, "El Cliente Ya Existe");
		
		client = new Client();
		client.setClieId(clientId);
		client.setAdress("Calle C");
		client.setName("Hernesto Perez");
		client.setEmail("amgrim@hotmail.com");
		client.setEnable("S");
		client.setPhone("5550000");
		
		DocumentType documentType = entityManager.find(DocumentType.class, 1L);
		assertNotNull(documentType, "El DocumentType no existe");
		
		client.setDocumentType(documentType);
		
		entityManager.persist(client);
	}
	
	@Test
	@DisplayName("find_by")
	@Transactional(readOnly = false)
	void bTest() {
		
		assertNotNull(entityManager);
		Client client = entityManager.find(Client.class, clientId);
		assertNotNull(client, "El Cliente No Existe");
	}
	
	@Test
	@DisplayName("Update")
	@Transactional(readOnly = false)
	void cTest() {
		
		assertNotNull(entityManager);
		Client client = entityManager.find(Client.class, clientId);
		assertNotNull(client, "El Cliente No Existe");
		
		client.setName("Prensejo Pendejo jr");
		
		entityManager.merge(client);
	}
	
	@Test
	@DisplayName("delete")
	@Transactional(readOnly = false)
	void dTest() {
		
		assertNotNull(entityManager);
		Client client = entityManager.find(Client.class, clientId);
		assertNotNull(client, "El Cliente NO Existe");
				
		entityManager.remove(client);
	}

}
