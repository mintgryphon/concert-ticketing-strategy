package kz.astanait.strategy;

import kz.astanait.strategy.pricing.*;
import kz.astanait.strategy.risk.*;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MarketDemandSource demandSource = new SimulatedMarketDemandSource();
        TicketPricingSelector pricingSelector = new TicketPricingSelector(demandSource);
        RiskScoringSelector riskSelector = new RiskScoringSelector();

        TicketPricingContext pricingContext = new TicketPricingContext(new EarlyBirdPricingStrategy());
        RiskScoringContext riskContext = new RiskScoringContext(new DomainBlacklistRiskStrategy());

        System.out.println("=== Concert Ticketing Strategy System Initialized ===");

        while (true) {
            System.out.println("\n-------------------------------------------------------------");
            System.out.println("1. Process same input across ALL Pricing Strategies");
            System.out.println("2. Process same input across ALL Risk Scoring Strategies");
            System.out.println("3. Automatic strategy selection via Selectors");
            System.out.println("4. Runtime Strategy Switching demonstration");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> {
                    double base = 100.0;
                    String sector = "VIP-FRONT";
                    int points = 50;

                    System.out.printf("\n--- Processing base ticket $%.2f, sector %s, %d points across ALL strategies ---\n",
                            base, sector, points);

                    TicketPricingStrategy earlyBird = new EarlyBirdPricingStrategy();
                    TicketPricingStrategy surge = new SurgeDynamicPricingStrategy(demandSource);
                    TicketPricingStrategy loyalty = new LoyaltyVipPricingStrategy();

                    System.out.printf("1. EarlyBirdStrategy:        $%.2f\n", earlyBird.calculate(base, sector, points));
                    System.out.printf("2. SurgeDynamicStrategy:     $%.2f\n", surge.calculate(base, sector, points));
                    System.out.printf("3. LoyaltyVipStrategy:       $%.2f\n", loyalty.calculate(base, sector, points));
                }
                case "2" -> {
                    BookingRiskRequest request = new BookingRiskRequest("scalper_buyer@tempmail.com", "198.51.100.4", 5, 8);
                    System.out.println("\n--- Processing customer request across ALL Risk Strategies ---");
                    System.out.println("Customer: " + request.getCustomerEmail() + " | IP: " + request.getClientIp()
                            + " | Attempts: " + request.getAttemptsLastTenMinutes() + " | Tickets: " + request.getRequestedTickets());

                    RiskScoringStrategy velocity = new VelocityRiskStrategy();
                    RiskScoringStrategy blacklist = new DomainBlacklistRiskStrategy();
                    RiskScoringStrategy subnet = new PaymentSubnetRiskStrategy();

                    System.out.printf("1. VelocityRiskStrategy:        Risk Score = %d/100\n", velocity.calculateRiskScore(request));
                    System.out.printf("2. DomainBlacklistRiskStrategy: Risk Score = %d/100\n", blacklist.calculateRiskScore(request));
                    System.out.printf("3. PaymentSubnetRiskStrategy:   Risk Score = %d/100\n", subnet.calculateRiskScore(request));
                }
                case "3" -> {
                    System.out.println("\n--- Automatic Strategy Selection by Selectors ---");
                    System.out.println("Scenario: Member with 100 points booking 10 days before concert with 90% occupancy:");
                    TicketPricingStrategy autoPricing = pricingSelector.selectStrategy(10, true, 100, 90);
                    pricingContext.setStrategy(autoPricing);
                    System.out.println("Selector chose: " + autoPricing.getClass().getSimpleName());
                    System.out.printf("Resulting price: $%.2f\n", pricingContext.executePricing(120.0, "VIP", 100));

                    System.out.println("\nScenario: Flash sale on high-demand rock concert:");
                    RiskScoringStrategy autoRisk = riskSelector.selectStrategy(true, false);
                    riskContext.setStrategy(autoRisk);
                    System.out.println("Selector chose: " + autoRisk.getClass().getSimpleName());
                    BookingRiskRequest botReq = new BookingRiskRequest("fan@gmail.com", "10.0.0.1", 7, 10);
                    System.out.printf("Resulting risk score: %d/100\n", riskContext.evaluateRisk(botReq));
                }
                case "4" -> {
                    System.out.println("\n--- Runtime Strategy Switching in Context ---");
                    System.out.println("Initial Strategy: " + pricingContext.getStrategy().getClass().getSimpleName());
                    System.out.printf("Calculated with initial: $%.2f\n", pricingContext.executePricing(100.0, "BALCONY", 10));

                    System.out.println("--> Changing strategy to SurgeDynamicPricingStrategy at runtime...");
                    pricingContext.setStrategy(new SurgeDynamicPricingStrategy(demandSource));
                    System.out.println("Current Strategy: " + pricingContext.getStrategy().getClass().getSimpleName());
                    System.out.printf("Calculated with switched: $%.2f\n", pricingContext.executePricing(100.0, "BALCONY", 10));

                    System.out.println("--> Changing strategy to LoyaltyVipPricingStrategy at runtime...");
                    pricingContext.setStrategy(new LoyaltyVipPricingStrategy());
                    System.out.println("Current Strategy: " + pricingContext.getStrategy().getClass().getSimpleName());
                    System.out.printf("Calculated with switched: $%.2f\n", pricingContext.executePricing(100.0, "BALCONY", 10));
                }
                case "0" -> {
                    System.out.println("Exiting application.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}