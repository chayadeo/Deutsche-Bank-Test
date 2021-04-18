package com.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.db.entity.Trade;
import com.db.repository.TradeRepository;
import com.db.service.TradeProcessor;
import com.exception.TradeException;

import org.junit.jupiter.api.Test;

class TestTradePlatform {
	TradeProcessor processor = new TradeProcessor();
	TradeRepository repo = new TradeRepository();

	@Test
	// test if old version comes later
	void testOlderVersionLater() {
		List<Trade> trades = createTradeEntriesForolderVersionLater();

		trades.stream().forEach(t -> {
			try {
				repo.saveTrade(t);
			} catch (TradeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		Trade trade4 = addTrade("T2", 1, "CP-1", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");

		Exception exception = assertThrows(TradeException.class, () -> repo.saveTrade(trade4));

		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains("is not latest"));

	}

	@Test
	// test override
	void testOverride() {
		List<Trade> trades = createTradeEntriesForTestOverride();
		trades.stream().forEach(t -> {
			try {
				repo.saveTrade(t);
			} catch (TradeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		List<Trade> finalTrades = repo.getAllTrades();
		int count = finalTrades.size();
		assertEquals(3, count);
	}

	@Test
	// PastMaturity should be skipped
	void testPastMaturtiy() {
		List<Trade> trades = createTradeEntriesForPastMaturtiy();
		trades.stream().forEach(t -> {
			try {
				repo.saveTrade(t);
			} catch (TradeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		List<Trade> finalTrades = repo.getAllTrades();
		int count = finalTrades.size();
		assertEquals(2, count);
	}



	private List<Trade> createTradeEntriesForolderVersionLater() {
		List<Trade> trades = new ArrayList<Trade>();

		Trade trade1 = addTrade("T1", 1, "CP-1", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade2 = addTrade("T2", 2, "CP-2", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade3 = addTrade("T3", 3, "CP-3", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");

		trades.add(trade1);
		trades.add(trade2);
		trades.add(trade3);
		return trades;

	}

	private List<Trade> createTradeEntriesForTestOverride() {
		List<Trade> trades = new ArrayList<Trade>();

		Trade trade1 = addTrade("T1", 1, "CP-1", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade2 = addTrade("T2", 1, "CP-1", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade3 = addTrade("T2", 1, "CP-2", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade4 = addTrade("T3", 3, "CP-3", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");

		trades.add(trade1);
		trades.add(trade2);
		trades.add(trade3);
		trades.add(trade4);
		return trades;

	}

	private List<Trade> createTradeEntriesForPastMaturtiy() {
		List<Trade> trades = new ArrayList<Trade>();

		Trade trade1 = addTrade("T1", 1, "CP-1", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade2 = addTrade("T2", 1, "CP-1", "B1", LocalDate.of(2020, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade3 = addTrade("T2", 2, "CP-2", "B1", LocalDate.of(2020, Month.MAY, 20), LocalDate.now(), "N");
		Trade trade4 = addTrade("T3", 3, "CP-3", "B1", LocalDate.of(2021, Month.MAY, 20), LocalDate.now(), "N");

		trades.add(trade1);
		trades.add(trade2);
		trades.add(trade3);
		trades.add(trade4);
		return trades;

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
