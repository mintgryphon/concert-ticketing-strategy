package kz.astanait.strategy.pricing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PricingRefactoringTest {

    @Test
    void testEarlyBirdPricing() {
        MonolithicPricingCalculator calc = new MonolithicPricingCalculator();
        double price = calc.calculatePrice(MonolithicPricingCalculator.PricingType.EARLY_BIRD, 100.0, 1.0, 0);
        assertEquals(90.0, price, 0.001); // 100 * 0.85 + 5.0
    }

    @Test
    void testSurgeDemandPricing() {
        MonolithicPricingCalculator calc = new MonolithicPricingCalculator();
        double price = calc.calculatePrice(MonolithicPricingCalculator.PricingType.SURGE_DEMAND, 100.0, 1.4, 0);
        assertEquals(140.0, price, 0.001);
    }

    @Test
    void testVipLoyaltyPricing() {
        MonolithicPricingCalculator calc = new MonolithicPricingCalculator();
        double price = calc.calculatePrice(MonolithicPricingCalculator.PricingType.VIP_LOYALTY, 100.0, 1.0, 40);
        assertEquals(80.0, price, 0.001); // 100 - min(30, 20) = 80
    }
}