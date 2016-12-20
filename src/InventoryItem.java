/**
 * @author Trevor Hodde
 */
public class InventoryItem {
    private String itemName;
    private double width;
    private double length;
    private double totalSquareFootage;
    private int maxStackSize;
    private int inventoryCount;
    private WarehouseSpace location;
    
    //private final double MAX_HEIGHT = 100; // total possible height of stack in inches
    
    public InventoryItem(String itemName, int maxStackSize, double width, double length, int inventoryCount) {
        this.itemName = itemName;
        this.width = width;
        this.length = length;
        this.maxStackSize = maxStackSize;
        this.inventoryCount = inventoryCount;
    }
    
    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }
    
    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
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
     * @return the totalSquareFootage
     */
    public double getTotalSquareFootage() {
        return totalSquareFootage;
    }
    
    /**
     * @param totalSquareFootage the totalSquareFootage to set
     */
    public void setTotalSquareFootage(double totalSquareFootage) {
        this.totalSquareFootage = totalSquareFootage;
    }
    
    /**
     * @return the maxStackSize
     */
    public int getMaxStackSize() {
        return maxStackSize;
    }
    
    /**
     * @param maxStackSize the maxStackSize to set
     */
    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }
    
    /**
     * @return the inventoryCount
     */
    public int getInventoryCount() {
        return inventoryCount;
    }

    /**
     * @param inventoryCount the inventoryCount to set
     */
    public void setInventoryCount(int inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    /**
     * @return the location
     */
    public WarehouseSpace getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(WarehouseSpace location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "InventoryItem [" + (itemName != null ? "itemName=" + itemName + ", " : "") + "width=" + width + ", length=" + length
                + ", inventoryCount=" + inventoryCount + ", " + (location != null ? "location=" + location : "") + "]";
    }
    
/*
    // might not ever need this, but it can't hurt to have
    private int calcMaxStackSize() {
        int currentStackCount = 0;
        double runningHeight = 0.0;
        do {
            runningHeight += this.getHeight();
            currentStackCount++;
        } while(runningHeight <= MAX_HEIGHT);
        setMaxStackSize(currentStackCount);
        return currentStackCount;
    }
    */
}
