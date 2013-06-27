package net.token.searcher;

public class TokenItem{
  private String url;
  private String image;
  private String content;
  private String logtime;
  //get link url
  public String getUrl(){
    return url;
  }
  //set link url
  public void setUrl(String u){
    url = u;
  }
  //get the content of dataItem
  public String getContent(){
    return content;
  }
  //set the content of dataItem
  public void setContent(String c){
    content = c;
  }
  //get the logtime
  public String getLogtime(){
    return logtime;
  }
  //set the logtime of the Item
  public void setLogtime(String l){
    logtime = l;
  }
  public String toString(){
    return url + "\n" + content + "\n" + logtime;
  }
  //set adn get the image:
  public String getImage() {
	return image;
  }
  public void setImage(String image) {
	this.image = image;
  }
}