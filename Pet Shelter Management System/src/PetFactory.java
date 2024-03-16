

public abstract class PetFactory implements Pet {
    public static final String CAT_TYPE = "cat";
    public static final String DOG_TYPE = "dog";
    private boolean adopted;
    private PetID petID;

    public PetFactory(PetID petID) {
        this.petID = petID;
    }

    @Override
    public PetID getPetID() {
        return petID;
    }
    @Override
    public boolean getAdopted() {
        return adopted;
    }

    public void setAdopted(boolean adopted) {
        this.adopted = adopted;
    }

}
