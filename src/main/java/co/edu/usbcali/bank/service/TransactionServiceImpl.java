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

import co.edu.usbcali.bank.domain.Transaction;
import co.edu.usbcali.bank.repository.TransactionRepository;

/** con sto indicamos a Spring que es una clase que administra, por lo que ya se puede indicar que cual 
 *  quiere que realize una llamado al DocumentTypeeService haga de forma automatica la inyeccion del 
 *  TransactionServiceImpl
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	Validator validator;

	@Override
	@Transactional (readOnly = true)
	public List<Transaction> findAll() {
		return transactionRepository.findAll();
	}

	@Override
	@Transactional (readOnly = true)
	public Optional<Transaction> findById(Long id) {
		return transactionRepository.findById(id);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Transaction save(Transaction entity) throws Exception {
		validate(entity);
		return transactionRepository.save(entity);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Transaction update(Transaction entity) throws Exception {
		validate(entity);
		return transactionRepository.save(entity);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Transaction entity) throws Exception {

		if(entity == null)
		{
			throw new Exception("El Transaction es nulo");
		}

		Optional<Transaction> TransactionOptional = transactionRepository.findById(entity.getTranId());
		if(TransactionOptional.isPresent() == false)
		{
			throw new Exception("El Transaction con id "+entity.getTranId() +" No existe");
		}
		entity = TransactionOptional.get();

		if(entity.getAccount() != null)
		{
			throw new Exception("El Transaction con id "+entity.getTranId() +" tiene Account asociado");
		}
		
		if(entity.getUsers() != null)
		{
			throw new Exception("El Transaction con id "+entity.getTranId() +" tiene Users asociado");
		}


		transactionRepository.delete(entity);

	}

	@Override
	@Transactional  (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long id) throws Exception {
		Optional<Transaction> TransactionOptioanl = transactionRepository.findById(id);
		if(TransactionOptioanl.isPresent() == false)
		{
			throw new Exception("El Transaction con id "+ id +" No existe");
		}
		Transaction entity = TransactionOptioanl.get();
		delete(entity);	
	}

	@Override
	@Transactional (readOnly = true)
	public Long count() {
		return transactionRepository.count();
	}

	@Override
	public void validate(Transaction Transaction) throws Exception {
		if(Transaction == null)
		{
			throw new Exception("El Transaction es nulo");
		}
		Set<ConstraintViolation<Transaction>> constraintViolations = validator.validate(Transaction);

		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<Transaction> constraintViolation : constraintViolations) {
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
