package br.com.timoteosoutello.enumerations;

import lombok.Getter;

@Getter
public enum CommandParametersEnum {

	FILE("file");
	
	private String value;

	CommandParametersEnum(String value) {
		this.value = value;
	}
}
