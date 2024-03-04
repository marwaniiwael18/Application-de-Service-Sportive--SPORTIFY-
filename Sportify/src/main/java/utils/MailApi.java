package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class MailApi {

    public static void sendEmail(String mailto,String sub,String msg){
        final String password="hrtf cpgj quhv ltrl";
        final String email="sahargaiche6@gmail.com";
        Properties properties=new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.ssl.trust","smtp.gmail.com");

        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email,password);
            }
        });
        try{
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(mailto));
            message.setSubject(sub);
            message.setText(msg);
            Transport.send(message);
            System.out.println("Done");
        }catch (MessagingException e){
            System.out.println(e.getMessage());
        }



    }
}
