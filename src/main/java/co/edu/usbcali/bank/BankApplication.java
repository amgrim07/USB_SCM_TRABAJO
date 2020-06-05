package co.edu.usbcali.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@SpringBootApplication indicamos que es una aplciacion spring boot y es la clase principal
@SpringBootApplication
//@EnableSwagger2 indicamos spring que debe habilitar las funciones de swagger
@EnableSwagger2
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}
	
	/**
	 * MEtodo implementado para hacer el llamado de swagger y que 
	 * construya las paginas a todos los metodos rest
	 * @return
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).
				select().
				apis(RequestHandlerSelectors.any()).
				build();
	}


}
