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
public class EmailRequestDTO {

    @NotBlank(message = "O e-mail do destinatário é obrigatório.")
    @Email(message = "E-mail do destinatário inválido.")
    private String recipientEmail;

    @NotBlank(message = "O nome do destinatário é obrigatório.")
    @Size(max = 60, message = "O nome do destinatário deve ter no máximo 60 caracteres.")
    private String recipientName;

    @NotBlank(message = "O e-mail do remetente é obrigatório.")
    @Email(message = "E-mail do remetente inválido.")
    private String senderEmail;

    @NotBlank(message = "O assunto é obrigatório.")
    @Size(max = 120, message = "O assunto deve ter no máximo 120 caracteres.")
    private String subject;

    @NotBlank(message = "O conteúdo é obrigatório.")
    @Size(max = 256, message = "O conteúdo deve ter no máximo 256 caracteres.")
    private String content;
}


