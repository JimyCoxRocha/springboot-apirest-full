package com.mdw.replica.entities.logs;

import java.util.ArrayList;

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
public class RequestEntity {

	@NonNull
	public String user = "";
	public ArrayList<ParamEntity> params = new ArrayList<>();
	public ArrayList<ParamTableEntity> paramTable = new ArrayList<>();

}
