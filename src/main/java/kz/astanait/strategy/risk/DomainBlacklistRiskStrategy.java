package kz.astanait.strategy.risk;

public class DomainBlacklistRiskStrategy implements RiskScoringStrategy {
    @Override
    public int calculateRiskScore(BookingRiskRequest request) {
        String email = request.getCustomerEmail() != null ? request.getCustomerEmail().toLowerCase() : "";
        if (email.contains("tempmail") || email.contains("throwaway") || email.contains("bot")) {
            return 95;
        }
        if (email.contains("scalper") || email.contains("resell")) {
            return 80;
        }
        return 10;
    }
}