package com.db.service;

import java.util.ArrayList;
import java.util.List;

import com.db.entity.Trade;
import com.db.repository.TradeRepository;
import com.db.validation.TradeValidator;
import com.exception.TradeException;

public class TradeProcessor {

	List<Trade> trades;
	private TradeRepository tradeRepository = new TradeRepository();
	List<Trade> outputTrades=new ArrayList<Trade>();

	public List<Trade> process(List<Trade> trades) throws TradeException {

		List<Trade> validTrades = TradeValidator.filterPastMaturityDateTrades(trades);
		if (validTrades != null && validTrades.size() > 0) {

			validTrades.stream().forEach(trade -> {
				try {
					outputTrades=tradeRepository.saveTrade(trade);
				} catch (TradeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		return outputTrades;
	}
}
