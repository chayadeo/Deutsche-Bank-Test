package com.db.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.db.entity.Trade;
import com.db.validation.TradeValidator;
import com.exception.TradeException;

public class TradeRepository {

	private LinkedHashMap<String, LinkedHashMap<Integer, Trade>> tradeMap = new LinkedHashMap<>();

	//To save Trade entry in Collection
	public List<Trade> saveTrade(Trade trade) throws TradeException {

		LinkedHashMap<Integer, Trade> tradeValues = null;
			if (tradeMap.get(trade.getTradeId()) == null) {
				tradeValues = new LinkedHashMap<Integer, Trade>();
				tradeValues.put(trade.getVersion(), trade);
			}

			else {
				tradeValues = tradeMap.get(trade.getTradeId());
				if (TradeValidator.isVersionLatest(tradeValues, trade))
					tradeValues.put(trade.getVersion(), trade);
				else {
					throw new TradeException(
							"Trade Id: + " + trade.getTradeId() + " Version: " + trade.getVersion() + " is not latest");
				}

			}

			tradeMap.put(trade.getTradeId(), tradeValues);

			System.out.println("Trade ID: " + trade.getTradeId() + " Saved.");
		
		return getAllTrades();
	}

	public List<Trade> getAllTrades() {
		List<LinkedHashMap<Integer, Trade>> trade = new ArrayList<>();

		trade = tradeMap.values().stream().collect(Collectors.toList());
		List<Trade> trades = trade.stream().flatMap(t -> t.values().stream()).collect(Collectors.toList());

		return trades;
	}

	public void updateExpiry() {
		List<Trade> trades = getAllTrades();
		setExpiryonTrade(trades);
	}

	private void setExpiryonTrade(List<Trade> trades) {
		trades.stream().filter(t -> t.getMaturityDate().isBefore(LocalDate.now())).forEach(t -> t.setExpired("Y"));
	}

}
