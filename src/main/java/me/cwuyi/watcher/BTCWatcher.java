package me.cwuyi.watcher;

import me.cwuyi.constant.BTCPriceAPI;
import me.cwuyi.util.HttpAgent;

public class BTCWatcher implements Watcher {


    public Object getEvent() {
        return HttpAgent.getResult(BTCPriceAPI.BTC_YAHOO_FINANCE_URL);
    }
}
