package kz.astanait.strategy.risk;

public class BookingRiskRequest {
    private final String customerEmail;
    private final String clientIp;
    private final int attemptsLastTenMinutes;
    private final int requestedTickets;

    public BookingRiskRequest(String customerEmail, String clientIp, int attemptsLastTenMinutes, int requestedTickets) {
        this.customerEmail = customerEmail;
        this.clientIp = clientIp;
        this.attemptsLastTenMinutes = attemptsLastTenMinutes;
        this.requestedTickets = requestedTickets;
    }

    public String getCustomerEmail() { return customerEmail; }
    public String getClientIp() { return clientIp; }
    public int getAttemptsLastTenMinutes() { return attemptsLastTenMinutes; }
    public int getRequestedTickets() { return requestedTickets; }
}