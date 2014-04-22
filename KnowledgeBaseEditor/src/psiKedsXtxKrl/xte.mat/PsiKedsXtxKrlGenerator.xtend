/*
 * generated by Xtext
 */
package org.psikeds.kb.modelling.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.xtext.generator.IFileSystemAccess	


import org.psikeds.kb.modelling.psiKedsXtxKrl.KnowledgeBase
import org.psikeds.kb.modelling.psiKedsXtxKrl.XLabel

import org.psikeds.kb.modelling.psiKedsXtxKrl.OpenContextualPath
import org.psikeds.kb.modelling.psiKedsXtxKrl.ClosedContextualPath
import org.psikeds.kb.modelling.psiKedsXtxKrl.RelationalStatement
import org.psikeds.kb.modelling.psiKedsXtxKrl.BOOLEANenum
import org.psikeds.kb.modelling.psiKedsXtxKrl.ExplicitVariant
import org.psikeds.kb.modelling.psiKedsXtxKrl.ImplicitVariant
import org.psikeds.kb.modelling.psiKedsXtxKrl.VariantPurposePair

/**
 * Generates code from your model files on save.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 */
class PsiKedsXtxKrlGenerator implements IGenerator {
	
	override void doGenerate(Resource resource, IFileSystemAccess fsa) {
		val model = resource.contents.head as KnowledgeBase
		fsa.generateFile(model.kbId+'.pkb.xml', model.toCode)
	}
	

