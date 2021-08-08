package com.mdw.replica.entities.request;

import java.util.List;

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
public class ReportEntity {

	private String fechaDesde;
	private String fechaHasta;
	private List<ReportModuleEntity> items;
}
