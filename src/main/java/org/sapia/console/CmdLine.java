package org.sapia.console;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Models a command-line. A command line is a list of arguments and options.
 * Options consist of a name/value combination, where value is optional. Arguments
 * have a name only. Options are delimited by a heading dash ('-'). The following
 * is parseable as a command line:
 * <p>
 * <pre>
 * start app -log debug "Hello World"
 * </pre>
 * <p>
 * This command line consists of two arguments (start and app) followed by
 * an option and its value (log/debug), and a trailing argument. Note that
 * if the <code>log</code> option would not have had a value specified,
 * <code>Hello World</code> would have been used as a value. In addition,
 * enclosing <code>Hello World</code> between quotes ensures that this
 * sentence is considered as a single argument. Without quotes, both
 * <code>Hello</code> and <code>World</code> would have been treated as
 * two separate arguments.
 * <p>
 * It is recommended that options that are not taking a mandatory value not
 * be followed by an argument.
 * <p>
 * <b>EXAMPLES</b>
 * <p>
 * An instance of this class can be created through parsing a command-line string:
 * <p>
 * <pre>
 * CmdLine cmd = CmdLine.parse("start app -log debug \"Hello World\"");
 * </pre>
 * <p>
 * It can also be created from an array of strings (such as specified by
 * <code>main</code> methods).
 * <p>
 * <pre>
 * ...
 * public static void main(String[] args){
 *  CmdLine cmd = CmdLine.fromArray(args);
 * }
 * ...
 * </pre>
 * <p>
 * Lastly, it can also be constructed programmatically:
 * <p>
 * <pre>
 * CmdLine cmd = new CmdLine().addArg("start").addArg("app").
 *   addOpt("log", "debug").addArg("\"Hello World\"");
 * </pre>
 * <p>
 * Instances of this class encapsulate <code>Arg</code> and <code>Option</code>
 * objects in a list. The list contains the objects in the order in which
 * they were added.
 * <p>
 * Once a CmdLine object is created, you can start a native process with it:
 * <pre>
 * ExecHandle handle = cmd.exec();
 *
 * // you can get the internal Process instance:
 * Process p = handle.getProcess();
 * // do stuff with it...
 *
 * // or you can acquire the process' input and error streams (corresponding
 * to that process' stderr and stdout):
 *
 * InputStream in  = handler.getInputStream();
 * InputStream err = handler.getErrStream();
 * </pre>
 * <p>
 * <b>IMPORTANT</b>: in any case, it is important that you dispose of
 * the ExecHandle cleanly, by calling its <code>close()</code> method, or
 * by calling the <code>close()</code> method on any of the streams that you
 * might have acquired from it:
 *
 * <pre>
 *   try{
 *     // do something with stream.
 *   }finally{
 *     in.close();
 *   }
 * </pre>
 * 
 * @author Yanick Duchesne
 */
public class CmdLine implements Cloneable {
  static final char SPACE    = ' ';
  static final char QUOTE    = '\"';
  static final char ESCAPE   = '\\';
  static final char DASH     = '-';
  private List<CmdElement>     _elems   = new ArrayList<CmdElement>();
  private Map<String, Option>  _options = new HashMap<String, Option>();
  private int       					 _index;

  public CmdLine() {
  }

  private CmdLine(List<CmdElement> elems, Map<String, Option> options) {
    _elems     = elems;
    _options   = options;
  }

  /**
   * Adds an argument with the given name.
   *
   * @param name the name of the argument to add.
   */
  public CmdLine addArg(String name) {
    return addArg(new Arg(name));
  }

  /**
   * Adds an argument with the given name.
   *
   * @param index the index at which to add the argument.
   * @param name the name of the argument to add.
   */
  public CmdLine addArg(int index, String name) {
    return addArg(index, new Arg(name));
  }

  /**
   * Adds an argument.
   *
   * @param arg an {@link Arg} instance.
   */
  public CmdLine addArg(Arg arg) {
    _elems.add(arg);

    return this;
  }

