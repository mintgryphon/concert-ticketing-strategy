package kz.astanait.strategy.pricing;

public interface MarketDemandSource {
    double getDemandMultiplier(String concertSector);
}