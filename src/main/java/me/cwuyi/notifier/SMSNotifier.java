package me.cwuyi.notifier;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import me.cwuyi.constant.SMSProperties;

import java.io.IOException;
import java.util.Properties;

public class SMSNotifier implements Notifier {
    private static int appid;
    private static String appkey;
    private static String[] phoneNumbers;
    private static int templateId;
    private static String smsSign;

    static {
        try {
            Properties properties = new Properties();
            properties.load(SMSNotifier.class.getResourceAsStream("/sms.properties"));

            appid = Integer.parseInt(properties.getProperty(SMSProperties.APP_ID));
            appkey = properties.getProperty(SMSProperties.APP_KEY);
            phoneNumbers = properties.getProperty(SMSProperties.PHONE_NUMBERS).split(",");
            templateId = Integer.parseInt(properties.getProperty(SMSProperties.TEMPLATE_ID));
            smsSign = properties.getProperty(SMSProperties.SMSSIGN);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void send(String[] params){
        SmsMultiSender sender = new SmsMultiSender(appid, appkey);
        try {
            SmsMultiSenderResult result =  sender.sendWithParam("86", phoneNumbers, templateId, params, smsSign, "", "");
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
