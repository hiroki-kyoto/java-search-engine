package net.token.searcher;

public class AutoArrayUnit<T>{
  private T data;
  private AutoArrayUnit<T> prevNode = null;
  private AutoArrayUnit<T> nextNode = null;
  public AutoArrayUnit(T dt){
    data = dt;
  }
  public AutoArrayUnit<T> setData(T dt){
    data = dt;
    return this;
  }
  public T getData(){
    return data;
  }
  public boolean hasNext(){
    if(nextNode!=null){return true;}
    else{return false;}
  }
  public boolean hasPrev(){
    if(prevNode!=null){return true;}
    else{return false;}
  }
  public AutoArrayUnit<T> setNext(AutoArrayUnit<T> aau){
    nextNode = aau;
    return this;
  }
  public AutoArrayUnit<T> setPrev(AutoArrayUnit<T> aau){
    prevNode = aau;
    return this;
  }
  public AutoArrayUnit<T> next(){
    return nextNode;
  }
  public AutoArrayUnit<T> prev(){
    return prevNode;
  }
  public AutoArrayUnit<T> breakFromList(){
    prevNode = null;
    nextNode = null;
    return this;
  }
}