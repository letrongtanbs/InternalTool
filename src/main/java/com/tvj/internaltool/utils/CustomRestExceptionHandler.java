package com.tvj.internaltool.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomRestExceptionHandler.class);

    // Handle other error
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error(ex.getMessage());
        ApiErrorResUtil error = new ApiErrorResUtil(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                "Server error", "Unexpected error occurred!!");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle invalid request param
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstaintViolatoinException(final ConstraintViolationException ex) {
        List<String> details = new ArrayList<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            details.add(violation.getMessage());
        }
        ApiErrorResUtil error = new ApiErrorResUtil(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "Request param error", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle missing request param
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add("parameter " + ex.getParameterName() + " is missing!!");
        ApiErrorResUtil error = new ApiErrorResUtil(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "Request param error", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle parameters in request param dto
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ApiErrorResUtil error = new ApiErrorResUtil(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "Request param dto error", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle parameters in request body
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ApiErrorResUtil error = new ApiErrorResUtil(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "Request body error", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle invalid http request method
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorResUtil error = new ApiErrorResUtil(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()),
                "Request error", "Http request method: '" + ex.getMethod() + "' is not supported!!");
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // Handle media type not supported
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorResUtil error = new ApiErrorResUtil(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()), "Request error",
                "Media type: '" + ex.getContentType().getType() + "' is not supported!!");
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    // Handle media type not acceptable
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorResUtil error = new ApiErrorResUtil(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()), "Request error",
                "Media type is not acceptable!!");
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    // Handle missing path variable
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ApiErrorResUtil error = new ApiErrorResUtil(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Request error",
                "Missing path variable " + ex.getVariableName() + "!!");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("----ServletRequestBindingException", HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("----ConversionNotSupportedException", HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("----TypeMismatchException", HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("----HttpMessageNotReadableException", HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("----HttpMessageNotWritableException", HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("----MissingServletRequestPartException", HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("----NoHandlerFoundException", HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
            HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        return new ResponseEntity<>("----AsyncRequestTimeoutException", HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("----Exception", HttpStatus.BAD_REQUEST);
    }

}
