package jyouhousyori.xml;


import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XMLCreater {
	private static final String SELECTION_REG = "^#(ア|イ|ウ|エ)";
	private static final String QUETION_REG = "^\\+";
	private static final String SELECTION_TAG ="<selection option='%s'> %s </selection>";
	private static final String QUETION_TAG ="<quetion num='%s'> %s ";

	public static void main(String args[])throws Exception{
		createXMLFile("work/text/result2017h29a_sc_am2_qs_new.txt","work/text/result2017h29a_sc_am2_qs_new.xml");
	}
	
	
	public static void createXMLFile(String fromdir,String toDir)throws Exception{
		Files.write(Paths.get(toDir), 
				createXMLLines(fromdir), 
				Charset.forName("MS932"),
				StandardOpenOption.CREATE_NEW);
	}
	
	public static List<String> createXMLLines(String path)throws Exception{
        List<String> list = Files.lines(Paths.get(path), Charset.forName("MS932"))
        .reduce(
        		new ArrayList<String>(),
        		(s,v)->{return makeXMLStrings(s,v);},
        		(s,v)->v
        );
        list.add(createQuetionCloseTag());
        return list;
	}
	
	public static ArrayList<String> makeXMLStrings(ArrayList<String> list,String target){
		if(target.matches(QUETION_REG+".*")) list.add(createQuetionTag(target,list.size()));
		else if(target.matches(SELECTION_REG+".*")) list.add(createSelectionTag(target));
		else list.add(target);
		
		return new ArrayList<String>(list);
	}
	
	public static String createQuetionTag(String str,int index){
		String[] strs = str.split(QUETION_REG);
		String questionnum = getFirstMatched(".*問(\\d+)",str);
		return (index != 0 ? createQuetionCloseTag():"")+String.format(QUETION_TAG, questionnum,strs[1].replaceFirst("\\+", ""));
	}
	public static String createQuetionCloseTag(){
		return "</quetion>";
	}
	public static String createSelectionTag(String str){
		String[] strs = str.split(SELECTION_REG);
		return String.format(SELECTION_TAG, 
				getFirstMatched(SELECTION_REG,str),
				strs[1]);
	}
	
	public static String getFirstMatched(String reg,String str){
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(str);
		if(m.find()){
			return m.group(1);
		}
		return "";
	}
}
