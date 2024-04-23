package BL.Inventory;

import java.util.Map;

public class RInventory extends Report{
    private Map<String, Integer> allInventory;

    public RInventory(Map<String, Integer> all_inventory, String report_name){
        super(report_name);
        this.allInventory = all_inventory;

    }
    public Map<String, Integer> getAllInventory(){
        return this.allInventory;
    }

    public void setAllInventory( Map<String, Integer> map){
        this.allInventory=map;
    }
}
