package kz.astanait.strategy.pricing;

public class TicketPricingContext {
    private TicketPricingStrategy strategy;

    public TicketPricingContext(TicketPricingStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        this.strategy = strategy;
    }

    public void setStrategy(TicketPricingStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        this.strategy = strategy;
    }

    public TicketPricingStrategy getStrategy() {
        return strategy;
    }

    public double executePricing(double basePrice, String sector, int loyaltyPoints) {
        return strategy.calculate(basePrice, sector, loyaltyPoints);
    }
}