/*************************************************
  * ���ݿ�������������sql server
  * ���ݿ�������jdbc���ֵ��ļ���
  * �У�ץȡ���ݿ��е���Ŀ��Ϣ��
  * ��һ����ʽ�洢��documents��
  * �����У����̴洢Դ�ļ���io��
  * һ�����⣬ͬʱ��Ӵ�������
  * �ݿ��ѹ�������Բ�ȡ���̱߳�
  * ��������������Ŀǰ��õ���
  * ������ʽ��
  *                           --��
  *                           --2013.3.16
**************************************************/
package purpleman;

import java.sql.*;
import java.io.File;   
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileOutputStream; 
import java.io.PrintStream;
import java.util.Calendar;
import purpleman.TokenItem;
import purpleman.AutoArray;

public class TokenItemMethod{
	
  //get the backpack data from sqlserver2008
  //and store the dataItem to the document database
  public void getTokenItemsFromDataBase(String mssqlUrl, String username, 
                                  String password, String dbName, 
                                  String[] tableNames, String[] urlPrefixs,
                                  String docFolder, int itemCountLimit) 
    throws Exception{
    Connection con;
    //register:
    try{
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }
    catch(Exception e){TokenLog.log("happend in TokenItemMethod: Cannot find the sqlserver jdbc!�޷��ҵ���ص����ݿ�������");}
    //start connect the database.
    //sql server 2008 port:1434, address:127.0.0.1:1434
    //String url = "jdbc:sqlserver://localhost:1434;databaseName=campus;";
    //String docpath = "documents/article.txt";
    //get the documents storing path:
    File docPath;
    FileOutputStream out;
    PrintStream pr;
    StringBuffer buffer = new StringBuffer("");
    Calendar cal = Calendar.getInstance();
    int yea = cal.get(Calendar.YEAR);
    int mon = cal.get(Calendar.MONTH)+1;
    int day = cal.get(Calendar.DAY_OF_MONTH);
    int hou = cal.get(Calendar.HOUR);
    int min = cal.get(Calendar.MINUTE);
    String logTime = yea + "��" + mon + "��" + day + "��" + hou + "ʱ" + min +"��"; 
    
//Establish a connection
    try {
    	con = DriverManager.getConnection("jdbc:sqlserver://" + mssqlUrl + 
                                        ";databaseName=" + dbName, username, password);
      
    	for(int num=0; num<tableNames.length; num++){
    		TokenItem ti = new TokenItem();
    		ti.setLogtime(logTime);
    		String query = "SELECT * FROM " + tableNames[num];
      
    		try{
    			Statement stmt = con.createStatement();
    			ResultSet rs = stmt.executeQuery(query);
    			ResultSetMetaData rsmd=rs.getMetaData();
        
    			int size=rsmd.getColumnCount();
    			int itemNum = 0, fileNum=0;
        
    			while(rs.next()){
    				buffer = new StringBuffer("");
    				//for every %itemCountLimit% items, I let it make a new file to store the items
    				if(itemNum>=itemCountLimit){
    					itemNum=0;
    					fileNum++;
    				}
    				itemNum++;
    				//get url of this documents link
    				ti.setUrl(urlPrefixs[num] + rs.getString(1));
    				//get cotent of the document
    				for(int i=2; i<=size; i++){
    					buffer.append(rs.getString(i));
    				}
    				ti.setContent(buffer.toString());
    				docPath = new File(docFolder + "/" + tableNames[num] + fileNum +".token");
    				out = new FileOutputStream(docPath);
    				pr = new PrintStream(out);
    				pr.print(ti.toString()+"\n::ENDOFITEM::\n");
    				pr.close();
    			}
    			con.close();
    		} 
    		catch (SQLException e) {
    			TokenLog.log("Error in Statement! " + e.getMessage());
    		} 
    	}
    } 
    catch (SQLException e) {
     TokenLog.log("Could not connect! " + e.getMessage());
    }
  }
  //��������info�ļ����µ������ĵ����õ��������ݿ����Ϣ
  public void startDBCraw() throws Exception{
    String pathPrefix = "info/";
    String pathAfter = ".token";
    //create the FileReader:
    FileReader fr;
    //next we need a BufferedReader:
    BufferedReader br;
    //���·ֱ��ȡ��ص����ݴ�����ļ��£�
    //get the information of sqlserverUrl:
    File infoFile = new File(pathPrefix + "SqlserverUrl" + pathAfter);
    fr = new FileReader(infoFile);
    br = new BufferedReader(fr);
    String SqlserverUrl = br.readLine();
    br.close();
    fr.close();
    //get the information of username:
    infoFile = new File(pathPrefix + "Username" + pathAfter);
    fr = new FileReader(infoFile);
    br = new BufferedReader(fr);
    String Username = br.readLine();
    br.close();
    fr.close();
    //get the information of password:
    infoFile = new File(pathPrefix + "Password" + pathAfter);
    fr = new FileReader(infoFile);
    br = new BufferedReader(fr);
    String Password = br.readLine();
    br.close();
    fr.close();
    //get the documents storing path:
    infoFile = new File(pathPrefix + "DocFolder" + pathAfter);
    fr = new FileReader(infoFile);
    br = new BufferedReader(fr);
    String DocFolder = br.readLine();
    br.close();
    fr.close();
    //get the ItemCountLimit:
    infoFile = new File(pathPrefix + "ItemCountLimit" + pathAfter);
    fr = new FileReader(infoFile);
    br = new BufferedReader(fr);
    int ItemCountLimit = Integer.parseInt(br.readLine());
    br.close();
    fr.close();
    //get the databaseList to craw:
    infoFile = new File(pathPrefix + "DatabaseList" + pathAfter);
    fr = new FileReader(infoFile);
    br = new BufferedReader(fr);
    AutoArray<String> aa = new AutoArray<String>();
    String[] DatabaseList;
    String t;
    while((t=br.readLine())!=null){
      aa.push(t);
    }
    br.close();
    fr.close();
    DatabaseList = new String[aa.length()-1];
    for(int k=0; k<DatabaseList.length; k++){
      DatabaseList[k] = aa.pop();
    }
    
    //according to the databaseList, 
    //we need a iterator to get the things on the lis done:
    for(int i=0; i<DatabaseList.length; i++){
      //get the dbName:
      infoFile = new File(pathPrefix + DatabaseList[i] + "/dbName" + pathAfter);
      fr = new FileReader(infoFile);
      br = new BufferedReader(fr);
      String dbName = br.readLine();
      br.close();
      fr.close();
      //get the tableNames List:
      //use the same method above:
      infoFile = new File(pathPrefix + DatabaseList[i] + "/tableNames" + pathAfter);
      fr = new FileReader(infoFile);
      br = new BufferedReader(fr);
      String[] tableNames;
      while((t=br.readLine())!=null){
        aa.push(t);
      }
      br.close();
      fr.close();
      tableNames = new String[aa.length()-1];
      for(int k=0; k<tableNames.length; k++){
      tableNames[k] = aa.pop();
    }
      //get the urlPrefixs List:
      infoFile = new File(pathPrefix + DatabaseList[i] + "/urlPrefixs" + pathAfter);
      fr = new FileReader(infoFile);
      br = new BufferedReader(fr);
      String[] urlPrefixs;
      while((t=br.readLine())!=null){
        aa.push(t);
      }
      urlPrefixs = new String[aa.length()-1];
      for(int k=0; k<urlPrefixs.length; k++){
        urlPrefixs[k] = aa.pop();
      }
      br.close();
      fr.close();
      //fill those data to the args list and start getting database information
      getTokenItemsFromDataBase(SqlserverUrl, Username, Password, dbName, 
                                tableNames, urlPrefixs, DocFolder, ItemCountLimit);
    }
    TokenIndexer ti = new TokenIndexer();
  	ti.index();
  }
}








