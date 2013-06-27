package purpleman;

import purpleman.TokenDocument;

public class TokenPage{
  private StringBuffer page = new StringBuffer("");
  
  public TokenPage setHead(int head){
    page.append("<page><count>" + head + "</count>");
    return this;
  }
  
  public TokenPage addDocument(TokenDocument td){
    page.append(td.toString());
    return this;
  }
  
  public String toString(){
	  page.append("</page>");
	  return page.toString();
  }
  
}