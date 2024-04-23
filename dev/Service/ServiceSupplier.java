package Service;

import BL.Inventory.ReportController;
import BL.Suppliers.OrderController;
import BL.Suppliers.SupplierOrder;
import org.junit.jupiter.api.Order;

import java.util.List;
import java.util.Map;

import static UI.Suppliers.Menu.updatePeriodicOrder;

public class ServiceSupplier {


    OrderController orderController;

    private static ServiceSupplier instance;

    public static ServiceSupplier getInstance(){
        if (instance == null) {
            instance = new ServiceSupplier();
        }
        return instance;
    }
    ServiceSupplier(){

        this.orderController = OrderController.getInstance();
    }

    public List<SupplierOrder> executeShortageOrderService(Map<String, Integer> shortage_rep){
        orderController.setProductMissingReport(shortage_rep);
        List<SupplierOrder> supplierOrders=orderController.executeOrder();
        return supplierOrders;
    }
    public void updatePeriodicOrderService(){
        updatePeriodicOrder();
    }
}
