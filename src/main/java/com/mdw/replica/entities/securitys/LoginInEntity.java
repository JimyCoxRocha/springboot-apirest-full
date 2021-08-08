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
public class LoginInEntity implements Serializable{

	private String username;
	private String password;
	private String typeEnvironment;
}
