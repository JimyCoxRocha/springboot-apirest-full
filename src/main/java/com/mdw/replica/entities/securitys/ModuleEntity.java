package com.mdw.replica.entities.securitys;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModuleEntity implements Serializable{

	private Integer idModule;
	private String name;
	private String description;
	private String icon;
	private Boolean status;
	
}
