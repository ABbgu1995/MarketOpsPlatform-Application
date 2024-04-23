package BL.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Branch {
    private final int branchNumber;
    private List<Item> ItemsInBranch;
    private final Map<String, Integer> minAmountOfAllItemByCatalog;
    private Map<String, List<String>> mapByCategoryCatalogNumber;
    private List<RInventory> reportInventory;
    private List<RDamage> reportDamage;
    private List<RShortage> reportShortage;
    private Map<String, Integer>  mapMissingProduct;


    public Branch(int branchNum, List<Item> ProductInBranch, Map<String , Integer> MinAmountOfAllItemByCatalog,  Map<String, List<String>> mapByCategoryCatalogNumber, Map<String, Integer>  mapMissingProduct){
        this.ItemsInBranch = ProductInBranch;
        this.branchNumber = branchNum;
        this.minAmountOfAllItemByCatalog = MinAmountOfAllItemByCatalog;
        this.mapMissingProduct = mapMissingProduct;
        this.mapByCategoryCatalogNumber = mapByCategoryCatalogNumber;
        this.reportInventory = new ArrayList<>();
        this.reportDamage = new ArrayList<>();
        this.reportShortage = new ArrayList<>();
    }

    public Branch(int branchNum, Map<String , Integer> MinAmountOfAllItemByCatalog){
        this.ItemsInBranch = new ArrayList<Item>();
        this.branchNumber = branchNum;
        this.minAmountOfAllItemByCatalog = MinAmountOfAllItemByCatalog;
        this.mapMissingProduct = new HashMap<>();
        this.mapByCategoryCatalogNumber = new HashMap<>();
        this.reportInventory = new ArrayList<>();
        this.reportDamage = new ArrayList<>();
        this.reportShortage = new ArrayList<>();
    }
    public void setReportInventory(RInventory reportToSet){
        this.reportInventory .add(reportToSet);
    }
    public void setReportDamage(RDamage reportToSet){
        this.reportDamage.add(reportToSet);
    }
    public void setReportShortage( RShortage reportToSet){
        this.reportShortage.add(reportToSet);
    }

    public Map<String, List<String>> getMapByCategoryCatalogNumber(){
        return this.mapByCategoryCatalogNumber;
    }
    public void setMapByCategoryCatalogNumber(String category, String catalogNum){
        if(this.mapByCategoryCatalogNumber.get(category) == null){
            List<String> new_list = new ArrayList<>();
            new_list.add(catalogNum);
            mapByCategoryCatalogNumber.put(category, new_list);
        }else{
            this.mapByCategoryCatalogNumber.get(category).add(catalogNum);
        }
    }
    public int getBranchNumber(){
        return this.branchNumber;
    }

    public List<Item> getItemsInBranch(){
        return this.ItemsInBranch;
    }

    public Map<String, Integer> getMinAmountOfAllItemByCatalog(){
        return this.minAmountOfAllItemByCatalog;
    }
    public void setMinAmountOfItemByCatalog(String catNum, int amount){
        this.getMinAmountOfAllItemByCatalog().put(catNum, amount);
    }

    public  Map<String, Integer> getMapMissingProduct(){
        return this.mapMissingProduct;
    }
    public void setItemInBranch(Item item){
        this.ItemsInBranch.add(item);

    }



}

