package com.termmed.complete.files;

import java.io.File;

public class FileValidator {
	private static File validationConfig=new File("config/validation-rules.xml");
	public static void main(String[] args) {
		try {
			File outputfolder = new File("/release_20140430/SnomedCT_es_SpanishExtension_20140430/");
			validateFiles(outputfolder);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void validateFiles(File folder) {
		if (folder.isDirectory()) {
			File[] subFolders = folder.listFiles();
			System.out.println(folder.getName() + " " + subFolders.length);
			for (File file : subFolders) {
				validateFiles(file);
			}
		} else {
			if (!folder.isHidden()){

				FileAnalyzer.validateByHeader(folder, validationConfig);
			}
		}

	}

}
