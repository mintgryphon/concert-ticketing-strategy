package kz.astanait.strategy.pricing;

public class SurgeDynamicPricingStrategy implements TicketPricingStrategy {
    private final MarketDemandSource demandSource;

    public SurgeDynamicPricingStrategy(MarketDemandSource demandSource) {
        this.demandSource = demandSource;
    }

    @Override
    public double calculate(double basePrice, String sector, int loyaltyPoints) {
        if (basePrice < 0) {
            throw new IllegalArgumentException("Base price cannot be negative");
        }
        double multiplier = demandSource.getDemandMultiplier(sector);
        if (multiplier < 1.0) {
            multiplier = 1.0;
        }
        return basePrice * multiplier;
    }
}