public final class Cat extends PetFactory {

    public Cat(PetID petID) {
        super(petID);
    }

    @Override
    public String getPetType() {
        return "cat";
    }

    @Override
    public String getCareInstructions() {
        return "Care instructions: two meals a day";
    }
}
