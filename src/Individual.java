public class Individual {

    static int defaultGeneLength = 64;
    private byte[] genes;
    // Cache
    private int fitness = 0;

    // Create a random individual
    public Individual() {
      genes = new byte[defaultGeneLength];
        for (int i = 0; i < size(); i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }

    // Create a local individual
    public Individual(String chromosome) {
        defaultGeneLength = chromosome.length();
        genes = new byte[defaultGeneLength];
        for (int i = 0; i < chromosome.length(); i++) {
            byte gene = Byte.parseByte(Character.toString(chromosome.charAt(i)));
            genes[i] = gene;
        }
    }

    // Create a local individual
    public Individual(int chromosomeSize) {
        defaultGeneLength = chromosomeSize;
        genes = new byte[defaultGeneLength];
        for (int i = 0; i < size(); i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }

    /* Getters and setters */
    // Use this if you want to create individuals with different gene lengths
    public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }

    public byte getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, byte value) {
        genes[index] = value;
        fitness = 0;
    }

    /* Public methods */
    public int size() {
        return genes.length;
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = Hash.getFitness(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
}
