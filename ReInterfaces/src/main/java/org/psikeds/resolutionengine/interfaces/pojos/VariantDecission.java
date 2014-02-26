/*******************************************************************************
 * psiKeds :- ps induced knowledge entity delivery system
 *
 * Copyright (c) 2013, 2014 Karsten Reincke, Marco Juliano, Deutsche Telekom AG
 *
 * This file is free software: you can redistribute
 * it and/or modify it under the terms of the
 * [x] GNU Affero General Public License
 * [ ] GNU General Public License
 * [ ] GNU Lesser General Public License
 * [ ] Creatice Commons ShareAlike License
 *
 * For details see file LICENSING in the top project directory
 *******************************************************************************/
package org.psikeds.resolutionengine.interfaces.pojos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A Decission made: Which Variant was selected for which Purpose?
 * 
 * Note 1: PurposeID and VariantID must reference existing Objects!
 * 
 * Note 2: Reading from and writing to JSON works out of the box.
 * However for XML the XmlRootElement annotation is required.
 * 
 * @author marco@juliano.de
 * 
 */
@XmlRootElement(name = "VariantDecission")
public class VariantDecission extends POJO implements Decission, Serializable {

  private static final long serialVersionUID = 1L;

  private String purposeID;
  private String variantID;

  public VariantDecission() {
    this((Purpose) null, (Variant) null);
  }

  public VariantDecission(final String purposeID, final String variantID) {
    super(purposeID, variantID);
    setPurposeID(purposeID);
    setVariantID(variantID);
  }

  public VariantDecission(final Purpose purpose, final Variant variant) {
    super(purpose, variant);
    setPurpose(purpose);
    setVariant(variant);
  }

  public String getPurposeID() {
    return this.purposeID;
  }

  public void setPurposeID(final String purposeID) {
    this.purposeID = purposeID;
  }

  public void setPurpose(final Purpose purpose) {
    this.purposeID = (purpose == null ? null : purpose.getId());
  }

  public String getVariantID() {
    return this.variantID;
  }

  public void setVariantID(final String variantID) {
    this.variantID = variantID;
  }

  public void setVariant(final Variant variant) {
    this.variantID = (variant == null ? null : variant.getId());
  }
}
