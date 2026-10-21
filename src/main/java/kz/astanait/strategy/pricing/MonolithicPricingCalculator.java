package kz.astanait.strategy.pricing;

public class MonolithicPricingCalculator {

    public enum PricingType {
        EARLY_BIRD,
        SURGE_DEMAND,
        VIP_LOYALTY
    }

    public double calculatePrice(PricingType type, double basePrice, double demandMultiplier, int loyaltyPoints) {
        if (basePrice < 0) {
            throw new IllegalArgumentException("Base price cannot be negative");
        }

        switch (type) {
            case EARLY_BIRD:
                // 15% discount plus fixed reservation fee of 5.0
                return (basePrice * 0.85) + 5.0;

            case SURGE_DEMAND:
                // Base price scaled by dynamic external demand multiplier
                if (demandMultiplier < 1.0) {
                    demandMultiplier = 1.0;
                }
                return basePrice * demandMultiplier;

            case VIP_LOYALTY:
                // Discount based on points: max 30% discount, minimum base price 10.0
                double discount = Math.min(basePrice * 0.30, loyaltyPoints * 0.5);
                return Math.max(10.0, basePrice - discount);

            default:
                throw new UnsupportedOperationException("Unknown pricing type");
        }
    }
}