package src.schedular;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.db.repository.TradeRepository;

public class TradeExpiryMonitor implements Callable<String>{
	TradeRepository tradeRepo=new TradeRepository();
	public static void registerTradeMonitor() {
		  ScheduledExecutorService scheduledExec = Executors.newSingleThreadScheduledExecutor();
		  Callable<String> tradeMonitor=new TradeExpiryMonitor();
		  long milliseconds = 24 * 60 * 60 * 1000;
		  ScheduledFuture<String> scheduledFuture =scheduledExec.schedule(tradeMonitor, milliseconds, TimeUnit.MILLISECONDS);
		  
		  try {
			System.out.println("result = " + scheduledFuture.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String call() throws Exception {
		
		tradeRepo.updateExpiry();
		System.out.println("Executed!");
		return "Called";
	}

}
