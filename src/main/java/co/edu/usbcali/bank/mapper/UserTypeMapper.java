package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.usbcali.bank.domain.UserType;
import co.edu.usbcali.bank.dto.UserTypeDTO;

/**
 * insumo para poder hacer uso del mapstruct para convertir los entity a DTO
 * @author amgri
 *
 */
@Mapper
public interface UserTypeMapper {
	
	//@Mapping ayuda especificar que variable del source se debe pasar al target
	UserTypeDTO toUserTypeDTO(UserType userType);

	UserType toUserType(UserTypeDTO userTypeDTO);
	
	List<UserTypeDTO> toUserTypeDTOs(List<UserType> userType);
	
	List<UserType> toUserTypes(List<UserTypeDTO> userTypeDTOs);
}
