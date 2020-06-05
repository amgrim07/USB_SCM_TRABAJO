package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.usbcali.bank.domain.Users;
import co.edu.usbcali.bank.dto.UsersDTO;

/**
 * insumo para poder hacer uso del mapstruct para convertir los entity a DTO
 * @author amgri
 *
 */
@Mapper
public interface UserMapper {
	
	//@Mapping ayuda especificar que variable del source se debe pasar al target
	@Mapping(source = "userType.ustyId", target = "ustyId")
	UsersDTO toUsersDTO(Users userss);
	
	@Mapping(source = "ustyId", target = "userType.ustyId")
	Users toUsers(UsersDTO usersDTO);
	
	List<UsersDTO> toUsersDTOs(List<Users> users);
	
	List<Users> toUserss(List<UsersDTO> userDTOs);
}
