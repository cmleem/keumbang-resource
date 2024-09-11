package backend.keumbangresource.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class ExceptionHandlerAdvice {
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> hanldeIllegalArgumentException(IllegalArgumentException e){
		String msg = e.getMessage();
		log.info(msg);
		return ResponseEntity.badRequest().body(msg);
	}
}
