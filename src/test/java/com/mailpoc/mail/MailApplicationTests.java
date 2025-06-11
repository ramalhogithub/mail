package com.mailpoc.mail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mailpoc.mail.adapter.EmailAdapter;
import com.mailpoc.mail.dto.EmailRequestDTO;
import com.mailpoc.mail.factory.EmailAdapterFactory;
import com.mailpoc.mail.service.EmailService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailApplicationTests {

    @Mock
    private EmailAdapterFactory adapterFactory;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private EmailAdapter<?> mockEmailAdapter;

    @InjectMocks
    private EmailService emailService;

    private EmailRequestDTO testRequestDTO;

    @BeforeEach
    void setUp() {
        testRequestDTO = new EmailRequestDTO(
                "destinatario@teste.com",
                "Nome Teste",
                "remetente@teste.com",
                "Assunto Teste",
                "Conteúdo do email de teste.");

        doReturn(mockEmailAdapter).when(adapterFactory).getAdapter();
    }

    @Test
    void testProcessEmailSuccess() throws JsonProcessingException {
        // GIVEN
        Object adaptedObject = new Object();
        ObjectWriter mockWriter = mock(ObjectWriter.class);

        doReturn(adaptedObject).when(mockEmailAdapter).adapt(any(EmailRequestDTO.class));

        when(objectMapper.writerWithDefaultPrettyPrinter()).thenReturn(mockWriter);
        when(mockWriter.writeValueAsString(any())).thenReturn("{\"key\":\"value\"}");

        // WHEN
        emailService.processEmail(testRequestDTO);

        // THEN
        verify(adapterFactory, times(1)).getAdapter();
        verify(mockEmailAdapter, times(1)).adapt(testRequestDTO);
        verify(objectMapper.writerWithDefaultPrettyPrinter(), times(1)).writeValueAsString(adaptedObject);
    }

    @Test
    void testProcessEmailThrowsJsonProcessingException() throws JsonProcessingException {
        // GIVEN
        Object adaptedObject = new Object();
        ObjectWriter mockWriter = mock(ObjectWriter.class);

        doReturn(adaptedObject).when(mockEmailAdapter).adapt(any(EmailRequestDTO.class));

        when(objectMapper.writerWithDefaultPrettyPrinter()).thenReturn(mockWriter);
        when(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(adaptedObject))
                .thenThrow(JsonProcessingException.class);

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> emailService.processEmail(testRequestDTO));

        verify(adapterFactory, times(1)).getAdapter();
        verify(mockEmailAdapter, times(1)).adapt(testRequestDTO);
        verify(objectMapper.writerWithDefaultPrettyPrinter(), times(1)).writeValueAsString(adaptedObject);
    }

    @Test
    void testProcessEmailThrowsGenericExceptionFromAdapter() {
        // GIVEN
        when(mockEmailAdapter.adapt(any(EmailRequestDTO.class)))
                .thenThrow(new IllegalStateException("Erro simulado do adaptador."));

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> emailService.processEmail(testRequestDTO));

        verify(adapterFactory, times(1)).getAdapter();
        verify(mockEmailAdapter, times(1)).adapt(testRequestDTO);
        verify(objectMapper, never()).writerWithDefaultPrettyPrinter();
    }
}