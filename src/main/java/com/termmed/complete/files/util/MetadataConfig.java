package com.termmed.complete.files.util;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class MetadataConfig {


	private String RF2_FSN;
	private String RF2_SYNONYM;
	private String RF2_PREFERRED;
	private String RF2_ACCEPTABLE;
	private String RF2_EN_REFSET;

	private String RF2_ICS_SIGNIFICANT;

	private String RF2_DEF_STATUS_PRIMITIVE;

	private String RF2_CTV3ID_REFSETID;
	private String RF2_SNOMEDID_REFSETID;

	private String RF1_FSN;
	private String RF1_SYNONYM;
	private String RF1_PREFERRED;

	private String RF1_EN_SUBSET;
	private String RF1_GB_SUBSET;
	private String RF1_GBLANG_CODE;
	private String RF1_USLANG_CODE;
	private String RF1_ENLANG_CODE;

	private XMLConfiguration xmlConfig;
	private String RF1_SUBSET_DEFINED;
	private String RF2_ENUS_REFSET;
	private String RF2_ENGB_REFSET;

	private String RF2_INACT_CONCEPT_REFSET;
	private String RF2_INACT_DESCRIPTION_REFSET;

	private String RF2_REFINABILITY_REFSETID;
	//	refina 900000000000488004

	private HashMap<String,String> RF2RF1RefinaMap;
	private HashMap<String,String> RF2RF1charTypeMap;
	private HashMap<String, String> RF2RF1inactStatMap;
	private HashMap<String, String> RF2RF1AssociationMap;
	private String RF2_ISA_RELATIONSHIP;
	private HashMap<String, String> RF1RF2charTypeMap;
	private String RF2ModifierSome;
	private HashMap<String, String> RF1RF2RefinaMap;
	private String RF2_DEF_STATUS_DEFINED;
	private HashMap<String, String> RF1RF2inactStatMap;
	private String RF2_ICS_NOSIGNIFICANT;
	private HashMap<String, String> RF1RF2AssociationMap;
	private TreeSet<String> RF1CauseInactiveConcept;
	private String RF1_ISA_RELATIONSHIP;
	private HashMap<String, String[]> RF2References;
	private String RF2_CA_REFSET;
	private String RF2_ES_REFSET;
	private String metadataModelSCTID;
	private String namespaceCptSCTID;
	private String linkageCptSCTID;

	private HashSet<String> RF2DescriptionReferences;

	public MetadataConfig() throws Exception {

		//		this.configFile = configFile;
		File configFile= new File("config/metadata.xml");
		try {
			xmlConfig=new XMLConfiguration(configFile);
			RF2_FSN=xmlConfig.getString("rf2.descriptionType.fsn");
			RF2_SYNONYM=xmlConfig.getString("rf2.descriptionType.synonym");
			RF2_PREFERRED=xmlConfig.getString("rf2.acceptability.preferred");
			RF2_ACCEPTABLE=xmlConfig.getString("rf2.acceptability.acceptable");
			RF2_EN_REFSET=xmlConfig.getString("rf2.enRefset");
			RF2_ENUS_REFSET=xmlConfig.getString("rf2.enUSRefset");
			RF2_ENGB_REFSET=xmlConfig.getString("rf2.enGBRefset");
			RF2_CA_REFSET=xmlConfig.getString("rf2.caRefset");
			RF2_ES_REFSET=xmlConfig.getString("rf2.esRefset");

			RF2_ICS_SIGNIFICANT=xmlConfig.getString("rf2.ics.significant");
			RF2_ICS_NOSIGNIFICANT=xmlConfig.getString("rf2.ics.nosignificant");

			RF2_DEF_STATUS_PRIMITIVE=xmlConfig.getString("rf2.definitionStatus.primitive");
			RF2_DEF_STATUS_DEFINED=xmlConfig.getString("rf2.definitionStatus.defined");

			RF2_CTV3ID_REFSETID=xmlConfig.getString("rf2.ctv3idRefset");
			RF2_SNOMEDID_REFSETID=xmlConfig.getString("rf2.snomedIdRefset");

			RF2_REFINABILITY_REFSETID=xmlConfig.getString("rf2.refinabilityRefset");

			RF1_FSN=xmlConfig.getString("rf1.fsn");
			RF1_SYNONYM=xmlConfig.getString("rf1.synonym");
			RF1_PREFERRED=xmlConfig.getString("rf1.preferred");
			RF1_SUBSET_DEFINED=xmlConfig.getString("rf1.subsetDefined");
			RF1_EN_SUBSET=xmlConfig.getString("rf1.descriptionSubset.enOriginalSubsetId");
			RF1_GB_SUBSET=xmlConfig.getString("rf1.descriptionSubset.gbOriginalSubsetId");
			RF1_GBLANG_CODE=xmlConfig.getString("rf1.gbLangCode");
			RF1_USLANG_CODE=xmlConfig.getString("rf1.usLangCode");
			RF1_ENLANG_CODE=xmlConfig.getString("rf1.enLangCode");

			RF2RF1RefinaMap=new HashMap<String, String>();
			RF2RF1RefinaMap.put(xmlConfig.getString("rf2.refinability.notRefinable"),xmlConfig.getString("rf1.refinability.notRefinable"));
			RF2RF1RefinaMap.put(xmlConfig.getString("rf2.refinability.optional"),xmlConfig.getString("rf1.refinability.optional"));
			RF2RF1RefinaMap.put(xmlConfig.getString("rf2.refinability.mandatory"),xmlConfig.getString("rf1.refinability.mandatory"));

			RF1RF2RefinaMap=new HashMap<String, String>();
			RF1RF2RefinaMap.put(xmlConfig.getString("rf1.refinability.notRefinable"),xmlConfig.getString("rf2.refinability.notRefinable"));
			RF1RF2RefinaMap.put(xmlConfig.getString("rf1.refinability.optional"),xmlConfig.getString("rf2.refinability.optional"));
			RF1RF2RefinaMap.put(xmlConfig.getString("rf1.refinability.mandatory"),xmlConfig.getString("rf2.refinability.mandatory"));

			RF2RF1charTypeMap=new HashMap<String, String>();
			RF2RF1charTypeMap.put(xmlConfig.getString("rf2.characteristicType.DefiningRelationship"),xmlConfig.getString("rf1.characteristicType.DefiningRelationship"));
			RF2RF1charTypeMap.put(xmlConfig.getString("rf2.characteristicType.QualifyingRelationship"),xmlConfig.getString("rf1.characteristicType.QualifyingRelationship"));
			RF2RF1charTypeMap.put(xmlConfig.getString("rf2.characteristicType.AdditionalRelationship"),xmlConfig.getString("rf1.characteristicType.AdditionalRelationship"));

			RF1RF2charTypeMap=new HashMap<String, String>();
			RF1RF2charTypeMap.put(xmlConfig.getString("rf1.characteristicType.DefiningRelationship"),xmlConfig.getString("rf2.characteristicType.DefiningRelationship"));
			RF1RF2charTypeMap.put(xmlConfig.getString("rf1.characteristicType.QualifyingRelationship"),xmlConfig.getString("rf2.characteristicType.QualifyingRelationship"));
			RF1RF2charTypeMap.put(xmlConfig.getString("rf1.characteristicType.AdditionalRelationship"),xmlConfig.getString("rf2.characteristicType.AdditionalRelationship"));

			RF2ModifierSome=xmlConfig.getString("rf2.modifier.some");

			RF2_INACT_CONCEPT_REFSET=xmlConfig.getString("rf2.conceptInactivationRefset");
			RF2_INACT_DESCRIPTION_REFSET=xmlConfig.getString("rf2.descriptionInactivationRefset");

			RF2RF1inactStatMap=new HashMap<String, String>();
			//			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.NotStatedReason"),xmlConfig.getString("rf1.inactivationStatus.NotStatedReason"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Limited"),xmlConfig.getString("rf1.inactivationStatus.Limited"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Duplicate"),xmlConfig.getString("rf1.inactivationStatus.Duplicate"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Pending_move"),xmlConfig.getString("rf1.inactivationStatus.Pending_move"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Ambiguous"),xmlConfig.getString("rf1.inactivationStatus.Ambiguous"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Moved_elsewhere"),xmlConfig.getString("rf1.inactivationStatus.Moved_elsewhere"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Concept_non-current"),xmlConfig.getString("rf1.inactivationStatus.Concept_non-current"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Outdated"),xmlConfig.getString("rf1.inactivationStatus.Outdated"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Inappropriate"),xmlConfig.getString("rf1.inactivationStatus.Inappropriate"));
			RF2RF1inactStatMap.put(xmlConfig.getString("rf2.inactivationStatus.Erroneous"),xmlConfig.getString("rf1.inactivationStatus.Erroneous"));

			RF1RF2inactStatMap=new HashMap<String, String>();
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.NotStatedReason"),xmlConfig.getString("rf2.inactivationStatus.NotStatedReason"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Limited"),xmlConfig.getString("rf2.inactivationStatus.Limited"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Duplicate"),xmlConfig.getString("rf2.inactivationStatus.Duplicate"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Pending_move"),xmlConfig.getString("rf2.inactivationStatus.Pending_move"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Ambiguous"),xmlConfig.getString("rf2.inactivationStatus.Ambiguous"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Moved_elsewhere"),xmlConfig.getString("rf2.inactivationStatus.Moved_elsewhere"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Concept_non-current"),xmlConfig.getString("rf2.inactivationStatus.Concept_non-current"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Outdated"),xmlConfig.getString("rf2.inactivationStatus.Outdated"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Inappropriate"),xmlConfig.getString("rf2.inactivationStatus.Inappropriate"));
			RF1RF2inactStatMap.put(xmlConfig.getString("rf1.inactivationStatus.Erroneous"),xmlConfig.getString("rf2.inactivationStatus.Erroneous"));


			RF2RF1AssociationMap=new HashMap<String, String>();
			RF2RF1AssociationMap.put(xmlConfig.getString("rf2.association.Part_Of"),xmlConfig.getString("rf1.association.Part_Of"));
			RF2RF1AssociationMap.put(xmlConfig.getString("rf2.association.May_Be"),xmlConfig.getString("rf1.association.May_Be"));
			RF2RF1AssociationMap.put(xmlConfig.getString("rf2.association.Was_A"),xmlConfig.getString("rf1.association.Was_A"));
			RF2RF1AssociationMap.put(xmlConfig.getString("rf2.association.Same_As"),xmlConfig.getString("rf1.association.Same_As"));
			RF2RF1AssociationMap.put(xmlConfig.getString("rf2.association.Replaced_By"),xmlConfig.getString("rf1.association.Replaced_By"));
			RF2RF1AssociationMap.put(xmlConfig.getString("rf2.association.Moved_To"),xmlConfig.getString("rf1.association.Moved_To"));
			RF2RF1AssociationMap.put(xmlConfig.getString("rf2.association.Moved_From"),xmlConfig.getString("rf1.association.Moved_From"));

			RF1RF2AssociationMap=new HashMap<String, String>();
			RF1RF2AssociationMap.put(xmlConfig.getString("rf1.association.Part_Of"),xmlConfig.getString("rf2.association.Part_Of"));
			RF1RF2AssociationMap.put(xmlConfig.getString("rf1.association.May_Be"),xmlConfig.getString("rf2.association.May_Be"));
			RF1RF2AssociationMap.put(xmlConfig.getString("rf1.association.Was_A"),xmlConfig.getString("rf2.association.Was_A"));
			RF1RF2AssociationMap.put(xmlConfig.getString("rf1.association.Same_As"),xmlConfig.getString("rf2.association.Same_As"));
			RF1RF2AssociationMap.put(xmlConfig.getString("rf1.association.Replaced_By"),xmlConfig.getString("rf2.association.Replaced_By"));
			RF1RF2AssociationMap.put(xmlConfig.getString("rf1.association.Moved_To"),xmlConfig.getString("rf2.association.Moved_To"));
			RF1RF2AssociationMap.put(xmlConfig.getString("rf1.association.Moved_From"),xmlConfig.getString("rf2.association.Moved_From"));

			RF1CauseInactiveConcept=new TreeSet<String>();


			RF1CauseInactiveConcept.add(xmlConfig.getString("rf1.inactiveConcept.ambiguous"));
			RF1CauseInactiveConcept.add(xmlConfig.getString("rf1.inactiveConcept.duplicate"));
			RF1CauseInactiveConcept.add(xmlConfig.getString("rf1.inactiveConcept.erroneous"));
			RF1CauseInactiveConcept.add(xmlConfig.getString("rf1.inactiveConcept.limited"));
			RF1CauseInactiveConcept.add(xmlConfig.getString("rf1.inactiveConcept.moved"));
			RF1CauseInactiveConcept.add(xmlConfig.getString("rf1.inactiveConcept.outdated"));
			RF1CauseInactiveConcept.add(xmlConfig.getString("rf1.inactiveConcept.reasonNotStated"));
			RF2References=new HashMap<String,String[]>();
			
			RF2References.put(xmlConfig.getString("rf2.references.Replaced_By.refset"),new String[]{xmlConfig.getString("rf2.references.Replaced_By.validSourceStatus"),xmlConfig.getString("rf2.references.Replaced_By.rf1Value")});
			RF2References.put(xmlConfig.getString("rf2.references.Alternative.refset"),new String[]{xmlConfig.getString("rf2.references.Alternative.validSourceStatus"),xmlConfig.getString("rf2.references.Alternative.rf1Value")});
			RF2References.put(xmlConfig.getString("rf2.references.Refers_To.refset"),new String[]{xmlConfig.getString("rf2.references.Refers_To.validSourceStatus"),xmlConfig.getString("rf2.references.Refers_To.rf1Value")});
			
			RF2_ISA_RELATIONSHIP=xmlConfig.getString("rf2.isarelationship");
			RF1_ISA_RELATIONSHIP=xmlConfig.getString("rf1.isarelationship");
			RF2DescriptionReferences=new HashSet<String>();
			RF2DescriptionReferences.add(xmlConfig.getString("rf2.references.Refers_To.refset"));

			metadataModelSCTID=xmlConfig.getString("rf2.metadataModelSCTID");
			namespaceCptSCTID=xmlConfig.getString("rf2.namespaceCptSCTID");
			linkageCptSCTID=xmlConfig.getString("rf2.linkageCptSCTID");
		} catch (ConfigurationException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}


	public String getRF1_ISA_RELATIONSHIP() {
		return RF1_ISA_RELATIONSHIP;
	}


	public TreeSet<String> getRF1CauseInactiveConcept() {
		return RF1CauseInactiveConcept;
	}


	public HashMap<String, String> getRF1RF2AssociationMap() {
		return RF1RF2AssociationMap;
	}


	public String getRF2_ICS_NOSIGNIFICANT() {
		return RF2_ICS_NOSIGNIFICANT;
	}


	public HashMap<String, String> getRF1RF2inactStatMap() {
		return RF1RF2inactStatMap;
	}


	public String getRF2_DEF_STATUS_DEFINED() {
		return RF2_DEF_STATUS_DEFINED;
	}


	public HashMap<String, String> getRF1RF2RefinaMap() {
		return RF1RF2RefinaMap;
	}


	public String getRF2ModifierSome() {
		return RF2ModifierSome;
	}


	public HashMap<String, String> getRF1RF2charTypeMap() {
		return RF1RF2charTypeMap;
	}


	public String getRF2_FSN() {
		return RF2_FSN;
	}


	public String getRF2_SYNONYM() {
		return RF2_SYNONYM;
	}


	public String getRF2_PREFERRED() {
		return RF2_PREFERRED;
	}


	public String getRF2_EN_REFSET() {
		return RF2_EN_REFSET;
	}


	public String getRF2_ICS_SIGNIFICANT() {
		return RF2_ICS_SIGNIFICANT;
	}


	public String getRF2_DEF_STATUS_PRIMITIVE() {
		return RF2_DEF_STATUS_PRIMITIVE;
	}


	public String getRF2_CTV3ID_REFSETID() {
		return RF2_CTV3ID_REFSETID;
	}


	public String getRF2_SNOMEDID_REFSETID() {
		return RF2_SNOMEDID_REFSETID;
	}


	public String getRF1_FSN() {
		return RF1_FSN;
	}


	public String getRF1_SYNONYM() {
		return RF1_SYNONYM;
	}


	public String getRF1_PREFERRED() {
		return RF1_PREFERRED;
	}


	public String getRF1_EN_SUBSET() {
		return RF1_EN_SUBSET;
	}


	public String getRF1_GB_SUBSET() {
		return RF1_GB_SUBSET;
	}


	public String getRF1_GBLANG_CODE() {
		return RF1_GBLANG_CODE;
	}


	public String getRF1_USLANG_CODE() {
		return RF1_USLANG_CODE;
	}


	public String getRF1_ENLANG_CODE() {
		return RF1_ENLANG_CODE;
	}


	public String getRF1_SUBSET_DEFINED() {
		return RF1_SUBSET_DEFINED;
	}


	public String getRF2_ACCEPTABLE() {
		return RF2_ACCEPTABLE;
	}


	public String getRF2_ENUS_REFSET() {
		return RF2_ENUS_REFSET;
	}


	public String getRF2_ENGB_REFSET() {
		return RF2_ENGB_REFSET;
	}


	public String getRF2_REFINABILITY_REFSETID() {
		return RF2_REFINABILITY_REFSETID;
	}


	public HashMap<String, String> getRF2RF1RefinaMap() {
		return RF2RF1RefinaMap;
	}


	public HashMap<String, String> getRF2RF1charTypeMap() {
		return RF2RF1charTypeMap;
	}


	/**
	 * @return the rF2RF1inactStatMap
	 */
	public HashMap<String, String> getRF2RF1inactStatMap() {
		return RF2RF1inactStatMap;
	}


	/**
	 * @return the rF2_INACT_CONCEPT_REFSET
	 */
	public String getRF2_INACT_CONCEPT_REFSET() {
		return RF2_INACT_CONCEPT_REFSET;
	}


	/**
	 * @return the rF2_INACT_DESCRIPTION_REFSET
	 */
	public String getRF2_INACT_DESCRIPTION_REFSET() {
		return RF2_INACT_DESCRIPTION_REFSET;
	}


	public HashMap<String, String> getRF2RF1AssociationMap() {
		return RF2RF1AssociationMap;
	}


	/**
	 * @return the rF2_ISA_RELATIONSHIP
	 */
	public String getRF2_ISA_RELATIONSHIP() {
		return RF2_ISA_RELATIONSHIP;
	}

	public HashMap<String, String[]> getRF2References() {
		return RF2References;
	}


	public String getRF2_CA_REFSET() {
		return RF2_CA_REFSET;
	}


	public String getRF2_ES_REFSET() {
		return RF2_ES_REFSET;
	}

	public HashSet<String> getRF2DescriptionReferences() {
		return RF2DescriptionReferences;
	}


	public String getMetadataModelSCTID() {
		return metadataModelSCTID;
	}


	public String getNamespaceCptSCTID() {
		return namespaceCptSCTID;
	}


	public String getLinkageCptSCTID() {
		return linkageCptSCTID;
	}
}
