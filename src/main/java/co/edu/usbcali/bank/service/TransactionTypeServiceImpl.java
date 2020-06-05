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

import co.edu.usbcali.bank.domain.TransactionType;
import co.edu.usbcali.bank.repository.TransactionTypeRepository;

/** con sto indicamos a Spring que es una clase que administra, por lo que ya se puede indicar que cual 
 *  quiere que realize una llamado al DocumentTypeeService haga de forma automatica la inyeccion del 
 *  TransactionServiceImpl
 */
@Service
public class TransactionTypeServiceImpl implements TransactionTypeService {

	@Autowired
	TransactionTypeRepository transactionTypeRepository;

	@Autowired
	Validator validator;

	@Override
	@Transactional (readOnly = true)
	public List<TransactionType> findAll() {
		return transactionTypeRepository.findAll();
	}

	@Override
	@Transactional (readOnly = true)
	public Optional<TransactionType> findById(Long id) {
		return transactionTypeRepository.findById(id);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public TransactionType save(TransactionType entity) throws Exception {
		validate(entity);
		return transactionTypeRepository.save(entity);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public TransactionType update(TransactionType entity) throws Exception {
		validate(entity);
		return transactionTypeRepository.save(entity);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(TransactionType entity) throws Exception {

		if(entity == null)
		{
			throw new Exception("El TransactionType es nulo");
		}

		Optional<TransactionType> transactionTypeOptional = transactionTypeRepository.findById(entity.getTrtyId());
		if(transactionTypeOptional.isPresent() == false)
		{
			throw new Exception("El TransactionType con id "+entity.getTrtyId() +" No existe");
		}
		entity = transactionTypeOptional.get();

		if(entity.getTransactions().size() > 0)
		{
			throw new Exception("El TransactionType con id "+entity.getTrtyId() +" tiene Transaction asociado");
		}
	
		transactionTypeRepository.delete(entity);

	}

	@Override
	@Transactional  (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long id) throws Exception {
		Optional<TransactionType> TransactionOptioanl = transactionTypeRepository.findById(id);
		if(TransactionOptioanl.isPresent() == false)
		{
			throw new Exception("El TransactionType con id "+ id +" No existe");
		}
		TransactionType entity = TransactionOptioanl.get();
		delete(entity);	
	}

	@Override
	@Transactional (readOnly = true)
	public Long count() {
		return transactionTypeRepository.count();
	}

	@Override
	public void validate(TransactionType transactionType) throws Exception {
		if(transactionType == null)
		{
			throw new Exception("El Transaction es nulo");
		}
		Set<ConstraintViolation<TransactionType>> constraintViolations = validator.validate(transactionType);

		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<TransactionType> constraintViolation : constraintViolations) {
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
