import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

/**
 * @author Trevor Hodde
 */
public class WarehouseManager {
    private ArrayList<InventoryItem> inventory = new ArrayList<InventoryItem>();
    private ArrayList<WarehouseSpace> warehouse = new ArrayList<WarehouseSpace>();
    
    private final double MAX_CAPACITY = 0.70;
    private final double MIN_CAPACITY = 0.50;
    
    // managing file IO
    private final String WAREHOUSE_LOT_SPACES_FILENAME = "Warehouse_Lot_Spaces.csv";
    private final String ITEM_REQUIREMENTS_FILENAME = "Item_Requirements.csv";
    private final String OUTFILE = "results.csv";
    private final String FILE_HEADER = "item name, inventory count, location";
    
    public void fillWarehouse() {
        ListIterator<InventoryItem> iter = inventory.listIterator();
        InventoryItem item;
        boolean possiblyFull = false;
        while(iter.hasNext()) {
            item = iter.next();
            possiblyFull = true;
            if(item.getLocation() != null)
                continue;
            
            for(WarehouseSpace space : warehouse) {
                if(!space.isFilled()) {
                    possiblyFull = false;
                    
                    // if the item (total inventory) fits in the space
                    if(item.getTotalSquareFootage() <= space.getSquareFootage() ) {                        
                        // try to stack vertically
                        if(item.getCurrentStackSize() < item.getMaxStackSize()) {
                            for(int i = item.getCurrentStackSize(); i <= item.getMaxStackSize() && i <= item.getInventoryCount(); i++) {
                                //item can be stacked
                                item.setCurrentStackSize(i);
                            }
                            //update remaining inventory count and create a new item for the remaining stuff
                            InventoryItem extraItem = new InventoryItem(item.getItemName(), item.getMaxStackSize(), 
                                    item.getWidth(), item.getLength(), (item.getInventoryCount()-item.getCurrentStackSize()));
                            iter.add(extraItem);
                            // the current item is stacked
                            item.setInventoryCount(item.getCurrentStackSize());
                            item.setTotalSquareFootage(item.getLength()*item.getWidth());
                            space.calcRemainingFootage(item.getLength()*item.getWidth());
                                                        
                            if(extraItem.getTotalSquareFootage() < space.getSquareFootage()) {
                                extraItem.setLocation(space);
                            }
                        }
                        else {
                            // update the remaining space
                            space.calcRemainingFootage(item.getTotalSquareFootage());
                        }
                        
                        //if(space.calcCapacity() >= MAX_CAPACITY) {
                            space.setFilled(true);
                        //}
                        
                        // the item has a home
                        item.setLocation(space);
                        space.setItem(item);
                        // move to the next item
                        break;
                    }
                    else {
                        // there is overflow so we need to split into two locations
                        double spaceTaken = 0.0;
                        int maxItems = 0;
                        for(maxItems = 0; maxItems < item.getInventoryCount(); maxItems++) {
                            // keep track of how many items can be stored in a space
                            if(spaceTaken < space.getSquareFootage())
                                spaceTaken += (item.getLength()*item.getWidth());
                            else
                                break;
                        }
                        //update remaining inventory count and create a new item for the remaining stuff
                        iter.add(new InventoryItem(item.getItemName(), item.getMaxStackSize(), 
                                    item.getWidth(), item.getLength(), (item.getInventoryCount() - maxItems)));
                        item.setInventoryCount(maxItems);
                        
                        // update the remaining space
                        space.calcRemainingFootage(item.getTotalSquareFootage());
                        //if(space.calcCapacity() >= MAX_CAPACITY) {
                            space.setFilled(true);
                        //}
                        // the item has a home
                        item.setLocation(space);
                        space.setItem(item);
                        break;
                    }
                }/*
                else {
                    // TODO:
                    // figure out if the current item has more inventory to store 
                    // than the item holding the spot
                    if((item.getInventoryCount() > space.getItem().getInventoryCount())) {
                        // the item that lives here no longer has a home
                        space.getItem().setLocation(null);
                        //space.setFilled(false);
                        space.setItem(item);
                        item.setLocation(space);
                        // update the remaining space
                        space.calcRemainingFootage(item.getTotalSquareFootage());
                        break;
                    }
                }
                */
            }
            if(possiblyFull)
                break;
            iter = inventory.listIterator();
        }
        
        for(WarehouseSpace w : warehouse) {
            if(!w.isFilled()) {
                possiblyFull = false;
            }
        }
        if(possiblyFull) {
            System.out.println("The warehouse is full. Cannot place any more items.");
            System.out.println("Displaying partial results.");
        }
    }
    
    public void updateInventory() {
        ListIterator<InventoryItem> iter = inventory.listIterator();
        InventoryItem item;
        while(iter.hasNext()) {
            item = iter.next();
            if(item.getInventoryCount() == 0) {
                iter.remove();
            }
        }
    }
    
    // once the work is done, iterate over the list and consolidate the inventory count
    // of the items that share a name
    public void consolidateData() {
        ArrayList<InventoryItem> mergedList = new ArrayList<InventoryItem>();

        for(InventoryItem item : inventory) {
            int index = mergedList.indexOf(item);
            if(index != -1) {
                mergedList.set(index, mergedList.get(index).merge(item));
            } else {
                mergedList.add(item);
            }
        }
        inventory = mergedList;
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
        
        Collections.sort(warehouse);
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
        
        Collections.sort(inventory);
    }
    
    // iterate over the whole inventory and display the data associated with each item
    public void displayInventory(boolean pretty) {
        int needAHome = 0;
        ArrayList<InventoryItem> leftover = new ArrayList<InventoryItem>();
        if(pretty) {
            // print the items nicely and track any leftovers
            for(InventoryItem i : inventory) {
                System.out.println(i.prettyPrint());
                // no location and there are leftovers
                if(i.getLocation() == null && i.getInventoryCount() != 0) {
                    needAHome++;
                    leftover.add(i);
                }
            }
        }
        else {
            // print the items normally and track any leftovers
            for(InventoryItem i : inventory) {
                System.out.println(i);
                // no location and there are leftovers
                if(i.getLocation() == null && i.getInventoryCount() != 0) {
                    needAHome++;
                    leftover.add(i);
                }
            }
        }
        
        // report the items leftover
        System.out.println();
        System.out.println("Still need a home: " + needAHome);
        for(InventoryItem i : leftover) {
            System.out.println(i.prettyPrint());
        }
    }
    
    // probably not needed but could come in handy
    public void displaySpaces() {
        for(WarehouseSpace i : warehouse) {
            System.out.println(i);
        }
    }
    
    public void writeCSVFile() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(OUTFILE);
            fileWriter.append(FILE_HEADER);
            fileWriter.append("\n");
            
            for(InventoryItem i : inventory) {
                fileWriter.append(i.prettyPrint());
                fileWriter.append("\n");
            }
            System.out.println(OUTFILE + " CSV file created!");
        }
        catch(Exception e) {
            System.out.println("Error writing to file: " + OUTFILE);
        }
        finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            }
            catch(IOException e) {
                System.out.println("Error flushing file writer.");
            }
        }
    }
}
