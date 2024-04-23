package BL.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import DAL.Inventory.ReportDAO;

public class ReportController {

    private static ReportController instance;
    private final ReportDAO reportDAO;
    public static ReportController getInstance(){
        if (instance == null) {
            instance = new ReportController();
        }
        return instance;
    }

    public ReportController(){
        reportDAO = ReportDAO.getInstance();
    }


    public  Map<String, Integer> createNewInventoryReport(int branchNum){
        BranchController branchController = BranchController.getInstance();
        Map<String, Integer> map = branchController.sortMapByCategory(branchController.countItemsInBranch(branchNum));
        String rep_name = "Inventory";
        if(map.size() == 0){
            return null;
        }
        RInventory new_report = new RInventory(map, rep_name);
        reportDAO.insert(new_report, branchNum);
        branchController.getBranchByNumber(branchNum).setReportInventory(new_report);
        return map;
    }


    public  Map<Item, damageType> createNewDamageReport(int branchNum){
        BranchController branchController = BranchController.getInstance();
        Map<Item, damageType> map = branchController.getAllDamageItems(branchNum);
        if(map.size() == 0){
            return null;
        }
        String rep_name = "Damage";
        RDamage new_report = new RDamage(map, rep_name);
        reportDAO.insert(new_report, branchNum);
        branchController.getBranchByNumber(branchNum).setReportDamage(new_report);
        return map;
    }

    public  Map<String, Integer> createNewShortageReport(int branchNum) {
        BranchController branchController = BranchController.getInstance();
        Map<String, Integer> map = branchController.updateLowAmountByCatalogNumber(branchNum);
        if(map.size() == 0){
            return null;
        }
        String rep_name = "Shortage";
        RShortage new_report = new RShortage(map, rep_name);
        reportDAO.insert(new_report, branchNum);
        branchController.getBranchByNumber(branchNum).setReportShortage(new_report);
        return map;
    }

    public RShortage getShortageReport(int repId, int branchNum){
        return reportDAO.getRSortage(repId, branchNum);
    }
    public RDamage  getDamageReport(int branchNum, int repId){
        return reportDAO.getRDamage(repId, branchNum);
    }
    public RInventory getInventoryReport(int branchNum, int repId){
        return reportDAO.getRInventory(repId, branchNum);
    }
}
