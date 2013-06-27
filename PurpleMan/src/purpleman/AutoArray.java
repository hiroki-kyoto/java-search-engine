package purpleman;

import purpleman.AutoArrayUnit;

public class AutoArray<T>{
  private int length = 1;
  private AutoArrayUnit<T> head = new AutoArrayUnit<T>(null);
  private AutoArrayUnit<T> tail = head;
  public int length(){
    return length;
  }
  public AutoArray<T> push(T data){
    AutoArrayUnit<T> aau = new AutoArrayUnit<T>(data);
    aau.setPrev(tail);
    tail.setNext(aau);
    tail = aau;
    length++;
    return this;
  }
  public T pop() throws Exception{
    if(length==1){
       TokenLog.log("Error happens in AutoArray: Array is already empty! Can't pop Element! --this kind of exception is safe, won't affect the main thread.");
    }
    T temp = tail.getData();
    tail = tail.prev();
    length--;
    return temp;
  }
}
