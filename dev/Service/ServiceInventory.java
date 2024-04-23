package Service;

import BL.Inventory.ReportController;
import DAL.Inventory.ReportDAO;

import java.util.Map;

public class ServiceInventory {
    ReportController reportController;
    private static ServiceInventory instance;
    public static ServiceInventory getInstance(){
        if (instance == null) {
            instance = new ServiceInventory();
        }
        return instance;
    }
    ServiceInventory(){
        this.reportController = ReportController.getInstance();
    }
    public Map<String, Integer> getShortageReportMap(int branchNum){
        return reportController.createNewShortageReport(branchNum);
    }


}