	def CharSequence toCode(KnowledgeBase base) '''
<?xml version="1.0" encoding="UTF-8"?>
<!--
«base.kbId» : «base?.kbRelease» : «base?.kbTeaser» 
(C) «IF base.kbCopyright != null»«base.kbCopyright»«ENDIF» 

A psiKeds knowledge base: developed for being used by the inference machine
of psiKeds, the purpose system induced knowledge entity delivery system
http://www.psikeds.org/) and generated by the psiKedsXtXKrl-Generator

«IF base.kbLicense != null»
This file is licensed under the conditions of a/the
«base.kbLicense»«ENDIF»

-->
<kb:knowledgebase 
  xmlns="http://org.psikeds.knowledgebase"
  xmlns:kb="http://org.psikeds.knowledgebase"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://org.psikeds.knowledgebase psikeds.xsd">
  <kb:meta>
    «IF base.kbId != null»<kb:id>«base.kbId»</kb:id>«ENDIF»
    «IF base.kbName != null»<kb:name>«base.kbName»</kb:name>«ENDIF»
    «IF base.kbTeaser != null»<kb:teaser>«base.kbName»</kb:teaser>«ENDIF»
    «IF base.kbRelease != null»<kb:release>«base.kbRelease»</kb:release>«ENDIF»
    «IF base.kbCopyright != null»
      <kb:copyright>«base.kbCopyright»</kb:copyright>«ENDIF»
    «IF base.kbLicense != null»
      <kb:license>«base.kbLicense»</kb:license>«ENDIF»
    «IF base.kbFirstCreationDate != null»
    <kb:created>«base.kbFirstCreationDate»</kb:created>«ENDIF»
    «IF base.kbLastModificationDate != null»
    <kb:lastmodified>«base.kbLastModificationDate»</kb:lastmodified>«ENDIF»
    «IF base.kbLanguage != null»
    <kb:language>«base.kbLanguage»</kb:language>«ENDIF»
    «IF base.kbCreatorName.length > 0»
      «base.kbCreatorName.map[stringToXmlTag(it,'kb:creator')].join()»
	  «ENDIF»
    «IF base.kbDescription.length > 0»
      «base.kbDescription.map[stringToXmlTag(it,'kb:description')].join()»
    «ENDIF»
  </kb:meta>
  <kb:data>
    «IF ( base.sensorAreas != null && base.sensorAreas.length > 0)»
    <kb:sensors>
      «FOR sensorArea : base.sensorAreas»
      <kb:sensor id="«sensorArea.sensor.name»">
        «sensorArea.sensor.xlabel.toXml»
        «IF sensorArea.sensor.unit != null»
        <kb:unit>«sensorArea.sensor.unit»</kb:unit> «ENDIF»
        <kb:values>
          «IF ( sensorArea.feedback.sensorStringValues != null )»
          «FOR att : sensorArea.feedback.sensorStringValues»
          <kb:strValue id="«att.name»">«att.value»</kb:strValue>
          «ENDFOR»
          «ENDIF»
          «IF ( sensorArea.feedback.sensorIntDigits != null)»
          «FOR att :sensorArea.feedback.sensorIntDigits»
          <kb:intValue id="«att.name»">«att.value»</kb:intValue>
          «ENDFOR»
          «ENDIF»
          «IF ( sensorArea.feedback.sensorIntRanges != null)»
          «FOR att :sensorArea.feedback.sensorIntRanges»
          <kb:intRange id="«att.name»" 
            min="«att.min»" max="«att.max»" inc="«att.inc»" />
          «ENDFOR»
          «ENDIF»
          «IF ( sensorArea.feedback.sensorFloatDigits != null)»
          «FOR att :sensorArea.feedback.sensorFloatDigits»
          <kb:floatValue id="«att.name»">«att.value»</kb:floatValue>
          «ENDFOR»
          «ENDIF»
          «IF ( sensorArea.feedback.sensorFloatRanges != null)»
          «FOR att :sensorArea.feedback.sensorFloatRanges»
          <kb:floatRange id="«att.name»" 
            min="«att.min»" max="«att.max»" inc="«att.inc»" />
          «ENDFOR»
          «ENDIF»                   
        </kb:values>        
      </kb:sensor>
      «ENDFOR»     
    </kb:sensors>         
    «ENDIF»

    «IF ( base.concepts != null && base.concepts.length > 0)»  
    <kb:concepts>
      «IF (base.concepts.length > 0) »
      «FOR concept : base.concepts» 
      <kb:concept id="«concept.name»">
        «concept.xlabel.toXml»
        <kb:attributes>
          «IF concept.attStrReferences != null»
          «FOR attRef : concept.attStrReferences»
          <kb:attribute type="str" ref="«attRef.ref.name»" />
          «ENDFOR»
          «ENDIF»
          «IF concept.attIntReferences != null»
          «FOR attRef : concept.attIntReferences»
          <kb:attribute type="int" ref="«attRef.ref.name»" />
          «ENDFOR»
          «ENDIF»
          «IF concept.attFloatReferences != null»
          «FOR attRef : concept.attFloatReferences»
          <kb:attribute type="float" ref="«attRef.ref.name»" />
          «ENDFOR»
          «ENDIF»
        </kb:attributes>
      </kb:concept>
      «ENDFOR»
      «ENDIF» 
    </kb:concepts>
    «ENDIF»   
      
    «IF base.purposes.length > 0»
    <kb:purposes>
      «FOR purpose : base.purposes»
      <kb:purpose id="«purpose.name»"«IF purpose.root!=null && purpose.root!=BOOLEANenum::UNKNOWN» root="«purpose.root»"«ENDIF»>
        «purpose.xlabel.toXml»
      </kb:purpose>
      «ENDFOR»
    </kb:purposes>
    «ENDIF»   

    <kb:variants>
      «FOR pv : base.explicitVariants»«pv.toXml»«ENDFOR»
      «IF ( base.implicitVariants != null )»
      «FOR pv : base.implicitVariants»«pv.toXml»«ENDFOR»      
      «ENDIF»      
    </kb:variants>  

    «IF base.isFulfilledByStatements.length > 0»
    <kb:alternatives>
      «FOR ifb : base.isFulfilledByStatements»
      <kb:fulfills psRef="«ifb.psRef.name»"
        «IF ( (ifb.explPvRefs!=null && ifb.explPvRefs.length > 0)
            ||  (ifb.implPvRefs!=null && ifb.implPvRefs.length > 0) )»
        pvRefs="«IF ifb.explPvRefs!=null»«FOR pv : ifb.explPvRefs»«pv.ref.name» «ENDFOR»«ENDIF»"
        implicitPvRefs="«IF ifb.implPvRefs!=null»«FOR pv : ifb.implPvRefs»«pv.ref.name»«ENDFOR»«ENDIF»"«ENDIF» />
      «ENDFOR»
    </kb:alternatives>  
    «ENDIF»
     
    «IF ( base.isConstitutedByStatements != null 
          && base.isConstitutedByStatements.length > 0 )» 
    <kb:constituents>
      «FOR isConstitutedBy : base.isConstitutedByStatements»
      <kb:constitutes pvRef="«isConstitutedBy.pvRef.name»">
        «FOR cba : isConstitutedBy.isConstitutedByAssignments»
        <kb:component many="«cba.many»" psRef="«cba.psRef.name»" />
        «ENDFOR»
      </kb:constitutes>  
      «ENDFOR»
    </kb:constituents> 
    «ENDIF»

    «IF ( base.isConstitutedBySubsetStatements != null 
          && base.isConstitutedBySubsetStatements.length > 0)» 
    <kb:derivations>
      «FOR isConstitutedBy : base.isConstitutedBySubsetStatements»
      <kb:setup pvImplRef="«isConstitutedBy.pvRef.name»"
        psRefs="«FOR psRef : isConstitutedBy.psRefs»«psRef.name» «ENDFOR»" />
      «ENDFOR»
    </kb:derivations> 
    «ENDIF»
    
    «IF ( ( base.contextEventClosedByVariants != null &&
            base.contextEventClosedByVariants.length > 0 )
        || ( base.contextEventClosedByStrings != null &&
            base.contextEventClosedByStrings.length > 0)
        || ( base.contextEventClosedByInts != null &&
            base.contextEventClosedByInts.length > 0)
        || ( base.contextEventClosedByFloats != null &&
            base.contextEventClosedByFloats.length > 0)
        || ( base.contextEventClosedByConcepts != null &&
            base.contextEventClosedByConcepts.length > 0)         
        )»
    <kb:events>
      «IF ( base.contextEventClosedByVariants != null )»
      «FOR vEv :  base.contextEventClosedByVariants»
      <kb:event id="«vEv.name»" 
        «vEv.context.toXml»> 
        «vEv.xlabel.toXml»
        «IF vEv.negation»<kb:not />«ENDIF»
        «IF vEv.exPvFact!=null»<kb:trigger tt="pv" ref="«vEv.exPvFact.ref.name»" />«ENDIF»
        «IF vEv.imPvFact!=null»<kb:trigger tt="pv.impl" ref="«vEv.imPvFact.ref.name»" />«ENDIF»
      </kb:event>
      «ENDFOR»
      «ENDIF» 
      «IF ( base.contextEventClosedByStrings != null )»
      «FOR attStrEvent : base.contextEventClosedByStrings»
      <kb:event id="«attStrEvent.name»"
        «attStrEvent.context.toXml» >
        «attStrEvent.xlabel.toXml»
        «IF attStrEvent.negation»<kb:not />«ENDIF»
        <kb:trigger tt="strAtt" ref="«attStrEvent.fact.ref.name»" />
      </kb:event>
      «ENDFOR»
      «ENDIF»
      «IF ( base.contextEventClosedByInts != null )»
      «FOR attIntEvent : base.contextEventClosedByInts»
      <kb:event id="«attIntEvent.name»"
        «attIntEvent.context.toXml» >
        «attIntEvent.xlabel.toXml»
        «IF attIntEvent.negation»<kb:not />«ENDIF»
        <kb:trigger tt="intAtt" ref="«attIntEvent.fact.ref.name»" />
      </kb:event>
      «ENDFOR»
      «ENDIF»
      «IF ( base.contextEventClosedByFloats != null )»
      «FOR attFloatEvent : base.contextEventClosedByFloats»
      <kb:event id="«attFloatEvent.name»" 
        «attFloatEvent.context.toXml» >
        «attFloatEvent.xlabel.toXml»
        «IF attFloatEvent.negation»<kb:not />«ENDIF»
        <kb:trigger tt="floatAtt" ref="«attFloatEvent.fact.ref.name»" />
      </kb:event>
      «ENDFOR»
      «ENDIF»
      «IF ( base.contextEventClosedByConcepts != null )»
      «FOR concEvent : base.contextEventClosedByConcepts»
      <kb:event id="«concEvent.name»"
        «concEvent.context.toXml» >
        «concEvent.xlabel.toXml»
        «IF concEvent.negation»<kb:not />«ENDIF»
        <kb:trigger tt="concept" ref="«concEvent.fact.ref.name»" />
      </kb:event>
      «ENDFOR»
      «ENDIF»      
    </kb:events> 
    «ENDIF»
    
    «IF  ( base.relationsParameters != null &&
            base.relationsParameters.length > 0)»
    <kb:parameters>
      «FOR parameter :  base.relationsParameters»
      <kb:parameter id="«parameter.name»"
        «parameter.context.toXml»>
        «parameter.xlabel.toXml»
        <kb:valueSet sensorRef="«parameter.fact.ref.name»" />
      </kb:parameter>
      «ENDFOR»
    </kb:parameters> 
    «ENDIF»
    
    «IF ( ( base.logicalEnforcers != null &&
            base.logicalEnforcers.length > 0)
        || ( base.logicalRules != null &&
            base.logicalRules.length > 0)         
        )»
    <kb:rules>
      «IF ( base.logicalEnforcers != null )»
      «FOR enfR :  base.logicalEnforcers»
      <kb:rule id="«enfR.name»"
        «IF enfR.nexusEpv!=null»nexusRef="«enfR.nexusEpv.ref.name»«ELSE»«enfR.nexusIpv.ref.name»«ENDIF»"
        «IF enfR.nexusEpv!=null»premiseRefs="«enfR.nexusEpv.ref.name»«ELSE»«enfR.nexusIpv.ref.name»«ENDIF»"
        conclusioRef="«enfR.conclusio.name»" >
        «enfR.xlabel.toXml»
      </kb:rule>
      «ENDFOR»
      «ENDIF» 
      «IF ( base.logicalRules != null )»
      «FOR logR :  base.logicalRules»
      <kb:rule id="«logR.name»"
       «IF logR.nexusEpv!=null»nexusRef="«logR.nexusEpv.ref.name»«ELSE»«logR.nexusIpv.ref.name»«ENDIF»"
        premiseRefs="«FOR evP : logR.premiseEvents»«evP.name» «ENDFOR»"
        conclusioRef="«logR.conclusio.name»" >
        «logR.xlabel.toXml»
      </kb:rule>
      «ENDFOR»
      «ENDIF»    
    </kb:rules> 
    «ENDIF»
    
    «IF (  ( base.relationalConstraints != null &&
             base.relationalConstraints.length > 0)
        || ( base.conditionedRelationalConstraints != null &&
             base.conditionedRelationalConstraints.length > 0)
        )»
    <kb:relations>
      «IF base.relationalConstraints != null»
      «FOR rel :  base.relationalConstraints»
      <kb:relation id="«rel.name»"
        «IF rel.nexusEpv!=null»nexusRef="«rel.nexusEpv.ref.name»«ELSE»«rel.nexusIpv.ref.name»«ENDIF»"
        «rel.stmnt.lArgToXml»
        rtype="«rel.stmnt.relType»"
        «rel.stmnt.rArgToXml» >
        «rel.xlabel.toXml»
      </kb:relation>
      «ENDFOR»
      «ENDIF»
      «IF base.conditionedRelationalConstraints != null»
      «FOR rel :  base.conditionedRelationalConstraints»
      <kb:condRelation id="«rel.name»"
        «IF rel.nexusEpv!=null»nexusRef="«rel.nexusEpv.ref.name»«ELSE»«rel.nexusIpv.ref.name»«ENDIF»"
        condRef="«rel.trigger.name»"
        «rel.stmnt.lArgToXml»
        rtype="«rel.stmnt.relType»"
        «rel.stmnt.rArgToXml» >
        «rel.xlabel.toXml»
      </kb:condRelation>
      «ENDFOR»
      «ENDIF»      
      
    </kb:relations>    
    «ENDIF»
  </kb:data>    
</kb:knowledgebase>
	'''

/* multiply usable: integrate the expandable label into tag structure */
def String toXml(XLabel xlabel) '''
  <kb:label>«xlabel.label»</kb:label>
  «IF xlabel.description != null»
  <kb:description>
  «xlabel.description»
  </kb:description>
  «ENDIF»
''' 
  
/* multiply usable: write any value into any tag */
  def String stringToXmlTag(String value, String tag) '''
    <«tag»>«value»</«tag»>
'''

/* multiply usable: paths into the different event or rule structures */
  def String toXml(OpenContextualPath opCtxPath) '''
  «IF opCtxPath.variantPurposeRow!=null && opCtxPath.variantPurposeRow.length>0»
  nexusRef="«opCtxPath.variantPurposeRow.head.deReferPairHead»"
  contextRefs="«FOR vpp:opCtxPath.variantPurposeRow»«vpp.deReferPair»«ENDFOR»"«ENDIF»'''
 
