package com.example.cefekiost.spring.api.service.mail;

import com.example.cefekiost.spring.client.mail.MailSendClient;
import com.example.cefekiost.spring.domain.history.mail.MailSendHistory;
import com.example.cefekiost.spring.domain.history.mail.MailSendHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class) // (2)
class MailServiceTest {

    @Mock //(2)
    private MailSendClient mailSendClient;

    @Mock //(2)
    private  MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks // (3)
    private MailService mailService;

    @Test
    @DisplayName("메일 전송 테스트")
    public void sendMail() throws Exception{
        //given
        // (1) -> (2) @ExtendWith(MockitoExtension.class), @Mock 추가
        //MailSendClient mailSendClient = Mockito.mock(MailSendClient.class);
        //MailSendHistoryRepository mailSendHistoryRepository = Mockito.mock(MailSendHistoryRepository.class);
        // (1) -> (3)  @InjectMocks    @Mock을 DI 함
        //MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

        // ------ 이게 귀찮다면 그냥 @MockBeab쓰자

        //when

        // 행위를 설정
        // stubbing
        Mockito.when(mailSendClient.sendEmail(any(String.class),any(String.class),any(String.class),any(String.class)))
                .thenReturn( true );

        // GIVEN 절이라 좀더 명확하긴함 BDD 스타일에 맞게 mockito를 상속받아서 만들었다. 방법은 똑같음
        BDDMockito.given(mailSendClient.sendEmail(any(String.class),any(String.class),any(String.class),any(String.class)))
                .willReturn( true );
        //        // spy 전용 stubbing
//        doReturn(true)
//                .when(mailSendClient)
//                .sendEmail(any(String.class),any(String.class),any(String.class),any(String.class));

        //when
        boolean result = mailService.sendMail("","","","");

        //then
        // mailSendHistoryRepository의 save 매소드가 몇번 실행되엇는지 확인 하는것
        // 실제 mailSendHistoryRepository 가 실행 된 후 Mockito.verify를 써야함
        Mockito.verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
        assertThat(result).isTrue();

    }
}