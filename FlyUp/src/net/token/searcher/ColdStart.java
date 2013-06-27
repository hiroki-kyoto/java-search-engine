package net.token.searcher;

import net.token.searcher.TokenItemMethod;

public class ColdStart{
  public static void main(String[] args)throws Exception{
    TokenItemMethod tim = new TokenItemMethod();
    tim.startDBCraw();
    //Thread.currentThread().sleep(5000);
    TokenLog tl = new TokenLog();
    tl.log("冷启动完成了！");
    System.out.println("cold start completed!");
  }
}