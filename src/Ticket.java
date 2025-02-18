import java.text.NumberFormat;
import java.util.Locale;

public final class Ticket {
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
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US); // Change Locale as needed
        return String.format("Ticket for %s, Price: %s", eventName, currencyFormat.format(price));
    }
}
