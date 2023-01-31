package br.com.timoteosoutello.enumerations;

import lombok.Getter;

@Getter
public enum LogEnum {
	APP_LOG_KEY("br.com.timoteosoutello");

	private String value;

	LogEnum(String value) {
		this.value = value;
	}
}
