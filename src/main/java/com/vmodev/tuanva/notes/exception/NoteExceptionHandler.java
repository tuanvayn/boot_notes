package com.vmodev.tuanva.notes.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vmodev.tuanva.notes.dto.BaseResponseDTO;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class NoteExceptionHandler {
	@ResponseBody
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<BaseResponseDTO<Exception>> methodArgumentNotValidException(
			MethodArgumentNotValidException e) {
		BaseResponseDTO<Exception> resp = new BaseResponseDTO<>(e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
	}

	@ResponseBody
	@ExceptionHandler(value = CustomNotFoundException.class)
	public ResponseEntity<BaseResponseDTO<Exception>> handleException(CustomNotFoundException e) {
		BaseResponseDTO<Exception> resp = new BaseResponseDTO<>(e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
	}

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<BaseResponseDTO<Exception>> commonException(Exception e) {
		BaseResponseDTO<Exception> resp = new BaseResponseDTO<>(e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
	}

}
