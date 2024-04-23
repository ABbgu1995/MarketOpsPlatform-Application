package BL.Inventory;
import DAL.Inventory.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class ItemController {
    private static ItemController instance;
    private ItemDAO itemDAO;

    public static ItemController getInstance(){
        if (instance == null){
            instance = new ItemController();
        }
        return instance;
    }
    public ItemController(){
        itemDAO = ItemDAO.getInstance();

    }

    protected Item createNewItem(String CatalogNumber, String Maker, String Supplier, double SupplierPrice,
                              double StorePrice, Date ExpiredDate, String Category, String SubCategory, String SubSubCategory){
        Item item = new Item(CatalogNumber, Maker, Supplier, SupplierPrice, StorePrice, ExpiredDate, Category,
                SubCategory, SubSubCategory);
        return item;
    }

    public status checkExpireDate(int branchNumber, int itemId){
//        BranchController branchController = BranchController.getInstance();
//        Item item = branchController.foundItemInBranch(branchNumber, itemId);
        Item item = itemDAO.getItemByID(itemId,branchNumber);
        Date exp_date = item.getExpiredDate();
        if (exp_date==null)
        {
            return status.failure;
        }
        LocalDate today = LocalDate.now();
        LocalDate givenLocalDate = exp_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (givenLocalDate.equals(today) || givenLocalDate.isBefore(today)) {
            item.setDamage(damageType.ExpiredDay);
            return status.success;
        }
        else {
            return status.failure;
        }
    }


    public long checkHowManyDayExpired(Item item){
        Date givenDate = item.getExpiredDate();
        if (givenDate==null){
            return -1;
        }
        LocalDate today = LocalDate.now();
        LocalDate givenLocalDate = givenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (givenLocalDate==null){
            return -1;
        }
        else if (givenLocalDate.isBefore(today)){
            return ChronoUnit.DAYS.between(givenLocalDate, today);
        }else{
            return -1;
        }
    }

    public void setItemPlace(place curr_place, Item item){
        item.setItemPlace(curr_place);
        itemDAO.update(item);
    }
    public void setItemDamage(damageType curr_damage, Item item){
        item.setDamage(curr_damage);
        itemDAO.update(item);
    }

    public void setItemDamageName(String str, Item item){
        item.setDamageTypeItem(str);
        itemDAO.update(item);
    }
}
