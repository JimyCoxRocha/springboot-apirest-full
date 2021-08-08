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
public class ParamTableEntity {
//10:25
	@NonNull
	private ArrayList<ModelSapEntity> model = new ArrayList<>();
	
	@NonNull
	private ArrayList<ArrayList<RowSapEntity>> rows = new ArrayList<ArrayList<RowSapEntity>>();
}
