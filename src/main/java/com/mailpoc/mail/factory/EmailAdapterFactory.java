package com.mailpoc.mail.factory;





import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mailpoc.mail.adapter.EmailAdapter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import jakarta.annotation.PostConstruct;

@Component
public class EmailAdapterFactory {

    @Value("${mail.integracao}")
    private String mailIntegrationConfig;

    private final Map<String, EmailAdapter<?>> adapters;

    // Injeta todas as implementações de EmailAdapter
    public EmailAdapterFactory(List<EmailAdapter<?>> emailAdapters) {
        this.adapters = emailAdapters.stream()
                .collect(Collectors.toMap(EmailAdapter::getPlatform, Function.identity()));
    }

    public EmailAdapter<?> getAdapter() {
        EmailAdapter<?> adapter = adapters.get(mailIntegrationConfig.toUpperCase());
        if (adapter == null) {
            throw new IllegalArgumentException(
                    "Configuração de integração de e-mail inválida: " + mailIntegrationConfig);
        }
        return adapter;
    }

    // Opcional: Para verificar se a configuração está correta na inicialização
    @PostConstruct
    public void validateConfig() {
        if (!adapters.containsKey(mailIntegrationConfig.toUpperCase())) {
            throw new IllegalStateException(
                    "A configuração 'mail.integracao' em application.properties é inválida. Valores permitidos: "
                            + adapters.keySet());
        }
    }
}