  /**
   * Adds an argument.
   *
   * @param index the index at which to add the argument.
   * @param arg an {@link Arg} instance.
   */
  public CmdLine addArg(int index, Arg arg) {
    _elems.add(index, arg);

    return this;
  }

  /**
   * Adds an option with the given name and value.
   *
   * @param name the name of the option to add.
   * @param value the value of the option.
   */
  public CmdLine addOpt(String name, String value) {
    Option opt = new Option(name, value);

    return addOpt(opt);
  }

  /**
   * Adds an option.
   *
   * @param opt an {@link Option}.
   */
  public CmdLine addOpt(Option opt) {
    _elems.add(opt);
    _options.put(opt.getName(), opt);

    return this;
  }

  /**
   * Returns the {@link CmdElement} at the specified index.
   *
   * @param i an index.
   * @return a {@link CmdElement}.
   */
  public CmdElement get(int i) {
    return _elems.get(i);
  }

  /**
   * Returns the last {@link CmdElement} in this command-line.
   * @return a {@link CmdElement}.
   */
  public CmdElement last() {
    return _elems.get(_elems.size() - 1);
  }

  /**
   * Returns the first {@link CmdElement} in this command-line.
   * @return a {@link CmdElement}.
   */
  public CmdElement first() {
    return _elems.get(0);
  }

  /**
   * Returns true if the {@link #next()}) method can be called (i.e.:
   * if the iteration can continue).
   *
   * @return <code>true</code> if the iteration can continue.
   */
  public boolean hasNext() {
    return _index < _elems.size();
  }

  /**
   * @return <code>true</code> if this instance as no options or arguments.
   */
  public boolean isEmpty() {
    return _elems.isEmpty();
  }
  
  /**
   * Returns the next command-line element (and internally increments
   * the iteration counter).
   *
   * @return a {@link CmdElement}.
   * 
   * @see #peek()
   */
  public CmdElement next() {
    return _elems.get(_index++);
  }
  
  /**
   * Returns the next command-line element, but does not increment
   * the iteration counter).
   * 
   * @return a {@link CmdElement}.
   * 
   * @see #next()
   */
  public CmdElement peek() {
    return _elems.get(_index);
  }

  /**
   * Returns true if the next element in this instance is an
   * {@link Arg} instance.
   * <p>
   * The {@link #hasNext()} method should be called priorly to ensure
   * that a "next" element is indeed present:
   * <p>
   * <pre>
   * if(cmdLine.hasNext()){
   *   if(cmdLine.isNextArg()){
   *     Arg arg = (Arg)cmdLine.next();
   *   }
   * }
   * </pre>
   */
  public boolean isNextArg() {
    return _elems.get(_index) instanceof Arg;
  }

  /**
   * Returns the next command-line element as an <code>Arg</code> instance.
   * This method internally makes sure that there is a "next" command-line
   * element, in that it is indeed an <code>Arg</code> instance. If these
   * conditions are met, the instance is returned; if not, an exception
   * is thrown.
   *
   * @throws InputException if no "next" argument exists.
   */
  public Arg assertNextArg() throws InputException {
    if (!hasNext() || !isNextArg()) {
      throw new InputException("Argument expected");
    }

    return (Arg) _elems.get(_index++);
  }

  /**
   * This method filters out all <code>Option</code> instances from its
   * internal list, keeping only <code>Arg</code> instances. Mine you,
   * the <code>Option</code> instances are still present in the returned
   * <code>CommandLine</code> instance (and so available for processing);
   * the difference is that successive calls to <code>next()</code>
   * will only return <code>Arg</code> instances. This is useful when the
   * iteration technique is used for processing arguments only, while
   * the <code>assertOption(...)</code> and <code>containsOption(...)</code>
   * methods are used for processing <code>Option</code> objects.
   *
   * @see #assertOption(String, String)
   * @see #assertOption(String, String[])
   * @see #containsOption(String, String)
   */
  public CmdLine filterArgs() {
    List<CmdElement> args = new ArrayList<CmdElement>();

    for (int i = 0; i < _elems.size(); i++) {
      if (_elems.get(i) instanceof Arg) {
        args.add((Arg) _elems.get(i));
      }
    }

    return new CmdLine(args, _options);
  }

