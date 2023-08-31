package com.example.cefekiost.spring.api.service.mail;

import com.example.cefekiost.spring.client.mail.MailSendClient;
import com.example.cefekiost.spring.domain.history.mail.MailSendHistory;
import com.example.cefekiost.spring.domain.history.mail.MailSendHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;
    public boolean sendMail(String fromEmail, String email, String title, String content) {

        boolean result = mailSendClient.sendEmail(fromEmail,email,title,content);
        if(result){
            mailSendHistoryRepository.save(
                    MailSendHistory.builder()
                            .fromEmail(fromEmail)
                            .toEmail(email)
                            .title(title)
                            .content(content)
                            .build()
            );
            return true;
        }
        return false;
    }
}
