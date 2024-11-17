package com.jiba.pcm.service.mailer;

public interface Imail {
    void sendMail(String to, String subject, String body);
}
