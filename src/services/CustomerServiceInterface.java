package services;

import models.Customer;

public interface CustomerServiceInterface {

    public void add();
    public void delete();
    public void findByName();
    public int getAge(Customer customer);
}
