package com.termmed.complete.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.UUID;

import com.termmed.complete.files.util.CommonUtils;
import com.termmed.complete.files.util.ConstantValues;
import com.termmed.complete.files.util.DeltaGenerator;
import com.termmed.complete.files.util.FileHelper;
import com.termmed.complete.files.util.FileSorter;
import com.termmed.complete.files.util.SnapshotGenerator;
import com.termmed.release.ChangeConsolidator;
import com.termmed.release.ChangeConsolidator.FILE_TYPE;
import com.termmed.release.GenerateRF2InactDescriptions;

public class CompleteCoverageExtensionRelease {
	String concepts;
	String othrConcepts;
	String descriptions;
	String prevSnapDescriptions;
	String prevFullDescriptions;
	String othrDescriptions;
	String language;
	String prevSnapLanguage;
	String prevFullLanguage;
	String prevFullAttValue;
	String otherAttValue;
	String outputDescriptions;
	String outputConcepts;
	String moduleId;
	private HashSet<String> descInLang;
	private String releaseDate;
	private File outputFolder;
	private String outputLanguage;
	private File outputSnapFolder;
	private File snapshotDescFinalFile;
	private File deltaDescFinalFile;
	private File snapshotLangFinalFile;
	private File deltaLangFinalFile;
	private File outputDescriptionFile;
	private HashMap<String,String> concInDesc;
	private File outputConceptFile;
	private String textDefin;
	private String prevSnapTextDefin;
	private String prevFullTextDefin;
	private File snapshotTxtDFinalFile;
	private File deltaTxtDFinalFile;
	private String outputTxtD;
	private File sortFolderTmp;
	private String outFullAttValue;
	private File outputDeltaFolder;
	private File outputFullFolder;
	private String prevReleaseDate;
	private File droolsFolder;
	private String extensionSuffix;
	private String extensionName;
	private String refsetDescriptor;
	private String moduleDependency;
	private String statedRelationships;
	private String relationships;
	
	
	public static void main(String[] args){
//		String concepts="/Users/ar/Downloads/ES_Release/release_20140430/SnomedCT_es_SpanishExtension_20140430/Snapshot/Terminology/sct2_Concept_SpanishExtensionSnapshot_INT_20140430.txt";
//		String othrConcepts="/Users/ar/Downloads/SnomedCT_Release_INT_20140731/RF2Release/Snapshot/Terminology/sct2_Concept_Snapshot_INT_20140731.txt";
//		String descriptions="/Users/ar/desc.txt";
//		String textDefin="/Users/ar/text.txt";
//		String prevSnapTextDefin="/Users/ar/Downloads/ES_Release/release_20140430/SnomedCT_es_SpanishExtension_20140430/Snapshot/Terminology/sct2_TextDefinition_SpanishExtensionSnapshot-es_INT_20140430.txt";
//		String prevFullTextDefin="/Users/ar/Downloads/ES_Release/release_20140430/SnomedCT_es_SpanishExtension_20140430/Full/Terminology/sct2_TextDefinition_SpanishExtensionFull-es_INT_20140430.txt";
//		String prevSnapDescriptions="/Users/ar/Downloads/ES_Release/release_20140430/SnomedCT_es_SpanishExtension_20140430/Snapshot/Terminology/sct2_Description_SpanishExtensionSnapshot-es_INT_20140430.txt";
//		String prevFullDescriptions="/Users/ar/Downloads/ES_Release/release_20140430/SnomedCT_es_SpanishExtension_20140430/Full/Terminology/sct2_Description_SpanishExtensionFull-es_INT_20140430.txt";
//		String othrDescriptions="/Users/ar/Downloads/SnomedCT_Release_INT_20140731/RF2Release/Snapshot/Terminology/sct2_Description_Snapshot-en_INT_20140731.txt";
//		String language="/Users/ar/lang.txt";
//		String prevSnapLanguage="/Users/ar/Downloads/ES_Release/release_20140430/SnomedCT_es_SpanishExtension_20140430/Snapshot/Refset/Language/der2_cRefset_LanguageSpanishExtensionSnapshot-es_INT_20140430.txt";
//		String prevFullLanguage="/Users/ar/Downloads/ES_Release/release_20140430/SnomedCT_es_SpanishExtension_20140430/Full/Refset/Language/der2_cRefset_LanguageSpanishExtensionFull-es_INT_20140430.txt";
//		String prevFullAttValue="/Users/ar/Downloads/ES_Release/release_20140430/SnomedCT_es_SpanishExtension_20140430/Full/Refset/Content/der2_cRefset_AttributeValueSpanishExtensionFull_INT_20140430.txt";
//		String otherAttValue="/Users/ar/Downloads/SnomedCT_Release_INT_20140731/RF2Release/Snapshot/Refset/Content/der2_cRefset_AttributeValueSnapshot_INT_20140731.txt";
//		String outputDescriptions="sct2_Description_SpanishExtensionSnapshot-es_INT_RELEASEDATE.txt";
//		String outputConcepts="sct2_Concept_SpanishExtensionSnapshot_INT_RELEASEDATE.txt";
//		String outputLanguage="der2_cRefset_LanguageSpanishExtensionSnapshot-es_INT_RELEASEDATE.txt";
//		String outputTxtD="sct2_TextDefinition_SpanishExtensionSnapshot-es_INT_RELEASEDATE.txt";
//		String outputAttValue="der2_cRefset_AttributeValueSpanishExtensionFull_INT_RELEASEDATE.txt";
//		String releaseDate="20141031";
//		String prevReleaseDate="20140430";
//		String moduleId="450829007";
//		FileComplete cc=new FileComplete(concepts, othrConcepts, descriptions,
//				prevSnapDescriptions,prevFullDescriptions, othrDescriptions, language, prevSnapLanguage, prevFullLanguage,prevFullAttValue,
//				otherAttValue, releaseDate,prevReleaseDate,moduleId,
//				textDefin,prevSnapTextDefin, prevFullTextDefin, outputDescriptions, outputConcepts, outputLanguage,outputTxtD,outputAttValue);
		

		String exportedDeltaFolder="/Users/ar/root/delta_20150430";
		String coreSnapshotReleaseFolder="/Users/ar/Downloads/RF2Release_INT_20150131/Snapshot";
		String previousSnapshotReleaseFolder="/Users/ar/Downloads/ES_Release/RF2/SnomedCT_es_SpanishExtension_20141031/Snapshot";
		String previousFullReleaseFolder="/Users/ar/Downloads/ES_Release/RF2/SnomedCT_es_SpanishExtension_20141031/Full";
		String languageCode="es";
		String extensionName="SpanishExtension";
		String extensionSuffix="_INT";
		String releaseDate="20150430";
		String prevReleaseDate="20141031";
		String moduleId="450829007";
		try {
		CompleteCoverageExtensionRelease cc=new CompleteCoverageExtensionRelease(exportedDeltaFolder, 
			coreSnapshotReleaseFolder, 
			previousSnapshotReleaseFolder,
			previousFullReleaseFolder,
			languageCode,
			extensionName,
			extensionSuffix,
			releaseDate, 
			prevReleaseDate,
			moduleId);
			cc.execute();
			cc=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.gc();
	}
	public CompleteCoverageExtensionRelease(String concepts, String othrConcepts, String descriptions,String prevDescriptions,String prevFullDescriptions,
			String othrDescriptions, String language, String prevLanguage, String prevFullLanguage, String prevAttValue,String otherAttValue,
			String releaseDate, String prevReleaseDate,String moduleId, String textDefin,String prevTextDefin,String prevFullTextDefin,String outputDescriptions,
			String outputConcepts,String outputLanguage,String outputTxtD, String outputAttValue) {
		super();
		this.concepts = concepts;
		this.othrConcepts=othrConcepts;
		this.descriptions = descriptions;
		this.prevSnapDescriptions=prevDescriptions;
		this.prevFullDescriptions=prevFullDescriptions;
		this.othrDescriptions = othrDescriptions;
		this.language = language;
		this.prevSnapLanguage=prevLanguage;
		this.prevFullLanguage=prevFullLanguage;
		this.prevFullAttValue=prevAttValue;
		this.otherAttValue=otherAttValue;
		this.outputDescriptions = outputDescriptions;
		this.outputConcepts=outputConcepts;
		this.outputLanguage=outputLanguage;
		this.outputTxtD=outputTxtD;
		this.outFullAttValue=outputAttValue;
		this.releaseDate=releaseDate;
		this.prevReleaseDate=prevReleaseDate;
		this.moduleId=moduleId;
		this.textDefin=textDefin;
		this.prevSnapTextDefin=prevTextDefin;
		this.prevFullTextDefin=prevFullTextDefin;
	}
	

	public CompleteCoverageExtensionRelease(
			String exportedDeltaFolder, 
			String coreSnapshotReleaseFolder, 
			String previousSnapshotReleaseFolder,
			String previousFullReleaseFolder,
			String languageCode,
			String extensionName,
			String extensionSuffix,
			String releaseDate, 
			String prevReleaseDate,
			String moduleId) throws IOException, Exception {
		super();
		
		this.concepts = FileHelper.getFile( new File(previousSnapshotReleaseFolder), "rf2-concepts",null,null,null);
		this.othrConcepts=FileHelper.getFile( new File(coreSnapshotReleaseFolder), "rf2-concepts",null,null,null);
		this.descriptions = FileHelper.getFile( new File(exportedDeltaFolder), "rf2-descriptions",null,null,null);
		this.prevSnapDescriptions=FileHelper.getFile( new File(previousSnapshotReleaseFolder), "rf2-descriptions",null,null,null);
		this.prevFullDescriptions=FileHelper.getFile( new File(previousFullReleaseFolder), "rf2-descriptions",null,null,null);
		this.othrDescriptions = FileHelper.getFile( new File(coreSnapshotReleaseFolder), "rf2-descriptions",null,null,null);
		this.language = FileHelper.getFile( new File(exportedDeltaFolder), "rf2-language",null,null,null);
		this.prevSnapLanguage=FileHelper.getFile( new File(previousSnapshotReleaseFolder), "rf2-language",null,null,null);
		this.prevFullLanguage=FileHelper.getFile( new File(previousFullReleaseFolder), "rf2-language",null,null,null);
		this.prevFullAttValue=FileHelper.getFile( new File(previousFullReleaseFolder), "rf2-attributevalue",null,null,null);
		this.otherAttValue=FileHelper.getFile( new File(coreSnapshotReleaseFolder), "rf2-attributevalue",null,null,null);
		this.textDefin=FileHelper.getFile( new File(exportedDeltaFolder), "rf2-textDefinition",null,null,null);
		this.prevSnapTextDefin=FileHelper.getFile( new File(previousSnapshotReleaseFolder), "rf2-textDefinition",null,null,null);
		this.prevFullTextDefin=FileHelper.getFile( new File(previousFullReleaseFolder), "rf2-textDefinition",null,null,null);
		this.refsetDescriptor=FileHelper.getFile( new File(previousFullReleaseFolder), "rf2-refsetdescriptor",null,null,null);
		this.moduleDependency=FileHelper.getFile( new File(previousFullReleaseFolder), "rf2-moduledependency",null,null,null);
		this.relationships=FileHelper.getFile( new File(previousFullReleaseFolder), "rf2-relationships",null,null,"stated");
		this.statedRelationships=FileHelper.getFile( new File(previousFullReleaseFolder), "rf2-relationships",null,"stated",null);
		this.outputDescriptions = "sct2_Description_" + extensionName + "Snapshot-" + languageCode + extensionSuffix + "_" + releaseDate + ".txt";
		this.outputConcepts="sct2_Concept_" + extensionName + "Snapshot"+ extensionSuffix + "_" + releaseDate + ".txt";
		this.outputLanguage="der2_cRefset_Language" + extensionName + "Snapshot-" + languageCode + extensionSuffix + "_" + releaseDate + ".txt";
		this.outputTxtD="sct2_TextDefinition_" + extensionName + "Snapshot-" + languageCode + extensionSuffix + "_" + releaseDate + ".txt";
		this.outFullAttValue="der2_cRefset_AttributeValue" + extensionName + "Full"+ extensionSuffix + "_" + releaseDate + ".txt";

		this.extensionName=extensionName;
		this.extensionSuffix=extensionSuffix;
		this.releaseDate=releaseDate;
		this.prevReleaseDate=prevReleaseDate;
		this.moduleId=moduleId;
	}
	public void execute() throws Exception{
		outputFolder=new File("outputfolder");
		if (!outputFolder.exists()){
			outputFolder.mkdirs();
		}
		
		outputSnapFolder=new File(outputFolder, "outputSnapshot");
		if (!outputSnapFolder.exists()){
			outputSnapFolder.mkdirs();
		}
		sortFolderTmp=new File(outputFolder, "sortFolderTmp");
		if (!sortFolderTmp.exists()){
			sortFolderTmp.mkdirs();
		}
		outputDeltaFolder=new File(outputFolder, "outputDelta");
		if (!outputDeltaFolder.exists()){
			outputDeltaFolder.mkdirs();
		}
		outputFullFolder=new File(outputFolder, "outputFull");
		if (!outputFullFolder.exists()){
			outputFullFolder.mkdirs();
		}

		droolsFolder=new File("filesfordrools");
		if (!droolsFolder.exists()){
			droolsFolder.mkdirs();
		}		
		setPreviousIds();
		
		postExportProcess();

		loadDescriptionsInLang();
		
		completeDescriptions();
		
		mergeDescriptionsToDroolsFolder();
		
		completeConceptsInDescriptionsOrActive();
		
		mergeConceptsToDroolsFolder();
		
		attributeFull();
		
		if (sortFolderTmp.exists()){
			FileHelper.emptyFolder( sortFolderTmp);
			sortFolderTmp.delete();
		}
		
	}

	private void setPreviousIds() throws IOException, FileNotFoundException {
		
		BufferedReader br = getReader(prevSnapLanguage);
		String line;
		String[] spl;
		HashMap<String, String> mapIdsRef = new HashMap<String,String>();
		while((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			mapIdsRef.put(spl[5], spl[0]);
		}
		br.close();
		
		br = getReader(language);
		BufferedWriter bw = getWriter(language + "2");
		bw.append(br.readLine());
		bw.append("\r\n");
		while((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			String prevId=mapIdsRef.get(spl[5]);
			if (prevId!=null && !spl[0].equals(prevId)){
				bw.append(prevId);
				for (int i=1;i<spl.length;i++){
					bw.append("\t");
					bw.append(spl[i]);
				}
			}else{
				bw.append(line);
			}
			bw.append("\r\n");
		}
		bw.close();
		br.close();
		
		File file=new File(language);
		if (file.exists()){
			file.delete();
		}
		File file2=new File (language + "2");
		if (file2.exists()){
			file2.renameTo(file);
		}
	}
	private void attributeFull(){
		
		try {
			getConceptStatuses();
			
			TreeMap<String, String> res = new TreeMap<String, String>();
			res.put(releaseDate, snapshotDescFinalFile.getAbsolutePath());

			File RF2file = new File(prevFullAttValue);
			File RF2SortedFile= new File(outputFullFolder, outFullAttValue);
			FileSorter sf=new FileSorter(RF2file,RF2SortedFile,sortFolderTmp,new int[]{5,1});
			sf.execute();
			System.gc();
			
			GenerateRF2InactDescriptions quals=new GenerateRF2InactDescriptions(res,RF2SortedFile, moduleId,outputSnapFolder,sortFolderTmp,concInDesc);
			
			quals.execute();
			quals=null;
			System.gc();
			
			File outDeltaAttValue = new File(outputDeltaFolder,outFullAttValue.replace("Full", "Delta"));
			DeltaGenerator dg= new DeltaGenerator(RF2SortedFile, prevReleaseDate,releaseDate, new int[]{5}, 1, outDeltaAttValue);
			dg.execute();
			dg=null;
			
			File outSnapAttValue = new File(outputSnapFolder,outFullAttValue.replace("Full", "Snapshot"));
			SnapshotGenerator sg= new SnapshotGenerator(RF2SortedFile, releaseDate, 5, 1, outSnapAttValue,null,null);
			sg.execute();
			sg=null;
			
			copyToDroolsFolder(outSnapAttValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void copyToDroolsFolder(File file) throws IOException {
		
		File outputFile=new File(droolsFolder,file.getName());
		FileHelper.copyTo(file, outputFile);
	}
	private void getConceptStatuses() throws IOException {
		BufferedReader br = getReader(otherAttValue);
		
		br.readLine();

		String line;
		String[]spl;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if ( spl[4].equals(ConstantValues.CONCEPT_INACTIVATION_REFSET) && spl[2].equals("1")){
				concInDesc.put(spl[5],spl[6]);
			}
		}
		br.close();
		
	}
	private void mergeConceptsToDroolsFolder() {
			File conceptFile=new File(concepts);
			HashSet<File> hFile=new HashSet<File>();
			hFile.add(conceptFile);
			hFile.add(outputConceptFile);
			CommonUtils.concatFile(hFile, outputConceptFile);

	}
	
	private void completeConceptsInDescriptionsOrActive() throws FileNotFoundException, IOException {
		loadConceptsInDescriptions();
		outputConceptFile=new File(droolsFolder,outputConcepts);
		BufferedWriter bw = getWriter(outputConceptFile.getAbsolutePath());
		
		BufferedReader br = getReader(othrConcepts);
		
		bw.append(br.readLine());
		bw.append("\r\n");
		
		String line;
		String[]spl;
		while ((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (concInDesc.containsKey(spl[0]) || spl[2].equals("1")){
				bw.append(line);
				bw.append("\r\n");
				if (spl[2].equals("0")){
					concInDesc.put(spl[0], "0");
				}
			}
		}
		bw.close();
		br.close();
		bw=null;
		br=null;
	}
	private void loadConceptsInDescriptions() throws IOException, FileNotFoundException {
		concInDesc=new HashMap<String,String>();
		BufferedReader br = getReader(outputDescriptionFile.getAbsolutePath());
		br.readLine();
		String line;
		String[] spl;
		while((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			concInDesc.put(spl[4],null);
		}
		br.close();
	}
	private void postExportProcess() throws Exception {
		
		fillNewReleaseFolderWithMissingFiles();
		
		snapshotDescFinalFile=new File(outputSnapFolder, outputDescriptions);
		deltaDescFinalFile=new File(outputDeltaFolder, outputDescriptions.replace("Snapshot", "Delta"));
		File fullDescFinalFile = new File(outputFullFolder, outputDescriptions.replace("Snapshot", "Full"));
		ChangeConsolidator cCons=new ChangeConsolidator(FILE_TYPE.RF2_DESCRIPTION, descriptions, prevSnapDescriptions, releaseDate, snapshotDescFinalFile, deltaDescFinalFile);
		cCons.execute();
		cCons=null;
		mergeFiles(deltaDescFinalFile,new File(prevFullDescriptions),fullDescFinalFile);
		
		snapshotLangFinalFile=new File(outputSnapFolder, outputLanguage);
		deltaLangFinalFile=new File(outputDeltaFolder,outputLanguage.replace("Snapshot", "Delta"));
		File fullLangFinalFile = new File(outputFullFolder, outputLanguage.replace("Snapshot", "Full"));
		cCons=new ChangeConsolidator(FILE_TYPE.RF2_LANGUAGE_REFSET, language, prevSnapLanguage, releaseDate, snapshotLangFinalFile, deltaLangFinalFile);
		cCons.execute();
		cCons=null;
		mergeFiles(deltaLangFinalFile,new File(prevFullLanguage),fullLangFinalFile);

		copyToDroolsFolder(snapshotLangFinalFile);
		
		snapshotTxtDFinalFile=new File(outputSnapFolder, outputTxtD);
		deltaTxtDFinalFile=new File(outputDeltaFolder, outputTxtD.replace("Snapshot", "Delta"));
		File fullTxtDFinalFile = new File(outputFullFolder, outputTxtD.replace("Snapshot", "Full"));
		cCons=new ChangeConsolidator(FILE_TYPE.RF2_TEXTDEFINITION, textDefin, prevSnapTextDefin, releaseDate, snapshotTxtDFinalFile, deltaTxtDFinalFile);
		cCons.execute();
		cCons=null;
		mergeFiles(deltaTxtDFinalFile,new File(prevFullTextDefin),fullTxtDFinalFile);
		
		copyToDroolsFolder(snapshotTxtDFinalFile);
	}
	private void fillNewReleaseFolderWithMissingFiles() throws IOException, ParseException {

		//old concepts
		File oldConcepts=new File(concepts);
	
		File outputFile=new File(outputSnapFolder,outputConcepts);
		FileHelper.copyTo(oldConcepts, outputFile);

		outputFile=new File(outputFullFolder,outputConcepts.replace("Snapshot", "Full"));
		FileHelper.copyTo(oldConcepts, outputFile);
		
		//empty concepts
		outputFile=new File(outputDeltaFolder,outputConcepts.replace("Snapshot", "Delta"));

		BufferedWriter fileWriter = getWriter(outputFile.getAbsolutePath());
		fileWriter.append("id	effectiveTime	active	moduleId	definitionStatusId");
		fileWriter.append("\r\n");
		fileWriter.close();
		

		//old relationships
		File oldRels=new File(relationships);
		String outputRels="sct2_Relationship_" + extensionName + "Snapshot"+ extensionSuffix + "_" + releaseDate + ".txt";
		
		outputFile=new File(outputSnapFolder,outputRels);
		FileHelper.copyTo(oldRels, outputFile);

		outputFile=new File(outputFullFolder,outputRels.replace("Snapshot", "Full"));
		FileHelper.copyTo(oldRels, outputFile);
		
		//empty concepts
		outputFile=new File(outputDeltaFolder,outputRels.replace("Snapshot", "Delta"));

		fileWriter = getWriter(outputFile.getAbsolutePath());
		fileWriter.append("id	effectiveTime	active	moduleId	sourceId	destinationId	relationshipGroup	typeId	characteristicTypeId	modifierId");
		fileWriter.append("\r\n");
		fileWriter.close();

		//old stated relationships
		oldRels=new File(statedRelationships);
		outputRels="sct2_StatedRelationship_" + extensionName + "Snapshot"+ extensionSuffix + "_" + releaseDate + ".txt";
		
		outputFile=new File(outputSnapFolder,outputRels);
		FileHelper.copyTo(oldRels, outputFile);

		outputFile=new File(outputFullFolder,outputRels.replace("Snapshot", "Full"));
		FileHelper.copyTo(oldRels, outputFile);
		
		//empty concepts
		outputFile=new File(outputDeltaFolder,outputRels.replace("Snapshot", "Delta"));

		fileWriter = getWriter(outputFile.getAbsolutePath());
		fileWriter.append("id	effectiveTime	active	moduleId	sourceId	destinationId	relationshipGroup	typeId	characteristicTypeId	modifierId");
		fileWriter.append("\r\n");
		fileWriter.close();

		//empty associations
		File assoc=new File (outputSnapFolder,"der2_cRefset_Association" + extensionName + "Snapshot" + extensionSuffix + "_" + releaseDate + ".txt");
		
		fileWriter = getWriter(assoc.getAbsolutePath());
		fileWriter.append("id	effectiveTime	active	moduleId	refsetId	referencedComponentId	valueId");
		fileWriter.append("\r\n");
		fileWriter.close();
		
		outputFile=new File(outputFullFolder,assoc.getName().replace("Snapshot", "Full"));
		FileHelper.copyTo(assoc, outputFile);

		outputFile=new File(outputDeltaFolder,assoc.getName().replace("Snapshot", "Delta"));
		FileHelper.copyTo(assoc, outputFile);
		
		//to drools
		outputFile=new File(droolsFolder,assoc.getName());
		FileHelper.copyTo(assoc, outputFile);
		
		
		//old refsetDescriptors
		File oldRefsetDesc=new File(refsetDescriptor);
	
		outputFile=new File(outputSnapFolder,"der2_cciRefset_RefsetDescriptor" + extensionName + "Snapshot" + extensionSuffix + "_" + releaseDate + ".txt");
		FileHelper.copyTo(oldRefsetDesc, outputFile);

		//to drools
		outputFile=new File(droolsFolder,outputFile.getName());
		FileHelper.copyTo(oldRefsetDesc, outputFile);
		
		
		outputFile=new File(outputFullFolder,outputFile.getName().replace("Snapshot", "Full"));
		FileHelper.copyTo(oldRefsetDesc, outputFile);
		
		//empty refsetDescriptor
		outputFile=new File(outputDeltaFolder,outputFile.getName().replace("Full", "Delta"));
		fileWriter = getWriter(outputFile.getAbsolutePath());
		fileWriter.append("id	effectiveTime	active	moduleId	refsetId	referencedComponentId	attributeDescription	attributeType	attributeOrder");
		fileWriter.append("\r\n");
		fileWriter.close();

		//Module dependency
		outputFile=new File(outputFullFolder,"der2_ssRefset_ModuleDependency" + extensionName + "Full" + extensionSuffix + "_" + releaseDate + ".txt");
		
		BufferedReader br = getReader(moduleDependency);
		fileWriter=getWriter(outputFile.getAbsolutePath());
		String line="";
		String header="";
		boolean first=true;
		while((line=br.readLine())!=null){
			if (first){
				header=line;
				first=false;
			}
			fileWriter.append(line);
			fileWriter.append("\r\n");
		}
		String coreReleaseDate=getPreviousCoreReleaseDate(releaseDate);
		String uuid=UUID.randomUUID().toString();

		line=uuid;
		line+="\t";
		line+=releaseDate;
		line+="\t";
		line+="1";
		line+="\t";
		line+=moduleId;
		line+="\t";
		line+="900000000000534007";
		line+="\t";
		line+="900000000000207008";
		line+="\t";
		line+=releaseDate;
		line+="\t";
		line+=coreReleaseDate;
		fileWriter.append(line);
		fileWriter.append("\r\n");
		fileWriter.close();

		
		//same as full
		File tmpFile=new File(outputSnapFolder,outputFile.getName().replace( "Full","Snapshot"));
		FileHelper.copyTo(outputFile,tmpFile);

		//to drools
		outputFile=new File(droolsFolder,tmpFile.getName());
		FileHelper.copyTo(tmpFile, outputFile);
		
		//just new line
		outputFile=new File(outputDeltaFolder,outputFile.getName().replace("Snapshot", "Delta"));
		fileWriter = getWriter(outputFile.getAbsolutePath());
		fileWriter.append(header);
		fileWriter.append("\r\n");
		fileWriter.append(line);
		fileWriter.append("\r\n");
		fileWriter.close();
				
	}
	private String getPreviousCoreReleaseDate(String release) throws ParseException {
		String year=release.substring(0, 4);
		int month=Integer.parseInt(release.substring(4, 6));
		if (month>7){
			return year + "0731"; 
		}else if (month>1){
			return year + "0131";
		}
		int intYear=Integer.parseInt(year);
		intYear--;
		return String.valueOf(intYear) + "0731";
	}
	private void mergeFiles(File file1,File file2,File output){
		HashSet<File> hFile=new HashSet<File>();
		hFile.add(file1);
		hFile.add(file2);
		CommonUtils.concatFile(hFile, output);
		
	}
	private void mergeDescriptionsToDroolsFolder() {
		HashSet<File> hFile=new HashSet<File>();
		hFile.add(snapshotDescFinalFile);
		hFile.add(outputDescriptionFile);
		CommonUtils.concatFile(hFile, outputDescriptionFile);
	}
	private void completeDescriptions() throws IOException {
		BufferedReader br = getReader(othrDescriptions);
		outputDescriptionFile=new File(droolsFolder,outputDescriptions);
		BufferedWriter bw = getWriter(outputDescriptionFile.getAbsolutePath());
		
		bw.append(br.readLine());
		bw.append("\r\n");
		String line;
		String spl[];

		while((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			if (descInLang.contains(spl[0])){
				bw.append(line);
				bw.append("\r\n");
			}
		}
		bw.close();
		br.close();
		
	}
	
	private void loadDescriptionsInLang() throws IOException {
		descInLang=new HashSet<String>();
		BufferedReader br = getReader(snapshotLangFinalFile.getAbsolutePath());
		br.readLine();
		String line;
		String[] spl;
		while((line=br.readLine())!=null){
			spl=line.split("\t",-1);
			descInLang.add(spl[5]);
		}
		br.close();
	}
	
	private BufferedWriter getWriter(String outFile) throws UnsupportedEncodingException, FileNotFoundException {

		FileOutputStream tfos = new FileOutputStream( outFile);
		OutputStreamWriter tfosw = new OutputStreamWriter(tfos,"UTF-8");
		return new BufferedWriter(tfosw);

	}
	private BufferedReader getReader(String inFile) throws UnsupportedEncodingException, FileNotFoundException {

		FileInputStream rfis = new FileInputStream(inFile);
		InputStreamReader risr = new InputStreamReader(rfis,"UTF-8");
		BufferedReader rbr = new BufferedReader(risr);
		return rbr;

	}
}
