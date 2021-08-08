package com.mdw.replica.entities.logs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParamEntity {

	@NonNull
	private String key;
	
	@NonNull
	private String type;
	
	@NonNull
	private String value;
}
