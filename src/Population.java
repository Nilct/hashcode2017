public class Population {

    Individual[] individuals;

    /*
     * Constructors
     */
     // Create a population
     public Population(int populationSize, boolean initialise) {
         individuals = new Individual[populationSize];
         // Initialise population
         if (initialise) {
             // Loop and create individuals
             for (int i = 0; i < size(); i++) {
                 Individual newIndividual = new Individual();
                 saveIndividual(i, newIndividual);
             }
         }
     }

     // Create a population with a specific chromosome size
     public Population(int populationSize, boolean initialise, int chromosomeSize) {
         individuals = new Individual[populationSize];
         // Initialise population
         if (initialise) {
             // Loop and create individuals
             for (int i = 0; i < size(); i++) {
                 Individual newIndividual = new Individual(chromosomeSize);
                 saveIndividual(i, newIndividual);
             }
         }
     }

     // Create a population with local solution
     public Population(int populationSize, boolean initialise, String chromosome) {
         individuals = new Individual[populationSize];
         // Initialise population
         if (initialise) {
             // Loop and create individuals
             for (int i = 0; i < size(); i++) {
                 Individual newIndividual = new Individual(chromosome);
                 saveIndividual(i, newIndividual);
             }
         }
     }

    /* Getters */
    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public Individual getFittest() {
        Individual fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    /* Public methods */
    // Get population size
    public int size() {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }
}
