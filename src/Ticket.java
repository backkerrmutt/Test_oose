
public class Ticket {
    private final String eventName;
    private final double price;

    public Ticket(String eventName, double price) {
        this.eventName = eventName;
        this.price = price;
    }

    public String getEventName() {
        return eventName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ticket for " + eventName + ", Price: " + price;
    }
}