package com.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.db.entity.Trade;
import com.db.service.TradeProcessor;
import com.db.validation.TradeValidator;

public class TradeValidatorTest {
	TradeProcessor processor=new TradeProcessor();
	
	@Test
	public void filterPastMaturityDateTradesTest() {

		List<Trade> trades = createTradeEntries();
		trades = TradeValidator.filterPastMaturityDateTrades(trades);
		assertEquals(3, trades.size());

	}
	
	@Test
	public void isVersionLatestTest() {
		LinkedHashMap<Integer, Trade> tradeValues=createTradeMap();
		Trade trade = addTrade("T2", 3, "CP-2", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		assertEquals(true, TradeValidator.isVersionLatest(tradeValues,trade));

	}

	
	@Test
	public void isVersionLatestNotTest() {
		LinkedHashMap<Integer, Trade> tradeValues=createTradeMap();
		Trade trade = addTrade("T2", 1, "CP-2", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		assertEquals(false, TradeValidator.isVersionLatest(tradeValues,trade));

	}
	
	@Test
	public void validateMaturityDateTest() {

		Trade trade1 = addTrade("T1", 1, "CP-1", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		assertEquals(true, TradeValidator.validateMaturityDate(trade1));
	}
	
	@Test
	public void validateMaturityDatePastTest() {

		Trade trade1 = addTrade("T1", 1, "CP-1", "B1", LocalDate.of(2020, Month.MAY, 20), LocalDate.now(), "N");
		assertEquals(false, TradeValidator.validateMaturityDate(trade1));
	}
	

	private List<Trade> createTradeEntries() {
		List<Trade> trades = new ArrayList<Trade>();

		Trade trade1 = addTrade("T1", 1, "CP-1", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade2 = addTrade("T2", 2, "CP-2", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade3 = addTrade("T3", 3, "CP-3", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade4 = addTrade("T4", 3, "CP-3", "B1", LocalDate.of(2020, Month.MAY, 20), LocalDate.now(), "N");

		trades.add(trade1);
		trades.add(trade2);
		trades.add(trade3);
		trades.add(trade4);
		return trades;

	}
	
	private LinkedHashMap<Integer, Trade> createTradeMap() {
		LinkedHashMap<Integer, Trade> tradeMap= new LinkedHashMap<Integer, Trade>();

		Trade trade1 = addTrade("T1", 1, "CP-1", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade2 = addTrade("T2", 2, "CP-2", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade3 = addTrade("T4", 3, "CP-3", "B1", LocalDate.of(2020, Month.MAY, 20), LocalDate.now(), "N");

		tradeMap.put(trade1.getVersion(), trade1);
		tradeMap.put(trade2.getVersion(), trade2);
		tradeMap.put(trade3.getVersion(), trade3);
		return tradeMap;

	}

	private Trade addTrade(String tradeId, int version, String countryPartId, String bookId, LocalDate maturityDate,
			LocalDate createdDate, String isExpired) {
		Trade trade = new Trade();
		trade.setTradeId(tradeId);
		trade.setVersion(version);
		trade.setCounterPartyId(countryPartId);
		trade.setBookId(bookId);
		trade.setMaturityDate(maturityDate);

		trade.setCreatedDate(createdDate);
		trade.setExpired(isExpired);
		return trade;
	}

}
