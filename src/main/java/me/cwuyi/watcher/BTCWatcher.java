package me.cwuyi.watcher;

import me.cwuyi.constant.BTCPriceProperties;
import me.cwuyi.util.HttpAgent;

public class BTCWatcher implements Watcher {


    public Object getEvent() {
        return HttpAgent.getResult(BTCPriceProperties.BTC_YAHOO_FINANCE_URL);
    }
}
