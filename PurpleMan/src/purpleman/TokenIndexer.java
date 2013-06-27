package purpleman;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.StringBuffer;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class TokenIndexer{
  
	public void index()throws Exception{
    //find the path for storing the index files:
    String indexPath = "indexFiles";
    String docPath = "documents";
    File docFolder = new File(docPath);
    
    //if it is a directory then list the files under it:
    File[] docFiles = docFolder.listFiles();
    boolean isCreate = true;
    
    //now we have the folder, so get the directory of it:
    FSDirectory fsd = FSDirectory.open(new File(indexPath));
    
    //create a IndexWriter:
    IndexWriter iw = new IndexWriter(fsd, new IKAnalyzer(),
                                     isCreate, IndexWriter.MaxFieldLength.UNLIMITED);
    
    //get the data from stored origin documents:
    String temp = "", contemp ="";
    TokenItem ti = new TokenItem();
    BufferedReader br = null;
    StringBuffer sbu;
    Document doc;
    
    //everything ready, start the cycle:
    for(int i=0; i<docFiles.length; i++){
      if(docFiles[i].isFile()&&docFiles[i].getName().endsWith(".token")){
        
    	//System.out.println("File" + docFiles[i].getCanonicalPath()
        //+"is under indexing...");
        br = new BufferedReader(new FileReader(docFiles[i]));
        
        //for each of the item, we store it to a document:
        while((temp = br.readLine())!=null){
          ti.setUrl(temp);
          sbu = new StringBuffer("");
          while(!(contemp=br.readLine()).contains("::ENDOFITEM::")){
            sbu.append(contemp); 
            temp = contemp;
          }
          
          //extract the image URL refereed from Page URL:
          String imageLink = ImageExtrator.getImageLink(ti.getUrl(), sbu.toString());
          ti.setImage(imageLink);
          
          //remove the html tags:
          String text = HtmlFlagRemover.removeTags(sbu.toString());
          
          //set the content :
          ti.setContent(text);
          
          //set the logtime:
          ti.setLogtime(temp);
          
          //a patch of index documents:
          Field url = new Field("url", ti.getUrl(), Field.Store.YES, Field.Index.NO);
          Field image = new Field("image", ti.getImage(), Field.Store.YES, Field.Index.NO);
          Field content = new Field("content", ti.getContent(), Field.Store.YES, Field.Index.ANALYZED);
          Field logtime = new Field("logtime", ti.getLogtime(), Field.Store.YES, Field.Index.NOT_ANALYZED);
          
          doc = new Document();
          doc.add(url);
          doc.add(image);
          doc.add(content);
          doc.add(logtime);
          iw.addDocument(doc);
        }
      }
    }
    iw.optimize();
    iw.close();
    /****************
    //create a html file:
    File htmFile = new File("demo.htm");
    WebSearch ws = new WebSearch();
    FileWriter fw = new FileWriter(htmFile);
    fw.write(ws.Search("青春在武汉理工采访"));
    fw.close();
    *****************/
  }
}