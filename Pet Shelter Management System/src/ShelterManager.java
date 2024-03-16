
import java.util.*;

public class ShelterManager {
    private Map<PetID, Pet> pets = new HashMap<>();
    private Map<String, CustomerRecord> records = new HashMap<>();
    private Map<CustomerRecord, List<Pet>> adoptList = new HashMap<>();

    //Task 1.3 methods
    public Pet addPet(String petType) {
//        Creates a new object when the pet is a cat or a dog
        PetID petID = PetID.valueOf();
        Pet pet;
        if (petType.equals(PetFactory.CAT_TYPE)) {
            pet = new Cat(petID);
        } else if (petType.equals(PetFactory.DOG_TYPE)) {
            pet = new Dog(petID);
        } else {
            throw new IllegalArgumentException("Invalid pet type: " + petType);
        }

        pets.put(petID, pet);
        return pet;
    }

    public Boolean updatePetRecord(PetID petID, Boolean trained) {
//      Get the value by the key in Collections,then determine the attributes of the value
        Pet pet = pets.get(petID);
        if (pet == null) {
            throw new IllegalArgumentException("Pet with ID " + petID.getPetId() + " does not exist in the shelter!");
        }
        if (pet instanceof Dog) {
            Dog dog = (Dog) pet;
            if (trained != null) {
                dog.setTrained(trained);
                return true;
            }
        }
        if (pet instanceof Cat) {
            throw new IllegalArgumentException("This type of pet cannot be trained");
        }
        return false;
    }

    public int noOfAvailablePets(String petType) {
//      Declare a variable, assume an initial value of 0, Iterate over the set, and accumulate the variable each time the Iterate meets the condition
        int count = 0;
        for (Pet Pets : pets.values()) {
            if (Pets.getPetType().equals(petType) && !Pets.getAdopted()) {
                count++;
            }
        }
        if (count == 0) {
            throw new IllegalArgumentException("No " + petType + " up for adoption");
        }
        return count;
    }

    //Task 2.2 methods
    public CustomerRecord addCustomerRecord(String firstName, String lastName,
                                            Date dob, Boolean hasGarden) {

        String record = firstName + lastName + dob.toString();
        Name name = Name.getInstance(firstName, lastName);
//      Create a unique customer number by name
        CustomerNumber customerNumber = CustomerNumber.createCustomerNumber(name);

        CustomerRecord newCustomer;
//      Throws an exception if both the name and birthday are the same.
        if (records.containsKey(record)) {
            throw new IllegalArgumentException("Customer with the same information already exists.");
        } else {
            newCustomer = new CustomerRecord(name, dob, customerNumber, hasGarden);
        }
        records.put(record, newCustomer);
        return newCustomer;
    }

    public Boolean adoptPet(CustomerRecord customerRecord, String petType) {
//        Get the list of corresponding pets by customerRecord
        List<Pet> pet = adoptList.get(customerRecord);
        if (pet == null) {
            pet = new ArrayList<>();
        }
//      Get the size of the pet list (number of pets),call the method of the customerRecord class to get the customer's age.
        int num = pet.size();
        boolean hasGarden = customerRecord.isHasGarden();
        int age = customerRecord.getAge(customerRecord.getDob());

//      Adding unadopted pets from a collections to a new ArrayList.
        List<Pet> availablePets = new ArrayList<>();
        for (Pet availablePet : pets.values()) {
            if (availablePet.getPetType().equals(petType) && !availablePet.getAdopted()) {
                availablePets.add(availablePet);
            }
        }
        if (availablePets.isEmpty()) {
            System.out.println("No available " + petType + " to adopt.");
            return null;
        }
        if (num >= 3) {
            System.out.println("You have reached the maximum number of pets you can adopt.");
            return false;
        }

//      Randomly giving a client an unadopted pet. Tests to see if the customer meets the adoption criteria for either a cat or a dog.
        Pet p = availablePets.get(new Random().nextInt(availablePets.size()));
        if (p instanceof Dog) {
            Dog dog = (Dog) p;
            boolean trained = dog.getTrained();
//            There are two types of dog adoption conditions, one trained and one untrained
            if ((trained && hasGarden && age >= 18) || (!trained && hasGarden && age >= 21)) {
                System.out.println("You are adopting a " + (trained ? "trained" : "untrained") + " dog");
                dog.setAdopted(true);
                pet.add(dog);
                adoptList.put(customerRecord, pet);
                return true;
            } else {
                System.out.println("Failed to adopt dog: " + (trained ? "trained" : "untrained") +
                        " dog requires " + (trained ? "18" : "21") + " years old with a garden.");
                return false;
            }
        }
//            To adopt a cat you only need to be 18 years old
        if (p instanceof Cat && age >= 18) {
            Cat cat = (Cat) p;
            System.out.println("You are adopting a cat");
            cat.setAdopted(true);
            pet.add(cat);
            adoptList.put(customerRecord, pet);
            return true;
        } else {
            System.out.println("Failed to adopt pet: Customer under 18 years of age.");
            return false;
        }

    }

    public Collection<Pet> adoptedPetsByCustomer(CustomerNumber customerNumber) {
//      Iterate through the collections, target customers by CustomerNumber comparison, bind them to their corresponding pets
        List<Pet> adoptedPets = new ArrayList<>();
        for (Map.Entry<CustomerRecord, List<Pet>> entry : adoptList.entrySet()) {
            CustomerRecord customerRecord = entry.getKey();
            if (customerRecord.getCustomerNumber().equals(customerNumber)) {
                adoptedPets.addAll(entry.getValue());
            }
        }
        if (adoptedPets.isEmpty()) {
            throw new NullPointerException("This customer has not adopted a pet");
        }
//        Returns a collections that cannot be modified
        return Collections.unmodifiableCollection(adoptedPets);

    }
}
