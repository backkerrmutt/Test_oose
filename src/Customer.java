
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Anuphong_PC
 */
class Customer extends User {
    private final ArrayList<Ticket> bookedTickets = new ArrayList<>();
    private final int balance;

    public Customer(String username, String password, String name, String lastname, int age, String gender,
            int balance) {
        super(username, password, name, lastname, age, gender);
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public List<Ticket> getBookedTickets() {
        return bookedTickets;
    }

    public void setBookedTickets(List<Ticket> bookedTickets) {
        this.bookedTickets.clear();
        this.bookedTickets.addAll(bookedTickets);
    }
}
