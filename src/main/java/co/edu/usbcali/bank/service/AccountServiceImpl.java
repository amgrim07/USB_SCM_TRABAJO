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

import co.edu.usbcali.bank.domain.Account;
import co.edu.usbcali.bank.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	Validator validator;

	@Override
	@Transactional(readOnly = true)
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Account> findById(String id) {
		return accountRepository.findById(id);
	}

	@Override
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Account save(Account entity) throws Exception {
		validate(entity);
		return accountRepository.save(entity);
	}

	@Override
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Account update(Account entity) throws Exception {
		validate(entity);
		return accountRepository.save(entity);
	}

	@Override
	@Transactional  (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Account entity) throws Exception {

		if(entity == null)
		{
			throw new Exception("El Account es nulo");
		}

		Optional<Account> accountOpcional = accountRepository.findById(entity.getAccoId());
		if(accountOpcional.isPresent() == false)
		{
			throw new Exception("El DocumentTypee con id "+entity.getAccoId() +" No existe");
		}
		entity = accountOpcional.get();

		if(entity.getRegisteredAccounts().size() > 0)
		{
			throw new Exception("El Account con id "+entity.getAccoId() +" tiene RegisteredAccount");
		}
		
		if(entity.getTransactions().size() > 0)
		{
			throw new Exception("El Account con id "+entity.getAccoId() +" tiene Transaction");
		}

		accountRepository.delete(entity);
	}

	@Override
	@Transactional (readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		Optional<Account> accountOpcional = accountRepository.findById(id);
		if(accountOpcional.isPresent() == false)
		{
			throw new Exception("El Account con id "+ id +" No existe");
		}
		Account entity = accountOpcional.get();
		delete(entity);	
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return accountRepository.count();
	}

	@Override
	public void validate(Account entity) throws Exception {
		if(entity == null)
		{
			throw new Exception("El DocumentTypee es nulo");
		}
		Set<ConstraintViolation<Account>> constraintViolations = validator.validate(entity);

		if (constraintViolations.size() > 0) {
			StringBuilder strMessage = new StringBuilder();

			for (ConstraintViolation<Account> constraintViolation : constraintViolations) {
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