  /**
   * Asserts that this instance contains a "next" argument whose name is
   * in the argument names passed as parameters.
   *
   * @return an <code>Arg</code> that corresponds one of the
   * passed in names.
   * @param argNames an array of argument names.
   * @throws InputException if no "next" argument with one of the given names
   * could be found.
   */
  public Arg assertNextArg(String[] argNames) throws InputException {
    if (!hasNext() || !isNextArg()) {
      throw new InputException("Argument expected");
    }

    Arg arg = (Arg) _elems.get(_index++);

    for (int i = 0; i < argNames.length; i++) {
      if (arg.getName().equals(argNames[i])) {
        return arg;
      }
    }

    StringBuffer buf = new StringBuffer();

    for (int i = 0; i < argNames.length; i++) {
      buf.append(argNames[i]);

      if (i < (argNames.length - 1)) {
        buf.append(" | ");
      }
    }

    throw new InputException("One of the following arguments expected: " +
      buf.toString());
  }

  /**
   * Returns <code>true</code> if the "next" command-line element is
   * an <code>Option</code> object.
   *
   * @return <code>true</code> if the next command-line element is
   * an option.
   */
  public boolean isNextOption() {
    return _elems.get(_index) instanceof Option;
  }

  /**
   * Asserts that an option with the given name exists and returns it.
   *
   * @return an {@link Option} that corresponds to the
   * passed in name.
   * @param hasValue indicates if the option must have a value. if <code>hasValue</code> is true, and
   * this instance has an {@link Option} for the given name but that option has no
   * value, this method throws an {@link InputException}.
   * @param optName the required option's name.
   * @throws InputException if no option with the given name
   * could be found.
   */
  public Option assertOption(String optName, boolean hasValue)
    throws InputException {
    Option o = _options.get(optName);
    
    if (o == null) {
      throw new InputException("Option '" + optName + "' not specified");
    }
    if (hasValue && o.getValue() == null) {
      throw new InputException("Option '" + optName + "' must have a value");
    }
  
    return o;
  }
  
  /**
   * @param optName an option name.
   * @return the {@link Option} with the given name, or <code>null</code> if no such option
   * exists.
   */
  public Option getOpt(String optName)  {
    Option opt = _options.get(optName);
    if (opt == null) {
      return null;
    }
    return opt;
  }
  
  
  /**
   * @param optName an option name.
   * @return the {@link Option} with the given name. If no such option is found, returns
   * a new {@link Option} whose value will be <code>null</code>. 
   */
  public Option getSafeOpt(String optName) {
    Option opt = _options.get(optName);
    if (opt == null) {
      return new Option(optName);
    }
    return opt;    
  }
  
  /**
   * Returns the {@link Option} with the given name, or throws an exception if such an option cannot
   * be found - the returned option may or may not have a value.
   * 
   * @param optName an option name.
   * @return the {@link Option} with the given name, or <code>null</code> if no such option
   * exists.
   * @throws InputException if no option with the given name could be found.
   */
  public Option getOptNotNull(String optName) {
    Option opt = _options.get(optName);
    if (opt == null) {
      throw new InputException("Option '" + optName + "' not specified");
    }
    return opt;    
  }
  
  /**
   * Returns the {@link Option} with the given name, or a new {@link Option} with the given default value if:
   * a) no option is found for the given name; or b) an option is found for the given name, but it has no value.
   * 
   * @param optName an option name.
   * @param defaultVal the default value to use, if no option corresonding to the given name is found,
   * or if one is found, but has no value.
   * @return
   */
  public Option getOptOrDefault(String optName, String defaultVal) {
    Option opt = _options.get(optName);
    if (opt == null || opt.getValue() == null) {
      return new Option(optName, defaultVal);
    } 
    return opt;
  }

