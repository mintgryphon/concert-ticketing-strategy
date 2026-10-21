package kz.astanait.strategy.risk;

public class VelocityRiskStrategy implements RiskScoringStrategy {
    @Override
    public int calculateRiskScore(BookingRiskRequest request) {
        int score = request.getAttemptsLastTenMinutes() * 15;
        if (request.getRequestedTickets() > 6) {
            score += 40;
        }
        return Math.min(100, Math.max(0, score));
    }
}