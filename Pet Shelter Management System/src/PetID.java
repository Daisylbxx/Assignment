import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PetID {
    private char prefix;
    private int number;
    private String petId;
    private static Set<String> uniqueID = new HashSet<>();

    public PetID(char prefix, int number) {
        this.prefix = prefix;
        this.number = number;
        this.petId = String.format("%c%02d", prefix, number);
        uniqueID.add(petId);
    }

    public static PetID valueOf() {
//        Generate a random pet ID
        Random random = new Random();
        char prefix = (char) ('A' + random.nextInt(26));
        int number = random.nextInt(99) + 1;
        String petId = String.format("%c%02d", prefix, number);

        while (uniqueID.contains(petId)) {
            prefix = (char) ('A' + random.nextInt(26));
            number = random.nextInt(99) + 1;
            petId = String.format("%c%02d", prefix, number);
        }
        return new PetID(prefix, number);
    }

    public char getPrefix() {
        return prefix;
    }

    public int getNumber() {
        return number;
    }

    public String getPetId() {
        return petId;
    }

    @Override
    public String toString() {
        return String.format("%c%02d", prefix, number);
    }
}
