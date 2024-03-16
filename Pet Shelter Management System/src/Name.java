import java.util.HashMap;
import java.util.Map;

public final class Name {
    private static final Map<String, Name> NAMES = new HashMap<>();
    private final String firstName, lastName, strRep;

    public Name(String firstName, String lastName, String strRep) {
        if (firstName == null || lastName == null){
            throw new NullPointerException("Name is mull");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.strRep = strRep;
    }

    public static Name getInstance(String firstName, String lastName) {
       String strRep = firstName + " " + lastName;
       Name name = NAMES.get(strRep);
       if (name == null){
           name = new Name(firstName,lastName,strRep);
           NAMES.put(strRep,name);
       }
       return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return strRep;
    }
}


