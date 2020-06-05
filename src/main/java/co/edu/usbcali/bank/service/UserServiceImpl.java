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

import co.edu.usbcali.bank.domain.Users;
import co.edu.usbcali.bank.repository.UsersRepository;

/** con sto indicamos a Spring que es una clase que administra, por lo que ya se puede indicar que cual 
 *  quiere que realize una llamado al UserseService haga de forma automatica la inyeccion del 
 *  UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UsersRepository userRepository;

	@Autowired
	Validator validator;

	@Override
	@Transactional (readOnly = true)
	public List<Users> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional (readOnly = true)
	public Optional<Users> findById(String id) {
		return userRepository.findById(id);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Users save(Users entity) throws Exception {
		validate(entity);
		if(userRepository.findById(entity.getUserEmail()).isPresent() == true)
		{
			throw new Exception("El user con ID: "+entity.getUserEmail()+" ya existe");
		}
		validate(entity);
		return userRepository.save(entity);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Users update(Users entity) throws Exception {
		validate(entity);
		return userRepository.save(entity);
	}

	@Override
	// propagation = Propagation.REQUIRED: indica que si ya existe una transaccion, se une a esa misma transaccion
	// si no existe transaccion este la abre.
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Users entity) throws Exception {

		if(entity == null)
		{
			throw new Exception("El Users es nulo");
		}

		Optional<Users> UserseOpcional = userRepository.findById(entity.getUserEmail());
		if(UserseOpcional.isPresent() == false)
		{
			throw new Exception("El Users con id "+entity.getUserEmail() +" No existe");
		}
		entity = UserseOpcional.get();

		if(entity.getTransactions().size() > 0)
		{
			throw new Exception("El Userse con id "+entity.getUserEmail() +" tiene transacciones asociadas");
		}

		userRepository.delete(entity);

	}

	@Override
	@Transactional  (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		Optional<Users> UserseOpcional = userRepository.findById(id);
		if(UserseOpcional.isPresent() == false)
		{
			throw new Exception("El Userse con id "+ id +" No existe");
		}
		Users entity = UserseOpcional.get();
		delete(entity);	
	}

	@Override
	@Transactional (readOnly = true)
	public Long count() {
		return userRepository.count();
	}

	@Override
	public void validate(Users Users) throws Exception {
		if(Users == null)
		{
			throw new Exception("El Userse es nulo");
		}
		Set<ConstraintViolation<Users>> constraintViolations = validator.validate(Users);

		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<Users> constraintViolation : constraintViolations) {
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
