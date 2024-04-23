package BL.Inventory;

import java.util.Map;

public class RShortage extends Report{

    private Map<String, Integer> allShortage;

    public RShortage( Map<String, Integer> all_shortage, String report_name){
        super(report_name);
        this.allShortage = all_shortage;
    }

    public  Map<String, Integer> getAllShortage()
    {
        return this.allShortage;
    }

    public void setAllShortage( Map<String, Integer> list){
        this.allShortage=list;
    }

}
