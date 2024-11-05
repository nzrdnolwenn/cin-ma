package repositories;

import models.Customer;
import models.Reservation;
import models.Session;

public interface ReservationRepositoryInterface {

    public void add(Reservation reservation);
    public Reservation findByCustomerAndSession(Customer customer, Session session);
    public int countReservationsBySession(Session session);
    public void delete(Reservation reservation);
}
