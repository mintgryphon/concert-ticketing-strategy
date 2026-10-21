package kz.astanait.strategy.risk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class RiskScoringStrategyTest {

    private RiskScoringSelector selector;

    @BeforeEach
    void setUp() {
        selector = new RiskScoringSelector();
    }

    static Stream<RiskScoringStrategy> provideRiskStrategies() {
        return Stream.of(
                new VelocityRiskStrategy(),
                new DomainBlacklistRiskStrategy(),
                new PaymentSubnetRiskStrategy()
        );
    }

    @ParameterizedTest
    @MethodSource("provideRiskStrategies")
    void testRiskScoreAlwaysWithinBounds(RiskScoringStrategy strategy) {
        RiskScoringContext context = new RiskScoringContext(strategy);
        BookingRiskRequest request = new BookingRiskRequest("test@mail.com", "10.0.0.1", 2, 2);
        int score = context.evaluateRisk(request);
        assertTrue(score >= 0 && score <= 100, "Risk score must remain between 0 and 100 inclusive");
    }

    @Test
    void testVelocityRiskHighAttempts() {
        VelocityRiskStrategy strategy = new VelocityRiskStrategy();
        BookingRiskRequest req = new BookingRiskRequest("user@mail.com", "127.0.0.1", 6, 8);
        int score = strategy.calculateRiskScore(req);
        assertEquals(100, score); // (6 * 15) + 40 = 130 -> capped at 100
    }

    @Test
    void testDomainBlacklistCatchesTempMail() {
        DomainBlacklistRiskStrategy strategy = new DomainBlacklistRiskStrategy();
        BookingRiskRequest req = new BookingRiskRequest("buyer@throwaway.org", "127.0.0.1", 1, 1);
        assertEquals(95, strategy.calculateRiskScore(req));
    }

    @Test
    void testPaymentSubnetRecognizesTrustedIp() {
        PaymentSubnetRiskStrategy strategy = new PaymentSubnetRiskStrategy();
        BookingRiskRequest req = new BookingRiskRequest("buyer@mail.com", "192.168.1.15", 1, 1);
        assertEquals(5, strategy.calculateRiskScore(req));
    }

    @Test
    void testSelectorRuleHighDemandSelectsVelocity() {
        assertInstanceOf(VelocityRiskStrategy.class, selector.selectStrategy(true, false));
    }

    @Test
    void testSelectorRuleNewUserSelectsBlacklist() {
        assertInstanceOf(DomainBlacklistRiskStrategy.class, selector.selectStrategy(false, true));
    }

    @Test
    void testSelectorDefaultSelectsSubnet() {
        assertInstanceOf(PaymentSubnetRiskStrategy.class, selector.selectStrategy(false, false));
    }
}