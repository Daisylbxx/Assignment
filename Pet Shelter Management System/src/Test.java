import uk.ac.ncl.teach.ex.test.Assertions;
import java.util.*;

public class Test {
    public static void main(String[] args) {

        Test test = new Test();

        System.out.println("Test add pet");
        test.testAddPet();
        System.out.println("________________________________");

        System.out.println("Test update pet record");
        test.testUpdatePetRecord();
        System.out.println("________________________________");

        System.out.println("Test number of unadopted pets");
        test.testNoOfAvailablePets();
        System.out.println("________________________________");

        System.out.println("Test add CustomerRecord");
        test.testAddCustomerRecord();
        System.out.println("________________________________");

        System.out.println("Test adopt pet");
        test.testAdoptPet();
        System.out.println("________________________________");

        System.out.println("Test adopted pets by customer");
        test.testAdoptedPetsByCustomer();
        System.out.println("________________________________");

        System.out.println("Test the class of customer number");
        test.testCustomerNumber();
        System.out.println("________________________________");

        System.out.println("Test the class of customer record");
        test.testCustomerRecord();
        System.out.println("________________________________");

    }

    public void testAddPet() {
        //      test normal case
        ShelterManager shelterManager = new ShelterManager();
        Pet pet1 = shelterManager.addPet(PetFactory.CAT_TYPE);
        Pet pet2 = shelterManager.addPet(PetFactory.DOG_TYPE);

        Assertions.assertNotNull(pet1);
        Assertions.assertEquals(PetFactory.CAT_TYPE, pet1.getPetType());
        Assertions.assertNotEquals(pet1.getPetType(), pet2.getPetType());

        //      test exceptional case
        try {
            pet1 = shelterManager.addPet("bird");
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, t);
        }
        //      test exceptional case
        try {
            pet1 = shelterManager.addPet(null);
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(NullPointerException.class, t);
        }
    }

    public void testUpdatePetRecord() {
        //      test normal case
        ShelterManager shelterManager = new ShelterManager();
        Pet pet = shelterManager.addPet(PetFactory.DOG_TYPE);
        Dog d = (Dog) pet;
        Assertions.assertFalse(d.getTrained());
        Boolean b = shelterManager.updatePetRecord(pet.getPetID(), true);
        Assertions.assertTrue(b);

        //      test exceptional case
        try {
            Pet pet1 = shelterManager.addPet(PetFactory.CAT_TYPE);
            shelterManager.updatePetRecord(pet1.getPetID(), true);
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, t);
        }
        //      test exceptional case
        try {
            PetID petID = PetID.valueOf();
            shelterManager.updatePetRecord(petID, true);
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, t);
        }

    }

    public void testNoOfAvailablePets() {

        ShelterManager shelterManager = new ShelterManager();
        Pet pet1 = shelterManager.addPet(PetFactory.CAT_TYPE);
        Pet pet2 = shelterManager.addPet(PetFactory.CAT_TYPE);

        //      test normal case
        int num1 = shelterManager.noOfAvailablePets(PetFactory.CAT_TYPE);
        Assertions.assertEquals(2, num1);

        Cat p1 = (Cat) pet1;
        p1.setAdopted(true);
        int num2 = shelterManager.noOfAvailablePets(PetFactory.CAT_TYPE);
        Assertions.assertEquals(1, num2);

        Cat p2 = (Cat) pet2;
        p2.setAdopted(true);

        //      test exceptional case
        try {
            shelterManager.noOfAvailablePets(PetFactory.CAT_TYPE);
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, t);
        }

    }

    public void testAddCustomerRecord() {

        ShelterManager shelterManager = new ShelterManager();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(1993, 5, 18);
        Date dob1 = calendar1.getTime();
        CustomerRecord custRecord1 = shelterManager.addCustomerRecord("Emily", "Johnson", dob1, false);

        //      test normal case
        Assertions.assertNotNull(custRecord1);

        //      test exceptional case
        try {
            CustomerRecord custRecord2 = shelterManager.addCustomerRecord("Emily", "Johnson", dob1, false);
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, t);
        }
    }

    public void testAdoptPet() {
        ShelterManager shelterManager = new ShelterManager();
        //        Customers
        CustomerRecord custRecord1 = CustomerRecord.createCustomerRecord("John", "Smith", 2007, 5, 6, true);
        CustomerRecord custRecord2 = CustomerRecord.createCustomerRecord("William", "Evans", 2000, 3, 20, false);
        CustomerRecord custRecord3 = CustomerRecord.createCustomerRecord("Emma", "Brown", 1993, 7, 8, true);

        Pet p1 = shelterManager.addPet(PetFactory.CAT_TYPE);
        //      test normal case
        Assertions.assertFalse(shelterManager.adoptPet(custRecord1, PetFactory.CAT_TYPE));
        //      test exceptional case
        Assertions.assertTrue(shelterManager.adoptPet(custRecord3, PetFactory.CAT_TYPE));

        Pet p2 = shelterManager.addPet(PetFactory.DOG_TYPE);
        //      test exceptional case
        Assertions.assertFalse(shelterManager.adoptPet(custRecord2, PetFactory.DOG_TYPE));
        //      test normal case
        Assertions.assertTrue(shelterManager.adoptPet(custRecord3, PetFactory.DOG_TYPE));

        Pet p3 = shelterManager.addPet(PetFactory.DOG_TYPE);
        shelterManager.updatePetRecord(p3.getPetID(), true);
        //      test exceptional case
        Assertions.assertFalse(shelterManager.adoptPet(custRecord1, PetFactory.DOG_TYPE));
        //      test normal case
        Assertions.assertTrue(shelterManager.adoptPet(custRecord3, PetFactory.DOG_TYPE));

        Pet p4 = shelterManager.addPet(PetFactory.DOG_TYPE);
        //      test boundary case
        Assertions.assertFalse(shelterManager.adoptPet(custRecord3, PetFactory.DOG_TYPE));
        Assertions.assertNull(shelterManager.adoptPet(custRecord2, PetFactory.CAT_TYPE));


    }

    public void testAdoptedPetsByCustomer() {
        ShelterManager shelterManager = new ShelterManager();
        CustomerRecord custRecord1 = CustomerRecord.createCustomerRecord("Emma", "Brown", 1993, 7, 8, true);
        CustomerRecord custRecord2 = CustomerRecord.createCustomerRecord("Thomas", "Walker", 1995, 5, 6, true);

        CustomerNumber custNum1 = custRecord1.getCustomerNumber();
        CustomerNumber custNum2 = custRecord2.getCustomerNumber();

        shelterManager.addPet(PetFactory.CAT_TYPE);
        shelterManager.addPet(PetFactory.DOG_TYPE);

        shelterManager.adoptPet(custRecord1, PetFactory.CAT_TYPE);
        shelterManager.adoptPet(custRecord1, PetFactory.DOG_TYPE);

        //      test normal case
        Collection<Pet> pets1 = shelterManager.adoptedPetsByCustomer(custNum1);
        Assertions.assertNotNull(pets1);

        //      test exceptional case
        try {
            Collection<Pet> pets2 = shelterManager.adoptedPetsByCustomer(custNum2);
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(NullPointerException.class, t);
        }


    }

    public void testCustomerNumber() {
        CustomerNumber custNum1 = CustomerNumber.createCustomerNumber(Name.getInstance("Thomas", "Walker"));
        CustomerNumber custNum2 = CustomerNumber.createCustomerNumber(Name.getInstance("Thomas", "Walker"));
        CustomerNumber custNum3 = CustomerNumber.createCustomerNumber(Name.getInstance("Emily", "Johnson"));
        //      test normal case
        Assertions.assertNotNull(custNum3);

        //      test normal case (Customers with the same name can generate different IDs, which are unique)
        Assertions.assertNotEquals(custNum1, custNum2);

        //      test exceptional case
        try {
            CustomerNumber.createCustomerNumber(Name.getInstance(null, null));
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(NullPointerException.class, t);
        }
        //      test exceptional case
        try {
            Name name = Name.getInstance("Thomas", "null");
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(NumberFormatException.class, t);
        }
        //      test exceptional case
        try {
            Name name = Name.getInstance("Thomas", "");
        } catch (Throwable t) {
            Assertions.assertExpectedThrowable(IllegalAccessException.class, t);
        }

    }


    public void testCustomerRecord() {

        CustomerRecord custRecord1 = CustomerRecord.createCustomerRecord("Thomas", "Walker", 1995, 5, 6, true);
        CustomerRecord custRecord2 = CustomerRecord.createCustomerRecord("Emily", "Johnson", 1998, 3, 20, true);

        //      test normal case
        Assertions.assertNotNull(custRecord1);

        Assertions.assertTrue(custRecord2.isHasGarden());
        int expectedAge = 25;
        Assertions.assertEquals(expectedAge, custRecord2.getAge(custRecord2.getDob()));

        CustomerRecord custRecord3 = new CustomerRecord(custRecord1.getName(), custRecord1.getDob(), custRecord1.getCustomerNumber(), custRecord1.isHasGarden());
        Assertions.assertEquals(custRecord1, custRecord3);
        Assertions.assertEquals(custRecord1.hashCode(), custRecord3.hashCode());
        Assertions.assertNotEquals(custRecord1.hashCode(), custRecord2.hashCode());

        String printFormat = "Name: Thomas Walker, Date of Birth: 06/05/1995, Customer Number: T20.22024, Garden Availability: Yes";
        Assertions.assertEquals(printFormat,custRecord1.toString());
        String custNumFormat = "T20.22024";
        Assertions.assertTrue(custNumFormat.equals(custRecord1.getCustomerNumber().toString()));

    }
}

