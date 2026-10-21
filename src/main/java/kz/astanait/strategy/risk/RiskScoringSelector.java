package kz.astanait.strategy.risk;

public class RiskScoringSelector {
    public kz.astanait.strategy.risk.RiskScoringStrategy selectStrategy(boolean isHighDemandSale, boolean isNewUserAccount) {
        if (isHighDemandSale) {
            return new VelocityRiskStrategy();
        }
        if (isNewUserAccount) {
            return new kz.astanait.strategy.risk.DomainBlacklistRiskStrategy();
        }
        return new kz.astanait.strategy.risk.PaymentSubnetRiskStrategy();
    }
}