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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Interface object representing a single Purpose. Purposes marked with the
 * root-flag are used for the initial context of a resolution, i.e. this is
 * where a GUI-dialog/wizard should start.
 * 
 * Note: ID must be globally unique.
 * 
 * @author marco@juliano.de
 * 
 */
@XmlRootElement(name = "Purpose")
public class Purpose extends POJO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String label;
  private String description;
  private boolean root;

  public Purpose() {
    this(null, null, null);
  }

  public Purpose(final String label, final String description, final String id) {
    this(label, description, id, false);
  }

  public Purpose(final String label, final String description, final String id, final boolean root) {
    super(id);
    this.label = label;
    this.description = description;
    this.root = root;
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

  public boolean isRoot() {
    return this.root;
  }

  public void setRoot(final boolean root) {
    this.root = root;
  }
}
