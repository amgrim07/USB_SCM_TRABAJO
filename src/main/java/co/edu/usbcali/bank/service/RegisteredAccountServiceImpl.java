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

import co.edu.usbcali.bank.domain.RegisteredAccount;
import co.edu.usbcali.bank.repository.RegisteredAccountRepository;

/** con sto indicamos a Spring que es una clase que administra, por lo que ya se puede indicar que cual 
 *  quiere que realize una llamado al DocumentTypeeService haga de forma automatica la inyeccion del 
 *  DocumentTypeServiceImpl
 */
@Service
public class RegisteredAccountServiceImpl implements RegisteredAccountService {

	@Autowired
	RegisteredAccountRepository registeredAccountRepository;

	@Autowired
	Validator validator;

	@Override
	@Transactional (readOnly = true)
	public List<RegisteredAccount> findAll() {
		return registeredAccountRepository.findAll();
	}

	@Override
	@Transactional (readOnly = true)
	public Optional<RegisteredAccount> findById(Long id) {
		return registeredAccountRepository.findById(id);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RegisteredAccount save(RegisteredAccount entity) throws Exception {
		validate(entity);
		return registeredAccountRepository.save(entity);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RegisteredAccount update(RegisteredAccount entity) throws Exception {
		validate(entity);
		return registeredAccountRepository.save(entity);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(RegisteredAccount entity) throws Exception {

		if(entity == null)
		{
			throw new Exception("El RegisteredAccount es nulo");
		}

		Optional<RegisteredAccount> registeredAccountOptional = registeredAccountRepository.findById(entity.getReacId());
		if(registeredAccountOptional.isPresent() == false)
		{
			throw new Exception("El RegisteredAccount con id "+entity.getReacId() +" No existe");
		}
		entity = registeredAccountOptional.get();

		if(entity.getClient() != null)
		{
			throw new Exception("El RegisteredAccount con id "+entity.getReacId() +" tiene cliente asociado");
		}
		
		if(entity.getAccount() != null)
		{
			throw new Exception("El RegisteredAccount con id "+entity.getReacId() +" tiene Account asociado");
		}


		registeredAccountRepository.delete(entity);

	}

	@Override
	@Transactional  (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long id) throws Exception {
		Optional<RegisteredAccount> registeredAccountOptioanl = registeredAccountRepository.findById(id);
		if(registeredAccountOptioanl.isPresent() == false)
		{
			throw new Exception("El DocumentTypee con id "+ id +" No existe");
		}
		RegisteredAccount entity = registeredAccountOptioanl.get();
		delete(entity);	
	}

	@Override
	@Transactional (readOnly = true)
	public Long count() {
		return registeredAccountRepository.count();
	}

	@Override
	public void validate(RegisteredAccount registeredAccount) throws Exception {
		if(registeredAccount == null)
		{
			throw new Exception("El RegisteredAccount es nulo");
		}
		Set<ConstraintViolation<RegisteredAccount>> constraintViolations = validator.validate(registeredAccount);

		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<RegisteredAccount> constraintViolation : constraintViolations) {
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
