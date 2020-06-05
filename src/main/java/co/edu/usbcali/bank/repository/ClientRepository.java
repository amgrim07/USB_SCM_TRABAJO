package co.edu.usbcali.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbcali.bank.domain.Client;
/**
Al heredar del JpaRepository, ya se esta heredenado de forma automatica
las fucionales CRUD hacia la tabla e implicitamente ya tiene el manejo de
las Transacciones.

LOS REPOSITORIOS son el REEMPLADO de los DAO, son los encargados de realizar
la conexion con la base de datos y de hacer todos los CRUD.
*/
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	//Con esta simple linea Sprint genera la Consulta de cliente por EMAIL,
	public List<Client> findByEmail(String email);
	
	//Con esta simple linea Sprint genera la Consulta de cliente donde el nomnre contenga el parametro enviado ,
	public List<Client> findByNameLike(String name);
	
	//con esta linea indicamos a JPA que debe ejecutar la consulta que se encuentra en el Query
	// la consulta se debe hacer por medio lon nombre de los objetos, no de las TABLAS
	@Query("FROM Client clie WHERE size(clie.accounts)>2")
	public List<Client> findWithTwoAccounts();

}
