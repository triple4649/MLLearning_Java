package mnlearning;

import java.io.File;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import net.sf.javaml.tools.weka.WekaAttributeSelection;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.CostSensitiveSubsetEval;
import weka.attributeSelection.GainRatioAttributeEval;
import weka.attributeSelection.GeneticSearch;
import weka.attributeSelection.GreedyStepwise;
import weka.attributeSelection.LinearForwardSelection;
import weka.attributeSelection.RaceSearch;
import weka.attributeSelection.RankSearch;
import weka.attributeSelection.Ranker;
import weka.attributeSelection.SVMAttributeEval;

public class WekaAttribute {
	public static void main(String args[])throws Exception{
		WekaAttributeSelection wekaattrsel = new WekaAttributeSelection( 
				new GainRatioAttributeEval(), //*Create a Weka AS Evaluation algorithm */
				new Ranker() ///* Create a Weka's AS Search algorithm */
		);
		/* Load the iris data set */
		/* Apply the algorithm to the data set */
		wekaattrsel.build(FileHandler.loadDataset(new File("iris.data"), 4, ","));
		/* Print out the score and rank  of each attribute */
		for (int i = 0; i < wekaattrsel.noAttributes()-1; i++) 
		    System.out.println("Attribute  " +  i +  "  Ranks  " + wekaattrsel.rank(i) + " and Scores " + wekaattrsel.score(i) );		
	}

}
