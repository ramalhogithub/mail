package com.mailpoc.mail.adapter.impl;

import org.springframework.stereotype.Component;

import com.mailpoc.mail.adapter.EmailAdapter;
import com.mailpoc.mail.dto.EmailOciDTO;
import com.mailpoc.mail.dto.EmailRequestDTO;



@Component
public class OciEmailAdapter implements EmailAdapter<EmailOciDTO> {

    @Override
    public EmailOciDTO adapt(EmailRequestDTO requestDTO) {
        EmailOciDTO ociDto = new EmailOciDTO();
        ociDto.setRecipientEmail(requestDTO.getRecipientEmail());
        ociDto.setRecipientName(requestDTO.getRecipientName());
        ociDto.setSenderEmail(requestDTO.getSenderEmail());
        ociDto.setSubject(requestDTO.getSubject());
        ociDto.setBody(requestDTO.getContent()); // OCI usa 'body' ao invés de 'content'

        // Validações de tamanho específicas da OCI DTO
        // Lembre-se que as anotações @Size do DTO de destino já cuidarão disso
        // automaticamente
        // no momento da serialização/validação, mas você pode adicionar lógicas
        // customizadas aqui.

        return ociDto;
    }

    @Override
    public String getPlatform() {
        return "OCI";
    }
}

