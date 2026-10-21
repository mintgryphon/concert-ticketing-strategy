package kz.astanait.strategy.pricing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PricingRefactoringTest {

    @Test
    void testEarlyBirdPricing() {
        TicketPricingContext context = new TicketPricingContext(new EarlyBirdPricingStrategy());
        double price = context.executePricing(100.0, "STANDARD", 0);
        assertEquals(90.0, price, 0.001); // 100 * 0.85 + 5.0
    }

    @Test
    void testSurgeDemandPricing() {
        MarketDemandSource fakeSource = sector -> 1.4;
        TicketPricingContext context = new TicketPricingContext(new SurgeDynamicPricingStrategy(fakeSource));
        double price = context.executePricing(100.0, "VIP", 0);
        assertEquals(140.0, price, 0.001);
    }

    @Test
    void testVipLoyaltyPricing() {
        TicketPricingContext context = new TicketPricingContext(new LoyaltyVipPricingStrategy());
        double price = context.executePricing(100.0, "VIP", 40);
        assertEquals(80.0, price, 0.001); // 100 - min(30, 20) = 80
    }
}