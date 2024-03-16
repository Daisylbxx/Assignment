public final class Dog extends PetFactory {
private boolean trained;
    public Dog(PetID petID) {
        super(petID);
    }
    public boolean getTrained(){
        return trained;
    }

    public void setTrained(boolean trained) {
        this.trained = trained;
    }
    @Override
    public String getPetType() {
        return "dog";
    }

    @Override
    public String getCareInstructions() {
        return "Care instructions: three meals a day, one walk";
    }

}
