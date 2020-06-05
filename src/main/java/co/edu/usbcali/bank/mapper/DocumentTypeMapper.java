package co.edu.usbcali.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.usbcali.bank.domain.DocumentType;
import co.edu.usbcali.bank.dto.DocumentTypeDTO;

/**
 * insumo para poder hacer uso del mapstruct para convertir los entity a DTO
 * @author amgri
 *
 */
@Mapper
public interface DocumentTypeMapper {
	
	//@Mapping ayuda especificar que variable del source se debe pasar al target
	DocumentTypeDTO toDocumentTypeDTO(DocumentType documentType);

	DocumentType toDocumentType(DocumentTypeDTO documentTypeDTO);
	
	List<DocumentTypeDTO> toDocumentTypeDTOs(List<DocumentType> documentTypes);
	
	List<DocumentType> toDocumentTypes(List<DocumentTypeDTO> documentTypeDTOs);
}
