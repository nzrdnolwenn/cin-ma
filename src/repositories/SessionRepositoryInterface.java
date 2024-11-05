package repositories;

import java.time.LocalDateTime;
import java.util.List;
import models.Session;

public interface SessionRepositoryInterface {
    void add(Session session);
    void delete(Session session);
    List<Session> findAll();
    Session findByStartHourly(LocalDateTime startHourly);
    List<Session> findAllAvailable();
}
