package com.mdw.replica.entities.securitys;

import java.io.Serializable;
import java.util.ArrayList;

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
public class MenuEntity implements Serializable{

	private Integer idModule;
	private String name;
	private String icon;
	private ArrayList<ItemMenuEntity> items = new ArrayList<>();
	
}
