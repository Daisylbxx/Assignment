import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class CustomerRecord {
    private final Name name;

    private final Date dob;

    private final CustomerNumber customerNumber;

    private final boolean hasGarden;


    public CustomerRecord(Name name, Date dob, CustomerNumber customerNumber, boolean hasGarden) {
        this.name = name;
        this.dob = new Date(dob.getTime());
        this.customerNumber = customerNumber;
        this.hasGarden = hasGarden;

    }

    // factory method
    public static CustomerRecord createCustomerRecord(String firstName, String lastName, int year, int month, int day, boolean hasGarden) {

        Name name = Name.getInstance(firstName, lastName);
        CustomerNumber customerNumber = CustomerNumber.createCustomerNumber(name);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);

        Date dob = calendar.getTime();
        return new CustomerRecord(name, dob, customerNumber, hasGarden);
    }

    public Name getName() {
        return name;
    }

    public Date getDob() {
        return (Date) dob.clone();
    }

    public CustomerNumber getCustomerNumber() {
        return customerNumber;
    }

    public boolean isHasGarden() {
        return hasGarden;
    }


    public int getAge(Date dob) {
//        This method is created to implement the adoptPet method in the ShelterManager class.
        Calendar b = Calendar.getInstance();
        b.setTime(dob);
        Calendar t = Calendar.getInstance();
//        Comparison of ages accurate to the number of days
        int age = t.get(Calendar.YEAR) - b.get(Calendar.YEAR) -
                ((t.get(Calendar.MONTH) < b.get(Calendar.MONTH)) || (t.get(Calendar.MONTH) == b.get(Calendar.MONTH)
                        && t.get(Calendar.DAY_OF_MONTH) < b.get(Calendar.DAY_OF_MONTH)) ? 1 : 0);
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CustomerRecord)) return false;
        final CustomerRecord customerRecord = (CustomerRecord) obj;
        return name.equals(customerRecord.name) && dob.equals(customerRecord.dob)
                && customerNumber.equals(customerRecord.customerNumber) && hasGarden == customerRecord.hasGarden;
    }

    @Override
    public int hashCode() {
        int hc = 17;
        hc = 37 * hc + name.hashCode();
        hc = 37 * hc + dob.hashCode();
        hc = 37 * hc + customerNumber.hashCode();
        return hc * 37 + Boolean.hashCode(hasGarden);
    }

    @Override
    public String toString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dob);

        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatDate.format(dob);

        final StringBuilder sb = new StringBuilder("Name: ");
        sb.append(name.toString()).append(", ")
                .append("Date of Birth: ").append(formattedDate).append(", ")
                .append("Customer Number: ").append(customerNumber).append(", ")
                .append("Garden Availability: ").append(hasGarden ? "Yes" : "No");

        return sb.toString();
    }

}
