package com.spoons.sehaehae.common.util;

import com.spoons.sehaehae.member.dto.EmailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

@Slf4j
@Service
public class EmailUtil {

    @Value("${spring.mail.host}")
    private String emailHost;

    @Value("${spring.mail.port}")
    private int emailPort;

    @Value("${spring.mail.username}")
    private String emailUser;

    @Value("${spring.mail.password}")
    private String emailPassword;

    public EmailDTO sndEmail(EmailDTO emailVo) throws MessagingException {
        Transport transport = null;
        try {
            // SMTP 발송 Properties 설정
            Properties props = new Properties();
            props.put("mail.smtps.auth", "true");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            props.put("mail.smtp.host", emailHost);
            props.put("mail.smtp.port", emailPort);
            props.put("mail.smtp.ssl.trust", emailHost);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            // SMTP Session 생성
            Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(emailUser, emailPassword);
                }
            });


            // Mail 조립
            MimeMessage mimeMessage = new MimeMessage(mailSession);

            mimeMessage.setFrom(new InternetAddress(emailUser));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailVo.getEmail()));

            // 메일 제목
            String subject = "세탁해병대 회원가입 - 이메일 인증";
            mimeMessage.setSubject(subject);

            // 첨부파일 필요한 경우
            MimeBodyPart htmlBodyPart = new MimeBodyPart();
            // 메일 본문 (.setText를 사용하면 단순 텍스트 전달 가능)
            String html = "" +
                    "<html>" +
                    "   <head><title>이메일 인증</title></head>" +
                    "   <body>" +
                    "   " + emailVo.getContent() +
                    "   </body>"
                    + "</html>";
            htmlBodyPart.setContent(html, "text/html; charset=UTF-8");

            Multipart multipart = new MimeMultipart("related");
            multipart.addBodyPart(htmlBodyPart);

            mimeMessage.setContent(multipart);

            // Mail 발송
            transport = mailSession.getTransport();
            transport.connect();
            transport.sendMessage(
                    mimeMessage
                    , mimeMessage.getAllRecipients()
            );
            log.info("발송 성공 하였습니다. ");
            emailVo.setSuccess(true);
            emailVo.setProcDate(new Date());
        } catch (Exception e) {
            log.error("메일 발송 오류!! : {}", e);
            emailVo.setSuccess(false);
            emailVo.setProcDate(new Date());
        } finally {
            transport.close();
        }
        return emailVo;
    }
}
