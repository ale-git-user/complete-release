package com.termmed.complete;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import com.termmed.complete.files.util.CommonUtils;
import com.termmed.complete.files.util.FileHelper;

public class JoinSnapshots {

	private String folder2;
	private String folder1;
	private String outputFolder;

	public JoinSnapshots(String folder1, String folder2, String outputFolder) {
		this.folder1=folder1;
		this.folder2=folder2;
		this.outputFolder=outputFolder ;
	}

	public void execute() throws IOException, Exception {
		File outputFolderFile=new File(outputFolder);
		if (!outputFolderFile.exists()){
			outputFolderFile.mkdirs();
		}
		joinFiles(folder1,folder2,"rf2-relationships",null,null,"stated",true);
		joinFiles(folder1,folder2,"rf2-relationships",null,"stated",null,true);
		joinFiles(folder1,folder2,"rf2-textDefinition",null,"definition",null,false);
		joinFiles(folder1,folder2,"rf2-association",null,null,null,false);
		joinFiles(folder1,folder2,"rf2-attributevalue",null,null,null,false);
		joinFiles(folder1,folder2,"rf2-language",null,null,null,false);
		joinFiles(folder1,folder2,"rf2-simple",null,null,null,false);
		joinFiles(folder1,folder2,"rf2-simplemaps",null,null,null,false);
		joinFiles(folder1,folder2,"rf2-descriptions",null,null,"definition",true);
		joinFiles(folder1,folder2,"rf2-concepts",null,null,null,true);

		joinFiles(folder1,folder2,"rf2-mrcm-attr-domain",null,null,null,true);
		joinFiles(folder1,folder2,"rf2-mrcm-module-scope",null,null,null,true);
		joinFiles(folder1,folder2,"rf2-mrcm-attr-range",null,null,null,true);
		joinFiles(folder1,folder2,"rf2-mrcm-domain",null,null,null,true);
		joinFiles(folder1,folder2,"rf2-owl-expression",null,"expression","ontology",true);
//		joinFiles(folder1,folder2,"rf2-owl-ontology",null,"ontology","axiom",true);
			
	}

	
	private void joinFiles(String folder1,String folder2,String pattern,String folderDefault, String mustHave, String doesNotMustHave, boolean forceUnique) throws IOException, Exception{
		File folderFile1=new File(folder1);
		File folderFile2=new File(folder2);
		String file1=FileHelper.getFile( folderFile1, pattern, folderDefault,mustHave,doesNotMustHave);
		String file2=FileHelper.getFile( folderFile2, pattern, folderDefault ,mustHave,doesNotMustHave);
		if (file1==null){
			System.out.println("No file in folder:" + folder1 + " with pattern: - " + pattern + " mustHave:" + mustHave + " doesn't must have:" + doesNotMustHave);
		}
		if (file2==null){
			System.out.println("No file in folder:" + folder2 + " with pattern: - " + pattern + " mustHave:" + mustHave + " doesn't must have:" + doesNotMustHave);
		}
		if (file1!=null && file2!=null){
			System.out.println("file1:" + file1);
			System.out.println("file2:" + file2);
			System.out.println("--------------------" );
			concatenateFiles(file1,file2);
		}else if (file1!=null && forceUnique){
			File file=new File(file1);
			FileHelper.copyTo(file,new File(outputFolder, file.getName()));
		}else if (file2!=null && forceUnique){
			File file=new File(file2);
			FileHelper.copyTo(file,new File(outputFolder, file.getName()));

		}
	}
	
	private void concatenateFiles(String file1, String file2) {
		HashSet<File> hFile=new HashSet<File>();
		File inf1=new File(file1);
		File inf2=new File(file2);
		File outfile=new File(outputFolder,inf1.getName());
		hFile.add(inf1);
		hFile.add(inf2);
		CommonUtils.concatFile(hFile, outfile);
	}
}
