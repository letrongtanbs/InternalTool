package com.tvj.internaltool.service.impl;

import static org.mockito.Mockito.when;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage mimeMessage;

    @Before
    public void setUp() {
        // Initialize value for @Value parameter
        ReflectionTestUtils.setField(emailService, "from", "ngocdc@tinhvan.com");
    }

    // ---------- sendSimpleMessage START ---------

    @Test
    public void sendSimpleMessage_success() throws MessagingException {
        String to = "ngocdc@tinhvan.com";
        String subject = "test subject";
        String text = "test text";

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendSimpleMessage(to, subject, text);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sendSimpleMessage_whenToIsNull() throws MessagingException {
        String to = null;
        String subject = "test subject";
        String text = "test text";

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendSimpleMessage(to, subject, text);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sendSimpleMessage_whenSubjectIsNull() throws MessagingException {
        String to = "ngocdc@tinhvan.com";
        String subject = null;
        String text = "test text";

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendSimpleMessage(to, subject, text);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sendSimpleMessage_whenTextIsNull() throws MessagingException {
        String to = "ngocdc@tinhvan.com";
        String subject = "test subject";
        String text = null;

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendSimpleMessage(to, subject, text);
    }

    // ---------- sendSimpleMessage END ---------

}
