package kz.astanait.strategy.pricing;

public interface TicketPricingStrategy {
    double calculate(double basePrice, String sector, int loyaltyPoints);
}