package com.mdw.replica.entities.securitys;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper =false)
public class UserAuthenticatedEntity extends UserEntity implements Serializable{

	private Integer idProfile;
	
	
	public UserAuthenticatedEntity(Integer idUser, String name, String lastName, String user, Integer idProfile) {
		super(idUser, name, lastName, user);
		this.idProfile = idProfile;
	}
	
}
