package com.db.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.db.entity.Trade;
import com.db.service.TradeProcessor;
import com.exception.TradeException;

public class TradeReader {

	private static List<Trade> trades = new ArrayList<>();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public void readInput() {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		scanner.skip("\r\n");
		for (int line = 0; line < n; line++) {
			String trade = scanner.nextLine();
			addTradeToList(trade);
		}
		scanner.close();
	}

	private void addTradeToList(String trade) {
		try {
			String[] tradeAttributes = trade.split(" ");
			Trade trd = new Trade();
			trd.setTradeId(tradeAttributes[0]);
			trd.setVersion(Integer.parseInt(tradeAttributes[1]));
			trd.setCounterPartyId(tradeAttributes[2]);
			trd.setBookId(tradeAttributes[3]);
			trd.setMaturityDate(LocalDate.parse(tradeAttributes[4], formatter));
			trd.setCreatedDate(LocalDate.parse(tradeAttributes[5], formatter));
			trd.setExpired(tradeAttributes[6]);
			// for each input field we can have validation
			trades.add(trd);
		} catch (Exception e) {
			System.out.println("Issue while capturing trade: " + trade);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TradeReader t = new TradeReader();
		t.readInput();
		TradeProcessor p = new TradeProcessor();
		try {
			 List<Trade> outputTrades=p.process(trades);
			 System.out.println("outputTrades--> "+outputTrades.size());
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
