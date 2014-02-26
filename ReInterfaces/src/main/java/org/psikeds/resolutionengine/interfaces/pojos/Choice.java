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

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 * A general Choice, either VariantChoice and FeatureChoice
 * 
 * @author marco@juliano.de
 * 
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({ @JsonSubTypes.Type(value = FeatureChoice.class, name = "FeatureChoice"), @JsonSubTypes.Type(value = VariantChoice.class, name = "VariantChoice"), })
public interface Choice {

  // the purpose of this interface is to mark an object as a choice
  // and to control its JSON-representation

  /**
   * Check whether a made Decission matches to this Choice
   * 
   * @param decission
   * @return POJO if matching, null else
   */
  POJO matches(final Decission decission);
}