  /**
   * Asserts that an option with the given name and value exists
   * and returns it.
   *
   * @param optName the required option's name.
   * @param value the required option's value.
   * @return an {@link Option} that corresponds to the
   * passed in name and value.
   * @throws InputException if no option with the given name and value
   * could be found.
   */
  public Option assertOption(String optName, String value)
    throws InputException {
    Option opt = assertOption(optName, true);

    if (!opt.getValue().equals(value)) {
      throw new InputException("Option '" + optName + "' expects value '" +
        value + "'");
    }

    return opt;
  }

  /**
   * Asserts that an option with the given name and one of the
   * given values exists and returns it.
   *
   * @param optName the required option's name.
   * @param values the required option's values.
   * @return an {@link Option} that corresponds to the
   * passed in name and one of the given values.
   * @throws InputException if no option with the given name and one
   * of the given values could be found.
   */
  public Option assertOption(String optName, String[] values)
    throws InputException {
    Option opt = assertOption(optName, true);

    for (int i = 0; i < values.length; i++) {
      if (opt.getValue().equals(values[i])) {
        return opt;
      }
    }

    StringBuffer buf = new StringBuffer();

    for (int i = 0; i < values.length; i++) {
      buf.append(values[i]);

      if (i < (values.length - 1)) {
        buf.append(" | ");
      }
    }

    throw new InputException("Option '" + optName +
      "' expects one of the following values: " + buf.toString());
  }

  /**
   * Returns <code>true</code> if this instance containes an
   * option with the given name.
   *
   * @param name the option's name.
   * @param hasValue indicates if the option must have a value. if <code>hasValue</code> is true, and
   * this instance has an {@link Option} for the given name but that option has no
   * value, this method returns <code>false</code>.
   * @return <code>true</code> if an option with the given name exists.
   */
  public boolean containsOption(String name, boolean hasValue) {
    Option opt = _options.get(name);

    if (opt == null) {
      return false;
    }

    if ((opt.getValue() == null) && hasValue) {
      return false;
    }

    return true;
  }
  
  /**
   * @param name the option's name.
   * @return <code>true</code> if this instance contains an option with the given name (whether the
   * options has a value or not).
   */
  public boolean containsOption(String name) {
    return _options.get(name) != null;
  }

  /**
   * Returns <code>true</code> if this instance containes an
   * option with the given name and value..
   *
   * @param name the option's name.
   * @param value the option's value.
   * @return <code>true</code> if an option with the given name and value exists.
   */
  public boolean containsOption(String name, String value) {
    Option opt = _options.get(name);

    if (opt == null) {
      return false;
    }

    return (opt.getValue() != null) && opt.getValue().equals(value);
  }
  
  /**
   * @return the {@link List} of {@link Option}s this instance holds.
   */
  public List<Option> getOptions() {
    List<Option> toReturn = new ArrayList<Option>();
    for (CmdElement e : _elems) {
      if (e instanceof Option) {
        toReturn.add((Option) e);
      }
    }
    return toReturn;
  }
  
  /**
   * @return the {@link List} of remaining {@link Option}s this instance holds.
   */
  public List<Option> getRemainingOptions() {
    List<Option> toReturn = new ArrayList<Option>();
    for (int i = _index; i < _elems.size(); i++) {
      CmdElement e = _elems.get(i);
      if (e instanceof Option) {
        toReturn.add((Option) e);
      }
    }
    return toReturn;
  }
  
  /**
   * Removes from this instance the option with the given name.
   * 
   * @param name the name of the option to remove.
   * @return this instance.
   */
  public CmdLine removeOption(String name) {
    for (int i = 0; i < _elems.size(); i++) {
      CmdElement e = _elems.get(i);
      if (e instanceof Option && e.getName().equals(name)) {
        _elems.remove(i);
        if (_index > 0) {
          _index--;
        }
      }
    }
    return this;
  }

