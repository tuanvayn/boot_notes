package com.vmodev.tuanva.notes.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@Log4j2
public class BaseResponseDTO<T> {
	private String statusCode;
	private String message;
	private T data;

	public BaseResponseDTO() {
		this.statusCode = "1";
		this.statusCode = "Success";
	}

	public BaseResponseDTO(Exception e) {
		this.statusCode = "0";
		this.statusCode = "Fail";
		this.data = (T) e.getMessage();
		log.error(e);
	}

	public BaseResponseDTO(T data) {
		this.statusCode = "1";
		this.statusCode = "Success";
		this.data = data;
	}
}
