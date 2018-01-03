package mnlearning;

import java.io.File;

import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.tools.data.FileHandler;

public class Figure {
	
	public static void main(String args[])throws Exception{
		new CrossValidation(new KNearestNeighbors(5))
	    .crossValidation(FileHandler.loadDataset(new File("work/text/iris.data"), 4, ","))
		.forEach((m1,m2)->System.out.println(String.format("%s : %s", m1,m2)));
	}
}
