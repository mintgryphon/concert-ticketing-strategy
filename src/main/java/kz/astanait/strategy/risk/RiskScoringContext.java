package kz.astanait.strategy.risk;

public class RiskScoringContext {
    private RiskScoringStrategy strategy;

    public RiskScoringContext(RiskScoringStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Risk strategy cannot be null");
        }
        this.strategy = strategy;
    }

    public void setStrategy(RiskScoringStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Risk strategy cannot be null");
        }
        this.strategy = strategy;
    }

    public RiskScoringStrategy getStrategy() {
        return strategy;
    }

    public int evaluateRisk(BookingRiskRequest request) {
        return strategy.calculateRiskScore(request);
    }
}