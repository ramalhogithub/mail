package com.mailpoc.mail.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mailpoc.mail.dto.EmailRequestDTO;
import com.mailpoc.mail.service.EmailService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private static final Logger log = LoggerFactory.getLogger(EmailController.class);
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@Valid @RequestBody EmailRequestDTO requestDTO) {
        log.info("Requisição recebida para envio de e-mail: {}", requestDTO);
        emailService.processEmail(requestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Status 204
    }

    // Tratamento de erros para validação de DTO (status 400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.warn("Erro de validação na requisição: {}", errors);
        return errors;
    }

    // Tratamento de erros genéricos (status 500)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleAllExceptions(Exception ex) {
        log.error("Ocorreu um erro interno no servidor: {}", ex.getMessage(), ex);
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Ocorreu um erro interno no servidor. Por favor, tente novamente mais tarde.");
        // Em um ambiente de produção, você pode não querer expor a mensagem exata do
        // erro.
        // errors.put("details", ex.getMessage());
        return errors;
    }
}


