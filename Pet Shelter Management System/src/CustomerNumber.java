import java.util.Calendar;
import java.util.Date;


public class CustomerNumber  {
    private  String prefix;
    private  Date issueDate;
    private static int serialNumber = 10;


    private CustomerNumber(String prefix, Date issueDate) {
        this.prefix = prefix;
        this.issueDate = issueDate;
    }
    public static CustomerNumber createCustomerNumber (Name name) {
        String initialLetter = String.valueOf(name.getFirstName().charAt(0)).toUpperCase();
        String prefix = initialLetter + serialNumber;
        Date issueDate = new Date();

//      Ensure the uniqueness of CustomerNumber with static variable accumulation
        serialNumber++;
        return new CustomerNumber(prefix, issueDate);
    }
    public String getPrefix() {
        return prefix;
    }

    public Date getIssueDate() {
        return (Date) (issueDate.clone());
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public String toString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

       StringBuilder sb = new StringBuilder(prefix);
         sb.append(".").append(month).append(year);
        return sb.toString();
    }
}
