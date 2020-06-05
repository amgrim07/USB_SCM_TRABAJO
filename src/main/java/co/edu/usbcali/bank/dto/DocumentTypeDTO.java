package co.edu.usbcali.bank.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class DocumentTypeDTO implements java.io.Serializable {
	
	private Long dotyId;
	@NotNull
	@NotEmpty
	@Size(max = 1)
	private String enable;
	@NotNull
	@NotEmpty
	@Size(max = 255)
	private String name;


	public DocumentTypeDTO() {
	}

	public DocumentTypeDTO(Long dotyId, String enable, String name) {
		this.dotyId = dotyId;
		this.enable = enable;
		this.name = name;
	}

	public Long getDotyId() {
		return dotyId;
	}

	public void setDotyId(Long dotyId) {
		this.dotyId = dotyId;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
