/**
 * @author Trevor Hodde
 */
public class Application {

    public static void main(String[] args) {        
        WarehouseManager manager = new WarehouseManager();
        manager.createEmptyWarehouse();
        manager.generateInventory();
        manager.fillWarehouse();
        manager.updateInventory();
        manager.displayInventory(true); 
        //manager.writeCSVFile();
    }
}