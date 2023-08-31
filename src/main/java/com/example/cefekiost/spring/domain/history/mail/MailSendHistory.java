package com.example.cefekiost.spring.domain.history.mail;

import com.example.cefekiost.spring.domain.BaseEntity;
import com.example.cefekiost.spring.domain.order.OrderStatus;
import com.example.cefekiost.spring.domain.orderProduct.OrderProduct;
import com.example.cefekiost.spring.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MailSendHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromEmail;
    private String toEmail;
    private String title;
    private String content;

    @Builder
    public MailSendHistory(String fromEmail, String toEmail, String title, String content) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.title = title;
        this.content = content;
    }
}
