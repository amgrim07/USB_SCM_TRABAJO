package co.edu.usbcali.bank.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.repository.ClientRepository;
import co.edu.usbcali.bank.repository.DocumentTypeRepository;

/** con sto indicamos a Spring que es una clase que administra, por lo que ya se puede indicar que cual 
 *  quiere que realize una llamado al clienteService haga de forma automatica la inyeccion del 
 *  ClinetServiceImpl
 */
@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	DocumentTypeRepository documentRepositorty;

	@Autowired
	Validator validator;

	@Override
	@Transactional (readOnly = true)
	public List<Client> findAll() {
		return clientRepository.findAll();
	}

	@Override
	@Transactional (readOnly = true)
	public Optional<Client> findById(Long id) {
		return clientRepository.findById(id);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Client save(Client entity) throws Exception {
		validate(entity);
		if(clientRepository.findById(entity.getClieId()).isPresent() == true)
		{
			throw new Exception("El cliente con ID: "+entity.getClieId()+" ya existe");
		}
		return clientRepository.save(entity);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Client update(Client entity) throws Exception {
		validate(entity);
		return clientRepository.save(entity);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Client entity) throws Exception {

		if(entity == null)
		{
			throw new Exception("El Cliente es nulo");
		}

		Optional<Client> clienteOpcional = clientRepository.findById(entity.getClieId());
		if(clienteOpcional.isPresent() == false)
		{
			throw new Exception("El Cliente con id "+entity.getClieId() +" No existe");
		}
		entity = clienteOpcional.get();

		if(entity.getAccounts().size() > 0)
		{
			throw new Exception("El Cliente con id "+entity.getClieId() +" tiene cuentas asociadas");
		}

		if(entity.getRegisteredAccounts().size() > 0)
		{
			throw new Exception("El Cliente con id "+entity.getClieId() +" tiene cuentas registradas");
		}

		clientRepository.delete(entity);

	}

	@Override
	@Transactional  (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long id) throws Exception {
		Optional<Client> clienteOpcional = clientRepository.findById(id);
		if(clienteOpcional.isPresent() == false)
		{
			throw new Exception("El Cliente con id "+ id +" No existe");
		}
		Client entity = clienteOpcional.get();
		delete(entity);	
	}

	@Override
	@Transactional (readOnly = true)
	public Long count() {
		return clientRepository.count();
	}

	@Override
	public void validate(Client client) throws Exception {
		if(client == null)
		{
			throw new Exception("El Cliente es nulo");
		}
		Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);

		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<Client> constraintViolation : constraintViolations) {
				strMessage.append(constraintViolation.getPropertyPath()
						.toString());
				strMessage.append(" - ");
				strMessage.append(constraintViolation.getMessage());
				strMessage.append(". \n");
			}
			throw new Exception(strMessage.toString());
		}       
	}
}
