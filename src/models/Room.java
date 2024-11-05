package models;

public class Room {
    private int numberRoom;
    private int seatCount;

    public Room(int numberRoom, int seatCount){
        this.numberRoom = numberRoom;
        this.seatCount = seatCount;
    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(int numberRoom) {
        this.numberRoom = numberRoom;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }
}
