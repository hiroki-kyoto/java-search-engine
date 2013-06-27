package net.token.searcher;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlAnalyzer {
	//get the child url link:
	public static Vector<String> getChildUrl(String html, String url){
		Vector<String> urlList = new Vector<String>();
		Pattern pt = Pattern.compile("<a href=\"([\\S]+/?+)\"");
		Matcher mr = pt.matcher(html);
		while(mr.find()){
				if(mr.group(1).contains("#")||mr.group(1).contains("javascript:")){
					continue;
				}
				urlList.addElement(getAbsoluteUrl(mr.group(1), url));
				System.out.println(mr.group(1));
			
		}
		return urlList;
	}
	//get the referer:
	public static String getReferer(String url){
		String referer = "";
		if(url.contains("http://")){
			url = url.substring(7);
			int index = url.indexOf("/");
			if(index==-1){referer = "http://" + url;}
			else {
				referer = "http://" + url.substring(0, index);
			}
		}
		else {
			int index = url.indexOf("/");
			if(index==-1){referer = "http://" + url;}
			else {
				referer = "http://" + url.substring(0, index);
			}
		}
		return referer;
	}
	//get the current directory of web:
	public static String getCurrentDir(String url){
		String curDir = "";
		if(url.contains("http://")){
			url = url.substring(7);
			int index = url.lastIndexOf("/");
			if(index==-1){curDir = "http://"+url;}
			else{
				curDir = "http://" + url.substring(0, index);
			}
		}
		else {
			int index = url.lastIndexOf("/");
			if(index==-1){curDir = "http://"+url;}
			else {
				curDir = "http://" + url.substring(0, index);
			}
		}
		return curDir;
	}
	
	//resolve the absolute path:
	public static String getAbsoluteUrl(String relativeUrl, String currentUrl){
		//some words contains folder up and down:
		Pattern pt;
		Matcher ma;
		if(relativeUrl.contains("http://")){return relativeUrl;}
		else { 
			pt = Pattern.compile("(?:^/.+?)");
			ma = pt.matcher(relativeUrl);
			if(ma.matches()){System.out.print("good reguler! ");
				relativeUrl =  relativeUrl.substring(1);
				currentUrl = getCurrentDir(currentUrl);
			}
			else {
				pt = Pattern.compile("(?:^./.+?)");
				ma = pt.matcher(relativeUrl);
				if(ma.matches()){
					relativeUrl =  relativeUrl.substring(2);
					currentUrl = getCurrentDir(currentUrl);
				}
				else {
					pt = Pattern.compile("(^../.+?)");
					ma = pt.matcher(relativeUrl);
					if(ma.matches()){
						relativeUrl = relativeUrl.substring(3);
						currentUrl = getCurrentDir(currentUrl);
					}
				}
			}
		}
		return getCurrentDir(currentUrl) + "/" + relativeUrl;
	}
}
