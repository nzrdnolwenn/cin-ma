package models;

public class Reservation {

    private int id;
    private Customer customer;
    private Session session;

    public Reservation(Customer customer, Session session){
        this.customer = customer;
        this.session = session;
    }

    public Reservation(int id, Customer customer, Session session){
        this.id = id;
        this.customer = customer;
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
