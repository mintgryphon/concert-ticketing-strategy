package kz.astanait.strategy.pricing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TicketPricingStrategyTest {

    private MarketDemandSource fakeDemandSource;
    private TicketPricingSelector selector;

    @BeforeEach
    void setUp() {
        // Fake реализация для изоляции тестов от внешних факторов
        fakeDemandSource = sector -> 1.5;
        selector = new TicketPricingSelector(fakeDemandSource);
    }

    // Поставщик всех стратегий для @ParameterizedTest
    static Stream<TicketPricingStrategy> provideStrategies() {
        MarketDemandSource fixedSource = sector -> 1.2;
        return Stream.of(
                new EarlyBirdPricingStrategy(),
                new SurgeDynamicPricingStrategy(fixedSource),
                new LoyaltyVipPricingStrategy()
        );
    }

    @ParameterizedTest
    @MethodSource("provideStrategies")
    void testPriceIsNeverNegativeOrZeroForValidInputs(TicketPricingStrategy strategy) {
        TicketPricingContext context = new TicketPricingContext(strategy);
        double result = context.executePricing(50.0, "VIP", 20);
        assertTrue(result > 0.0, "Ticket price must strictly be positive for positive base price");
    }

    @ParameterizedTest
    @MethodSource("provideStrategies")
    void testNegativeBasePriceThrowsException(TicketPricingStrategy strategy) {
        TicketPricingContext context = new TicketPricingContext(strategy);
        assertThrows(IllegalArgumentException.class, () -> context.executePricing(-10.0, "REGULAR", 0));
    }

    @Test
    void testSelectorRuleMemberWithPointsSelectsLoyalty() {
        TicketPricingStrategy strategy = selector.selectStrategy(10, true, 50, 40);
        assertInstanceOf(LoyaltyVipPricingStrategy.class, strategy);
    }

    @Test
    void testSelectorRuleEarlyBookingSelectsEarlyBird() {
        TicketPricingStrategy strategy = selector.selectStrategy(35, false, 0, 50);
        assertInstanceOf(EarlyBirdPricingStrategy.class, strategy);
    }

    @Test
    void testSelectorRuleHighOccupancySelectsSurge() {
        TicketPricingStrategy strategy = selector.selectStrategy(5, false, 0, 85);
        assertInstanceOf(SurgeDynamicPricingStrategy.class, strategy);
    }

    @Test
    void testContextThrowsOnNullStrategy() {
        assertThrows(IllegalArgumentException.class, () -> new TicketPricingContext(null));
        TicketPricingContext context = new TicketPricingContext(new EarlyBirdPricingStrategy());
        assertThrows(IllegalArgumentException.class, () -> context.setStrategy(null));
    }
}