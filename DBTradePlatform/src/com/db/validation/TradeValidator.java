package com.db.validation;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.db.entity.Trade;

//Generic Validation can be added
public class TradeValidator implements ValidatorIntreface {

	//filter all passed Maturity date Trades
	@Override
	public List<Trade> filterPastMaturityDateTrades(List<Trade> trades) {
		return trades.stream().filter(trade -> !(trade.getMaturityDate().isBefore(LocalDate.now())))
				.collect(Collectors.toList());

	}

	//Validate if input trade version is latest in existing
	@Override
	public  boolean isVersionLatest(LinkedHashMap<Integer, Trade> tradeValues, Trade trade) {
		if (getLatestVersionInTrade(tradeValues) <= trade.getVersion()) {
			return true;
		} else {
			return false;
		}
	}

	//get Latest version for a trade
	@Override
	public  Integer getLatestVersionInTrade(LinkedHashMap<Integer, Trade> tradeValues) {
		final long count = tradeValues.entrySet().stream().count();
		return tradeValues.entrySet().stream().skip(count - 1).findFirst().get().getKey();
	}

}
