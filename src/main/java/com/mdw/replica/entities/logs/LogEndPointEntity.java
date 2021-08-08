package com.mdw.replica.entities.logs;

import java.util.List;

import com.mdw.replica.entities.http.RSEntity;

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
public class LogEndPointEntity {
	
	private String KeyService;
	private String key;
	private String name;
	

}
