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
package org.psikeds.resolutionengine.datalayer.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A Context-Path is an ordered List of Object-IDs describing a Path along
 * the Graph of our Knowledge Base, i.e. Variant->Purpose->Variant->...)
 * 
 * Note: The Object-IDs within the List of a Context-Path must reference
 * existing Variants and Purposes in a reasonalbe Order!
 * 
 * @author marco@juliano.de
 * 
 */
public class ContextPath extends ValueObject implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final char PATH_SEPARATOR = '/';

  private List<String> pathIDs;

  public ContextPath() {
    this(null);
  }

  public ContextPath(final List<String> pathIDs) {
    super(pathAsString(pathIDs));
    this.pathIDs = pathIDs;
  }

  public List<String> getPathIDs() {
    if (this.pathIDs == null) {
      this.pathIDs = new ArrayList<String>();
    }
    return this.pathIDs;
  }

  public void setPathIDs(final List<String> pathIDs) {
    this.pathIDs = pathIDs;
  }

  public void addPathID(final String id) {
    getPathIDs().add(id);
  }

  // ------------------------------------------------------

  public String pathAsString() {
    return pathAsString(this);
  }

  public static String pathAsString(final ContextPath cp) {
    return pathAsString(cp.getPathIDs());
  }

  public static String pathAsString(final List<String> pathIDs) {
    final StringBuilder sb = new StringBuilder();
    for (final String id : pathIDs) {
      if (sb.length() > 0) {
        sb.append(PATH_SEPARATOR);
      }
      sb.append(id);
    }
    return sb.toString();
  }
}
