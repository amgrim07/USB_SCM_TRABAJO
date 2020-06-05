package co.edu.usbcali.bank.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.bank.domain.UserType;
import co.edu.usbcali.bank.dto.ResponseErrorDTO;
import co.edu.usbcali.bank.dto.UserTypeDTO;
import co.edu.usbcali.bank.mapper.UserTypeMapper;
import co.edu.usbcali.bank.service.UserTypeService;

@RestController
@RequestMapping("/api/userType/")
// Indicamos que cualquier IP puede realizar peticiones a esta servicio backend
@CrossOrigin("*")
public class UserTypeController {
	
	@Autowired
	UserTypeService userTypeService;
	
	@Autowired
	UserTypeMapper userTypeMapper;
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id)
	{
		try
		{
			userTypeService.deleteById(id);			
			return ResponseEntity.ok("");
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().body(new ResponseErrorDTO("400", e.getMessage()));
		}
	}
	
	@PutMapping("update")
	public ResponseEntity<?> update(@Valid @RequestBody UserTypeDTO userTypeDTO)
	{
		try
		{
			UserType userType = userTypeMapper.toUserType(userTypeDTO);
			userType = userTypeService.update(userType);
			userTypeDTO = userTypeMapper.toUserTypeDTO(userType);
			
			return ResponseEntity.ok(userTypeDTO);
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().body(new ResponseErrorDTO("400", e.getMessage()));
		}
	}
	
	// @RequestBody indica que el parametro debe venir en el body del mensaje
	// @Valid indica que antes de entrar al metodo haga la valdiacion del DTO de acuerdo a los decoradores del DTO
	// en caso de que cumpla ingresa al metodo, en caso de que no cumpla ninguna de las condiciones se llama al
	// metodo handleValidationExceptions el cual contiene las anotaciones 
	// @ResponseStatus y @ExceptionHandler
	@PostMapping("save")
	public ResponseEntity<?> save(@Valid @RequestBody UserTypeDTO UserTypeDTO)
	{
		try
		{
			UserType UserType = userTypeMapper.toUserType(UserTypeDTO);
			UserType = userTypeService.save(UserType);
			UserTypeDTO = userTypeMapper.toUserTypeDTO(UserType);
			
			return ResponseEntity.ok(UserTypeDTO);
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().body(new ResponseErrorDTO("400", e.getMessage()));
		}
	}
	
	@GetMapping("findAll")
	public ResponseEntity<?> findAll()
	{
		List<UserType> UserTypes = userTypeService.findAll();
		List<UserTypeDTO> cllientDTOs = userTypeMapper.toUserTypeDTOs(UserTypes);
		
		return ResponseEntity.ok().body(cllientDTOs);
	}

	@GetMapping("findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id)
	{
		Optional<UserType> UserTypeOptional = userTypeService.findById(id);
		
		if(UserTypeOptional.isPresent() == false)
		{
			return ResponseEntity.badRequest().body(new ResponseErrorDTO("400", "El Client no existe"));
		}
		
		UserType UserType = UserTypeOptional.get();
		UserTypeDTO UserTypeDTO = userTypeMapper.toUserTypeDTO(UserType);
		
		return ResponseEntity.ok().body(UserTypeDTO);
	}
	
	/**
	 * MEtodo encargado de realizar las validaciones de los arametros de entrada de los metodos
	 * para evitar que reaize la logia inesesario
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
		StringBuilder strMessage = new StringBuilder();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        strMessage.append(fieldName);
	        strMessage.append("-");
	        strMessage.append(errorMessage);
	    });
	    return ResponseEntity.badRequest().body(new ResponseErrorDTO("400",strMessage.toString()));
	}
}
