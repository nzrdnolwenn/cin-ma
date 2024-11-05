package models;

import java.time.LocalDateTime;

public class Session {
    private int id;
    private LocalDateTime startHourly;
	private Movie movie;
	private Room room;
	private boolean isVo;
	private boolean isSt;

    public Session(LocalDateTime startHourly, Movie movie, Room room, boolean isVo, boolean isSt) {
		this.startHourly = startHourly;
		this.movie = movie;
		this.room = room;
		this.isVo = isVo;
		this.isSt = isSt;
	}

    public Session(int id, LocalDateTime startHourly, Movie movie, Room room, boolean isVo, boolean isSt) {
        this.id = id;
		this.startHourly = startHourly;
		this.movie = movie;
		this.room = room;
		this.isVo = isVo;
		this.isSt = isSt;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartHourly() {
        return startHourly;
    }

    public void setStartHourly(LocalDateTime startHourly) {
        this.startHourly = startHourly;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isIsVo() {
        return isVo;
    }

    public void setIsVo(boolean isVo) {
        this.isVo = isVo;
    }

    public boolean isIsSt() {
        return isSt;
    }

    public void setIsSt(boolean isSt) {
        this.isSt = isSt;
    }
}
