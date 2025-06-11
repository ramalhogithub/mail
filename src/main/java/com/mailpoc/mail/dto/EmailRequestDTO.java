package com.mailpoc.mail.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Importações do OpenAPI/Swagger
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para requisição de envio de e-mail") // Anotação para o DTO
public class EmailRequestDTO {

    @NotBlank(message = "O e-mail do destinatário é obrigatório.")
    @Email(message = "O e-mail do destinatário deve ser um formato válido.")
    @Schema(description = "Endereço de e-mail do destinatário", example = "destino@example.com")
    private String recipientEmail;

    @NotBlank(message = "O nome do destinatário é obrigatório.")
    @Schema(description = "Nome do destinatário", example = "Fulano de Tal")
    private String recipientName;

    @NotBlank(message = "O e-mail do remetente é obrigatório.")
    @Email(message = "O e-mail do remetente deve ser um formato válido.")
    @Schema(description = "Endereço de e-mail do remetente", example = "remetente@example.com")
    private String senderEmail;

    @NotBlank(message = "O assunto do e-mail é obrigatório.")
    @Schema(description = "Assunto do e-mail", example = "Convite para Evento")
    private String subject;

    @NotBlank(message = "O conteúdo é obrigatório.")
    @Schema(description = "Conteúdo do e-mail (HTML ou texto simples)", example = "Prezado Fulano, \n\nConvidamos você para nosso evento...")
    private String content;
}