  /**
   * Resets this instance's iteration counter to 0.
   */
  public void reset() {
    _index = 0;
  }

  /**
   * Adds a {@link CmdElement} instance.
   *
   * @param elem a {@link CmdElement} instance.
   */
  public void addElement(CmdElement elem) {
    if (elem instanceof Option) {
      addOpt((Option) elem);
    } else if (elem instanceof Arg) {
      addArg((Arg) elem);
    } else {
      throw new IllegalArgumentException("Argument must be an instance of " +
        Option.class.getName() + " or " + Arg.class.getName());
    }
  }

  /**
   * Removes the first element in this instance and returns it.
   */
  public CmdElement chop() {
    CmdElement elem = _elems.remove(0);

    if (_index > 0) {
      _index--;
    }

    return elem;
  }

  /**
   * Removes the first element in this instance and returns it as
   * an {@link Arg}, if it is such.
   */
  public Arg chopArg() {
    CmdElement elem = _elems.remove(0);

    if (_index > 0) {
      _index--;
    }

    if (elem instanceof Arg) {
      return (Arg) elem;
    } else {
      return null;
    }
  }

  /**
   * Returns the number of command-line elements in this instance.
   */
  public int size() {
    return _elems.size();
  }

  /**
   * Returns this instance as an array of strings, such as would be
   * passed to a <code>main()</code> method.
   *
   * @return an array of {@link String}s.
   */
  public String[] toArray() {
    List<String> all  = new ArrayList<String>();
    Option 			 opt;
    
    for (int i = 0; i < _elems.size(); i++) {
      if (_elems.get(i) instanceof Arg) {
        Arg arg = (Arg) _elems.get(i);

        if (arg.getName() != null) {
          all.add(arg.getName());
        }
      } else {
        opt = (Option) _elems.get(i);

        if (opt.getName() != null) {
          all.add("-" + opt.getName());

          if (opt.getValue() != null) {
            all.add(opt.getValue());
          }
        }
      }
    }

    return (String[]) all.toArray(new String[all.size()]);
  }

  /**
   * Returns this instance as an array of strings, such as would be
   * passed to a <code>main</code> method. This instance's options
   * are placed at the end of the array.
   *
   * @return an array of {@link String}s, with options at the end.
   */
  public String[] toArrayOptionsLast() {
    List<String>   all     = new ArrayList<String>();
    List<String>   options = new ArrayList<String>();
    Option opt;

    for (int i = 0; i < _elems.size(); i++) {
      if (_elems.get(i) instanceof Arg) {
        all.add(((Arg) _elems.get(i)).getName());
      } else {
        opt = (Option) _elems.get(i);
        options.add("-" + opt.getName());

        if (opt.getValue() != null) {
          options.add(opt.getValue());
        }
      }
    }

    all.addAll(options);

    return (String[]) all.toArray(new String[all.size()]);
  }

  /**
   * Returns this instance as an array of strings, such as would be
   * passed to a <code>main</code> method. This instance's options
   * are placed at the beginning of the array.
   *
   * @return an array of {@link String}s, with options at the beginning.
   */
  public String[] toArrayOptionsFirst() {
    List<String>   all     = new ArrayList<String>();
    List<String>   options = new ArrayList<String>();
    Option opt;

    for (int i = 0; i < _elems.size(); i++) {
      if (_elems.get(i) instanceof Arg) {
        all.add(((Arg) _elems.get(i)).getName());
      } else {
        opt = (Option) _elems.get(i);
        options.add("-" + opt.getName());

        if (opt.getValue() != null) {
          options.add(opt.getValue());
        }
      }
    }

    options.addAll(all);

    return (String[]) options.toArray(new String[options.size()]);
  }
  
