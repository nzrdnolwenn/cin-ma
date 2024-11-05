package services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import models.Customer;
import models.Reservation;
import models.Session;
import repositories.CustomerRepositoryInterface;
import repositories.ReservationRepositoryInterface;
import repositories.SessionRepositoryInterface;

public class ReservationService implements ReservationServiceInterface {

    private final ReservationRepositoryInterface reservationRepository;
    private final SessionServiceInterface sessionService;
    private final SessionRepositoryInterface sessionRepository;
    private CustomerServiceInterface customerService;
    private final CustomerRepositoryInterface customerRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final Scanner scanner = new Scanner(System.in);

    public ReservationService(ReservationRepositoryInterface reservationRepository, SessionServiceInterface sessionService, SessionRepositoryInterface sessionRepository, CustomerServiceInterface customerService, CustomerRepositoryInterface customerRepository) {
        this.reservationRepository = reservationRepository;
        this.sessionService = sessionService;
        this.sessionRepository = sessionRepository;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }


    @Override
    public void add() {
        try {
            sessionService.findAllAvailable();
            LocalDateTime startHourly = promptForDateTime();
            if (startHourly == null) return;

            Session session = sessionRepository.findByStartHourly(startHourly);
            if (session == null) {
                System.out.println("Séance introuvable.");
                return;
            }

            String name = promptForCustomerName();
            Customer customer = customerRepository.findByName(name);
            if (customer == null) {
                System.out.println("Client introuvable.");
                return;
            }

            if (session.getMovie().isForbiddenUnder18() && customerService.getAge(customer) < 18) {
                System.out.println("Réservation refusée : le film est interdit aux moins de 18 ans.");
            } else {
                Reservation reservation = new Reservation(customer, session);
                reservationRepository.add(reservation);
                System.out.println("Réservation effectuée avec succès.");
            }
        } catch (Exception e) {
            System.out.println("Erreur dans la saisie des informations : " + e.getMessage());
        }
    }

    @Override
    public void findByCustomerAndSession() {
        LocalDateTime startHourly = promptForDateTime();
        if (startHourly == null) return;

        Session session = sessionRepository.findByStartHourly(startHourly);
        if (session == null) {
            System.out.println("Séance introuvable.");
            return;
        }

        String name = promptForCustomerName();
        Customer customer = customerRepository.findByName(name);
        if (customer == null) {
            System.out.println("Client introuvable.");
            return;
        }

        Reservation reservation = reservationRepository.findByCustomerAndSession(customer, session);
        if (reservation != null) {
            System.out.println("Réservation " + reservation.getId() + " pour la séance : " 
                    + reservation.getSession().getMovie().getTitle() 
                    + ", le " + reservation.getSession().getStartHourly().toLocalDate() 
                    + " à " + reservation.getSession().getStartHourly().toLocalTime()
                    + ", Salle : " + reservation.getSession().getRoom().getNumberRoom()
                    + ", VO : " + (reservation.getSession().isIsVo() ? "Oui" : "Non")
                    + ", ST : " + (reservation.getSession().isIsSt() ? "Oui" : "Non"));
        } else {
            System.out.println("Réservation introuvable.");
        }
    }

    @Override
    public void delete() {
        LocalDateTime startHourly = promptForDateTime();
        if (startHourly == null) return;

        Session session = sessionRepository.findByStartHourly(startHourly);
        if (session == null) {
            System.out.println("Séance introuvable.");
            return;
        }

        String name = promptForCustomerName();
        Customer customer = customerRepository.findByName(name);
        if (customer == null) {
            System.out.println("Client introuvable.");
            return;
        }

        Reservation reservation = reservationRepository.findByCustomerAndSession(customer, session);
        if (reservation != null) {
            reservationRepository.delete(reservation);
            System.out.println("Réservation annulée avec succès !");
        } else {
            System.out.println("Réservation introuvable.");
        }
    }


    private LocalDateTime promptForDateTime() {
        try {
            System.out.println("Saisir la date de la séance (dd/MM/yyyy) : ");
            String dateString = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateString, dateFormatter);

            System.out.println("Saisir l'heure de la séance (HH:mm) : ");
            String timeString = scanner.nextLine();
            LocalTime time = LocalTime.parse(timeString, timeFormatter);

            return LocalDateTime.of(date, time);
        } catch (DateTimeParseException e) {
            System.out.println("Format de date ou d'heure invalide.");
        }
        return null;
    }

    private String promptForCustomerName() {
        System.out.println("Saisir le nom du client : ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Le nom du client ne peut pas être vide.");
            return null;
        }
        return name;
    }

}
