package org.sapia.console;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class CmdLineTest {
  CmdLine _cmd;

  @Before
  public void setUp() throws Exception {
    _cmd = CmdLine.parse(
        "arg1 arg2 -opt1 val1 arg3 -opt2 val2 -opt3 \"opt3 value\" -opt4 -opt5");
  }

  public void testFirst() throws Exception {
    assertEquals("arg1", ((Arg) _cmd.first()).getName());
  }

  @Test
  public void testLast() throws Exception {
    assertEquals("opt5", ((Option) _cmd.last()).getName());
  }

  @Test
  public void testFilterArgs() throws Exception {
    CmdLine args = _cmd.filterArgs();
    assertEquals(3, args.size());
  }

  @Test
  public void testAssertNextArg() throws Exception {
    CmdLine args = _cmd.filterArgs();

    while (args.hasNext()) {
      args.assertNextArg();
    }
  }

  @Test
  public void testAssertArgs() throws Exception {
    CmdLine args = _cmd.filterArgs();
    args.assertNextArg(new String[] { "arg1", "arg2", "arg3" });
    args.assertNextArg(new String[] { "arg1", "arg2", "arg3" });
    args.assertNextArg(new String[] { "arg1", "arg2", "arg3" });
  }
  
  @Test(expected = InputException.class)
  public void testAssertArgs_no_more_left() {
    CmdLine args = _cmd.filterArgs();
    args.assertNextArg(new String[] { "arg1", "arg2", "arg3" });
    args.assertNextArg(new String[] { "arg1", "arg2", "arg3" });
    args.assertNextArg(new String[] { "arg1", "arg2", "arg3" });
    
    args.assertNextArg(new String[] { "arg1", "arg2", "arg3" });
    
  }

  @Test
  public void testContainsOption() throws Exception {
    assertTrue("Option missing", _cmd.containsOption("opt4", false));
    assertTrue("Option should not have been found",
      !_cmd.containsOption("opt4", true));
  }

  @Test
  public void testAssertOption() throws Exception {
    _cmd.assertOption("opt3", true);
    _cmd.assertOption("opt2", "val2");
    _cmd.assertOption("opt2", new String[] { "val1", "val2", "val3" });
  }
  
  @Test
  public void testGetOpt() {
    assertEquals("val2", _cmd.getOpt("opt2").getValue());
  }
  
  @Test
  public void testGetOptNotNull() {
    assertEquals("val2", _cmd.getOpt("opt2").getValue());
  }
  
  @Test(expected = InputException.class)
  public void testGetOptNotNullFails() {
    _cmd.getOptNotNull("opt10").getValue();
  }
  
  @Test
  public void testGetSafeOpt() {
    assertTrue(_cmd.getSafeOpt("opt10").getValue() == null);
  }
  
  @Test
  public void testGetOptOrDefault() {
    assertEquals("default", _cmd.getOptOrDefault("opt10", "default").getValue());
  }

  @Test
  public void testChop() throws Exception {
    int size = _cmd.size();
    _cmd.chop();
    assertEquals(size - 1, _cmd.size());
  }

  @Test
  public void testChopArg() throws Exception {
    assertEquals("arg1", _cmd.chopArg().getName());
  }
}
