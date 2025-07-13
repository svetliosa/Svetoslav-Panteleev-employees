package com.svetoslav.panteleev.employees.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectCollaborationDto {
	private Long employeeId1;
	private Long employeeId2;
	private Long projectId;
	private Long daysWorked;

	public ProjectCollaborationDto(Long employeeId1, Long employeeId2, Long projectId, Long daysWorked) {
		this.employeeId1 = employeeId1;
		this.employeeId2 = employeeId2;
		this.projectId = projectId;
		this.daysWorked = daysWorked;
	}
}
