package co.edu.usbcali.bank.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserTypeDTO implements java.io.Serializable {
	
	private Long ustyId;
	@NotNull
	@NotEmpty
	@Size(max = 1)
	private String enable;
	@NotNull
	@NotEmpty
	@Size(max = 255)
	private String name;
	
	public UserTypeDTO() {
		super();
	}
	public UserTypeDTO(Long ustyId, @NotNull @NotEmpty @Size(max = 1) String enable,
			@NotNull @NotEmpty @Size(max = 255) String name) {
		super();
		this.ustyId = ustyId;
		this.enable = enable;
		this.name = name;
	}
	public Long getUstyId() {
		return ustyId;
	}
	public void setUstyId(Long ustyId) {
		this.ustyId = ustyId;
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
