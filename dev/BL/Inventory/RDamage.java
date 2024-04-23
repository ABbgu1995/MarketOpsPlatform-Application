package BL.Inventory;

import java.util.Map;

public class RDamage extends Report {
    private Map<Item, damageType> allDamage;

    public RDamage(Map<Item, damageType> all_damadge, String report_name){
        super(report_name);
        this.allDamage = all_damadge;

    }

    public Map<Item, damageType> getAllDamage(){
        return this.allDamage;
    }

    public void setAllDamage( Map<Item, damageType> map){
        this.allDamage=map;
    }




}
