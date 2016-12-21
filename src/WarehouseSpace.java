/**
 * @author Trevor Hodde
 */
public class WarehouseSpace implements Comparable<WarehouseSpace> {
    private boolean filled;
    private String location;
    private double width;
    private double length;
    private double squareFootage;
    private double remainingSpace;
    private double capacity;

    /**
     * @param filled
     * @param row
     * @param spaceNumber
     * @param width
     * @param length
     * @param squareFootage
     */
    public WarehouseSpace(boolean filled, String location, double width, double length) {
        this.filled = filled;
        this.location = location;
        this.width = width;
        this.length = length;
        this.squareFootage = length*width;
        this.remainingSpace = squareFootage;
    }
    
    /**
     * @return the filled
     */
    public boolean isFilled() {
        return filled;
    }
    
    /**
     * @param filled the filled to set
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }
    
    /**
     * @return the spaceNumber
     */
    public String getLocation() {
        return location;
    }
    
    /**
     * @param spaceNumber the spaceNumber to set
     */
    public void setLocation(String location) {
        this.location = location;
    }
    
    /**
     * @return the width
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * @param width the width to set
     */
    public void setWidth(double width) {
        this.width = width;
    }
    
    /**
     * @return the length
     */
    public double getLength() {
        return length;
    }
    
    /**
     * @param length the length to set
     */
    public void setLength(double length) {
        this.length = length;
    }
    
    /**
     * @return the squareFootage
     */
    public double getSquareFootage() {
        // return the remaining space 
        return this.remainingSpace;
    }
    
    /**
     * @param squareFootage the squareFootage to set
     */
    public void setSquareFootage(double squareFootage) {
        this.squareFootage = squareFootage;
    }
    
    /**
     * @return the remaining space for this spot
     */
    public double calcRemainingFootage(double filledSpace) {
        this.remainingSpace -= filledSpace;
        return this.remainingSpace;
    }
    
    // return the available capacity of the space
    public double calcCapacity() {
        capacity = (squareFootage - remainingSpace) / squareFootage;
        return capacity;
    }
    
    @Override
    public String toString() {
        return "WarehouseSpace [" + (location != null ? "location=" + location + ", " : "") + "width=" + width + ", length=" + length + "]";
    }

    // compare spaces by square footage to organize them
    @Override
    public int compareTo(WarehouseSpace other) {
        if(this.squareFootage > other.getSquareFootage()) {
            return -1;
        }
        else if(this.squareFootage < other.getSquareFootage()) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
