package co.edu.usbcali.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController indica a Spring que es va a interpretar como un servicio rest.
@RestController
// @RequestMapping es el nombre del servicio como se va a exponer
@RequestMapping("/mat/")
public class OpercionesMatematicas {

	// @GetMapping indica a sprint que es un metodo del servicio y va hacer un metodo GET
	// ("sumar/{n1}/{n2}") indica el nombre del metodo y las variables que recibe por el get
	// @@PathVariable("nombre") indica a Spring que la variable que llega del servicio rest, se convierte en varable de estrada del metodo
	@GetMapping("sumar/{numeroUno}/{numeroDos}")
	public Resultado sumar(@PathVariable("numeroUno") Integer n1, 
			             @PathVariable("numeroDos") Integer n2)
	{
		return new Resultado(n1+n2);
	}
	
	@GetMapping("restar/{numeroUno}/{numeroDos}")
	public Resultado restar(@PathVariable("numeroUno") Integer n1, 
			              @PathVariable("numeroDos") Integer n2)
	{
		return new Resultado(n1-n2);
	}
	
	@GetMapping("div/{numeroUno}/{numeroDos}")
	public ResponseEntity<?> div(@PathVariable("numeroUno") Integer n1, 
			              @PathVariable("numeroDos") Integer n2)
	{
		try
		{
			return ResponseEntity.ok().body(new ResultadoDiv( new Float(n1)/ new Float(n2))); 
		}
		catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(new DivError(e.getMessage()));
		}
	}
}

class DivError
{
	private String mensaje;

	public DivError(String mensaje) {
		super();
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}

class ResultadoDiv
{
	private Float valor;
	
	public ResultadoDiv(Float valor) {
		super();
		this.valor = valor;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}
}

class Resultado
{
	private Integer valor;
	
	public Resultado(Integer valor) {
		super();
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
}
