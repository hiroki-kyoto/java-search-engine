package purpleman;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlFlagRemover {
	public static String removeTags(String html){
		
		Pattern p1 = Pattern.compile("<(.+?)>");
		Pattern p2 = Pattern.compile("</(.+?>)");
		
		Matcher m = p1.matcher(html);
		
		while(m.find()){
			html = html.replaceAll(m.group(0), " ");
		}
		m.reset();
		
		m = p2.matcher(html);
		while(m.find()){
			html = html.replaceAll(m.group(0), " ");
		}
		return html;
	}
}