  /**
   * Returns a clone of this instance.
   *
   * @return a {@link CmdLine} that is this instance's copy.
   */
  public Object clone() {
    return new CmdLine(new ArrayList<CmdElement>(_elems), new HashMap<String, Option>(_options));
  }

  /**
   * Returns this instance as a string.
   *
   * @return this instance as a {@link String}.
   */
  public String toString() {
    CmdElement   elem;
    StringBuffer buf = new StringBuffer();

    for (int i = 0; i < _elems.size(); i++) {
      elem = _elems.get(i);
      buf.append(elem.toString());

      if (i < (_elems.size() - 1)) {
        buf.append(SPACE);
      }
    }

    return buf.toString();
  }

  /**
   * Returns a {@link CmdLine} containing the given parameters. This
   * method is convenient to recuperate parameters from a main method.
   *
   * @return a {@link CmdLine} instance.
   */
  public static CmdLine parse(String[] args) {
    StringBuffer buf = new StringBuffer();

    for (int i = 0; i < args.length; i++) {
      buf.append(args[i]).append(' ');
    }

    return parse(buf.toString().trim());
  }

  /**
   * Executes this command-line. Returns the corresponding
   * {@link ExecHandle}, which wraps the executed process.
   *
   * @return {@link ExecHandle}
   */
  public ExecHandle exec() {
    CmdLineThread th = new CmdLineThread(this);
    th.start();

    return new ExecHandle(th);
  }

  /**
   * Executes this command-line. Returns the corresponding
   * {@link ExecHandle}, which wraps the executed process.
   *
   * @param env the environment variables, or <code>null</code> if no env. variables are to
   * be passed to the process.
   * @return {@link ExecHandle}
   */
  public ExecHandle exec(String[] env) {
    CmdLineThread th = new CmdLineThread(this, env);
    th.start();

    return new ExecHandle(th);
  }

  /**
   * Executes this command-line. Returns the corresponding
   * {@link ExecHandle}, which wraps the executed process.
   *
   * @param processDir the process base directory.
   * @param env the environment variables, or <code>null</code> if no env. variables are to
   * be passed to the process.
   * @return {@link ExecHandle}
   */
  public ExecHandle exec(File processDir, String[] env) {
    CmdLineThread th = new CmdLineThread(this, processDir, env);
    th.start();

    return new ExecHandle(th);
  }

  /**
   * Parses the given command-line and returns an instance of
   * this class.
   *
   * @return a {@link CmdLine} instance.
   */
  public static CmdLine parse(String line) {
    CmdLine      cmd = new CmdLine();

    char         c;
    StringBuffer buf   = new StringBuffer();
    boolean      quote = false;

    for (int i = 0; i < line.length(); i++) {
      c = line.charAt(i);

      if (c == SPACE) {
        if (quote) {
          buf.append(c);
        } else {
          addElem(cmd, buf);
        }
      } else if (c == QUOTE) {
        if ((i > 0) && (line.charAt(i - 1) == ESCAPE)) {
          buf.append(c);
        } else if (quote) {
          quote = false;
        } else {
          quote = true;
        }
      } else {
        buf.append(c);
      }
    }

    addElem(cmd, buf);

    return cmd;
  }

  private static void addElem(CmdLine cmd, StringBuffer buf) {
    if ((buf.length() > 0) && (buf.toString().charAt(0) == DASH)) {
      StringBuffer buf2 = new StringBuffer(buf.toString().substring(1));
      Option       opt = new Option(buf2.toString());
      cmd.addOpt(opt);
    } else if (buf.length() > 0) {
      if (cmd.size() > 0) {
        if (cmd.last() instanceof Option) {
          Option opt = (Option) cmd.last();

          if (opt.getValue() == null) {
            opt.setValue(buf.toString());
          } else {
            cmd.addArg(new Arg(buf.toString()));
          }
        } else {
          cmd.addArg(new Arg(buf.toString()));
        }
      } else {
        cmd.addArg(new Arg(buf.toString()));
      }
    }

    buf.delete(0, buf.length());
  }
}
