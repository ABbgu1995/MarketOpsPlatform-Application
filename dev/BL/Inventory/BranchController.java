package BL.Inventory;

import DAL.Inventory.BranchDAO;
import DAL.Inventory.ItemDAO;
import DAL.Inventory.DiscountDAO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class BranchController {
    private static BranchController instance;
    private final ItemDAO itemDAO;
    private final BranchDAO branchDAO;
    private final DiscountDAO discountDAO;
    public static BranchController getInstance(){
        if (instance == null) {
            instance = new BranchController();
        }
        return instance;
    }
    public BranchController(){
        itemDAO = ItemDAO.getInstance();
        branchDAO = BranchDAO.getInstance();
        discountDAO = DiscountDAO.getInstance();
    }
    public Map<Integer, Branch> getAllBranch(){
        return branchDAO.getAllBranch();
    }

    public void createNewBranch(int branchNum, Map<String , Integer> MinAmountOfAllItemByCatalog){
        Branch new_branch = new Branch(branchNum, MinAmountOfAllItemByCatalog);
        branchDAO.insert(new_branch);
    }

    public Item createItemToBranch(int branchNum, String CatalogNumber, String Maker, String Supplier, double SupplierPrice,
                                   double StorePrice, Date ExpiredDate, String Category, String SubCategory, String SubSubCategory){
        ItemController itemController = ItemController.getInstance();
        Item item = itemController.createNewItem(CatalogNumber, Maker, Supplier, SupplierPrice, StorePrice, ExpiredDate, Category, SubCategory, SubSubCategory);

        itemDAO.insert(item, branchNum);
        branchDAO.updateCategoryByCatalogNumber(item, branchNum);
        Branch curr_branch = branchDAO.getBranchByNumber(branchNum);
        curr_branch.setItemInBranch(item);
        curr_branch.setMapByCategoryCatalogNumber(item.getCategory(), item.getCatalogNumber());

        return item;
    }

    public Branch getBranchByNumber(int branchNum){
        return branchDAO.getBranchByNumber(branchNum);
    }


    public Map<String, Integer> countItemsInBranch(int branchNum){
        Map<String, Integer> count_items = new HashMap<String, Integer>();
        List<String> list_all_catalog_num = new ArrayList<String>();
        Branch branch = getBranchByNumber(branchNum);
        if(branch == null) {
            return count_items;
        }
        List<Item> all_item_in_branch = branch.getItemsInBranch();
        if(all_item_in_branch == null){
            return count_items;
        }
        return countCatalogNumByList(list_all_catalog_num, all_item_in_branch, count_items);
    }


    private Map<String, Integer> countCatalogNumByList(List<String> list_all_catalog_num, List<Item> all_item, Map<String, Integer> count_items){
        for(Item item1 : all_item){
            String currCatalog = item1.getCatalogNumber();
            if(!list_all_catalog_num.contains(currCatalog)){
                int counter = 0;
                for (Item item2 : all_item){
                    if(item2.getCatalogNumber().equals(currCatalog)){
                        counter ++;
                    }
                }
                count_items.put(currCatalog, counter);
                list_all_catalog_num.add(currCatalog);
            }
        }
        return count_items;
    }

    public Map<String, Integer> sortMapByCategory(Map<String, Integer> map1){
        return new TreeMap<>(map1);
    }
    public boolean deleteItemFromBranch(Item remove_item,  int branchNum) {
        Branch branch = getBranchByNumber(branchNum);
        List<Item> list_items =  branch.getItemsInBranch();
        int curr_id = remove_item.getItemID();
        for(int i = 0; i<list_items.size(); i++){
            if(list_items.get(i).getItemID() == curr_id){
                list_items.remove(i);
                itemDAO.delete(remove_item);
                checkIfItemAmountMinAfterRemove(remove_item, branchNum);
                return true;
            }
        }
        return false;
    }

    //when item removed
    public void checkIfItemAmountMinAfterRemove(Item remove_item, int branchNum){
        Branch branch = getBranchByNumber(branchNum);
        String mkt = remove_item.getCatalogNumber();
        Map<String, Integer> allItems = countItemsInBranch(branchNum);
        Integer value = allItems.get(mkt);
        if(value == null){
            value = 0;
        }
        Integer minAmount= branch.getMinAmountOfAllItemByCatalog().get(mkt);
        if(value <= minAmount){
            Map<String, Integer> allMissing = branch.getMapMissingProduct();
            if(minAmount == 0){
                allMissing.put(mkt, 2*5 - value);
            }else {
                allMissing.put(mkt, minAmount * 5 - value);
            }
        }
    }

    //to get report map
    public  Map<String, Integer> updateLowAmountByCatalogNumber(int branchNum){
        Map<String, Integer> map3 = countItemsInBranch(branchNum);
        Branch branch = getBranchByNumber(branchNum);
        if (branch==null){
            return map3;
        }
        Map<String, Integer> mapOfMinByCatalogNum = branch.getMinAmountOfAllItemByCatalog();
        Map<String, Integer> mapAllMissing = branch.getMapMissingProduct();
        for(String mkt : mapOfMinByCatalogNum.keySet()){
            int minAmount = mapOfMinByCatalogNum.get(mkt);
            int currentAmount = map3.getOrDefault(mkt, 0);
            if (currentAmount <= minAmount) {
                if(minAmount == 0){
                    mapAllMissing.put(mkt, 2*5 - currentAmount);
                }else {
                    mapAllMissing.put(mkt, minAmount * 5 - currentAmount);
                }
            }
        }
        return mapAllMissing;
    }

    public Map<Item, damageType> getAllDamageItems(int branchNum){
        Map<Item, damageType> mapAllDamage = new HashMap<>();
        Branch branch = getBranchByNumber(branchNum);
        ItemController itemController = ItemController.getInstance();
        List<Item> listBranch = branch.getItemsInBranch();
        for (Item item2 : listBranch){
            itemController.checkExpireDate(branchNum, item2.getItemID());
            if (item2.getDamage() == damageType.ExpiredDay) {
                mapAllDamage.put(item2, damageType.ExpiredDay);
            } else if (item2.getDamage() == damageType.Damage) {
                mapAllDamage.put(item2, damageType.Damage);
            }
        }
        return mapAllDamage;
    }



    public void DiscountCalculation(Item item, Discount newDiscount){
        double currPrice = item.getItemStorePrice() ;
        double currDiscount = newDiscount.getDiscountPercent();
        double finalPrice = ((100-currDiscount)*currPrice)/100;
        item.setItemStorePriceWithDiscount(finalPrice);
        itemDAO.update(item);
    }

    public boolean updateByDate(Date fromDate, Date toDate){
        LocalDate today = LocalDate.now();
        Instant instant = today.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        if((date.after(fromDate)||date.equals(fromDate)) && date.before(toDate)){
            return true;
        }
        return false;
    }


    public status updateNewDiscountByCategory(String category, int branchNum, Discount newDiscount){
        Branch branch = getBranchByNumber(branchNum);
        if(branch == null){
            return status.failure;
        }
        for(Item item : branch.getItemsInBranch()){
            if(item.getCategory().equals(category)){
                item.setDiscount(newDiscount);
//                discountDAO.insert(newDiscount);
                if (updateByDate(newDiscount.getTheFirstDayOfDiscount(), newDiscount.getTheLimitDayOfDiscount())){
                    DiscountCalculation(item,newDiscount);
                }
            }
        }
        return status.success;
    }


    public status updateNewDiscountByCatalogNumber(String catalogNumber, int branchNum, Discount newDiscount){
        Branch branch = getBranchByNumber(branchNum);
        if(branch == null){
            return status.failure;
        }
        for(Item item : branch.getItemsInBranch()){
            if(item.getCatalogNumber().equals(catalogNumber)){
                item.setDiscount(newDiscount);
//                itemDAO.update(item);
//                discountDAO.insert(newDiscount);
                if (updateByDate(newDiscount.getTheFirstDayOfDiscount(), newDiscount.getTheLimitDayOfDiscount())){
                    DiscountCalculation(item,newDiscount);
                }
            }
        }
        return status.success;
    }

    public Item foundItemInBranch(int branchNum, int itemIdNum){
        return itemDAO.getItemByID(itemIdNum, branchNum);
    }

    public void updateMinAmountOfCatalog(Item item, int branchNum, int newMinNumber){
        String catNum = item.getCatalogNumber();
        boolean bool = branchDAO.checkIfCatalogExist(catNum, branchNum);
        Branch branch = branchDAO.getBranchByNumber(branchNum);
        branch.setMinAmountOfItemByCatalog(catNum, newMinNumber);
        if(!bool){
            branchDAO.updateCategoryByCatalogNumber(item, branchNum);
            branchDAO.updateMinAmount(branch);
        }
    }
//111111111111111111111111111111111111
}
