package com.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.db.entity.Trade;
import com.db.service.TradeProcessor;
import com.db.validation.TradeValidator;
import com.db.validation.ValidatorIntreface;

public class TradeValidatorTest {
	TradeProcessor processor = new TradeProcessor();
	ValidatorIntreface validator=new TradeValidator();
	LinkedHashMap<Integer, Trade> tradeMap;

	@Test
	public void filterPastMaturityDateTradesTest() {

		LinkedHashMap<Integer, Trade> tradeMap = createTradeMap();
		List<Trade> trades = tradeMap.values().stream().collect(Collectors.toList());
		
		Trade trade4 = addTrade("T1", 5, "CP-3", "B1", LocalDate.of(2020, Month.MAY, 20), LocalDate.now(), "N");
		trades.add(trade4);
		trades = (ArrayList<Trade>) validator.filterPastMaturityDateTrades(trades);
		assertEquals(3, trades.size());

	}

	@Test
	public void isVersionLatestTest() {
		LinkedHashMap<Integer, Trade> tradeValues = createTradeMap();
		Trade trade = addTrade("T1", 5, "CP-2", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		assertEquals(true, validator.isVersionLatest(tradeValues, trade));

	}

	@Test
	public void isVersionLatestNotTest() {
		LinkedHashMap<Integer, Trade> tradeValues = createTradeMap();
		Trade trade = addTrade("T2", 2, "CP-2", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		assertEquals(false, validator.isVersionLatest(tradeValues, trade));

	}

	@Before
	private LinkedHashMap<Integer, Trade> createTradeMap() {
		tradeMap=new LinkedHashMap<Integer, Trade>();
		Trade trade1 = addTrade("T1", 1, "CP-1", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade2 = addTrade("T1", 2, "CP-2", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade3 = addTrade("T1", 3, "CP-3", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");

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
	
	@After
	private void resetData()
	{
		tradeMap=null;
	}

}
