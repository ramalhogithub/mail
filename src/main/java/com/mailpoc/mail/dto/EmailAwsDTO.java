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
    private String recipient; 

    @NotBlank
    @Size(max = 60)
    private String recipientName; 

    @NotBlank
    @Size(max = 45)
    @Email
    private String sender; 

    @NotBlank
    @Size(max = 120)
    private String subject; 

    @NotBlank
    @Size(max = 256)
    private String content; 
}

