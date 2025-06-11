package com.mailpoc.mail.adapter;

import com.mailpoc.mail.dto.EmailRequestDTO;

public interface EmailAdapter<T> {
    T adapt(EmailRequestDTO requestDTO);
    String getPlatform();
}
