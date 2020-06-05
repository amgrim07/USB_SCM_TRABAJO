package co.edu.usbcali.bank.service;

import java.util.List;
import java.util.Optional;

/**
 * <T, ID> indica a JAVA que ba a realizar una clase generica que puede ser aplciada y usada por los demas
 */
public interface GenericService<T, ID> {
	
	public List<T> findAll();
	public Optional<T> findById(ID id);
	
	public T save(T entity) throws Exception;
	public T update(T entity) throws Exception;
	public void delete(T entity) throws Exception;
	public void deleteById(ID id)throws Exception;
	
	public Long count();
	public void validate(T entity) throws Exception;

}
