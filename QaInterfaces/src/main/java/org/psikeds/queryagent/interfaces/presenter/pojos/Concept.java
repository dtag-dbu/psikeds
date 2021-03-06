/*******************************************************************************
 * psiKeds :- ps induced knowledge entity delivery system
 *
 * Copyright (c) 2013, 2014 Karsten Reincke, Marco Juliano, Deutsche Telekom AG
 *
 * This file is free software: you can redistribute
 * it and/or modify it under the terms of the
 * [ ] GNU Affero General Public License
 * [ ] GNU General Public License
 * [x] GNU Lesser General Public License
 * [ ] Creatice Commons ShareAlike License
 *
 * For details see file LICENSING in the top project directory
 *******************************************************************************/
package org.psikeds.queryagent.interfaces.presenter.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Interface object representing a Concept, i.e. a subsumption
 * of Features-Values for a Variant.
 * 
 * Note 1: Concept-ID and Feature-Value-IDs must be globally unique!
 * 
 * Note 2: Feature-Value-IDs must reference distinct Features.
 * 
 * @author marco@juliano.de
 * 
 */
@XmlRootElement(name = "Concept")
public class Concept extends POJO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String label;
  private String description;
  private List<String> featureIds; // distinct features referenced by all values
  private FeatureValues values;

  public Concept() {
    this(null);
  }

  public Concept(final String conceptID) {
    this(conceptID, null, conceptID);
  }

  public Concept(final String label, final String description, final String conceptID) {
    this(label, description, conceptID, null, null);
  }

  public Concept(final String label, final String description, final String conceptID, final List<String> featureIds, final FeatureValues values) {
    super(conceptID);
    setValues(values);
    setLabel(label);
    setDescription(description);
    setFeatureIds(featureIds);
    setValues(values);
  }

  // ----------------------------------------------------------------

  public String getLabel() {
    return this.label;
  }

  public void setLabel(final String label) {
    this.label = label;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getConceptID() {
    return getId();
  }

  public void setConceptID(final String conceptID) {
    setId(conceptID);
  }

  // ----------------------------------------------------------------

  public List<String> getFeatureIds() {
    if (this.featureIds == null) {
      this.featureIds = new ArrayList<String>();
    }
    return this.featureIds;
  }

  public void setFeatureIds(final List<String> featureIds) {
    this.featureIds = featureIds;
  }

  public void addFeatureIds(final List<String> featureIds) {
    if ((featureIds != null) && !featureIds.isEmpty()) {
      for (final String fid : featureIds) {
        addFeatureId(fid);
      }
    }
  }

  public boolean addFeatureId(final String featureId) {
    return ((featureId != null) && !getFeatureIds().contains(featureId) && getFeatureIds().add(featureId));
  }

  public void clearFeatureIds() {
    if (this.featureIds != null) {
      this.featureIds.clear();
      this.featureIds = null;
    }
  }

  // ----------------------------------------------------------------

  public FeatureValues getValues() {
    if (this.values == null) {
      this.values = new FeatureValues();
    }
    return this.values;
  }

  public void setValues(final FeatureValues values) {
    this.values = values;
  }

  public boolean addValue(final FeatureValue value) {
    return ((value != null) && getValues().add(value));
  }

  public boolean addValue(final Collection<? extends FeatureValue> values) {
    return ((values != null) && !values.isEmpty() && getValues().addAll(values));
  }

  public void clearValues() {
    if (this.values != null) {
      this.values.clear();
      this.values = null;
    }
  }
}
