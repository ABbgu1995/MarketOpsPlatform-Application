package BL.Suppliers;

import BL.Suppliers.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Supplier {

    private int supplierId;
    private String name;
    private String address;

    private int shipmentTime;

    private boolean mobility;
    private List<String> companies;
    private Map <String, Integer> contacts ;
    private Contract currentContract;

    public Supplier() {
    }

    public Supplier(int supplierId, String address, boolean mobility, String name, int shipmentTime){
        this.supplierId=supplierId;
        this.address=address;
        this.mobility=mobility;
        this.companies=new ArrayList<String>();
        this.contacts=new HashMap<String, Integer>();
        this.name=name;
        this.shipmentTime = shipmentTime;
    }

    public int getShipmentTime() {
        return shipmentTime;
    }

    public void setShipmentTime(int shipmentTime) {
        this.shipmentTime = shipmentTime;
    }

    public boolean getMobility(){
        return this.mobility;
    }
    public void setCurrentContract(Contract currentContract) {
        this.currentContract = currentContract;
    }

    public void addContact(String name, int number){
        contacts.put(name, number);
    }

    public void addCompany(String company){
        this.companies.add(company);
    }

    public int getSupplierId() {
        return supplierId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Contract getCurrentContract() {
        return currentContract;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMobility(boolean mobility) {
        this.mobility = mobility;
    }

    public void setCompanies(List<String> companies) {
        this.companies = companies;
    }

    public void setContacts(Map<String, Integer> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return this.address + "," + this.name + "," + this.supplierId +  "\n";
    }



}
