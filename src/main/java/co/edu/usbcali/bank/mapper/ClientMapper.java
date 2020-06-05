package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.usbcali.bank.domain.Client;
import co.edu.usbcali.bank.dto.ClientDTO;

/**
 * insumo para poder hacer uso del mapstruct para convertir los entity a DTO
 * @author amgri
 *
 */
@Mapper
public interface ClientMapper {
	
	//@Mapping ayuda especificar que variable del source se debe pasar al target
	@Mapping(source = "documentType.dotyId", target = "dotyId")
	ClientDTO toClientDTO(Client client);
	
	@Mapping(source = "dotyId", target = "documentType.dotyId")
	Client toClient(ClientDTO clientDTO);
	
	List<ClientDTO> toClientDTOs(List<Client> clinets);
	
	List<Client> toClients(List<ClientDTO> clinetDTOs);
}
