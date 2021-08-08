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
@NoArgsConstructor
@ToString
public class UserEntity implements Serializable{

	private Integer idUser;
	private String name;
	private String lasName;
	private String user;
	
	public UserEntity(Integer idUser, String name, String lastName, String user) {
		this.idUser = idUser;
		this.name = name;
		this.lasName = lastName;
		this.user = user;
	}
	
}
