package BL.Inventory;


public abstract class Report {
    private String reportName;
    private static int nextID = 1;
    private final int reportID;

    public Report(String ReportName){
        this.reportName=ReportName;
        this.reportID=nextID++;
    }

    public String getReportName(){
        return this.reportName;
    }

    public int getReportID(){
        return this.reportID;
    }


}
