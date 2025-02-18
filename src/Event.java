/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Anuphong_PC
 */
class Event {
    private String name;
    private String date;
    private String venue;
    private int ticketsAvailable;

    public Event(String name, String date, String venue, int ticketsAvailable) {
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.ticketsAvailable = ticketsAvailable;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }
}
