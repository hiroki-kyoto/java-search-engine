package net.token.searcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageExtrator {
	public static String getImageLink(String url, String html){
		String imageLink = "";
		
		Pattern p1 = Pattern.compile("<img (.+?)src=\"(.+?)\"");
		Pattern p2 = Pattern.compile("<img (.+?)src='(.+?)'");
		
		Matcher m = p1.matcher(html);
		
		if(m.find()){
			imageLink = UrlAnalyzer.getAbsoluteUrl(m.group(m.groupCount()), url);
			m.reset();
		}
		else {
			m = p2.matcher(html);
			if(m.find()){
				imageLink = UrlAnalyzer.getAbsoluteUrl(m.group(m.groupCount()), url);
			}
		}
		return imageLink;
	}
}
