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
		// �����_���t�H���X�g�̍쐬�Ɗw�K
		Classifier rf = new RandomForest(5);
		rf.buildClassifier(FileHandler.loadDataset(new File("work/text/iris.data"), 4, ","));
		
		// ���ؗp�f�[�^�̓ǂݍ��݁i�����f�[�^���g�p�j
		FileHandler.loadDataset(new File("work/text/iris.data"), 4, ",")
		.stream()
		//reduce��for���Ɠ����������s��
		.reduce(new HashMap<String,Integer>(), //���[�v�̏����l��ݒ肷��
				(accum, ins) -> {return createOperation(accum, ins,rf);},//���[�v�œK�p���鏈�����L�ڂ���
				(s,v)->v)//paralle�������l�����Ȃ��ꍇ�͓K���ȃ����_����n��
		//forEach��Map�̃L�[�ƒl�̑g�ݍ��킹��񋓂���
		.forEach((m1,m2)->System.out.println(String.format("%s : %d", m1,m2)));
	}
	
	//���،��ʂƌ��ؗp�̃f�[�^�̌������s��
	//Map<String,Integer> accum : ���،��ʂ��i�[����Ă���
	//Instance ins : ���ؗp�f�[�^���i�[����Ă���
	//Classifier rf : �w�K�������ʂ��i�[����Ă���
	public static  HashMap<String,Integer> createOperation(Map<String,Integer> accum,Instance ins,Classifier rf){
		HashMap<String,Integer> m = new HashMap<String,Integer>(accum);
		//�w�K�������ʂ���������΁A�����̌������J�E���g�A�b�v����
		if(rf.classify(ins).equals(ins.classValue())){
			m.compute("correct",(key,value)-> value==null ? 0:value+1);
			m.compute("wrong",(key,value)-> value==null ? 0:value);
		//�w�K�������ʂ��Ԉ���Ă���΁A�듚�̌������J�E���g�A�b�v����
		}else{
			m.compute("correct",(key,value)-> value==null ? 0:value);
			m.compute("wrong",(key,value)-> value==null ? 0:value+1);
		}
		return m;
	}
}
