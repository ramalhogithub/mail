package com.mailpoc.mail.adapter.impl;



import org.springframework.stereotype.Component;

import com.mailpoc.mail.adapter.EmailAdapter;
import com.mailpoc.mail.dto.EmailAwsDTO;
import com.mailpoc.mail.dto.EmailRequestDTO;



@Component
public class AwsEmailAdapter implements EmailAdapter<EmailAwsDTO> {

    @Override
    public EmailAwsDTO adapt(EmailRequestDTO requestDTO) {
        EmailAwsDTO awsDto = new EmailAwsDTO();
        awsDto.setRecipient(requestDTO.getRecipientEmail());
        awsDto.setRecipientName(requestDTO.getRecipientName());
        awsDto.setSender(requestDTO.getSenderEmail());
        awsDto.setSubject(requestDTO.getSubject());
        awsDto.setContent(requestDTO.getContent());

        // Validações de tamanho específicas da AWS DTO se necessário (já feito pelas anotações)
        // Lembre-se que as anotações @Size do DTO de destino já cuidarão disso automaticamente
        // no momento da serialização/validação, mas você pode adicionar lógicas customizadas aqui.

        return awsDto;
    }

    @Override
    public String getPlatform() {
        return "AWS";
    }
}



