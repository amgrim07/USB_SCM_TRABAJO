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

import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.dto.ClientDTO;
import co.edu.usbcali.bank.dto.ResponseErrorDTO;
import co.edu.usbcali.bank.mapper.ClientMapper;
import co.edu.usbcali.bank.service.ClientService;

@RestController
@RequestMapping("/api/client/")
// Indicamos que cualquier IP puede realizar peticiones a esta servicio backend
@CrossOrigin("*")
public class ClientController {
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	ClientMapper clientMapper;
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id)
	{
		try
		{
			clientService.deleteById(id);			
			return ResponseEntity.ok("");
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().body(new ResponseErrorDTO("400", e.getMessage()));
		}
	}
	
	@PutMapping("update")
	public ResponseEntity<?> update(@Valid @RequestBody ClientDTO clientDTO)
	{
		try
		{
			Client client = clientMapper.toClient(clientDTO);
			client = clientService.update(client);
			clientDTO = clientMapper.toClientDTO(client);
			
			return ResponseEntity.ok(clientDTO);
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
	public ResponseEntity<?> save(@Valid @RequestBody ClientDTO clientDTO)
	{
		try
		{
			Client client = clientMapper.toClient(clientDTO);
			client = clientService.save(client);
			clientDTO = clientMapper.toClientDTO(client);
			
			return ResponseEntity.ok(clientDTO);
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().body(new ResponseErrorDTO("400", e.getMessage()));
		}
	}
	
	@GetMapping("findAll")
	public ResponseEntity<?> findAll()
	{
		List<Client> clients = clientService.findAll();
		List<ClientDTO> cllientDTOs = clientMapper.toClientDTOs(clients);
		
		return ResponseEntity.ok().body(cllientDTOs);
	}

	@GetMapping("findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id)
	{
		Optional<Client> clientOptional = clientService.findById(id);
		
		if(clientOptional.isPresent() == false)
		{
			return ResponseEntity.badRequest().body(new ResponseErrorDTO("400", "El Client no existe"));
		}
		
		Client client = clientOptional.get();
		ClientDTO clientDTO = clientMapper.toClientDTO(client);
		
		return ResponseEntity.ok().body(clientDTO);
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
