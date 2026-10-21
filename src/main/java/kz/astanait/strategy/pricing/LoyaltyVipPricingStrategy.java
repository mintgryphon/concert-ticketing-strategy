package kz.astanait.strategy.pricing;

public class LoyaltyVipPricingStrategy implements TicketPricingStrategy {
    @Override
    public double calculate(double basePrice, String sector, int loyaltyPoints) {
        if (basePrice < 0) {
            throw new IllegalArgumentException("Base price cannot be negative");
        }
        // Скидка баллами (максимум 30% от цены), минимальная стоимость билета 10.0
        double discount = Math.min(basePrice * 0.30, loyaltyPoints * 0.5);
        return Math.max(10.0, basePrice - discount);
    }
}