  def String toXml(ClosedContextualPath clCtxPath) '''
  «IF clCtxPath.variantPurposeRow!=null && clCtxPath.variantPurposeRow.length>0»
  nexusRef="«clCtxPath.variantPurposeRow.head.deReferPairHead»"«ELSE»nexusRef="«clCtxPath.closingVariant.ref.name»"«ENDIF»
  contextRefs=" «IF clCtxPath.variantPurposeRow!=null»«FOR vpp : clCtxPath.variantPurposeRow»«vpp.deReferPair» «ENDFOR»«ENDIF»«clCtxPath.closingVariant.ref.name»"'''

  def String deReferPairHead(VariantPurposePair vpp) '''
  «IF vpp.explPvRef!=null»«vpp.explPvRef.ref.name»«ELSE»«vpp.implPvRef.ref.name»«ENDIF»'''

  def String deReferPair(VariantPurposePair vpp) '''
  «IF vpp.explPvRef!=null»«vpp.explPvRef.ref.name»«ELSE»«vpp.implPvRef.ref.name»«ENDIF» «vpp.purpose.name»'''
	
/* multiply usable: left and write arguments into relations */
  def String lArgToXml (RelationalStatement stmnt) '''
  «IF stmnt.leVarArg != null»lpType="vVal" lpRef="«stmnt.leVarArg.name»"«ENDIF»
  «IF stmnt.leStrConst != null»lpType="cStr" lpRef="«stmnt.leStrConst.name»"«ENDIF»
  «IF stmnt.leIntConst != null»lpType="cInt" lpRef="«stmnt.leIntConst.name»"«ENDIF»
  «IF stmnt.leFloatConst != null»lpType="cFloat" lpRef="«stmnt.leFloatConst.name»"«ENDIF»'''
  
