package me.cwuyi.Monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.cwuyi.constant.BTCPriceProperties;
import me.cwuyi.notifier.Notifier;
import me.cwuyi.notifier.SlackNotifier;
import me.cwuyi.watcher.BTCWatcher;
import me.cwuyi.watcher.WebWatcher;
import me.cwuyi.watcher.Watcher;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class BTCMonitor implements Runnable{

    private static long MIN_SEND_INTERVAL;
    private static String PIRCE_HIGH_BAR_INIT;

    static {
        try {
            Properties properties = new Properties();
            properties.load(WebWatcher.class.getResourceAsStream("/watcher.properties"));
            MIN_SEND_INTERVAL = Long.parseLong(properties.getProperty(BTCPriceProperties.BTC_ISALIVE_SLEEPTIME));
            PIRCE_HIGH_BAR_INIT = properties.getProperty(BTCPriceProperties.BTC_MONITOR_PRICE_INIT_PRO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Notifier notifier = new SlackNotifier();
        Watcher btcWatcher = new BTCWatcher();

        BigDecimal priceHighBar = new BigDecimal(PIRCE_HIGH_BAR_INIT);

        while (true) {
            String btcJson = btcWatcher.getEvent().toString();
            JSONObject jsonObject = JSON.parseObject(btcJson);
            JSONObject result = jsonObject.getJSONObject("quoteResponse")
                    .getJSONArray("result").getJSONObject(0);

            String regularMarketPrice = result.getString("regularMarketPrice");
            System.out.println("BTC-USD当前市场价格：" + regularMarketPrice + " USD");
            BigDecimal nowPrice = new BigDecimal(regularMarketPrice);

            if (priceHighBar.compareTo(nowPrice) < 0) {
                LocalDateTime now = LocalDateTime.now();
                String param = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "，现在价格：" + nowPrice.toString() + " USD";
                notifier.send(new String[]{priceHighBar.toString(), (priceHighBar = priceHighBar.add(new BigDecimal(100))).toString(), param});
            }

            try {
                Thread.sleep(MIN_SEND_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
