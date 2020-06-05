package co.edu.usbcali.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usbcali.bank.domain.Account;
/**
Al heredar del JpaRepository, ya se esta heredenado de forma automatica
las fucionales CRUD hacia la tabla e implicitamente ya tiene el manejo de
las Transacciones.

LOS REPOSITORIOS son el REEMPLADO de los DAO, son los encargados de realizar
la conexion con la base de datos y de hacer todos los CRUD.
*/
public interface AccountRepository extends JpaRepository<Account, String> {

}
