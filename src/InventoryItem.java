import java.util.Objects;

/**
 * @author Trevor Hodde
 */
public class InventoryItem implements Comparable<InventoryItem> {
    private String itemName;
    private double width;
    private double length;
    private double totalSquareFootage;
    private int maxStackSize;
    private int inventoryCount;
    private int currentStack;
    private WarehouseSpace location;
    
    //private final double MAX_HEIGHT = 100; // total possible height of stack in inches
    
    public InventoryItem(String itemName, int maxStackSize, double width, double length, int inventoryCount) {
        this.itemName = itemName;
        this.width = width;
        this.length = length;
        this.maxStackSize = maxStackSize;
        this.inventoryCount = inventoryCount;
        this.totalSquareFootage = inventoryCount * (length*width);
        currentStack = 0;
        this.location = null;
    }
    
    public InventoryItem(String itemName, int maxStackSize, double width, double length, int inventoryCount, WarehouseSpace position) {
        this(itemName, maxStackSize, width, length, inventoryCount);
        //this.totalSquareFootage = inventoryCount * (length*width);
        //currentStack = 0;
        this.location = position;
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
        // based on the total number of units * each units size
        this.totalSquareFootage = this.inventoryCount * (this.length*this.width);
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
    
    public int getCurrentStackSize() {
        return this.currentStack;
    }
    
    public void setCurrentStackSize(int size) {
        this.currentStack = size;
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
                + ", inventoryCount=" + inventoryCount + (location != null ? ", location=" + location : "") + "]";
    }
    
    public String prettyPrint() {
        return (itemName != null ? itemName + ", " : "") + inventoryCount + (location != null ? ", " + location.getLocation() : "");
    }
    
    // compare items by square footage to organize them
    @Override
    public int compareTo(InventoryItem other) {
        double thisSingleSquareFootage = this.length * this.width;
        double otherSingleSquareFootage = other.getLength() * other.getWidth();
        
        // we want to organize based on the individual size of each product
        if(thisSingleSquareFootage > otherSingleSquareFootage) {
            return -1;
        }
        else if(thisSingleSquareFootage < otherSingleSquareFootage) {
            return 1;
        }
        else {
            // however, if two products are the same size, take the one which has more inventory
            if(this.totalSquareFootage > other.getTotalSquareFootage()) {
                return -1;
            }
            else if(this.totalSquareFootage < other.getTotalSquareFootage()) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    // specific implementation used only for merging
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        InventoryItem other = (InventoryItem) obj;
        // returns true if two things are true:
        //    1) the item names match
        //    2) the item locations match
        return Objects.equals(this.itemName, other.getItemName()) && Objects.equals(this.getLocation().getLocation(), other.getLocation().getLocation());
    }

    public InventoryItem merge(InventoryItem other) {
        assert(this.equals(other));
        return new InventoryItem(this.itemName, this.maxStackSize, this.width, this.length, this.inventoryCount + other.getInventoryCount(), this.location);
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
