package services;

import java.util.List;
import models.Room;
import repositories.RoomRepositoryInterface;

public class RoomService implements RoomServiceInterface {

    private final RoomRepositoryInterface roomRepository;

    public RoomService(RoomRepositoryInterface roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void findAll(){
        List<Room> rooms = roomRepository.findAll();

        if (rooms.isEmpty()) {
            System.out.println("Aucune salle trouvée dans la base de données.");
        } else {
            System.out.println("Liste des salles disponibles :");
            for (Room room : rooms) {
                System.out.println("Salle n°" + room.getNumberRoom() + ", capacité : " + room.getSeatCount() + " sièges.");
            }
        }
    }
}
