# Assignment 5: Strategy Pattern (Concert Ticketing Domain)

## 1. Class Responsibility Table (Single Responsibility Principle)
Strictly adheres to SRP: max 25 words per sentence, without "if", "and", "or", "but".

| Class / Interface | Responsibility |
| :--- | :--- |
| `TicketPricingStrategy` | Declares the contract for ticket price calculation algorithms. |
| `EarlyBirdPricingStrategy` | Computes advance reservation prices with discount plus booking fee. |
| `SurgeDynamicPricingStrategy` | Calculates prices scaled by external market demand factors. |
| `LoyaltyVipPricingStrategy` | Applies club reward point discounts subject to minimum floors. |
| `MarketDemandSource` | Declares an abstraction isolating pricing algorithms from external data. |
| `SimulatedMarketDemandSource` | Supplies sector-specific market multiplier values for runtime calculations. |
| `TicketPricingContext` | Holds current pricing strategy reference executing price calculations cleanly. |
| `TicketPricingSelector` | Chooses appropriate pricing strategy matching current booking situational parameters. |
| `RiskScoringStrategy` | Declares the contract for evaluating fraud risk scores. |
| `BookingRiskRequest` | Encapsulates immutable booking attributes needed for risk evaluation algorithms. |
| `VelocityRiskStrategy` | Calculates risk scores based on request frequency transaction volumes. |
| `DomainBlacklistRiskStrategy` | Assigns risk scores through customer email domain heuristic analysis. |
| `PaymentSubnetRiskStrategy` | Evaluates risk levels according to client IP network subnets. |
| `RiskScoringContext` | Maintains risk strategy reference delegating scoring execution directly. |
| `RiskScoringSelector` | Resolves target risk strategy based on sale type attributes. |
| `App` | Provides interactive CLI demonstrating strategy execution runtime switching. |

---

## 2. Architectural Reflection: Strategy vs. State vs. Template Method

Our solution utilizes the **Strategy pattern** because it addresses interchangeable algorithmic behaviors rather than object lifecycle states or skeletal workflows.

### Strategy vs. State
- **State Pattern:** Changes an object's behavior when its internal state changes, making the object appear to change its class. State transitions are typically driven from within the State classes themselves, forming a directed graph of lifecycles (e.g., Ticket: `NEW -> CONFIRMED -> CANCELLED`).
- **Strategy Pattern:** Represents independent, swappable algorithms solving a specific problem (calculating a price or scoring fraud risk). The context (`TicketPricingContext`) does not undergo internal lifecycle evolution; strategies are chosen externally by a Selector or client based on contextual parameters. Strategies operate independently without knowing each other.

### Strategy vs. Template Method
- **Template Method:** Relies on class inheritance (`extends`). An abstract base class defines the invariant skeleton of an algorithm in a `final` method, letting subclasses override specific steps. This binds the algorithm tightly to compile-time inheritance.
- **Strategy Pattern:** Relies on object composition (`implements`). The context delegates work to a strategy interface at runtime. This provides dynamic flexibility, allows changing algorithms on the fly via `setStrategy()`, and honors the Open/Closed Principle cleanly.
