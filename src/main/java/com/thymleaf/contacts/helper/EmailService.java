package com.thymleaf.contacts.helper;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {
    public boolean sendEmail(String message, String subject, String to) {
        boolean flag=false;
        String from = "backendemail6@gmail.com";
        //variable for gmail
        String host="smtp.gmail.com";

        //get system  properties
        Properties properties=System.getProperties();

        //setting important information to properties object

        //host set
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        //step 1:To get the session object
        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("backendemail6@gmail.com","cfahxcatumsxxnro");
            }
        });
        session.setDebug(true);

        //step 2: Compose the message[text,multimedia]
        MimeMessage mimeMessage=new MimeMessage(session);
        try{
            mimeMessage.setFrom(from);

            //adding recipient
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

            //adding subject to message
            mimeMessage.setSubject(subject);

            //adding text to message
//            mimeMessage.setText(message);
            mimeMessage.setContent(message,"text/html");

            //step 3: send the message using Transport class
            Transport.send(mimeMessage);
            System.out.println("Sent successfully!");
            flag=true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
