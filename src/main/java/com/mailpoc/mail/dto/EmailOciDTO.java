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
public class EmailOciDTO {

    @NotBlank
    @Size(max = 40)
    @Email
    private String recipientEmail; // E-mail destinatário

    @NotBlank
    @Size(max = 50)
    private String recipientName; // Nome destinatário

    @NotBlank
    @Size(max = 40)
    @Email
    private String senderEmail; // E-mail remetente

    @NotBlank
    @Size(max = 100)
    private String subject; // Assunto do e-mail

    @NotBlank
    @Size(max = 250)
    private String body; // Conteúdo do e-mail
}


