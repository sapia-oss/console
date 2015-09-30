package org.sapia.console;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OptionTest {

  private Option opt;
  private Option nullOpt;
  
  @Before
  public void setUp() throws Exception {
    opt = new Option("test", "val");
    nullOpt = new Option("test");
  }

  @Test
  public void testGetValue() {
    assertEquals("val", opt.getValue());
  }
  
  @Test
  public void testGetValue_null() {
    assertNull(nullOpt.getValue());
  }

  @Test
  public void testGetSplitValue() {
    opt = new Option("test", "1,2,3");
    assertArrayEquals(new String[]{"1", "2", "3"}, opt.getSplitValue());
  
  }

  @Test
  public void testGetSplitValue_with_delim() {
    opt = new Option("test", "1;2;3");
    assertArrayEquals(new String[]{"1", "2", "3"}, opt.getSplitValue(';'));
  }

  @Test
  public void testGetValueNotNull() {
    assertEquals("val", opt.getValueNotNull());
  }
  
  @Test(expected = InputException.class)
  public void testGetValueNotNull_with_null() {
    nullOpt.getValueNotNull();
  }

  @Test
  public void testGetValueOrDefault_with_string() {
    assertEquals("val", opt.getValueOrDefault("default"));
  }
  
  @Test
  public void testGetValueOrDefault_null_with_string() {
    assertEquals("default", nullOpt.getValueOrDefault("default"));
  }

  @Test
  public void testGetValueOrDefault_with_boolean() {
    opt = new Option("test", "false");
    assertFalse(opt.getValueOrDefault(true));
  }
  
  @Test
  public void testGetValueOrDefault_null_with_boolean() {
    opt = new Option("test", null);
    assertFalse(opt.getValueOrDefault(false));
  }

  @Test
  public void testGetValueOrDefault_with_int() {
    opt = new Option("test", "1");
    assertEquals(1, opt.getValueOrDefault(2));
  }
  
  @Test
  public void testGetValueOrDefault_null_with_int() {
    opt = new Option("test");
    assertEquals(2, opt.getValueOrDefault(2));
  }

  @Test
  public void testAsInt() {
    opt = new Option("test", "1");
    assertEquals(1, opt.asInt());
  }

  @Test
  public void testAsBoolean() {
    opt = new Option("test", "false");
    assertFalse(opt.asBoolean());
  }
  
  @Test
  public void testAsBoolean_value_null() {
    opt = new Option("test");
    assertTrue(opt.asBoolean());
  }
  
  @Test
  public void testAsBoolean_value_yes() {
    opt = new Option("test", "yes");
    assertTrue(opt.asBoolean());
  }
  
  @Test
  public void testAsBoolean_value_y() {
    opt = new Option("test", "y");
    assertTrue(opt.asBoolean());
  }
  
  @Test
  public void testIsNull_false() {
    assertFalse(opt.isNull());
  }

  @Test
  public void testIsNull_true() {
    assertTrue(nullOpt.isNull());
  }
  
  @Test
  public void testIsSet_false() {
    assertFalse(nullOpt.isSet());
  }

  @Test
  public void testIsSet_true() {
    assertTrue(opt.isSet());
  }

  @Test
  public void testToString() {
    assertEquals("-test val", opt.toString());
  }
  
  @Test
  public void testToString_value_null() {
    assertEquals("-test", nullOpt.toString());
  }

}
