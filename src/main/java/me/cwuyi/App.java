package me.cwuyi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.cwuyi.notifier.Notifier;
import me.cwuyi.notifier.SMSNotifier;
import me.cwuyi.watcher.BTCWatcher;
import me.cwuyi.watcher.Watcher;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) throws Exception {

        Notifier smsNotifier = new SMSNotifier();
        Watcher btcWatcher = new BTCWatcher();

        BigDecimal priceHighBar = new BigDecimal(9000);

        while (true) {
            String btcJson = btcWatcher.getEvent().toString();
            JSONObject jsonObject = JSON.parseObject(btcJson);
            JSONObject result = jsonObject.getJSONObject("quoteResponse")
                    .getJSONArray("result").getJSONObject(0);

            String regularMarketPrice = result.getString("regularMarketPrice");
            BigDecimal nowPrice = new BigDecimal(regularMarketPrice);

            if (priceHighBar.compareTo(nowPrice) < 0) {
                LocalDateTime now = LocalDateTime.now();
                String param = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "，现在价格：" + nowPrice.toString() + " USD";
                smsNotifier.send(new String[]{priceHighBar.toString(), priceHighBar.add(new BigDecimal(100)).toString(), param});
            }

            Thread.sleep(15000);
        }

    }
}
