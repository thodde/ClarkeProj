import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Trevor Hodde
 */
public class WarehouseManager {
    private ArrayList<InventoryItem> inventory = new ArrayList<InventoryItem>();
    private ArrayList<WarehouseSpace> warehouse = new ArrayList<WarehouseSpace>();
    private final String WAREHOUSE_LOT_SPACES_FILENAME = "Warehouse_Lot_Spaces.csv";
    private final String ITEM_REQUIREMENTS_FILENAME = "Item_Requirements.csv";
    private final String OUTFILE = "results.csv";
    
    // TODO: determine how to set each InventoryItem.location 
    public void fillWarehouse() {
        
    }

    // generate all the empty spaces in the warehouse
    public void createEmptyWarehouse() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(WAREHOUSE_LOT_SPACES_FILENAME));
            String line = null;
            while((line = br.readLine()) != null) {
                String[] spaceData = line.split(",");
                WarehouseSpace space = new WarehouseSpace(false, spaceData[0], Double.parseDouble(spaceData[1]), Double.parseDouble(spaceData[2]));
                warehouse.add(space);
            }
            br.close();
        }
        catch(IOException e) {
            System.out.println(WAREHOUSE_LOT_SPACES_FILENAME + " not found or could not be opened.");
        }
    }
    
    // populate the inventory
    public void generateInventory() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ITEM_REQUIREMENTS_FILENAME));
            String line = null;
            while((line = br.readLine()) != null) {
                String[] itemData = line.split(",");
                InventoryItem space = new InventoryItem(itemData[0], Integer.parseInt(itemData[1]), Double.parseDouble(itemData[2]), 
                                                        Double.parseDouble(itemData[3]), Integer.parseInt(itemData[4]));
                inventory.add(space);
            }
            br.close();
        }
        catch(IOException e) {
            System.out.println(ITEM_REQUIREMENTS_FILENAME + " not found or could not be opened.");
        }
    }
    
    // iterate over the whole inventory and display the data associated with each item
    public void displayInventory() {
        for(InventoryItem i : inventory) {
            System.out.println(i);
        }
    }
    
    // probably not needed but could come in handy
    public void displaySpaces() {
        for(WarehouseSpace i : warehouse) {
            System.out.println(i);
        }
    }
}
