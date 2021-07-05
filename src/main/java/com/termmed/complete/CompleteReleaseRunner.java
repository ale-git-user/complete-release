package com.termmed.complete;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.termmed.complete.files.CompleteCoverageExtensionRelease;
import com.termmed.complete.files.ExtensionAndSourceReleaseCompletion;
import com.termmed.complete.files.ExtensionReleaseCompletion;

public class CompleteReleaseRunner {
	
	String inputfolder;
	
	private static Logger logger;

	public static void main(String[] args){

		logger = Logger.getLogger("com.termmed.complete.CompleteReleaseRunner");
		try {
			
			if (args==null || args.length<1){
				logger.info("Error happened getting params.");
				System.exit(0);
			}
			if (args[0].toUpperCase().equals("-RELEASECOMPLETE")){
				executeReleaseComplete(args);
			}
			if (args[0].toUpperCase().equals("-RELEASECOMPLETEWITHSOURCE")){
				executeReleaseCompleteWithSource(args);
			}
			if (args[0].toUpperCase().equals("-RELEASECOMPLETECOVERAGE")){
				executeReleaseCompleteCoverage(args);
			}
			if (args[0].toUpperCase().equals("-JOINSNAPSHOT")){
				executeJoinSnapshot(args);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.exit(0);
	}
	
	private static void executeJoinSnapshot(String[] args) throws IOException, Exception {

		logger.info("Executing Join Snapshots");
		if (args.length<4){
			logger.info("Error happened getting params. Expected param order: -JOINSNAPSHOT" +
				 "\n SnapshotReleaseFolder1" +  
				 "\n SnapshotReleaseFolder2" +  
				 "\n OutputFolder" );
			System.exit(0);
		}
		JoinSnapshots js=new JoinSnapshots(
				args[1], 
				args[2],
				args[3]);
		js.execute();
						
	}

	private static void executeReleaseCompleteWithSource(String[] args) throws IOException, Exception {

		logger.info("Executing Release Complete");
		if (args.length<11){
			logger.info("Error happened getting params. Expected param order: -RELEASECOMPLETEWITHSOURCE" +
				 "\n exportedDeltaFolder " + 
				 "\n coreSnapshotReleaseFolder" +  
				 "\n previousSnapshotReleaseFolder" + 
				 "\n previousFullReleaseFolder" + 
				 "\n languageCode" + 
				 "\n extensionName" + 
				 "\n extensionSuffix" + 
				 "\n releaseDate" +  
				 "\n prevReleaseDate" + 
				 "\n moduleId");
			System.exit(0);
		}
		ExtensionAndSourceReleaseCompletion fc=new ExtensionAndSourceReleaseCompletion(
				args[1], 
				args[2], 
				args[3],
				args[4],
				args[5],
				args[6],
				args[7],
				args[8], 
				args[9],
				args[10]);
		fc.execute();
				
	}

	private static void executeReleaseCompleteCoverage(String[] args) throws IOException, Exception {
		// TODO Auto-
		logger.info("Executing Release Complete");
		if (args.length<11){
			logger.info("Error happened getting params. Expected param order: -RELEASECOMPLETECOVERAGE" +
				 "\n exportedDeltaFolder " + 
				 "\n coreSnapshotReleaseFolder" +  
				 "\n previousSnapshotReleaseFolder" + 
				 "\n previousFullReleaseFolder" + 
				 "\n languageCode" + 
				 "\n extensionName" + 
				 "\n extensionSuffix" + 
				 "\n releaseDate" +  
				 "\n prevReleaseDate" + 
				 "\n moduleId");
			System.exit(0);
		}
		CompleteCoverageExtensionRelease fc=new CompleteCoverageExtensionRelease(
				args[1], 
				args[2], 
				args[3],
				args[4],
				args[5],
				args[6],
				args[7],
				args[8], 
				args[9],
				args[10]);
		fc.execute();
		
	}

	private static void executeReleaseComplete(String[] args) throws IOException, Exception {

		logger.info("Executing Release Complete");
		if (args.length<11){
			logger.info("Error happened getting params. Expected param order: -RELEASECOMPLETE" +
				 "\n exportedDeltaFolder " + 
				 "\n coreSnapshotReleaseFolder" +  
				 "\n previousSnapshotReleaseFolder" + 
				 "\n previousFullReleaseFolder" + 
				 "\n languageCode" + 
				 "\n extensionName" + 
				 "\n extensionSuffix" + 
				 "\n releaseDate" +  
				 "\n prevReleaseDate" + 
				 "\n moduleId");
			System.exit(0);
		}
		ExtensionReleaseCompletion fc=new ExtensionReleaseCompletion(
				args[1], 
				args[2], 
				args[3],
				args[4],
				args[5],
				args[6],
				args[7],
				args[8], 
				args[9],
				args[10]);
		fc.execute();
		
	}

}
