package kz.astanait.strategy.risk;

public interface RiskScoringStrategy {
    int calculateRiskScore(BookingRiskRequest request);
}