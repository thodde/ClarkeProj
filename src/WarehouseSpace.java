/**
 * @author Trevor Hodde
 */
public class WarehouseSpace {
    private boolean filled;
    private String location;
    private double width;
    private double length;
    private double squareFootage;

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
        return this.squareFootage;
    }
    
    /**
     * @param squareFootage the squareFootage to set
     */
    public void setSquareFootage(double squareFootage) {
        this.squareFootage = squareFootage;
    }
    
    @Override
    public String toString() {
        return "WarehouseSpace [" + (location != null ? "location=" + location + ", " : "") + "width=" + width + ", length=" + length + "]";
    }
}
