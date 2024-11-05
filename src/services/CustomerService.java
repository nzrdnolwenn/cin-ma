package services;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import models.Customer;
import repositories.CustomerRepositoryInterface;

public class CustomerService implements CustomerServiceInterface {

    private final CustomerRepositoryInterface customerRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final Scanner scanner = new Scanner(System.in);

    public CustomerService(CustomerRepositoryInterface customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void add() {
        try{
            String name = promptForName();

            LocalDate birthDate = promptForBirthDate();

            if (name != null && birthDate != null) {
                Customer customer = new Customer(name, birthDate);
                customerRepository.add(customer);
                System.out.println("Client ajouté avec succès !");
            }

        }catch (Exception e) {
            System.out.println("Erreur dans la saisie des informations : " + e.getMessage());
        }
    }

    @Override
    public void delete() {
        String name = promptForName();
        if (name == null) return;

        Customer customer = customerRepository.findByName(name);
        if (customer != null) {
            customerRepository.delete(customer);
            System.out.println("Client supprimé avec succès !");
        } else {
            System.out.println("Client introuvable.");
        }
    }

    @Override
    public void findByName(){
        String name = promptForName();

        Customer customer = customerRepository.findByName(name);
        if (customer != null) {
            System.out.println("Nom : " + customer.getName() + ", Date de naissance : " + customer.getBirthDate());
        } 
        else {
            System.out.println("Client introuvable.");
        }
    }

    @Override
    public int getAge(Customer customer) {
        return Period.between(customer.getBirthDate(), LocalDate.now()).getYears();
    }


    private String promptForName() {
        System.out.println("Saisir le nom du client : ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Le nom du client ne peut pas être vide.");
            return null;
        }
        return name;
    }

    private LocalDate promptForBirthDate() {
        System.out.println("Saisir la date de naissance du client (dd/MM/yyyy) : ");
        String birthDateString = scanner.nextLine().trim();
        try {
            return LocalDate.parse(birthDateString, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Format de date invalide. Veuillez entrer la date au format dd/MM/yyyy");
        }
        return null;
    }
}
