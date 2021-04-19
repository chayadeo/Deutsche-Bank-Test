package com.db.repository;

import java.util.List;

import com.db.entity.Trade;
import com.exception.TradeException;

public interface TradeInterface {
	public List<Trade> saveTrade(Trade trade) throws TradeException;
	public List<Trade> getAllTrades();
	public void updateExpiry();
	public void setExpiryonTrade(List<Trade> trades);
}
