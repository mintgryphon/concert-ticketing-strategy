package kz.astanait.strategy.risk;

public class PaymentSubnetRiskStrategy implements RiskScoringStrategy {
    @Override
    public int calculateRiskScore(kz.astanait.strategy.risk.BookingRiskRequest request) {
        String ip = request.getClientIp() != null ? request.getClientIp() : "";
        if (ip.startsWith("192.168.") || ip.startsWith("10.") || ip.equals("127.0.0.1")) {
            return 5;
        }
        if (ip.startsWith("198.51.") || ip.startsWith("203.0.")) {
            return 75;
        }
        return 25;
    }
}