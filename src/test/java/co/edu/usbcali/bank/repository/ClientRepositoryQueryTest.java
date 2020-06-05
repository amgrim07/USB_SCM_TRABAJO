package co.edu.usbcali.bank.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.usbcali.bank.domain.Client;

@SpringBootTest
class ClientRepositoryQueryTest {
	
	final static Logger log = LoggerFactory.getLogger(ClientRepositoryQueryTest.class); 
	
	@Autowired
	ClientRepository clienteRepository;

	@Test
	void findAll() {
		
		List<Client> clients = clienteRepository.findAll();
		
		clients.forEach(client->log.info(client.getName()));
	}
	
	@Test
	void findByEmailTest()
	{
		List<Client> clients = clienteRepository.findByEmail("hdownes0@bloomberg.com");
		clients.forEach(client->log.info(client.getName()+" - "+ client.getEmail()));
	}
	
	@Test
	void findByNameLikeTest()
	{
		List<Client> clients = clienteRepository.findByNameLike("%j%");
		clients.forEach(client->log.info(client.getName()+" - "+ client.getEmail()));
	}
	
	@Test
	void findWithTwoAccountsTest()
	{
		List<Client> clients = clienteRepository.findWithTwoAccounts();
		clients.forEach(client->log.info(client.getName()+" - "+ client.getEmail()));
	}

}
