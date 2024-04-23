package BL.Inventory;

import DAL.Inventory.DiscountDAO;
import DAL.Inventory.ItemDAO;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DiscountController {
    private static DiscountController instance;
    private final DiscountDAO discountDAO;
    private final ItemDAO itemDAO;
    public static DiscountController getInstance(){
        if (instance == null) {
            instance = new DiscountController();
        }
        return instance;
    }

    public DiscountController(){
        discountDAO = DiscountDAO.getInstance();
        itemDAO = ItemDAO.getInstance();
    }

    public status createNewDiscountByCategory(int branchNum, double DiscountPercent, Date DateFrom, Date DateTo, String type){
        Discount new_dis = new Discount(DiscountPercent, DateFrom, DateTo, type);
        discountDAO.insert(new_dis);
        BranchController branchController = BranchController.getInstance();
        status curr_status = branchController.updateNewDiscountByCategory(type, branchNum, new_dis);
        if(curr_status.equals(status.failure)){
            return status.failure;
        }
        return status.success;
    }


    public status createNewDiscountByCatalogNumber(int branchNum, double DiscountPercent, Date DateFrom, Date DateTo, String type){
        Discount new_dis = new Discount(DiscountPercent, DateFrom, DateTo, type);
        discountDAO.insert(new_dis);
        BranchController branchController = BranchController.getInstance();
        if(branchController == null){
            return status.failure;
        }
        if(branchController.updateNewDiscountByCatalogNumber(type, branchNum, new_dis).equals(status.failure)){
            return status.failure;
        }
        return status.success;
    }


    public void allBranchSetDiscount(){
        BranchController branchController = BranchController.getInstance();
        Map<Integer, Branch> mapOfBranch = branchController.getAllBranch();
        if(mapOfBranch == null){
            return;
        }
        for (Map.Entry<Integer, Branch> entry : mapOfBranch.entrySet()) {
            checkIfDiscountExist(entry.getValue());
        }
    }
    public void checkIfDiscountExist(Branch branch){
        LocalDate today = LocalDate.now();
        Instant instant = today.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        for (Item item : branch.getItemsInBranch()){
            checkIfDiscountExistHelper(date, item);
        }

    }

    private void checkIfDiscountExistHelper(Date date, Item item) {
        double biggest_dis_present = 0;
        Discount dis = null;
        if(item.getDiscount() == null){
            return;
        }
        for (Discount discount : item.getDiscount()){
            Date toDate = discount.getTheLimitDayOfDiscount();
            Date fromDate = discount.getTheFirstDayOfDiscount();
            if(date.after(fromDate) && date.before(toDate)){
                double curr_dis_precent = discount.getDiscountPercent();
                if(curr_dis_precent>biggest_dis_present){
                    dis = discount;
                    biggest_dis_present = curr_dis_precent;
                }
            }
        }
        if(dis == null){
            item.setItemStorePriceWithDiscount(-1);
            itemDAO.update(item);
        }else {
            item.setItemStorePriceWithDiscount(biggest_dis_present);
            itemDAO.update(item);
        }
    }


}
