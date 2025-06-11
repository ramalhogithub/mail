package com.mailpoc.mail.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailpoc.mail.dto.EmailRequestDTO;
import com.mailpoc.mail.factory.EmailAdapterFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final EmailAdapterFactory adapterFactory;
    private final ObjectMapper objectMapper; // Para serializar JSON

    public EmailService(EmailAdapterFactory adapterFactory, ObjectMapper objectMapper) {
        this.adapterFactory = adapterFactory;
        this.objectMapper = objectMapper;
    }

    public void processEmail(EmailRequestDTO emailRequestDTO) {
        try {
            // Obtém o adaptador correto dinamicamente
            Object adaptedObject = adapterFactory.getAdapter().adapt(emailRequestDTO);

            // Serializa o objeto adaptado para JSON
            String jsonOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(adaptedObject);

            // Imprime no console
            log.info("Objeto de e-mail adaptado e serializado:\n{}", jsonOutput);

            // Aqui você "simularia" o envio de e-mail para AWS/OCI
            // Por exemplo: if (adaptedObject instanceof EmailAwsDTO) { // chamar sdk aws }
            // else if (adaptedObject instanceof EmailOciDTO) { // chamar sdk oci }

        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar o objeto de e-mail para JSON: {}", e.getMessage(), e);
            throw new RuntimeException("Erro interno ao processar o e-mail.", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao processar o e-mail: {}", e.getMessage(), e);
            throw new RuntimeException("Erro inesperado ao processar o e-mail.", e);
        }
    }
}


