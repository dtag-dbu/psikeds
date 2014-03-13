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

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Interface object representing a Description of a Feature, i.e.
 * ID, Label, Description ... but no Values!!
 * 
 * Note: Feature-ID must be globally unique!
 * 
 * @author marco@juliano.de
 * 
 */
@XmlRootElement(name = "FeatureDescription")
public class FeatureDescription extends Feature implements Serializable {

  private static final long serialVersionUID = 1L;

  public FeatureDescription() {
    super();
  }

  public FeatureDescription(final String featureID, final String valueType) {
    this(featureID, null, featureID, valueType);
  }

  public FeatureDescription(final String label, final String description, final String featureID, final String valueType) {
    super(label, description, featureID, valueType);
  }

  public FeatureDescription(final Feature feature) {
    super(feature);
  }

  @JsonIgnore
  public FeatureValue toFeatureValue(final String value) {
    return new FeatureValue(this, value);
  }
}
