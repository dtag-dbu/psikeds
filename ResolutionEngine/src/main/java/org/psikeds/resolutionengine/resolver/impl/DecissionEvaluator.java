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
package org.psikeds.resolutionengine.resolver.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cxf.common.util.StringUtils;

import org.psikeds.resolutionengine.interfaces.pojos.Choice;
import org.psikeds.resolutionengine.interfaces.pojos.Decission;
import org.psikeds.resolutionengine.interfaces.pojos.Knowledge;
import org.psikeds.resolutionengine.interfaces.pojos.KnowledgeEntity;
import org.psikeds.resolutionengine.interfaces.pojos.Metadata;
import org.psikeds.resolutionengine.interfaces.pojos.Variant;
import org.psikeds.resolutionengine.resolver.ResolutionException;
import org.psikeds.resolutionengine.resolver.Resolver;

/**
 * Based on the made Decission this Resolver will reduce the corresponding
 * Choice to just one Variant so that the AutoCompletion-Resolver afterwards
 * constructs new Knowledge-Entities for it.
 * If no Decission is supplied, nothing happens. Metadata will be ignored.
 * 
 * @author marco@juliano.de
 */
public class DecissionEvaluator implements Resolver {

  private static final Logger LOGGER = LoggerFactory.getLogger(DecissionEvaluator.class);

  /**
   * @param knowledge
   *          current old Knowledge
   * @param decission
   *          Decission (can be null)
   * @param metadata
   *          Metadata (ignored)
   * @return Knowledge resulting new Knowledge
   * @throws ResolutionException
   *           if any error occurs
   */
  @Override
  public Knowledge resolve(final Knowledge knowledge, final Decission decission, final Metadata metadata) throws ResolutionException {
    boolean ok = false;
    boolean found = false;
    try {
      LOGGER.debug("Evaluating made Decission ...");
      if (decission != null) {
        if (StringUtils.isEmpty(decission.getPurposeID()) || StringUtils.isEmpty(decission.getVariantID())) {
          final String errmsg = String.format("Cannot evaluate, illegal Decission: %s", decission);
          LOGGER.warn(errmsg);
          throw new ResolutionException(errmsg);
        }
        found = updateKnowledge(knowledge, decission, metadata);
      }
      else {
        LOGGER.debug("Skipping Evaluation, Decission is null.");
      }
      ok = true;
      return knowledge;
    }
    finally {
      LOGGER.debug("... finished evaluating made Decission. " + (found ? "" : "NOT") + " found, " + (ok ? "OK." : "ERROR!"));
    }
  }

  private boolean updateKnowledge(final Knowledge knowledge, final Decission decission, final Metadata metadata) throws ResolutionException {
    boolean found = false;
    try {
      LOGGER.trace("--> updateKnowledge: Knowledge = {}\nDecission = {}", knowledge, decission);
      if (knowledge == null) {
        final String errmsg = "Cannot evaluate Decission: Knowledge is null!";
        LOGGER.warn(errmsg);
        throw new ResolutionException(errmsg);
      }
      final List<Choice> choices = knowledge.getChoices();
      found = updateChoices(choices, decission, metadata);
      for (final KnowledgeEntity ke : knowledge.getEntities()) {
        found = found | updateEntity(ke, decission, metadata);
      }
      return found;
    }
    finally {
      LOGGER.trace("<-- updateKnowledge: Found = {}\nKnowledge = {}\nDecission = {}", found, knowledge, decission);
    }
  }

  private boolean updateEntity(final KnowledgeEntity ke, final Decission decission, final Metadata metadata) throws ResolutionException {
    boolean found = false;
    try {
      LOGGER.trace("--> updateEntity: KnowledgeEntity = {}", ke);
      found = updateChoices(ke.getChoices(), decission, metadata);
      for (final KnowledgeEntity sibling : ke.getSiblings()) {
        found = found | updateEntity(sibling, decission, metadata);
      }
      return found;
    }
    finally {
      LOGGER.trace("<-- updateEntity: Found = {}\nKnowledgeEntity = {}", found, ke);
    }
  }

  private boolean updateChoices(final List<Choice> choices, final Decission decission, final Metadata metadata) throws ResolutionException {
    boolean found = false;
    try {
      LOGGER.trace("--> updateChoices: Choices = {}", choices);
      for (final Choice c : choices) {
        final Variant v = c.matches(decission);
        if (v != null) {
          c.setVariant(v);
          found = true;
          decissionMessage(metadata, decission, c);
        }
      }
      return found;
    }
    finally {
      LOGGER.trace("<-- updateChoices: Found = {}\nChoices = {}", found, choices);
    }
  }

  private void decissionMessage(final Metadata metadata, final Decission decission, final Choice c) {
    final String key = String.format("Decission%d", System.currentTimeMillis());
    final String msg = String.format("Found Choice matching Decission.\nDecission = %s\nChoice = %s", decission, c);
    LOGGER.debug(msg);
    metadata.saveInfo(key, msg);
  }
}