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
    private String recipientEmail;

    @NotBlank
    @Size(max = 50)
    private String recipientName;

    @NotBlank
    @Size(max = 40)
    @Email
    private String senderEmail;

    @NotBlank
    @Size(max = 100)
    private String subject;

    @NotBlank
    @Size(max = 250)
    private String body;
}
