public class Account {
    public int recordId;
    public String usageDescription;
    public int amount;
    public String usageDate;
    public int clubId;

    public Account(int recordId, String usageDescription, int amount, String usageDate, int clubId) {
        this.recordId = recordId;
        this.usageDescription = usageDescription;
        this.amount = amount;
        this.usageDate = usageDate;
        this.clubId = clubId;
    }
}
