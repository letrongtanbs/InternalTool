package com.tvj.internaltool.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
