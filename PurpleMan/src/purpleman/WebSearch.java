package purpleman;

import java.io.IOException;
import java.io.Reader;
import java.io.File;
import java.io.StringReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.wltea.analyzer.lucene.IKAnalyzer;

import purpleman.TokenDocument;

public class WebSearch{
  //analyzer for the user's input:
  public static String GetKeyWordSplitBySpace
    (Analyzer analyzer, String keyWords)throws IOException{
    String res = "";
    Reader reader = new StringReader(keyWords);
    TokenStream ts = (TokenStream)analyzer.tokenStream("", reader);
    TermAttribute termAtt = (TermAttribute)ts.addAttribute(TermAttribute.class);
    while(ts.incrementToken()){
      res = res + termAtt.term() + " ";
    }
    return res;
  }
  //analyze the web request and then response with a html page:
  public String Search(String KeyWords)
    throws Exception{
    
	//use ikanalyzer:
	if(KeyWords==null||KeyWords.equals("")){return "";}
    Analyzer analyzer = new IKAnalyzer();
    KeyWords =  GetKeyWordSplitBySpace(new IKAnalyzer(), KeyWords);
    
    IndexSearcher is = new IndexSearcher(FSDirectory.open(new File("indexFiles")), true);
    
    QueryParser parser = new QueryParser(Version.LUCENE_30, "content", analyzer);
    //in case of null pointer:
    if(KeyWords==null||KeyWords.equals("")){
    	is.close();
    	return null;
    }
    
    Query query = parser.parse(KeyWords);
    
    TopDocs topDocs = is.search(query, 100000);
    
    //Prepare a page to store the information collected from indexFiles:
    TokenPage tp = new TokenPage();
    
    //create a TokenDocument:
    TokenDocument td;
    
    ScoreDoc[] hits = topDocs.scoreDocs;
    tp.setHead(hits.length);
    
    for(int i=0; i<hits.length; i++){
      
    	//lucene lighter has a bug: 
    	//return null with nothing to highlight
      SimpleHTMLFormatter shf = new SimpleHTMLFormatter("&lt;FONT COLOR='#FF3300'&gt;", "&lt;/FONT&gt;");
      Highlighter hl = new Highlighter(shf, new QueryScorer(query));
      hl.setTextFragmenter(new SimpleFragmenter(120));
      
      //start get the information of each doc returned:
      int DocId = hits[i].doc;
      Document document = is.doc(DocId);
      td = new TokenDocument();
      td.addField("url", document.get("url"));
      
      //add high lighter part:
      String content = document.get("content");
      
      //get the image:
      String image = document.get("image");
      td.addField("image", image);
      
      String escapNull = "";
      
      if(!"".equals(content)&&content!=null){
        TokenStream ts = analyzer.tokenStream("content",
                                              new StringReader(content));
        escapNull = hl.getBestFragment(ts, content);
        if(escapNull != null){ content = escapNull; }
        else {
        	int len = (content.length()<100 ? content.length() : 100);
        	content = content.substring(0, len)+"бнбн";
        }
      }
      
      td.addField("content", content);
      td.addField("logtime", document.get("logtime"));
      tp.addDocument(td);
    }
    is.close();
    return tp.toString();
  }
}