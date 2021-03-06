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
package org.psikeds.resolutionengine.datalayer.knowledgebase.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import org.psikeds.resolutionengine.datalayer.vo.FeatureValue;
import org.psikeds.resolutionengine.datalayer.vo.FloatFeatureValue;
import org.psikeds.resolutionengine.datalayer.vo.IntegerFeatureValue;
import org.psikeds.resolutionengine.datalayer.vo.RelationOperator;

public class ComparatorTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(ComparatorTest.class);

  private static String LOG4J;

  @BeforeClass
  public static void setUpBeforeClass() {
    BasicConfigurator.configure();
    LOG4J = System.getProperty("org.psikeds.test.log4j.xml", "../ResolutionEngine/src/main/resources/log4j.xml");
    DOMConfigurator.configure(LOG4J);
  }

  /**
   * Test method for FeatureValueComparator and FeatureValueHelper.
   */
  @Test
  public void test1_FeatureValueHelper() throws Exception {
    boolean ok = false;
    LOGGER.info("Starting Test 1: FeatureValueComparator and FeatureValueHelper ...");
    try {
      LOGGER.info("... creating Feature-Values ...");
      final FeatureValue fvi1 = new IntegerFeatureValue("FVI1", "FVI1", "1");
      final FeatureValue fvf1 = new FloatFeatureValue("FVF1", "FVF1", "1.000");
      final FeatureValue fvf2 = new FloatFeatureValue("FVF2", "FVF2", "2.000");
      final FeatureValue fvs2 = new FeatureValue("FVS2", "FVS2", "2.000");
      final FeatureValue fv3 = new IntegerFeatureValue("FV3", "FV3", "3");
      final FeatureValue fv4 = new FloatFeatureValue("FV4", "FV4", "4.0");
      final FeatureValue fv5 = new FeatureValue("FV5", "FV5", "5");
      final IntegerFeatureValue fvi6 = new IntegerFeatureValue("FVI6", "FVI6", "6");
      final FeatureValue fv7 = new FloatFeatureValue("FV7", "FV7", "7");
      final FeatureValue fv8 = new IntegerFeatureValue("FV8", "FV8", "8");
      final FloatFeatureValue fvf9 = new FloatFeatureValue("FVF9", "FVF9", "9.000003267");
      fvf9.setScale(2);
      LOGGER.trace("fvf9 = {}", fvf9);
      final FeatureValue fvs9 = new FeatureValue("FVS9", "FVS9", "9");
      final FeatureValue fv10 = new FeatureValue("FV10", "FV10", "10.000");
      LOGGER.info("... comparing single Feature-Values ...");
      assertTrue(FeatureValueHelper.isEqual(fvi1, fvf1));
      assertFalse(FeatureValueHelper.notEqual(fvi1, fvf1));
      assertTrue(FeatureValueHelper.isEqual(fvf2, fvs2));
      assertFalse(FeatureValueHelper.notEqual(fvf2, fvs2));
      assertTrue(FeatureValueHelper.isEqual(fvf9, fvs9));
      assertTrue(FeatureValueHelper.notEqual(fv3, fvf9));
      assertTrue(FeatureValueHelper.notEqual(fvs2, fv10));
      assertTrue(FeatureValueHelper.greaterThan(fv7, fv5));
      assertTrue(FeatureValueHelper.greaterOrEqual(fv7, fv5));
      assertTrue(FeatureValueHelper.lessThan(fv3, fvs9));
      assertTrue(FeatureValueHelper.lessOrEqual(fv3, fvs9));
      LOGGER.info("... creating and shuffling List of Feature-Values ...");
      final List<FeatureValue> lst = new ArrayList<FeatureValue>();
      lst.add(fvi1);
      lst.add(fvf2);
      lst.add(fv3);
      lst.add(fv4);
      lst.add(fv5);
      lst.add(fvi6);
      lst.add(fv7);
      lst.add(fv8);
      lst.add(fvf9);
      lst.add(fv10);
      FeatureValueHelper.shuffle(lst);
      assertEquals(10, lst.size());
      LOGGER.info("... reversing Order of List of Feature-Values ...");
      FeatureValueHelper.reverse(lst);
      assertEquals(10, lst.size());
      LOGGER.info("... getting minimum Feature-Value ...");
      final FeatureValue min = FeatureValueHelper.min(lst);
      LOGGER.trace("MIN = {}", min);
      assertNotNull(min);
      assertEquals(fvi1, min);
      LOGGER.info("... getting maximum Feature-Value ...");
      final FeatureValue max = FeatureValueHelper.max(lst);
      LOGGER.trace("MAX = {}", min);
      assertNotNull(max);
      assertEquals(fv10, max);
      LOGGER.info("... sorting List of Feature-Values ...");
      FeatureValueHelper.sort(lst);
      assertEquals(10, lst.size());
      LOGGER.info("... looking for certain Feature-Value ...");
      assertEquals(5, FeatureValueHelper.find(lst, fvi6));
      assertEquals(0, FeatureValueHelper.find(lst, fvi1));
      assertEquals(9, FeatureValueHelper.find(lst, fv10));
      LOGGER.info("... shuffling List of Feature-Values once again ...");
      FeatureValueHelper.shuffle(lst);
      assertEquals(10, lst.size());
      LOGGER.info("... comparing Lists and Value-Ranges ...");
      List<FeatureValue> result = FeatureValueHelper.isEqual(lst, new FloatFeatureValue("3", "3", "3"));
      assertEquals(1, result.size());
      assertTrue(result.contains(fv3));
      result = FeatureValueHelper.notEqual(lst, new FeatureValue("3", "3", "3.00"));
      assertEquals(9, result.size());
      assertFalse(result.contains(fv3));
      assertTrue(result.contains(fvi1));
      assertTrue(result.contains(fv10));
      result = FeatureValueHelper.lessThan(lst, new IntegerFeatureValue("4", "4", 4));
      assertEquals(3, result.size());
      assertTrue(result.contains(fv3));
      assertFalse(result.contains(fv4));
      assertFalse(result.contains(fv5));
      result = FeatureValueHelper.lessOrEqual(lst, new FeatureValue("4", "4", "4"));
      assertEquals(4, result.size());
      assertTrue(result.contains(fv3));
      assertTrue(result.contains(fv4));
      assertFalse(result.contains(fv5));
      result = FeatureValueHelper.greaterThan(lst, new FeatureValue("8", "8", "8"));
      assertEquals(2, result.size());
      assertTrue(result.contains(fv10));
      assertTrue(result.contains(fvf9));
      assertFalse(result.contains(fv8));
      assertFalse(result.contains(fv7));
      result = FeatureValueHelper.greaterOrEqual(lst, new FloatFeatureValue("8", "8", 8.000345f, 3));
      assertEquals(3, result.size());
      assertFalse(result.contains(fv7));
      assertTrue(result.contains(fv8));
      assertTrue(result.contains(fvf9));
      assertTrue(result.contains(fv10));
      LOGGER.info("... shuffling List of Feature-Values once again ...");
      FeatureValueHelper.shuffle(lst);
      assertEquals(10, lst.size());
      LOGGER.info("... checking Limits of Value-Range ...");
      final FeatureValue nothing = null;
      result = FeatureValueHelper.isEqual(lst, nothing);
      assertNull(result);
      result = FeatureValueHelper.notEqual(lst, nothing);
      assertNotNull(result);
      assertEquals(10, result.size());
      result = FeatureValueHelper.lessThan(lst, nothing);
      assertNull(result);
      result = FeatureValueHelper.greaterThan(lst, nothing);
      assertNotNull(result);
      assertEquals(10, result.size());
      final FeatureValue tooBig = new IntegerFeatureValue("TooBig", "TooBig", 999999);
      result = FeatureValueHelper.greaterThan(lst, tooBig);
      assertNull(result);
      result = FeatureValueHelper.lessThan(lst, tooBig);
      assertNotNull(result);
      assertEquals(10, result.size());
      result = FeatureValueHelper.lessOrEqual(lst, tooBig);
      assertNotNull(result);
      assertEquals(10, result.size());
      final FeatureValue tooSmall = new IntegerFeatureValue("TooSmall", "TooSmall", -99999);
      result = FeatureValueHelper.greaterOrEqual(lst, tooSmall);
      assertNotNull(result);
      assertEquals(10, result.size());
      result = FeatureValueHelper.greaterThan(lst, tooSmall);
      assertNotNull(result);
      assertEquals(10, result.size());
      result = FeatureValueHelper.lessThan(lst, tooSmall);
      assertNull(result);
      // done
      ok = true;
    }
    catch (final AssertionError ae) {
      ok = false;
      LOGGER.error("Functional Error: " + ae.getMessage(), ae);
      throw ae;
    }
    catch (final Throwable t) {
      ok = false;
      LOGGER.error("Technical Error: " + t.getMessage(), t);
      fail(t.getMessage());
    }
    finally {
      LOGGER.info("... Test 1: FeatureValueComparator and FeatureValueHelper finished " + (ok ? "without problems." : "with ERRORS!!!"));
    }
  }

  /**
   * Test method for RelationHelper.
   */
  @Test
  public void test2_RelationHelper() throws Exception {
    boolean ok = false;
    LOGGER.info("Starting Test 2: RelationHelper ...");
    try {
      LOGGER.info("... creating Feature-Values ...");
      final FeatureValue fvi1 = new IntegerFeatureValue("FVI1", "FVI1", "1");
      final FeatureValue fvf1 = new FloatFeatureValue("FVF1", "FVF1", "1.000");
      final FeatureValue fvf2 = new FloatFeatureValue("FVF2", "FVF2", "2.000");
      final FeatureValue fvs2 = new FeatureValue("FVS2", "FVS2", "2.000");
      final FeatureValue fv3 = new IntegerFeatureValue("FV3", "FV3", "3");
      final FeatureValue fv4 = new FloatFeatureValue("FV4", "FV4", "4.0");
      final FeatureValue fv5 = new FeatureValue("FV5", "FV5", "5");
      final IntegerFeatureValue fvi6 = new IntegerFeatureValue("FVI6", "FVI6", "6");
      final FeatureValue fv7 = new FloatFeatureValue("FV7", "FV7", "7");
      final FeatureValue fv8 = new IntegerFeatureValue("FV8", "FV8", "8");
      final FloatFeatureValue fvf9 = new FloatFeatureValue("FVF9", "FVF9", "9.000003267");
      fvf9.setScale(2);
      LOGGER.trace("fvf9 = {}", fvf9);
      final FeatureValue fvs9 = new FeatureValue("FVS9", "FVS9", "9");
      final FeatureValue fv10 = new FeatureValue("FV10", "FV10", "10.000");
      LOGGER.info("... applying Relation-Operator on single Feature-Values ...");
      assertTrue(RelationHelper.fulfillsOperation(fvi1, RelationOperator.EQUAL, fvf1));
      assertFalse(RelationHelper.fulfillsOperation(fvi1, RelationOperator.NOT_EQUAL, fvf1));
      assertTrue(RelationHelper.fulfillsOperation(fvf2, RelationOperator.EQUAL, fvs2));
      assertFalse(RelationHelper.fulfillsOperation(fvf2, RelationOperator.NOT_EQUAL, fvs2));
      assertTrue(RelationHelper.fulfillsOperation(fvf9, RelationOperator.EQUAL, fvs9));
      assertTrue(RelationHelper.fulfillsOperation(fv3, RelationOperator.NOT_EQUAL, fvf9));
      assertTrue(RelationHelper.fulfillsOperation(fvs2, RelationOperator.NOT_EQUAL, fv10));
      assertTrue(RelationHelper.fulfillsOperation(fv7, RelationOperator.GREATER_THAN, fv5));
      assertTrue(RelationHelper.fulfillsOperation(fv7, RelationOperator.GREATER_OR_EQUAL, fv5));
      assertTrue(RelationHelper.fulfillsOperation(fv3, RelationOperator.LESS_THAN, fvs9));
      assertTrue(RelationHelper.fulfillsOperation(fv3, RelationOperator.LESS_OR_EQUAL, fvs9));
      LOGGER.info("... creating and shuffling List of Feature-Values ...");
      final List<FeatureValue> lst = new ArrayList<FeatureValue>();
      lst.add(fvi1);
      lst.add(fvf2);
      lst.add(fv3);
      lst.add(fv4);
      lst.add(fv5);
      lst.add(fvi6);
      lst.add(fv7);
      lst.add(fv8);
      lst.add(fvf9);
      lst.add(fv10);
      FeatureValueHelper.shuffle(lst);
      assertEquals(10, lst.size());
      LOGGER.info("... applying Relation-Operator on Lists and Value-Ranges ...");
      List<FeatureValue> result = RelationHelper.fulfillsOperation(lst, RelationOperator.EQUAL, new FeatureValue("3", "3", "3"));
      assertEquals(1, result.size());
      assertTrue(result.contains(fv3));
      result = RelationHelper.fulfillsOperation(lst, RelationOperator.NOT_EQUAL, new FloatFeatureValue("3", "3", 3.0000432f, 3));
      assertEquals(9, result.size());
      assertFalse(result.contains(fv3));
      assertTrue(result.contains(fvi1));
      assertTrue(result.contains(fv10));
      result = RelationHelper.fulfillsOperation(lst, RelationOperator.LESS_THAN, new IntegerFeatureValue("4", "4", "4"));
      assertEquals(3, result.size());
      assertTrue(result.contains(fv3));
      assertFalse(result.contains(fv4));
      assertFalse(result.contains(fv5));
      result = RelationHelper.fulfillsOperation(lst, RelationOperator.LESS_OR_EQUAL, new FloatFeatureValue("4", "4", new BigDecimal("4.00")));
      assertEquals(4, result.size());
      assertTrue(result.contains(fv3));
      assertTrue(result.contains(fv4));
      assertFalse(result.contains(fv5));
      result = RelationHelper.fulfillsOperation(lst, RelationOperator.GREATER_THAN, new IntegerFeatureValue("8", "8", 8));
      assertEquals(2, result.size());
      assertTrue(result.contains(fv10));
      assertTrue(result.contains(fvf9));
      assertFalse(result.contains(fv8));
      assertFalse(result.contains(fv7));
      result = RelationHelper.fulfillsOperation(lst, RelationOperator.GREATER_OR_EQUAL, new FeatureValue("8", "8", "8.0"));
      assertEquals(3, result.size());
      assertFalse(result.contains(fv7));
      assertTrue(result.contains(fv8));
      assertTrue(result.contains(fvf9));
      assertTrue(result.contains(fv10));
      LOGGER.info("... shuffling List of Feature-Values once again ...");
      FeatureValueHelper.shuffle(lst);
      assertEquals(10, lst.size());
      LOGGER.info("... applying complementary Operators on Lists and Value-Ranges ...");
      result = RelationHelper.fulfillsOperation(new FeatureValue("3", "3", "3"), RelationOperator.EQUAL, lst);
      assertEquals(1, result.size());
      assertTrue(result.contains(fv3));
      result = RelationHelper.fulfillsOperation(new FloatFeatureValue("3", "3", "3"), RelationOperator.NOT_EQUAL, lst);
      assertEquals(9, result.size());
      assertFalse(result.contains(fv3));
      assertTrue(result.contains(fvi1));
      assertTrue(result.contains(fv10));
      result = RelationHelper.fulfillsOperation(new IntegerFeatureValue("4", "4", 4), RelationOperator.GREATER_THAN, lst);
      assertEquals(3, result.size());
      assertTrue(result.contains(fv3));
      assertFalse(result.contains(fv4));
      assertFalse(result.contains(fv5));
      result = RelationHelper.fulfillsOperation(new FeatureValue("4", "4", "4.0"), RelationOperator.GREATER_OR_EQUAL, lst);
      assertEquals(4, result.size());
      assertTrue(result.contains(fv3));
      assertTrue(result.contains(fv4));
      assertFalse(result.contains(fv5));
      result = RelationHelper.fulfillsOperation(new FeatureValue("8", "8", "8.0"), RelationOperator.LESS_THAN, lst);
      assertEquals(2, result.size());
      assertTrue(result.contains(fv10));
      assertTrue(result.contains(fvf9));
      assertFalse(result.contains(fv8));
      assertFalse(result.contains(fv7));
      result = RelationHelper.fulfillsOperation(new FloatFeatureValue("8", "8", new BigDecimal("8.0000")), RelationOperator.LESS_OR_EQUAL, lst);
      assertEquals(3, result.size());
      assertFalse(result.contains(fv7));
      assertTrue(result.contains(fv8));
      assertTrue(result.contains(fvf9));
      assertTrue(result.contains(fv10));
      // done
      ok = true;
    }
    catch (final AssertionError ae) {
      ok = false;
      LOGGER.error("Functional Error: " + ae.getMessage(), ae);
      throw ae;
    }
    catch (final Throwable t) {
      ok = false;
      LOGGER.error("Technical Error: " + t.getMessage(), t);
      fail(t.getMessage());
    }
    finally {
      LOGGER.info("... Test 2: RelationHelper finished " + (ok ? "without problems." : "with ERRORS!!!"));
    }
  }

  /**
   * Test method for double-sided Comparison of FV-Lists.
   */
  @Test
  public void test3_FeatureValueList() throws Exception {
    boolean ok = false;
    LOGGER.info("Starting Test 3: Double-sided comparison of FeatureValue-Lists ...");
    try {
      LOGGER.info("... creating Feature-Values ...");
      final FeatureValue fv1 = new IntegerFeatureValue("FV1", "FV1", "1");
      final FeatureValue fv2 = new FloatFeatureValue("FV2", "FV2", 1.9999999999f, 3);
      final FeatureValue fv3 = new FloatFeatureValue("FV3", "FV3", "3.000");
      final FeatureValue fv4 = new FeatureValue("FV4", "FV4", "4");
      final FeatureValue fv5 = new IntegerFeatureValue("FV5", "FV5", 5);
      final FeatureValue fv6 = new FloatFeatureValue("FV6", "FVF6", 6.012345f, 2);
      final FeatureValue fv7 = new FloatFeatureValue("FV7", "FV7", "7");
      final FeatureValue fv8 = new FeatureValue("FV8", "FV8", "8.0000");
      final FeatureValue fv9 = new IntegerFeatureValue("FV9", "FV9", 9);
      final FeatureValue fv10 = new FeatureValue("FV10", "FV10", "10");
      LOGGER.info("... creating and shuffling Lists of Feature-Values ...");
      final List<FeatureValue> list1to3 = new ArrayList<FeatureValue>();
      list1to3.add(fv1);
      list1to3.add(fv2);
      list1to3.add(fv3);
      FeatureValueHelper.shuffle(list1to3);
      assertEquals(3, list1to3.size());
      final List<FeatureValue> list1to5 = new ArrayList<FeatureValue>();
      list1to5.add(fv1);
      list1to5.add(fv2);
      list1to5.add(fv3);
      list1to5.add(fv4);
      list1to5.add(fv5);
      FeatureValueHelper.shuffle(list1to5);
      assertEquals(5, list1to5.size());
      final List<FeatureValue> list4to7 = new ArrayList<FeatureValue>();
      list4to7.add(fv4);
      list4to7.add(fv5);
      list4to7.add(fv6);
      list4to7.add(fv7);
      FeatureValueHelper.shuffle(list4to7);
      assertEquals(4, list4to7.size());
      final List<FeatureValue> list6to10 = new ArrayList<FeatureValue>();
      list6to10.add(fv6);
      list6to10.add(fv7);
      list6to10.add(fv8);
      list6to10.add(fv9);
      list6to10.add(fv10);
      FeatureValueHelper.shuffle(list6to10);
      assertEquals(5, list6to10.size());
      final List<FeatureValue> nothing = null;
      LOGGER.info("... comparing FeatureValue-Lists ...");
      List<FeatureValue> result = RelationHelper.fulfillsOperation(list1to5, RelationOperator.EQUAL, list4to7);
      assertNotNull(result);
      assertEquals(2, result.size());
      assertTrue(result.contains(fv4));
      assertTrue(result.contains(fv5));
      result = RelationHelper.fulfillsOperation(list6to10, RelationOperator.EQUAL, nothing);
      assertNotNull(result);
      assertEquals(0, result.size());
      result = RelationHelper.fulfillsOperation(list1to3, RelationOperator.EQUAL, list6to10);
      assertNotNull(result);
      assertEquals(0, result.size());
      result = RelationHelper.fulfillsOperation(list1to5, RelationOperator.NOT_EQUAL, list4to7);
      assertNotNull(result);
      assertEquals(3, result.size());
      assertTrue(result.contains(fv1));
      assertTrue(result.contains(fv2));
      assertTrue(result.contains(fv3));
      result = RelationHelper.fulfillsOperation(list4to7, RelationOperator.NOT_EQUAL, list4to7);
      assertNotNull(result);
      assertEquals(0, result.size());
      result = RelationHelper.fulfillsOperation(list6to10, RelationOperator.NOT_EQUAL, nothing);
      assertNotNull(result);
      assertEquals(5, result.size());
      result = RelationHelper.fulfillsOperation(list1to5, RelationOperator.LESS_THAN, list6to10);
      assertNotNull(result);
      assertEquals(5, result.size());
      assertTrue(result.contains(fv1));
      assertTrue(result.contains(fv5));
      assertFalse(result.contains(fv6));
      assertFalse(result.contains(fv10));
      result = RelationHelper.fulfillsOperation(list1to5, RelationOperator.LESS_OR_EQUAL, list1to3);
      assertNotNull(result);
      assertEquals(3, result.size());
      assertTrue(result.contains(fv1));
      assertTrue(result.contains(fv2));
      assertTrue(result.contains(fv3));
      result = RelationHelper.fulfillsOperation(list6to10, RelationOperator.GREATER_THAN, list1to5);
      assertNotNull(result);
      assertEquals(5, result.size());
      assertEquals(5, result.size());
      assertTrue(result.contains(fv6));
      assertTrue(result.contains(fv10));
      assertFalse(result.contains(fv1));
      assertFalse(result.contains(fv5));
      result = RelationHelper.fulfillsOperation(list1to5, RelationOperator.GREATER_OR_EQUAL, list4to7);
      assertNotNull(result);
      assertEquals(2, result.size());
      assertTrue(result.contains(fv4));
      assertTrue(result.contains(fv5));
      LOGGER.info("... testing Relations on String-Values ...");
      final FeatureValue azure = new FeatureValue("azure", "azure", "azure");
      final FeatureValue pink = new FeatureValue("pink", "pink", "pink");
      final FeatureValue ivory = new FeatureValue("ivory", "ivory", "ivory");
      final FeatureValue white = new FeatureValue("white", "white", "white");
      final List<FeatureValue> color1 = new ArrayList<FeatureValue>();
      color1.add(azure);
      color1.add(pink);
      color1.add(ivory);
      FeatureValueHelper.shuffle(color1);
      final List<FeatureValue> color2 = new ArrayList<FeatureValue>();
      color2.add(pink);
      color2.add(ivory);
      color2.add(white);
      FeatureValueHelper.shuffle(color2);
      result = RelationHelper.fulfillsOperation(color1, RelationOperator.EQUAL, color2);
      assertNotNull(result);
      assertEquals(2, result.size());
      assertFalse(result.contains(azure));
      assertTrue(result.contains(pink));
      assertTrue(result.contains(ivory));
      assertFalse(result.contains(white));
      result = RelationHelper.fulfillsOperation(color1, RelationOperator.NOT_EQUAL, color2);
      assertNotNull(result);
      assertEquals(1, result.size());
      assertTrue(result.contains(azure));
      assertFalse(result.contains(pink));
      assertFalse(result.contains(ivory));
      assertFalse(result.contains(white));
      result = RelationHelper.fulfillsOperation(color2, RelationOperator.NOT_EQUAL, color1);
      assertNotNull(result);
      assertEquals(1, result.size());
      assertFalse(result.contains(azure));
      assertFalse(result.contains(pink));
      assertFalse(result.contains(ivory));
      assertTrue(result.contains(white));
      // done
      ok = true;
    }
    catch (final AssertionError ae) {
      ok = false;
      LOGGER.error("Functional Error: " + ae.getMessage(), ae);
      throw ae;
    }
    catch (final Throwable t) {
      ok = false;
      LOGGER.error("Technical Error: " + t.getMessage(), t);
      fail(t.getMessage());
    }
    finally {
      LOGGER.info("... Test 3: Double-sided comparison of FeatureValue-Lists finished " + (ok ? "without problems." : "with ERRORS!!!"));
    }
  }
}
