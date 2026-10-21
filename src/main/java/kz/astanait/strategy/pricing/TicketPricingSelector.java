package kz.astanait.strategy.pricing;

public class TicketPricingSelector {
    private final MarketDemandSource demandSource;

    public TicketPricingSelector(MarketDemandSource demandSource) {
        this.demandSource = demandSource;
    }

    public TicketPricingStrategy selectStrategy(int daysBeforeConcert, boolean isClubMember, int loyaltyPoints, int occupancyPercent) {
        if (isClubMember && loyaltyPoints > 0) {
            return new LoyaltyVipPricingStrategy();
        }
        if (daysBeforeConcert >= 30) {
            return new EarlyBirdPricingStrategy();
        }
        if (occupancyPercent >= 75) {
            return new SurgeDynamicPricingStrategy(demandSource);
        }
        return new EarlyBirdPricingStrategy();
    }
}
