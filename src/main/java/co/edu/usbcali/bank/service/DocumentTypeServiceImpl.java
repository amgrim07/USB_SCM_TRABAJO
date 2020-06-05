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

import co.edu.usbcali.bank.domain.DocumentType;
import co.edu.usbcali.bank.repository.DocumentTypeRepository;

/** con sto indicamos a Spring que es una clase que administra, por lo que ya se puede indicar que cual 
 *  quiere que realize una llamado al DocumentTypeeService haga de forma automatica la inyeccion del 
 *  DocumentTypeServiceImpl
 */
@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

	@Autowired
	DocumentTypeRepository documentRepositorty;

	@Autowired
	Validator validator;

	@Override
	@Transactional (readOnly = true)
	public List<DocumentType> findAll() {
		return documentRepositorty.findAll();
	}

	@Override
	@Transactional (readOnly = true)
	public Optional<DocumentType> findById(Long id) {
		return documentRepositorty.findById(id);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DocumentType save(DocumentType entity) throws Exception {
		validate(entity);
		return documentRepositorty.save(entity);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DocumentType update(DocumentType entity) throws Exception {
		validate(entity);
		return documentRepositorty.save(entity);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(DocumentType entity) throws Exception {

		if(entity == null)
		{
			throw new Exception("El DocumentTypee es nulo");
		}

		Optional<DocumentType> DocumentTypeeOpcional = documentRepositorty.findById(entity.getDotyId());
		if(DocumentTypeeOpcional.isPresent() == false)
		{
			throw new Exception("El DocumentTypee con id "+entity.getDotyId() +" No existe");
		}
		entity = DocumentTypeeOpcional.get();

		if(entity.getClients().size() > 0)
		{
			throw new Exception("El DocumentTypee con id "+entity.getDotyId() +" tiene clientes asociadas");
		}

		documentRepositorty.delete(entity);

	}

	@Override
	@Transactional  (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long id) throws Exception {
		Optional<DocumentType> DocumentTypeeOpcional = documentRepositorty.findById(id);
		if(DocumentTypeeOpcional.isPresent() == false)
		{
			throw new Exception("El DocumentTypee con id "+ id +" No existe");
		}
		DocumentType entity = DocumentTypeeOpcional.get();
		delete(entity);	
	}

	@Override
	@Transactional (readOnly = true)
	public Long count() {
		return documentRepositorty.count();
	}

	@Override
	public void validate(DocumentType DocumentType) throws Exception {
		if(DocumentType == null)
		{
			throw new Exception("El DocumentTypee es nulo");
		}
		Set<ConstraintViolation<DocumentType>> constraintViolations = validator.validate(DocumentType);

		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<DocumentType> constraintViolation : constraintViolations) {
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
