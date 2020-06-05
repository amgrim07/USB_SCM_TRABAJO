package co.edu.usbcali.bank.service;

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

import co.edu.usbcali.bank.domain.DocumentType;

// indica que es una calse Junit combinada con Spring
@SpringBootTest
// el rollback false, indica que no debe de hacer rollback al finalzar el metodo
@Rollback(false)
class DocumentTypeServiceTest {

	//Siempre se recomienda usar el Log de slf4j, para el manejo de los LOGS
	final static Logger log = LoggerFactory.getLogger(DocumentTypeServiceTest.class);

	@Autowired
	DocumentTypeService documentTypeService;
	
	static Long documentTypeId = null;

	// el @Test indica al JUnit que es un metod Test que se debe de ejecutar
	@Test
	// el @DisplayName indica al JUnit el nombre de la prueba que se debe desplegar
	@DisplayName("Save")
	@Transactional(readOnly = false)
	void atest() {
		log.info("Se ejecuto el Test Save");
		
		DocumentType documentType = new DocumentType();
		documentType.setDotyId(0L);
		documentType.setName("TEST");
		documentType.setEnable("S");
				
		try {
			documentType = documentTypeService.save(documentType);
			log.info("Id "+documentType.getDotyId());
			documentTypeId = documentType.getDotyId();
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}

	}
	
	@Test
	@DisplayName("findByID")
	@Transactional(readOnly = false)
	void btest() {
		
		log.info("Se ejecuto el Test find_by_ID");
		
		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<DocumentType> documentTypeOptional = documentTypeService.findById(documentTypeId);
		// funcional
		assertTrue(documentTypeOptional.isPresent(), "El Documento existe");
	}
	
	@Test
	@DisplayName("Update")
	@Transactional(readOnly = false)
	void ctest() {
		
		log.info("Se ejecuto el Test Update");
		
		// el java.util.Optional es adicionado el Java 8 para realizar el manejo de los NullPointerException, esto es programacion Funcional
		Optional<DocumentType> documentTypeOptional = documentTypeService.findById(documentTypeId);
		// funcional
		assertTrue(documentTypeOptional.isPresent(), "El DocumentTypee existe");
		
		DocumentType documentType = documentTypeOptional.get();
		documentType.setName("NUIII");
		
		try {
			documentTypeService.update(documentType);
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
		Optional<DocumentType> DocumentTypeOptional = documentTypeService.findById(documentTypeId);
		// funcional
		assertTrue(DocumentTypeOptional.isPresent(), "El DocumentTypee existe");
		
		DocumentType documentType = DocumentTypeOptional.get();
		documentType.setName("NUIII");
		
		try {
			documentTypeService.delete(documentType);
		} catch (Exception e) {
			assertNull(e, e.getMessage());
		}
	}

	// el beforeEach indica al JUnit que de ejecutar este metodo antes de cada metodo @test que se ejecute
	@BeforeEach
	void beforeEach()
	{
		log.info("Se ejecuto el metodo beforeEach");
		assertNotNull(documentTypeService, "el DocumentTypeRepository es null");
	}

	// el afterEach indica al JUnit que de ejecutar este metodo despues de cada metodo @test que se ejecute
	@AfterEach
	void afterEach()
	{	
		log.info("Se ejecuto el metodo afterEach");
	}

}
