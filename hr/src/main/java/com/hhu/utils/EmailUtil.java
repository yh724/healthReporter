package com.hhu.utils;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 邮件工具类，打卡成功后用来发送邮件提醒打卡成功。
 */

public class EmailUtil {

    public static void sendMessage(String receiveAccount) throws UnsupportedEncodingException, MessagingException {
        String sendAccount = "xxxxxxx@qq.com";
        String password = "";                                   // SMTP账号的独立密码，可以在QQ邮箱设置中获取
        Properties props = new Properties();                    // 参数配置，下面是通用配置
        props.put("mail.smtp.host","smtp.qq.com");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback","false");
        props.put("mail.smtp.port","465");
        props.put("mail.smtp.socketFactory.port","465");            //Linux下需要设置此项，Windows默认localhost为127.0.0.1
        props.put("mail.smtp.localhost","127.0.0.1");
        //鉴权验证
        props.put("mail.smtp.auth", "true");

        Session session  = Session.getInstance(props);
        session.setDebug(true);

        MimeMessage message = createMimeMessage(session,sendAccount,receiveAccount);
        Transport transport = session.getTransport();
        transport.connect(sendAccount,password);
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
    }

    private static MimeMessage createMimeMessage(Session session, String sendAccount, String receiveAccount) throws MessagingException, UnsupportedEncodingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = sdf.format(new Date());
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendAccount,"自动健康打卡小助手","utf-8"));
        message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(receiveAccount));
        message.setSubject("打卡成功提醒","utf-8");
        message.setContent("您于"+time+"打卡成功！","text/html;charset=utf-8");
        message.setSentDate(new Date());
        return message;
    }
}
