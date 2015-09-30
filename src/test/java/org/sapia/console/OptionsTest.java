package org.sapia.console;

import org.junit.Before;
import org.junit.Test;

public class OptionsTest {
  
  private Options requiredOpt, nonRequiredOpt, mustHaveValueOpt, reqMustHaveValueOpt;

  @Before
  public void setUp() throws Exception {
    requiredOpt = Options.Builder.newInstance()
        .option().name("opt").required().buildOptions();
    
    mustHaveValueOpt = Options.Builder.newInstance()
        .option().name("opt").mustHaveValue().buildOptions();

    reqMustHaveValueOpt = Options.Builder.newInstance()
        .option().name("opt").mustHaveValue().required().buildOptions();
    

    nonRequiredOpt = Options.Builder.newInstance()
        .option().name("opt").buildOptions();
  }

  @Test
  public void testValidate_required() {
    requiredOpt.validate(CmdLine.parse("-opt"));
  }

  @Test(expected = InputException.class)
  public void testValidate_required_error() {
    requiredOpt.validate(CmdLine.parse("-someOpt"));
  }
  
  @Test(expected = InputException.class)
  public void testValidate_must_have_value_opt_null() {
    mustHaveValueOpt.validate(CmdLine.parse("-opt"));
  }
  
  @Test
  public void testValidate_must_have_value() {
    mustHaveValueOpt.validate(CmdLine.parse("-opt val"));
  }

  @Test(expected = InputException.class)
  public void testValidate_must_have_value_error() {
    reqMustHaveValueOpt.validate(CmdLine.parse("-opt"));
  }
  
  @Test
  public void testValidate_non_required() {
    nonRequiredOpt.validate(CmdLine.parse("-opt"));
  }

}
