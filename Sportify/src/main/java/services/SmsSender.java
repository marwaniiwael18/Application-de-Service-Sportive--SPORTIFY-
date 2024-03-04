package services;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SmsSender {
    public static final String ACCOUNT_SID = "AC77e5c94925607278c58cb5751cdf38ae";
    public static final String AUTH_TOKEN = "ad3efdefa11557f8dc0db7e1805d3575";

    public static void sendSms(String numeroDestinataire, String contenuMessage) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+216"+numeroDestinataire),
                        new com.twilio.type.PhoneNumber("+17817421532"),
                        contenuMessage)
                .create();

        System.out.println(message.getSid());
    }
}