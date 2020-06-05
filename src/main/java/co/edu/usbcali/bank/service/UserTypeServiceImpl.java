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

import co.edu.usbcali.bank.domain.UserType;
import co.edu.usbcali.bank.repository.UserTypeRepository;

/** con sto indicamos a Spring que es una clase que administra, por lo que ya se puede indicar que cual 
 *  quiere que realize una llamado al UserseService haga de forma automatica la inyeccion del 
 *  ClinetServiceImpl
 */
@Service
public class UserTypeServiceImpl implements UserTypeService {

	@Autowired
	UserTypeRepository userTypeRepositorty;

	@Autowired
	Validator validator;

	@Override
	@Transactional (readOnly = true)
	public List<UserType> findAll() {
		return userTypeRepositorty.findAll();
	}

	@Override
	@Transactional (readOnly = true)
	public Optional<UserType> findById(Long id) {
		return userTypeRepositorty.findById(id);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserType save(UserType entity) throws Exception {
		validate(entity);
		return userTypeRepositorty.save(entity);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserType update(UserType entity) throws Exception {
		validate(entity);
		return userTypeRepositorty.save(entity);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(UserType entity) throws Exception {

		if(entity == null)
		{
			throw new Exception("El Users es nulo");
		}

		Optional<UserType> UsersTypeOpcional = userTypeRepositorty.findById(entity.getUstyId());
		if(UsersTypeOpcional.isPresent() == false)
		{
			throw new Exception("El Users con id "+entity.getUstyId() +" No existe");
		}
		entity = UsersTypeOpcional.get();

		if(entity.getUserses().size() > 0)
		{
			throw new Exception("El UserType con id "+entity.getUstyId() +" tiene users asociadas");
		}

		userTypeRepositorty.delete(entity);

	}

	@Override
	@Transactional  (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long id) throws Exception {
		Optional<UserType> UsersTypeOpcional = userTypeRepositorty.findById(id);
		if(UsersTypeOpcional.isPresent() == false)
		{
			throw new Exception("El Userse con id "+ id +" No existe");
		}
		UserType entity = UsersTypeOpcional.get();
		delete(entity);	
	}

	@Override
	@Transactional (readOnly = true)
	public Long count() {
		return userTypeRepositorty.count();
	}

	@Override
	public void validate(UserType userType) throws Exception {
		if(userType == null)
		{
			throw new Exception("El UsersType es nulo");
		}
		Set<ConstraintViolation<UserType>> constraintViolations = validator.validate(userType);

		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<UserType> constraintViolation : constraintViolations) {
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
