package Server;

public class Product {

    private int logId, cmdType;
    private String lineId, logTime, description, unitType;
    private double loggedValue;
    


    public Product(){
        this.logId = 0;
        this.lineId = "";
        this.logTime = "";
        this.loggedValue = 0.0;
        this.cmdType = 0;
        this.description = "";
        this.unitType = "";
    }

    public Product(int logId, String lineId,
     String logTime, double loggedValue, 
     int cmdType, String description, String unitType ){

        setLogId(logId);
        setLineId(lineId);
        setLogTime(logTime);
        setLoggedValue(loggedValue);
        setCmdType(cmdType);
        setDescription(description);
        setUnitType(unitType);

    }
    public Product(int logId, String lineId,
     String logTime, double loggedValue){

        setLogId(logId);
        setLineId(lineId);
        setLogTime(logTime);
        setLoggedValue(loggedValue);
    }
    public Product(String lineId, double loggedValue){
        setLineId(lineId);
        setLoggedValue(loggedValue);
    }
    public Product(int logId, double loggedValue){

        setLogId(logId);
        setLoggedValue(loggedValue);
    }



    public int getLogId() {
        return logId;
    }
    public void setLogId(int logId) {
        this.logId = logId;
    }
    public int getCmdType() {
        return cmdType;
    }
    public void setCmdType(int cmdType) {
        this.cmdType = cmdType;
    }
    public String getUnitType() {
        return unitType;
    }
    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLogTime() {
        return logTime;
    }
    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }
    public String getLineId() {
        return lineId;
    }
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }
    public double getLoggedValue() {
        return loggedValue;
    }
    public void setLoggedValue(double loggedValue) {
        this.loggedValue = loggedValue;
    }
}
