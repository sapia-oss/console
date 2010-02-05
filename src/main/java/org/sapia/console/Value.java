package org.sapia.console;

public class Value {
  
  private String _data;
  
  Value(String data){
    _data = data;
  }
  
  public String get(){
    if(_data == null){
      throw new IllegalStateException("Data is null");
    }
    return _data;
  }
  
  public boolean isNull(){
    return _data == null || _data.length() == 0;
  }
  
  public boolean isNotNull(){
    return _data != null && _data.length() > 0;
  }  
  
  public boolean isYes(){
    return isNotNull() && 
    (_data.equalsIgnoreCase("y") ||
     _data.equalsIgnoreCase("yes") || 
     _data.equalsIgnoreCase("true") ||
     _data.equalsIgnoreCase("on"));
  }
  
  public int asInt(){
    return Integer.parseInt(get());
  }
  
  public float asFloat(){
    return Float.parseFloat(get());
  }  

  public long asLong(){
    return Long.parseLong(get());
  }  

  public double asDouble(){
    return Long.parseLong(get());
  }  

}
