package net.token.searcher;

import net.token.searcher.TokenItem;

public class TokenDocument{
  private String document = "";
  private TokenItem ti = new TokenItem();
  
  //add field to the document:
  public TokenDocument addField(String fieldName, String fieldContent){
    if(fieldName.equals("url")){
      ti.setUrl(fieldContent);
      return this; 
    }
    if(fieldName.equals("content")){
      ti.setContent(fieldContent);
      return this;
    }
    if(fieldName.equals("logtime")){
      ti.setLogtime(fieldContent);
      return this;
    }
    if(fieldName.equals("image")){
    	ti.setImage(fieldContent);
    	return this;
    }
    return this;
  }
  //print the document to the page:
  //use the xmlDocument type to provide data:
  public String toString(){
    document = "<item><url>" + ti.getUrl()
    		 + "</url><content>" + ti.getContent()
    		 + "</content><logtime>" + ti.getLogtime()
    		 + "</logtime><imageLink>" + ti.getImage()
    		 + "</imageLink></item>";
    return document;
  }
}