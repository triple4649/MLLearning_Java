package mnlearning;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.tree.RandomForest;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;

public class Learning {
	
	public static void main(String args[])throws Exception{
		// ランダムフォレストの作成と学習
		Classifier rf = new RandomForest(5);
		rf.buildClassifier(FileHandler.loadDataset(new File("work/text/iris.data"), 4, ","));
		
		// 検証用データの読み込み（同じデータを使用）
		FileHandler.loadDataset(new File("work/text/iris.data"), 4, ",")
		.stream()
		//reduceでfor文と同じ処理を行う
		.reduce(new HashMap<String,Integer>(), //ループの初期値を設定する
				(accum, ins) -> {return createOperation(accum, ins,rf);},//ループで適用する処理を記載する
				(s,v)->v)//paralle処理を考慮しない場合は適当なラムダ式を渡す
		//forEachでMapのキーと値の組み合わせを列挙する
		.forEach((m1,m2)->System.out.println(String.format("%s : %d", m1,m2)));
	}
	
	//検証結果と検証用のデータの検査を行う
	//Map<String,Integer> accum : 検証結果が格納されている
	//Instance ins : 検証用データが格納されている
	//Classifier rf : 学習した結果が格納されている
	public static  HashMap<String,Integer> createOperation(Map<String,Integer> accum,Instance ins,Classifier rf){
		HashMap<String,Integer> m = new HashMap<String,Integer>(accum);
		//学習した結果が正しければ、正当の件数をカウントアップする
		if(rf.classify(ins).equals(ins.classValue())){
			m.compute("correct",(key,value)-> value==null ? 0:value+1);
			m.compute("wrong",(key,value)-> value==null ? 0:value);
		//学習した結果が間違っていれば、誤答の件数をカウントアップする
		}else{
			m.compute("correct",(key,value)-> value==null ? 0:value);
			m.compute("wrong",(key,value)-> value==null ? 0:value+1);
		}
		return m;
	}
}
