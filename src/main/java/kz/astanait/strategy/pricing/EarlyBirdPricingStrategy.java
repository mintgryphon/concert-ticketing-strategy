package kz.astanait.strategy.pricing;

public class EarlyBirdPricingStrategy implements TicketPricingStrategy {
    @Override
    public double calculate(double basePrice, String sector, int loyaltyPoints) {
        if (basePrice < 0) {
            throw new IllegalArgumentException("Base price cannot be negative");
        }
        return (basePrice * 0.85) + 5.0;
    }
}