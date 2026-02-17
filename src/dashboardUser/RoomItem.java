package dashboardUser;

import static jdk.nashorn.internal.runtime.Debug.id;

public class RoomItem {
    int roomId;
    String roomNumber;
    String type;
    String description;
    int capacity;
    String floor;
    double price;
    String roomDesc;

    public RoomItem(int roomId, String roomNumber, String type,
                    String description, int capacity,
                    String floor, double price, String roomDesc) {

        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.type = type;
        this.description = description;
        this.capacity = capacity;
        this.floor = floor;
        this.price = price;
        this.roomDesc = roomDesc;
    }
    
    public int getroomId() {
        return roomId;
    }
    
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return roomNumber + " | " + type + 
               " | Cap:" + capacity + 
               " | Floor:" + floor + 
               " | ₱" + price;
    }
}
