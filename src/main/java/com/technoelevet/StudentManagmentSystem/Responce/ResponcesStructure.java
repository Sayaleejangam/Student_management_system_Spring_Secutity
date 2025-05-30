package com.technoelevet.StudentManagmentSystem.Responce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponcesStructure {

	private boolean error;
	private String message;
	private Object data;

}
