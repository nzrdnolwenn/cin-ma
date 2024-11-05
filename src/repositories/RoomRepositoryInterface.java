package repositories;

import java.util.List;
import models.Room;

public interface RoomRepositoryInterface {
    void add(Room room);
    void delete(Room room);
    Room findByNumber(int numberRoom);
    List<Room> findAll();
}
