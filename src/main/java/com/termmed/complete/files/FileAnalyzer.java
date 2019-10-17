package com.termmed.complete.files;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class FileAnalyzer {

	public enum FileType {
		RF1_CONCEPTS("rf1-concepts"), RF1_DESCRIPTIONS("rf1-descriptions"), RF1_RELATIONSHIPS("rf1-relationships"), RF1_SUBSETS("rf1-subsets"), RF1_SUBSETMEMBERS("rf1-subsetmembers"), RF1_CROSSMAPS(
				"rf1-crossmaps"), RF1_CROSSMAPSETS("rf1-crossmapsets"), RF1_CROSSMAPTARGETS("rf1-crossmaptargets"), RF1_COMPONENTHISTORY("rf1-componenthistory"), RF1_REFERENCES("rf1-references"), RF1_TEXTDEFINITIONS(
						"rf1-textdefinitions"), RF2_SIMPLE("rf2-simple"), RF2_REFINABILITY("rf2-refinability"), RF2_QUALIFIER("rf2-qualifier"), RF2_COMPONENTHISTORY("rf2-componenthistory"), RF2_HISTORICALRELATIONSHIPIDENTIFIERS(
								"rf2-historicalrelationshipidentifiers"), RF2_RETIREDISARELATIONSHIP("rf2-retiredisarelationship"), RF2_RETIREDSTATEDISARELATIONSHIP("rf2-retiredstatedisarelationship"), RF2_SUBSETS(
										"rf2-subsets"), RF2_CROSSMAPSETS("rf2-crossmapsets"), RF2_CROSSMAPTARGETS("rf2-crossmaptargets"), RF2_EQUIVALENCEMAP("rf2-equivalencemap"), RF2_DESCRIPTIONTYPE("rf2-descriptiontype"), RF2_REFSETDESCRIPTOR(
												"rf2-refsetdescriptor"), RF2_IDENTIFIER("rf2-identifier"), RF2_TEXTDEFINITION("rf2-textDefinition"), RF2_ATTRIBUTEVALUE("rf2-attributevalue"), RF2_ASSOCIATION("rf2-association"), RF2_CONCEPTS(
														"rf2-concepts"), RF2_LANGUAGE("rf2-language"), RF2_DESCRIPTIONS("rf2-descriptions"), RF2_SIMPLEMAPS("rf2-simplemaps"), RF2_STATEDRELATIONSHIP("rf2-statedrelationship"), RF2_RELATIONSHIPS(
																"rf2-relationships"), RF2_MODULEDEPENDENCY("rf2-moduledependency"), RF2_NAVIGATION("rf2-navigation");

		private String typeName;

		private FileType(String typeName) {
			this.typeName = typeName;
		}

		public String getTypeName() {
			return this.typeName;
		}
	}

	public enum RF2InputFileTypes {
		RF2_IDENTIFIER("rf2-identifier"), RF2_TEXTDEFINITION("rf2-textDefinition"), RF2_ATTRIBUTEVALUE("rf2-attributevalue"), RF2_ASSOCIATION("rf2-association"), RF2_REFINABILITY("rf2-refinability"), RF2_QUALIFIER(
				"rf2-qualifier"), RF2_CONCEPTS("rf2-concepts"), RF2_SIMPLE("rf2-simple"), RF2_LANGUAGE("rf2-language"), RF2_DESCRIPTIONS("rf2-descriptions"), RF2_SIMPLEMAPS("rf2-simplemaps"), RF2_STATEDRELATIONSHIP(
						"rf2-statedrelationship"), RF2_RELATIONSHIPS("rf2-relationships");

		private String typeName;

		private RF2InputFileTypes(String typeName) {
			this.typeName = typeName;
		}

		public String getTypeName() {
			return this.typeName;
		}
	}

	@Deprecated
	public static FileType identifyType(File inputFile, File validationConfig) throws FileNotFoundException, Exception {

		ArrayList<FileType> matchedTypes = new ArrayList<FileType>();
		String nameRule = null;
		String headerRule = null;

		if (!inputFile.isFile())
			throw new FileNotFoundException();

		for (FileType loopType : FileType.values()) {
			XMLConfiguration xmlConfig = new XMLConfiguration(validationConfig);
			List<String> namePatterns = new ArrayList<String>();

			Object prop = xmlConfig.getProperty("files.file.fileType");
			if (prop instanceof Collection) {
				namePatterns.addAll((Collection) prop);
			}
			for (int i = 0; i < namePatterns.size(); i++) {
				if (xmlConfig.getString("files.file(" + i + ").fileType").equals(loopType.getTypeName())) {
					nameRule = xmlConfig.getString("files.file(" + i + ").nameRule.regex");
					headerRule = xmlConfig.getString("files.file(" + i + ").headerRule.regex");
					if (inputFile.getName().matches(nameRule)) {
						//System.out.println( "FileAnalizer: " +   "Validating: " + inputFile.getName() + " against " + namePatterns.get(i).toString());
						FileInputStream fis = new FileInputStream(inputFile);
						InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
						BufferedReader br = new BufferedReader(isr);
						String header = br.readLine();
						if (header.matches(headerRule))
							matchedTypes.add(loopType);
					}
				}
			}
		}
		if (matchedTypes.size() == 1) {
			return matchedTypes.get(0);
		} else {
			System.out.println( "FileAnalizer: " +   "Skip: " + inputFile.getName());
			if (!matchedTypes.isEmpty())
				throw new Exception(); // exception if size > 1
			// return null if size = 0
			return null;
		}
	}

	@Deprecated
	public static boolean validate(File inputFile, FileType fileType, File validationConfig) {
		boolean result = true;
		boolean nameResult = false;
		boolean headerResult = false;
		boolean lineResult = false;
		boolean finalLineResult = true;
		try {
			Thread currThread = Thread.currentThread();
			if (currThread.isInterrupted()) {
				return true;
			}
			XMLConfiguration xmlConfig = new XMLConfiguration(validationConfig);

			List<String> namePatterns = new ArrayList<String>();

			Object prop = xmlConfig.getProperty("files.file.fileType");
			if (prop instanceof Collection) {
				namePatterns.addAll((Collection) prop);
			}
			boolean toCheck = false;
			String nameRule = null;
			String headerRule = null;
			String contentRule = null;
			for (int i = 0; i < namePatterns.size(); i++) {
				if (currThread.isInterrupted()) {
					return true;
				}
				if (xmlConfig.getString("files.file(" + i + ").fileType").equals(fileType.getTypeName().toLowerCase())) {
					toCheck = true;
					nameRule = xmlConfig.getString("files.file(" + i + ").nameRule.regex");
					headerRule = xmlConfig.getString("files.file(" + i + ").headerRule.regex");
					contentRule = xmlConfig.getString("files.file(" + i + ").contentRule.regex");
					System.out.println( "FileAnalizer: " +   contentRule);
				}
			}
			if (toCheck) {
				nameResult = inputFile.getName().matches(nameRule);
				System.out.println( "FileAnalizer: " +   "Validating: " + inputFile.getName());
				System.out.println( "FileAnalizer: " +   " ** Result for name: " + nameResult);

				FileInputStream fis = new FileInputStream(inputFile);
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				BufferedReader br = new BufferedReader(isr);

				double lines = 1;
				String line;
				String header = br.readLine();
				headerResult = header.matches(headerRule);

				// System.out.print(header);
				// System.out.println( "FileAnalizer: " +   " ** Result for Header: " + headerResult);
				while ((line = br.readLine()) != null) {
					if (currThread.isInterrupted()) {
						return true;
					}
					lines++;
					lineResult = line.matches(contentRule);
					if (!lineResult && finalLineResult) {
						finalLineResult = false;
						System.out.print(line);
						System.out.println( "FileAnalizer: " +   " ** Eror in line: " + lines);
						break;
					}
				}
			} else {
				System.out.println( "FileAnalizer: " +   "Skip: " + inputFile.getName());
			}
		} catch (FileNotFoundException e) {
			System.out.println(  "FileAnalizer: " +    e.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.out.println(  "FileAnalizer: " +    e.getMessage());
		} catch (IOException e) {
			System.out.println(  "FileAnalizer: " +    e.getMessage());
		} catch (ConfigurationException e) {
			System.out.println(  "FileAnalizer: " +    e.getMessage());
		}
		// add nameResult
		result = (true == headerResult == finalLineResult);
		System.out.println("Validation result = " + result);
		return result;
	}

	public static FileType identifyTypeByHeader(File inputFile, File validationConfig) throws IOException, ConfigurationException {

		ArrayList<FileType> matchedTypes = new ArrayList<FileType>();
		String headerRule = null;
		ArrayList<String> nameRules = new ArrayList<String>();

		if (!inputFile.isFile())
			throw new FileNotFoundException();

		for (FileType loopType : FileType.values()) {
			XMLConfiguration xmlConfig = new XMLConfiguration(validationConfig);
			List<String> namePatterns = new ArrayList<String>();

			Object prop = xmlConfig.getProperty("files.file.fileType");
			if (prop instanceof Collection) {
				namePatterns.addAll((Collection) prop);
			}
			// System.out.println( "FileAnalizer: " +   namePatterns.size());
			// System.out.println( "FileAnalizer: " +   FileType.values().length);
			FileInputStream fis = new FileInputStream(inputFile);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String header = br.readLine();
			for (int i = 0; i < namePatterns.size(); i++) {
				if (xmlConfig.getString("files.file(" + i + ").fileType").equals(loopType.getTypeName())) {
					headerRule = xmlConfig.getString("files.file(" + i + ").headerRule.regex");
					//System.out.println( "FileAnalizer: " +   "Validating: " + inputFile.getName() + " against " + namePatterns.get(i).toString());
					if (header.matches(headerRule)) {
						matchedTypes.add(loopType);
						nameRules.add(xmlConfig.getString("files.file(" + i + ").nameRule.regex"));
					}
				}
			}
		}
		if (matchedTypes.size() != 1) {
			if (matchedTypes.isEmpty()) {
				//System.out.println( "FileAnalizer: " +   "Skip: " + inputFile.getName());
				return null; // return null if size = 0
			} else {
				Iterator it = nameRules.iterator();
				String nameRule;
				int i = 0;
				while (it.hasNext()) {
					nameRule = it.next().toString();
					if (inputFile.getName().matches(nameRule))
						return matchedTypes.get(i);
					i++;
				}
				System.out.println( "FileAnalizer: " +   "More than one results:");
				for (String string : nameRules) {
					System.out.println( "FileAnalizer: " +   string);
				}
				return null;
			}
		} else {
			return matchedTypes.get(0);
		}
	}

	public static boolean validateByHeader(File inputFile, File validationConfig) {
		boolean result = true;
		boolean headerResult = false;
		boolean lineResult = false;
		boolean finalLineResult = true;
		try {
			Thread currThread = Thread.currentThread();
			if (currThread.isInterrupted()) {
				return true;
			}
			XMLConfiguration xmlConfig = new XMLConfiguration(validationConfig);

			List<String> namePatterns = new ArrayList<String>();

			Object prop = xmlConfig.getProperty("files.file.fileType");
			if (prop instanceof Collection) {
				namePatterns.addAll((Collection) prop);
			}
//			System.out.println("Patterns Size: " + namePatterns.size());
			System.out.println("");
			boolean toCheck = false;
			String headerRule = null;
			String contentRule = null;

			FileInputStream fis = new FileInputStream(inputFile);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String header = br.readLine();
			for (int i = 0; i < namePatterns.size(); i++) {
				if (currThread.isInterrupted()) {
					return true;
				}
				headerRule = xmlConfig.getString("files.file(" + i + ").headerRule.regex");
				contentRule = xmlConfig.getString("files.file(" + i + ").contentRule.regex");
				if( header.matches(headerRule)){
					toCheck = true;
					break;
				}
			}
			if (toCheck) {

				double lines = 1;
				String line;
				headerResult=true;
				
				 System.out.println( "FileAnalizer: " + inputFile.getAbsolutePath() +  " ** match header rule: " + headerRule);
				while ((line = br.readLine()) != null) {
					if (currThread.isInterrupted()) {
						return true;
					}
					lines++;
					lineResult = line.matches(contentRule);
					if (!lineResult && finalLineResult) {
						finalLineResult = false;
						System.out.println( "FileAnalizer content rule: " +   contentRule);
						System.out.println( "FileAnalizer line: " +   line);
						System.out.println( "FileAnalizer: " +   " ** Error in line: " + lines);
						break;
					}
				}
			} else {
				headerResult = false;
				System.out.println( "FileAnalizer: " +   "Cannot found header matcher for : " + inputFile.getName());
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(  "FileAnalizer: " +    e.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.out.println(  "FileAnalizer: " +    e.getMessage());
		} catch (IOException e) {
			System.out.println(  "FileAnalizer: " +    e.getMessage());
		} catch (ConfigurationException e) {
			System.out.println(  "FileAnalizer: " +    e.getMessage());
		}
		result = (headerResult && finalLineResult);
		System.out.println("Validation result = " + result);
		return result;
	}

	public static String getSnapshotName(String targetFolder, FileType identifyType, String snapshotDate) {
		String sortedFileName = "";
		if(!new File(targetFolder+ "/snapshot").exists()){
			new File(targetFolder+ "/snapshot").mkdirs();
		}
		if (identifyType.equals(FileType.RF2_RELATIONSHIPS)) {
			sortedFileName = targetFolder + "/snapshot/sct2_Relationship_Snapshot_INT_" + snapshotDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_CONCEPTS)) {
			sortedFileName = targetFolder + "/snapshot/sct2_Concept_Snapshot_INT_" + snapshotDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_DESCRIPTIONS)) {
			sortedFileName = targetFolder + "/snapshot/sct2_Description_Snapshot-en_INT_" + snapshotDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_LANGUAGE)) {
			sortedFileName = targetFolder + "/snapshot/der2_cRefset_LanguageSnapshot-en_INT_" + snapshotDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_ATTRIBUTEVALUE)) {
			sortedFileName = targetFolder + "/snapshot/der2_cRefset_AttributeValueSnapshot_INT_" + snapshotDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_SIMPLEMAPS)) {
			sortedFileName = targetFolder + "/snapshot/der2_sRefset_SimpleMapSnapshot_INT_" + snapshotDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_STATEDRELATIONSHIP)) {
			sortedFileName = targetFolder + "/snapshot/sct2_StatedRelationship_Snapshot_INT_" + snapshotDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_REFINABILITY)) {
			sortedFileName = targetFolder + "/snapshot/xder2_cRefset_RefinabilitySnapshot_INT_" + snapshotDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_QUALIFIER)) {
			sortedFileName = targetFolder + "/snapshot/sct2_Qualifier_Snapshot_INT_" + snapshotDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_SIMPLE)) {
			sortedFileName = targetFolder + "/snapshot/der2_Refset_SimpleSnapshot_INT_" + snapshotDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_TEXTDEFINITION)) {
			sortedFileName = targetFolder + "/snapshot/xsct2_TextDefinition_Snapshot-en_INT_" + snapshotDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_ASSOCIATION)) {
			sortedFileName = targetFolder + "/snapshot/der2_cRefset_AssociationSnapshot_INT_" + snapshotDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_EQUIVALENCEMAP)) {
			sortedFileName = targetFolder + "/snapshot/der2_iissscRefset_ICD9CMEquivalenceMapSnapshot_INT_" + snapshotDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_IDENTIFIER)) {
			sortedFileName = "";
		}
		return sortedFileName;
	}

	public static String getDeltaName(String targetFolder, FileType identifyType, String deltaInitialDate, String deltaFinalDate) {
		String sortedFileName = "";
		if(!new File(targetFolder+ "/delta").exists()){
			new File(targetFolder+ "/delta").mkdirs();
		}
		if (identifyType.equals(FileType.RF2_RELATIONSHIPS)) {
			sortedFileName = targetFolder + "/delta/sct2_Relationship_Delta_INT_" + deltaInitialDate + "_" + deltaFinalDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_CONCEPTS)) {
			sortedFileName = targetFolder + "/delta/sct2_Concept_Delta_INT_" + deltaInitialDate + "_" + deltaFinalDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_DESCRIPTIONS)) {
			sortedFileName = targetFolder + "/delta/sct2_Description_Delta-en_INT_" + deltaInitialDate + "_" + deltaFinalDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_LANGUAGE)) {
			sortedFileName = targetFolder + "/delta/der2_cRefset_LanguageDelta-en_INT_" + deltaInitialDate + "_" + deltaFinalDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_ATTRIBUTEVALUE)) {
			sortedFileName = targetFolder + "/delta/der2_cRefset_AttributeValueDelta_INT_" + deltaInitialDate + "_" + deltaFinalDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_SIMPLEMAPS)) {
			sortedFileName = targetFolder + "/delta/der2_sRefset_SimpleMapDelta_INT_" + deltaInitialDate + "_" + deltaFinalDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_STATEDRELATIONSHIP)) {
			sortedFileName = targetFolder + "/delta/sct2_StatedRelationship_Delta_INT_" + deltaInitialDate + "_" + deltaFinalDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_REFINABILITY)) {
			sortedFileName = targetFolder + "/delta/xder2_cRefset_RefinabilityDelta_INT_" + deltaInitialDate + "_" + deltaFinalDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_QUALIFIER)) {
			sortedFileName = targetFolder + "/delta/sct2_Qualifier_Delta_INT_" + deltaInitialDate + "_" + deltaFinalDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_SIMPLE)) {
			sortedFileName = targetFolder + "/delta/der2_Refset_SimpleDelta_INT_" + deltaInitialDate + "_" + deltaFinalDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_TEXTDEFINITION)) {
			sortedFileName = targetFolder + "/delta/xsct2_TextDefinition_Delta-en_INT_" + deltaInitialDate + "_" + deltaFinalDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_ASSOCIATION)) {
			sortedFileName = targetFolder + "/delta/der2_cRefset_AssociationDelta_INT_" + deltaInitialDate + "_" + deltaFinalDate + ".txt";
		} else if (identifyType.equals(FileType.RF2_IDENTIFIER)) {
			sortedFileName = "";
		}
		return sortedFileName;
	}






}
