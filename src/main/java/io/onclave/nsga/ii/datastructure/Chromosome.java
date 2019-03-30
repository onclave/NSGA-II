/*
 * This code / file / algorithm is completely free to use and modify as necessary.
 * Any attribution is welcome and highly appriciated.
 */
package io.onclave.nsga.ii.datastructure;

import io.onclave.nsga.ii.api.Configuration;
import io.onclave.nsga.ii.interfaces.IObjectiveFunction;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 2.0
 * @since   2.0
 */
public class Chromosome {
    
    private final Allele[] geneticCode;
    private final List<Double> objectiveValues = new ArrayList<>();
    private double crowdingDistance = 0;
    private int dominationCount = 0;
    private List<Chromosome> dominatedChromosomes = new ArrayList<>();
    private int rank;
    private final List<double[]> selectedGeneMatrix = new ArrayList<>();
    private double[] geneExpressionColumn;
    private ConfusionMatrix confusionMatrix;
    
    private Chromosome(final Chromosome chromosome) {
        
        this.geneticCode = new Allele[Configuration.CHROMOSOME_LENGTH];
        
        for(int i = 0; i < Configuration.CHROMOSOME_LENGTH; i++) this.geneticCode[i] = new Allele(chromosome.getGeneticCode()[i].getGene());
        for(int i = 0; i < Configuration.objectives.size(); i++) this.objectiveValues.add(chromosome.getObjectiveValues().get(i));
    }
    
    public Chromosome(final Allele[] geneticCode) {
        
        int columnCount = 0;
        this.rank = Integer.MAX_VALUE;
        this.geneticCode = geneticCode;
        
        for(int index = 0; index < this.geneticCode.length; index++) if(geneticCode[index].getGene()) {
            
            int j = 0;
            this.geneExpressionColumn = new double[Configuration.DATA_SAMPLE_COUNT];
            
            for(GeneSample geneSample : Configuration.PROCESSED_MICROARRAY_GENE_EXPRESSION.getGeneSamples()) this.geneExpressionColumn[j++] = geneSample.getGeneExpressions()[index].getGeneExpressionValue();
            
            this.selectedGeneMatrix.add(columnCount++, geneExpressionColumn);
        }
        
        this.confusionMatrix = new ConfusionMatrix(selectedGeneMatrix);
        
        for(int j = 0; j < Configuration.objectives.size(); j++) switch(Configuration.objectives.get(j).getRequirement()) {
            
            case IObjectiveFunction.REQUIRES_CONFUSION_MATRIX: this.objectiveValues.add(j, Configuration.objectives.get(j).getObjectiveValue(this.confusionMatrix)); break;
            case IObjectiveFunction.REQUIRES_SELECTED_GENE_MATRIX: this.objectiveValues.add(j, Configuration.objectives.get(j).getObjectiveValue(this.selectedGeneMatrix)); break;
        }
        
    }
    
    public double getCrowdingDistance() {
        return crowdingDistance;
    }

    public void setCrowdingDistance(double crowdingDistance) {
        this.crowdingDistance = crowdingDistance;
    }

    public List<Double> getObjectiveValues() {
        return objectiveValues;
    }

    public int getDominationCount() {
        return dominationCount;
    }

    public void incrementDominationCount(int incrementValue) {
        this.dominationCount += incrementValue;
    }

    public List<Chromosome> getDominatedChromosomes() {
        return dominatedChromosomes;
    }

    public void setDominatedChromosome(final Chromosome chromosome) {
        this.dominatedChromosomes.add(chromosome);
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Allele[] getGeneticCode() {
        return geneticCode;
    }

    public List<double[]> getSelectedGeneMatrix() {
        return selectedGeneMatrix;
    }
    
    public void reset() {
        
        this.dominationCount = 0;
        this.rank = Integer.MAX_VALUE;
        this.dominatedChromosomes = new ArrayList<>();
    }
    
    public Chromosome getCopy() {
        return new Chromosome(this);
    }
    
    @Override
    public String toString() {
        
        String result = "GENETIC CODE >>>> ";
        
        for(Allele allele : this.geneticCode) result += "[ " + allele.toString() + " ]";
        
        for(int j = 0; j < Configuration.objectives.size(); j++) result += "\n\n" + Configuration.objectives.get(j).objectiveFunctionTitle() + " >>>> " + this.objectiveValues.get(j);
        
        result += "\n\nCROWDING DISTANCE : " + this.crowdingDistance + "\n\nRANK : " + this.rank;
        
        return result;
    }
}
