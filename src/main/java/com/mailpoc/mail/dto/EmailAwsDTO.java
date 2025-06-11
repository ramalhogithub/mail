package com.mailpoc.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailAwsDTO {

    @NotBlank
    @Size(max = 45)
    @Email
    private String recipient; // E-mail destinatário

    @NotBlank
    @Size(max = 60)
    private String recipientName; // Nome destinatário

    @NotBlank
    @Size(max = 45)
    @Email
    private String sender; // E-mail remetente

    @NotBlank
    @Size(max = 120)
    private String subject; // Assunto do e-mail

    @NotBlank
    @Size(max = 256)
    private String content; // Conteúdo do e-mail
}