  def String rArgToXml (RelationalStatement stmnt) '''
  «IF stmnt.riVarArg != null»rpType="vVal" rpRef="«stmnt.riVarArg.name»"«ENDIF»
  «IF stmnt.riStrConst != null»rpType="cStr" rpRef="«stmnt.riStrConst.name»"«ENDIF»
  «IF stmnt.riIntConst != null»rpType="cInt" rpRef="«stmnt.riIntConst.name»"«ENDIF»
  «IF stmnt.riFloatConst != null»rpType="cFloat" rpRef="«stmnt.riFloatConst.name»"«ENDIF»'''


  def String toXml(ImplicitVariant variant) '''
    <kb:variant id="«variant.name»" type="implicit">
      «variant.xlabel.toXml»
    </kb:variant>
  '''

  def String toXml(ExplicitVariant variant) '''
    <kb:variant id="«variant.name»"
      «IF variant.singleton!=null && variant.singleton!=BOOLEANenum::UNKNOWN»singleton="«variant.singleton»"«ENDIF»
      type="explicit">
      «variant.xlabel.toXml»
      «IF ( (variant.oneOfTheseAttributesSections != null
            && variant.oneOfTheseAttributesSections.size>0)
          || (variant.oneOutOfThisRanges != null
            && variant.oneOutOfThisRanges.size>0) 
          || (variant.conceptReferences != null
            && variant.conceptReferences.size()>0)
          )»
      <kb:primarilyDenotedBy>
        «IF variant.oneOfTheseAttributesSections != null»
        «FOR oneOutOfThisSection : variant.oneOfTheseAttributesSections»
        «IF (oneOutOfThisSection.strAttReferences != null &&
             oneOutOfThisSection.strAttReferences.size>0)»
        <kb:oneOutOfTheseStrAttributes>
          «FOR attStrRef : oneOutOfThisSection.strAttReferences»
          <kb:attribute type="str" ref="«attStrRef.ref.name»" />
          «ENDFOR»
        </kb:oneOutOfTheseStrAttributes>           
        «ENDIF»
        «IF (oneOutOfThisSection.intAttReferences != null &&
                 oneOutOfThisSection.intAttReferences.size>0)»
        <kb:oneOutOfTheseIntAttributes>
          «FOR attStrRef : oneOutOfThisSection.intAttReferences»
          <kb:attribute type="int" ref="«attStrRef.ref.name»" />
          «ENDFOR»
        </kb:oneOutOfTheseIntAttributes>           
        «ENDIF»  
        «IF (oneOutOfThisSection.floatAttReferences != null &&
                 oneOutOfThisSection.floatAttReferences.size>0)»
        <kb:oneOutOfTheseFloatAttributes>
          «FOR attStrRef : oneOutOfThisSection.floatAttReferences»
          <kb:attribute type="float" ref="«attStrRef.ref.name»" />
          «ENDFOR»
        </kb:oneOutOfTheseFloatAttributes>           
        «ENDIF»
        «ENDFOR»
        «ENDIF»
        «IF variant.oneOutOfThisRanges != null»
        «FOR oneOutOfThisRange : variant.oneOutOfThisRanges »
        «IF oneOutOfThisRange.attIntRangeReference != null »
        <kb:oneOutOfThisRange rangeRef="«oneOutOfThisRange.attIntRangeReference.ref.name»" />
        «ENDIF»
        «IF oneOutOfThisRange.attFloatRangeReference != null »
        <kb:oneOutOfThisRange rangeRef="«oneOutOfThisRange.attFloatRangeReference.ref.name»" />
        «ENDIF»
        «ENDFOR»
        «ENDIF»
        «IF ( variant.conceptReferences != null
           && variant.conceptReferences.size()>0)»
        <kb:oneOutOfTheseTerminalConcepts>
          «FOR conc : variant.conceptReferences »
          <kb:subsumption type="sensored" ref="«conc.ref.name»" />
          «ENDFOR»
        </kb:oneOutOfTheseTerminalConcepts>
        «ENDIF»
      </kb:primarilyDenotedBy>
      «ENDIF»
      «IF (  variant.conceptReferences != null
          && variant.conceptReferences.size()>0
          && variant.derivedConceptReferences != null
          && variant.derivedConceptReferences.size()>0 )»
      <kb:secondarilyDenotedBy>
        «FOR conc : variant.derivedConceptReferences »
        <kb:subsumption type="derived" ref="«conc.ref.name»" />
        «ENDFOR»
      </kb:secondarilyDenotedBy>
      «ENDIF»
    </kb:variant>
    
  '''
  
  

}

