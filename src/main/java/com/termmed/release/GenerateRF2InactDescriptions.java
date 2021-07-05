package com.termmed.release;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.TreeMap;

import com.termmed.complete.files.util.CommonUtils;
import com.termmed.complete.files.util.FileSorter;


public class GenerateRF2InactDescriptions {

	private TreeMap<String, String> RF1SourceFiles;
	private File RF2InactDescriptionsFile;
	private String moduleId;
	private String RF2_refset;
	private File sortingFolder;
	private File sortedFinalFolder;
	private HashMap<String, String> concInDesc;
	private HashMap<String, String> mapIdsRef;
	
	public GenerateRF2InactDescriptions(TreeMap<String,String>RF1SourceFiles, File RF2InactDescriptionsFile,String moduleId, File sortedFinalFolder2,File sortingFolder2, HashMap<String, String> concInDesc) {
		super();
		this.RF1SourceFiles=RF1SourceFiles;
		this.RF2InactDescriptionsFile = RF2InactDescriptionsFile;
		this.moduleId=moduleId;
		sortedFinalFolder = sortedFinalFolder2;
		sortingFolder=sortingFolder2;
		this.concInDesc=concInDesc;
		this.RF2_refset="900000000000490003";
	}



	public void execute(){

		try {
			long start1 = System.currentTimeMillis();

			getPreviousIds();
			String qualFileName=RF2InactDescriptionsFile.getName();
			
			File RF2Tmp = new File(RF2InactDescriptionsFile.getParentFile()  + "/tmp_" + qualFileName);

			if (!RF2InactDescriptionsFile.exists()){

				FileOutputStream fos = new FileOutputStream( RF2InactDescriptionsFile);
				OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
				BufferedWriter bw = new BufferedWriter(osw);
				
				bw.append("id");
				bw.append("\t");
				bw.append("effectiveTime");
				bw.append("\t");
				bw.append("active");
				bw.append("\t");
				bw.append("moduleId");
				bw.append("\t");
				bw.append("refsetId");
				bw.append("\t");
				bw.append("referencedComponentId");
				bw.append("\t");
				bw.append("valueId");
				bw.append("\r\n");

				bw.close();
			}

			double lines = 0;
			for (String key:RF1SourceFiles.keySet()){
				String fName = RF1SourceFiles.get(key);
				File RF1file = new File(fName);
				File RF1SortedFile= new File(sortedFinalFolder,"Sorted_" + RF1file.getName());
				FileSorter sf=new FileSorter(RF1file,RF1SortedFile,sortingFolder,new int[]{0});
				sf.execute();
				System.gc();

				if (RF2Tmp.exists()){
					RF2Tmp.delete();
				}
				FileOutputStream fos = new FileOutputStream( RF2Tmp);
				OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
				BufferedWriter bw = new BufferedWriter(osw);

				FileInputStream fis = new FileInputStream(RF1SortedFile	);
				InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
				BufferedReader br = new BufferedReader(isr);

				FileInputStream qfis = new FileInputStream(RF2InactDescriptionsFile	);
				InputStreamReader qisr = new InputStreamReader(qfis,"UTF-8");
				BufferedReader rf2br = new BufferedReader(qisr);


				String nextLine;
				br.readLine();
				rf2br.readLine();

				bw.append("id");
				bw.append("\t");
				bw.append("effectiveTime");
				bw.append("\t");
				bw.append("active");
				bw.append("\t");
				bw.append("moduleId");
				bw.append("\t");
				bw.append("refsetId");
				bw.append("\t");
				bw.append("referencedComponentId");
				bw.append("\t");
				bw.append("valueId");
				bw.append("\r\n");
				//TODO reason
				String did="";
				String uuid="";
				String active="";
				String valueId="";
				String[] splittedLine;
				String RF2Line;
				String[] rf2SplittedLine=new String[7];
				String[] tmpRF2SplittedLine=new String[7];
				String lastLine="";
				RF2Line=rf2br.readLine();
				if (RF2Line!=null){
					rf2SplittedLine=RF2Line.split("\t",-1);
				}			

				while ((nextLine= br.readLine()) != null) {
					splittedLine = nextLine.split("\t",-1);

					did=splittedLine[0];
					uuid=mapIdsRef.get(did);
					if (uuid==null){
						uuid=CommonUtils.get(null,RF2_refset + did).toString();
					}
					valueId=getValueId(splittedLine[2],splittedLine[4]);
					active=getRF2Status(splittedLine[2],splittedLine[4]);
					if (RF2Line!=null){
						int comp = did.compareTo(rf2SplittedLine[5]);
						if ( comp<0 ){
							if (valueId!=null){
							bw.append(uuid);
							bw.append("\t");
							bw.append(key);
							bw.append("\t");
							bw.append(active);
							bw.append("\t");
							bw.append(moduleId);
							bw.append("\t");
							bw.append(RF2_refset);
							bw.append("\t");
							bw.append(did);
							bw.append("\t");
							bw.append(valueId);
							bw.append("\r\n");
							lines++;
							}
						}else{
							if (comp>0){
								while (comp>0){
									bw.append(RF2Line);
									bw.append("\r\n");
									lines++;
									RF2Line=rf2br.readLine();
									if (RF2Line==null){
										comp=-1;
										break;
									}
									rf2SplittedLine=RF2Line.split("\t",-1);
									comp = did.compareTo(rf2SplittedLine[5]);
								}
							}
							if ( comp<0 ){
								if (valueId!=null){

								bw.append(uuid);
								bw.append("\t");
								bw.append(key);
								bw.append("\t");
								bw.append(active);
								bw.append("\t");
								bw.append(moduleId);
								bw.append("\t");
								bw.append(RF2_refset);
								bw.append("\t");
								bw.append(did);
								bw.append("\t");
								bw.append(valueId);
								bw.append("\r\n");
								lines++;
								}
							}else{
								while(comp==0){

									lastLine=RF2Line;
									bw.append(RF2Line);
									bw.append("\r\n");
									lines++;
									RF2Line=rf2br.readLine();
									if (RF2Line==null){
										break;
									}
									rf2SplittedLine=RF2Line.split("\t",-1);									
									comp = did.compareTo(rf2SplittedLine[5]);

								}

								tmpRF2SplittedLine=lastLine.split("\t",-1);
								if (tmpRF2SplittedLine[2].compareTo(active)!=0 
										|| (valueId!=null && valueId.compareTo(tmpRF2SplittedLine[6])!=0)){

									bw.append(uuid);
									bw.append("\t");
									bw.append(key);
									bw.append("\t");
									bw.append(active);
									bw.append("\t");
									bw.append(moduleId);
									bw.append("\t");
									bw.append(RF2_refset);
									bw.append("\t");
									bw.append(did);
									bw.append("\t");
									if (valueId==null)
										bw.append(tmpRF2SplittedLine[6]);
									else
										bw.append(valueId);
									bw.append("\r\n");
									lines++;
								}
							}

						}
					}else{
						if (valueId!=null){
						bw.append(uuid);
						bw.append("\t");
						bw.append(key);
						bw.append("\t");
						bw.append(active);
						bw.append("\t");
						bw.append(moduleId);
						bw.append("\t");
						bw.append(RF2_refset);
						bw.append("\t");
						bw.append(did);
						bw.append("\t");
						bw.append(valueId);
						bw.append("\r\n");
						lines++;
						}
					}
				}

				if (RF2Line!=null){

					bw.append(RF2Line);
					bw.append("\r\n");
					lines++;

					while ((RF2Line= rf2br.readLine()) != null) {
						bw.append(RF2Line);

						bw.append("\r\n");
						lines++;
					}
				}

				br.close();
				rf2br.close();
				bw.close();
				
				if (RF2InactDescriptionsFile.exists())
					RF2InactDescriptionsFile.delete();
				RF2Tmp.renameTo(RF2InactDescriptionsFile) ;

				if (RF1SortedFile.exists()){
					RF1SortedFile.delete();
				}
			}

			if (RF2Tmp.exists())
				RF2Tmp.delete();
			
			long end1 = System.currentTimeMillis();
			long elapsed1 = (end1 - start1);
			System.out.println("Lines in output file  : " + String.format("f", lines));
			System.out.println("Completed in " + elapsed1 + " ms");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getPreviousIds() throws IOException {

		FileInputStream qfis = new FileInputStream(RF2InactDescriptionsFile	);
		InputStreamReader qisr = new InputStreamReader(qfis,"UTF-8");
		BufferedReader rf2br = new BufferedReader(qisr);

		rf2br.readLine();
		String line;
		String[] spl;
		mapIdsRef=new HashMap<String,String>();
		while((line=rf2br.readLine())!=null){
			spl=line.split("\t",-1);
			mapIdsRef.put(spl[5], spl[0]);
		}
		rf2br.close();
	}



	private String getValueId(String descActive,String conceptId) {
		if (descActive.equals("0")){
			return null;
		}else{
			String conceptStat=concInDesc.get(conceptId);
			if (conceptStat==null || conceptStat.equals("900000000000492006")){
				return null;
			}else if (conceptStat.equals("900000000000486000")){
				return "900000000000486000";
			}else {
				return "900000000000495008";
			}
		}
	}



	private String getRF2Status(String descActive, String conceptId) {
		if (descActive.equals("0")){
			return "0";
		}else{
			String conceptStat=concInDesc.get(conceptId);
			if (conceptStat==null || conceptStat.equals("900000000000492006")){
				return "0";
			}else {
				return "1";
			}
		}
	}

}
