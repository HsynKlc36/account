package com.hsyntech.account.exception.handler;

import com.hsyntech.account.exception.CustomerNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice//bütün exception throwlarını yakalar ve bir exception response üretir!!yani servislerde oluşan bir hatanın karşılığı burada varsa araya girer ve akışı keser yani servisten controller a bir cevap dönmez doğrudan burada handler eder yani burada hatanın cevabı bende der ve burada oluşturduğu response nesnesini doğrudan controller olmaksızın cevabı döner!!
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error->{
            String fieldName=((FieldError) error).getField();
            String errorMessage=error.getDefaultMessage();
            errors.put(fieldName,errorMessage);//validasyonla ilgili hataları buraya map ile eklemiş olduk böylelikle kullanıcı request'indeki validasyon hatalarını görecektir!!
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }//buradaki valid ile controller'a istek geldiğinde @valid ile controller da isteği kontrol eder ve validasyon hatalıysa controller'a hiç girmeden buraya düşer ve kullanıcıya override ettiğimiz bu metod ile nasıl bir istek yapması gerektiğini söylemiş oluruz! böylelikle servisleri yormadan valid olmayan istekleri yakalar ve buradan cevap döneriz

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> customerNotFoundExceptionHandler(CustomerNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
