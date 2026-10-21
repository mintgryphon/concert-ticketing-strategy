package kz.astanait.strategy.pricing;

public class SimulatedMarketDemandSource implements MarketDemandSource {
    @Override
    public double getDemandMultiplier(String concertSector) {
        if (concertSector != null && concertSector.toUpperCase().startsWith("VIP")) {
            return 1.50;
        }
        return 1.15;
    }
}