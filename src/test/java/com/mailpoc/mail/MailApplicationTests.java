package com.mailpoc.mail;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailpoc.mail.adapter.EmailAdapter;
import com.mailpoc.mail.dto.EmailRequestDTO;
import com.mailpoc.mail.factory.EmailAdapterFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita o Mockito para JUnit 5
public class MailApplicationTests {

    @Mock // Cria um mock para EmailAdapterFactory
    private EmailAdapterFactory adapterFactory;

    @Mock // Cria um mock para ObjectMapper
    private ObjectMapper objectMapper;

    @Mock // Cria um mock para EmailAdapter (será retornado pelo adapterFactory)
    private EmailAdapter<Object> mockEmailAdapter; // Usamos <Object> pois o adapt retorna Object

    @InjectMocks // Injeta os mocks criados acima nesta instância
    private EmailService emailService;

    // Podemos injetar o Logger diretamente no @InjectMocks, mas para controlar sua saída
    // é mais comum usar um Spy, ou simplesmente ignorar o log nos testes unitários focados na lógica.
    // Para este exemplo, vamos focar na lógica.

    private EmailRequestDTO testRequestDTO;

    @BeforeEach // Este método é executado antes de cada teste
    void setUp() {
        // Inicializa um DTO de teste com dados válidos
        testRequestDTO = new EmailRequestDTO(
                "destinatario@teste.com",
                "Nome Teste",
                "remetente@teste.com",
                "Assunto Teste",
                "Conteúdo do email de teste."
        );

        // Configura o comportamento do adapterFactory mock para sempre retornar o mockEmailAdapter
        when(adapterFactory.getAdapter()).thenReturn(mockEmailAdapter);
    }

    @Test
    void testProcessEmailSuccess() throws JsonProcessingException {
        // GIVEN (Dado)
        Object adaptedObject = new Object(); // Objeto fictício que o adapter retornaria

        // Configura o comportamento do mockEmailAdapter:
        // Quando 'adapt' for chamado com qualquer EmailRequestDTO, ele retorna 'adaptedObject'
        when(mockEmailAdapter.adapt(any(EmailRequestDTO.class))).thenReturn(adaptedObject);

        // Quando 'writeValueAsString' for chamado no objectMapper com o 'adaptedObject', retorna uma string JSON
        when(objectMapper.writerWithDefaultPrettyPrinter()).thenReturn(mock(ObjectMapper.ObjectWriter.class));
        when(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(adaptedObject))
                .thenReturn("{\"key\":\"value\"}"); // JSON de exemplo

        // WHEN (Quando)
        emailService.processEmail(testRequestDTO);

        // THEN (Então)
        // Verifica se o método getAdapter foi chamado exatamente uma vez no adapterFactory
        verify(adapterFactory, times(1)).getAdapter();
        // Verifica se o método adapt foi chamado exatamente uma vez no mockEmailAdapter com o DTO correto
        verify(mockEmailAdapter, times(1)).adapt(testRequestDTO);
        // Verifica se o método writeValueAsString foi chamado no objectMapper
        verify(objectMapper.writerWithDefaultPrettyPrinter(), times(1)).writeValueAsString(adaptedObject);

        // Nota: Testar o log de INFO é mais complexo e geralmente não é feito em testes unitários.
        // Focamos em testar a interação entre os componentes e se as exceções são lançadas corretamente.
    }

    @Test
    void testProcessEmailThrowsJsonProcessingException() throws JsonProcessingException {
        // GIVEN
        Object adaptedObject = new Object();
        when(mockEmailAdapter.adapt(any(EmailRequestDTO.class))).thenReturn(adaptedObject);

        // Configura o objectMapper para lançar JsonProcessingException
        when(objectMapper.writerWithDefaultPrettyPrinter()).thenReturn(mock(ObjectMapper.ObjectWriter.class));
        when(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(adaptedObject))
                .thenThrow(JsonProcessingException.class); // Simula uma exceção durante a serialização

        // WHEN & THEN (Quando e Então)
        // Espera que uma RuntimeException seja lançada (pois a EmailService a encapsula)
        assertThrows(RuntimeException.class, () -> emailService.processEmail(testRequestDTO));

        // Verifica se os métodos foram chamados antes da exceção
        verify(adapterFactory, times(1)).getAdapter();
        verify(mockEmailAdapter, times(1)).adapt(testRequestDTO);
        verify(objectMapper.writerWithDefaultPrettyPrinter(), times(1)).writeValueAsString(adaptedObject);
    }

    @Test
    void testProcessEmailThrowsGenericExceptionFromAdapter() {
        // GIVEN
        // Configura o mockEmailAdapter para lançar uma exceção genérica no método adapt
        when(mockEmailAdapter.adapt(any(EmailRequestDTO.class)))
                .thenThrow(new IllegalStateException("Erro simulado do adaptador."));

        // WHEN & THEN
        // Espera que uma RuntimeException seja lançada pela EmailService
        assertThrows(RuntimeException.class, () -> emailService.processEmail(testRequestDTO));

        // Verifica se os métodos corretos foram chamados até o ponto da exceção
        verify(adapterFactory, times(1)).getAdapter();
        verify(mockEmailAdapter, times(1)).adapt(testRequestDTO);
        // objectMapper não deve ser chamado neste cenário
        verify(objectMapper, never()).writerWithDefaultPrettyPrinter();
    }
}
