package com.example.cefekiost.spring.client.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailSendClient {
    public boolean sendEmail(String fromEmail, String email, String title, String content) {
        // 실제로 외부 API 이용한다는 가정
        log.info("메일전송");
        throw new IllegalArgumentException("메일 전송");
        //return true;
    }
}
