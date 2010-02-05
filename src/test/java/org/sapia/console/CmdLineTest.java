package org.sapia.console;

import junit.framework.*;


/**
 * @author Yanick Duchesne
 * 5-May-2003
 */
public class CmdLineTest extends TestCase {
  CmdLine _cmd;

  /**
   * Constructor for CmdLineTest.
   */
  public CmdLineTest(String name) {
    super(name);
  }

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    _cmd = CmdLine.parse(
        "arg1 arg2 -opt1 val1 arg3 -opt2 val2 -opt3 \"opt3 value\" -opt4 -opt5");
  }

  public void testFirst() throws Exception {
    super.assertEquals("arg1", ((Arg) _cmd.first()).getName());
  }

  public void testLast() throws Exception {
    super.assertEquals("opt5", ((Option) _cmd.last()).getName());
  }

  public void testFilterArgs() throws Exception {
    CmdLine args = _cmd.filterArgs();
    super.assertEquals(3, args.size());
  }

  public void testAssertNextArg() throws Exception {
    CmdLine args = _cmd.filterArgs();

    while (args.hasNext()) {
      args.assertNextArg();
    }
  }

  public void testAssertArgs() throws Exception {
    CmdLine args = _cmd.filterArgs();
    args.assertNextArg(new String[] { "arg1", "arg2", "arg3" });
    args.assertNextArg(new String[] { "arg1", "arg2", "arg3" });
    args.assertNextArg(new String[] { "arg1", "arg2", "arg3" });

    try {
      args.assertNextArg(new String[] { "arg1", "arg2", "arg3" });
      throw new Exception("InputException should have been thrown");
    } catch (InputException e) {
      // ok
    }
  }

  public void testContainsOption() throws Exception {
    super.assertTrue("Option missing", _cmd.containsOption("opt4", false));
    super.assertTrue("Option should not have been found",
      !_cmd.containsOption("opt4", true));
  }

  public void testAssertOption() throws Exception {
    _cmd.assertOption("opt3", true);
    _cmd.assertOption("opt2", "val2");
    _cmd.assertOption("opt2", new String[] { "val1", "val2", "val3" });
  }

  public void testChop() throws Exception {
    int size = _cmd.size();
    _cmd.chop();
    super.assertEquals(size - 1, _cmd.size());
  }

  public void testChopArg() throws Exception {
    super.assertEquals("arg1", _cmd.chopArg().getName());
  }
}
