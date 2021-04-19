package com.db.validation;

import java.util.LinkedHashMap;
import java.util.List;

import com.db.entity.Trade;

public interface ValidatorIntreface {

	public List<Trade> filterPastMaturityDateTrades(List<Trade> trades);
	public boolean isVersionLatest(LinkedHashMap<Integer, Trade> tradeValues, Trade trade);
	public Integer getLatestVersionInTrade(LinkedHashMap<Integer, Trade> tradeValues);
	
}
