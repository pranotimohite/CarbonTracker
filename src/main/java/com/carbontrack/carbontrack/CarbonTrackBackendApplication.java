package com.carbontrack.carbontrack;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class CarbonTrackBackendApplication {

	public static void main(String[] args) {
				SpringApplication.run(CarbonTrackBackendApplication.class, args);
		
		/*
		 * List<Transaction> transactions = List.of( new Transaction("Alice", 5000,
		 * 10150), new Transaction("Alice", 2000, 10200), new Transaction("Bob", 12000,
		 * 10400), new Transaction("Alice", 6000, 10700) );
		 * 
		 * System.out.println(FraudDetector.findFraudulentUsers(transactions));
		 */
	}
}

class Transaction {
	String userId;
	int amount;
	long timestamp;

	public Transaction(String userId, int amount, long timestamp) {
		this.userId = userId;
		this.amount = amount;
		this.timestamp = timestamp;
	}
}

class FraudDetector {

	public static Map<String, String> findFraudulentUsers(List<Transaction> transactions) {
		Set<String> fraudulentUsers = new HashSet<>();
		
		Map<String,String> fraudulentUsersWithReason = new HashMap();

		// Group transactions by userId field
		Map<String, List<Transaction>> groupedByUser =
				transactions.stream().collect(Collectors.groupingBy(t -> t.userId));

		for (String user : groupedByUser.keySet()) {
			List<Transaction> userTx = groupedByUser.get(user);

			// Sort by timestamp to efficiently find transactions within time window
			userTx.sort(Comparator.comparingLong(t -> t.timestamp));

			for (int i = 0; i < userTx.size(); i++) {
				Transaction current = userTx.get(i);

				// Rule 1: Amount > 10,000
				if (current.amount > 10000) {
					fraudulentUsers.add(user);
					fraudulentUsersWithReason.put(user, "Amount is more than 10000 Euro!");
				}

				// Rule 2: Another transaction within 100 seconds
				if (i > 0) {
					Transaction previous = userTx.get(i - 1);
					if (current.timestamp - previous.timestamp <= 100) {
						fraudulentUsers.add(user);
						fraudulentUsersWithReason.put(user, "Two transaction within 100 seconds!");
					}
				}
			}
		}
//		return fraudulentUsers;
		return fraudulentUsersWithReason;
	}
}