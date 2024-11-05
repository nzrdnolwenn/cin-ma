package repositories;

import models.Customer;

public interface CustomerRepositoryInterface {

    public void add(Customer customer);
    public Customer findByName(String name);
    public void delete(Customer customer);
}
