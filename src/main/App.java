package main;

import database.Database;
import java.util.InputMismatchException;
import java.util.Scanner;

import repositories.*;

import services.*;

public class App {
	public static void main(String[] args) {

		CustomerRepositoryInterface customerRepository = new CustomerRepository();
		CustomerServiceInterface customerService = new CustomerService(customerRepository);

		RoomRepositoryInterface roomRepository = new RoomRepository();
		RoomServiceInterface roomService = new RoomService(roomRepository);

		MovieRepositoryInterface movieRepository = new MovieRepository();
		MovieServiceInterface movieService = new MovieService(movieRepository);

		SessionRepositoryInterface sessionRepository = new SessionRepository();
		SessionServiceInterface sessionService = new SessionService(sessionRepository, roomService, roomRepository, movieService, movieRepository);

		ReservationRepositoryInterface reservationRepository = new ReservationRepository();
		ReservationServiceInterface reservationService = new ReservationService(reservationRepository, sessionService, sessionRepository, customerService, customerRepository);

		Scanner scanner = new Scanner(System.in);

		try {
            if (Database.getConnection() != null) {
                System.out.println("Connexion à la base de données établie avec succès !");
            } else {
                System.out.println("Échec de la connexion à la base de données.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
            return;
        }

		System.out.println("--CINEMA ESIEA--");
		System.out.println("MENU UTILISATEUR");

		while (true) {
			try {
				System.out.println("\n1 - Gestion des séances");
				System.out.println("2 - Gestion des comptes clients");
				System.out.println("3 - Gestion des réservations");
				System.out.println("4 - Quitter");

				System.out.print("Votre choix : ");
				int choice = scanner.nextInt();
				scanner.nextLine();  // Consomme le retour à la ligne

				switch (choice) {
					case 1:
						handleSessionManagement(sessionService, scanner);
						break;
					case 2:
						handleCustomerManagement(customerService, scanner);
						break;
					case 3:
						handleReservationManagement(reservationService, scanner);
						break;
					case 4:
						System.out.println("Au revoir !");
						scanner.close();
						System.exit(0);
						break;
					default:
						System.out.println("Choix invalide. Veuillez réessayer.");
						break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrée invalide. Veuillez entrer un nombre.");
				scanner.nextLine(); // Consomme la saisie incorrecte
			}
		}
	}

	private static void handleSessionManagement(SessionServiceInterface sessionService, Scanner scanner) {
		boolean manageSession = true;
		while (manageSession) {
			System.out.println("\nGESTION DES SEANCES");
			System.out.println("1 - Ajouter une nouvelle séance");
			System.out.println("2 - Annuler une séance planifiée");
			System.out.println("3 - Afficher les séances prévues disponibles avec le nombre de places restantes");
			System.out.println("4 - Retour");

			System.out.print("Votre choix : ");
			int sessionChoice = scanner.nextInt();
			scanner.nextLine();

			switch (sessionChoice) {
				case 1:
					sessionService.add();
					break;
				case 2:
					sessionService.delete();
					break;
				case 3:
					sessionService.findAllAvailable();
					break;
				case 4:
					manageSession = false;
					break;
				default:
					System.out.println("Choix invalide. Veuillez réessayer.");
					break;
			}
		}
	}

	private static void handleCustomerManagement(CustomerServiceInterface customerService, Scanner scanner) {
		boolean manageCustomer = true;
		while (manageCustomer) {
			System.out.println("\nGESTION DES COMPTES CLIENTS");
			System.out.println("1 - Créer un nouveau compte client");
			System.out.println("2 - Supprimer un compte client");
			System.out.println("3 - Afficher un client à partir de son nom");
			System.out.println("4 - Retour");

			System.out.print("Votre choix : ");
			int customerChoice = scanner.nextInt();
			scanner.nextLine();

			switch (customerChoice) {
				case 1:
					customerService.add();
					break;
				case 2:
					customerService.delete();
					break;
				case 3:
					customerService.findByName();
					break;
				case 4:
					manageCustomer = false;
					break;
				default:
					System.out.println("Choix invalide. Veuillez réessayer.");
					break;
			}
		}
	}

	private static void handleReservationManagement(ReservationServiceInterface reservationService, Scanner scanner) {
		boolean manageReservation = true;
		while (manageReservation) {
			System.out.println("\nGESTION DES RESERVATIONS");
			System.out.println("1 - Ajouter une nouvelle réservation");
			System.out.println("2 - Annuler une réservation");
			System.out.println("3 - Afficher une réservation");
			System.out.println("4 - Retour");

			System.out.print("Votre choix : ");
			int reservationChoice = scanner.nextInt();
			scanner.nextLine();

			switch (reservationChoice) {
				case 1:
					reservationService.add();
					break;
				case 2:
					reservationService.delete();
					break;
				case 3:
					reservationService.findByCustomerAndSession();
					break;
				case 4:
					manageReservation = false;
					break;
				default:
					System.out.println("Choix invalide. Veuillez réessayer.");
					break;
			}
		}
	}
}
