package me.cwuyi.notifier;

import com.alibaba.fastjson.JSON;
import me.cwuyi.bean.SlackWebHookMess;
import me.cwuyi.constant.SlackProperties;
import me.cwuyi.util.HttpAgent;

import java.util.Properties;

public class SlackNotifier implements Notifier{
    private static String slack_webhook_url;
    private static final String MESSAGE_TEMPLATE = "BTC-USD已超过%s$，下一次将在%s$通知，当前时间%s";

    static {
        try {
            Properties properties = new Properties();
            properties.load(SlackNotifier.class.getResourceAsStream("/slack.properties"));
            slack_webhook_url = properties.getProperty(SlackProperties.SLACK_WEBHOOK_URL_PRO);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(String[] params) {

        String message = String.format(MESSAGE_TEMPLATE, params[0], params[1], params[2]);
        SlackWebHookMess webHookMess = new SlackWebHookMess(message);

        try {
            HttpAgent.postData(slack_webhook_url, JSON.toJSONString(webHookMess));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        SlackWebHookMess webHookMess = new SlackWebHookMess(message);

        try {
            HttpAgent.postData(slack_webhook_url, JSON.toJSONString(webHookMess));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
