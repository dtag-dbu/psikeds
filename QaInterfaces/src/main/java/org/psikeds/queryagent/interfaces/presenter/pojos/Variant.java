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
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Interface object representing a single Variant. Variants can optionally
 * have certain Features, i.e. hold a List of IDs of the referenced Features.
 * 
 * Note 1: ID must be globally unique.
 * 
 * Note 2: FeatureIDs must reference existing Objects!
 * 
 * Note 3: Reading from and writing to JSON works out of the box.
 * However for XML the XmlRootElement annotation is required.
 * 
 * @author marco@juliano.de
 * 
 */
@XmlRootElement(name = "Variant")
public class Variant extends POJO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String label;
  private String description;
  private List<Feature> features;

  public Variant() {
    this(null, null, null);
  }

  public Variant(final String label, final String description, final String id) {
    this(label, description, id, null);
  }

  public Variant(final String label, final String description, final String id, final List<Feature> features) {
    super(id);
    this.label = label;
    this.description = description;
    this.features = features;
  }

  public String getLabel() {
    return this.label;
  }

  public void setLabel(final String value) {
    this.label = value;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(final String value) {
    this.description = value;
  }

  public List<Feature> getFeatures() {
    if (this.features == null) {
      this.features = new ArrayList<Feature>();
    }
    return this.features;
  }

  public void setFeatures(final List<Feature> features) {
    this.features = features;
  }

  public void addFeature(final Feature feature) {
    getFeatures().add(feature);
  }
}
