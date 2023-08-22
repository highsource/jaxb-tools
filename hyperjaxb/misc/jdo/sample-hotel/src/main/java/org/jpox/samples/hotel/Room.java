package org.jpox.samples.hotel;

/**
 * Room in a Hotel
 */
public class Room
{
    private int roomNumber;
    private int floor;

    private Hotel hotel;

    public Room(int roomNumber, int floor, Hotel hotel)
    {
        super();
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.hotel = hotel;
    }

    public Room()
    {
        super();
    }

    public int getFloor()
    {
        return floor;
    }

    public int getRoomNumber()
    {
        return roomNumber;
    }
}
