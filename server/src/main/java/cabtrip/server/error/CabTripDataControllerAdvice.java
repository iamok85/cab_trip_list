package cabtrip.server.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;;

//This class to handle exception occurring during api request
@ControllerAdvice
public class CabTripDataControllerAdvice extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(CabTripDataControllerAdvice.class);
   
    // Handling malformat of parameter values
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
	  MethodArgumentNotValidException ex, 
	  HttpHeaders headers, 
	  HttpStatus status, 
	  WebRequest request) {
	    List<String> errors = new ArrayList<String>();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        errors.add(error.getField() + ": " + error.getDefaultMessage());
	    }
	    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	        errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	    }
	    
	    ApiError apiError = 
	      new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
	    return new ResponseEntity<Object>(
  	          apiError, new HttpHeaders(), apiError.getStatus());
	}
    
       //Handling missing parameter during request 
	   @Override
	   protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	       String name = ex.getParameterName();
	       ApiError apiError = new ApiError(
	    	          HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getMessage());
	       return new ResponseEntity<Object>(
	    	          apiError, new HttpHeaders(), apiError.getStatus());
	   }
	   //Handling all other exceptions
	    @ExceptionHandler({ Exception.class })
	    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
	        ApiError apiError = new ApiError(
	          HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getMessage());
	        return new ResponseEntity<Object>(
	          apiError, new HttpHeaders(), apiError.getStatus());
	    }

}