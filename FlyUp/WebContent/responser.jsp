<%@page language="java" contentType="text/html" pageEncoding="utf-8" %>
<%@page import="net.token.searcher.WebSearch" %>
<%@page import="java.io.*" %>

<%
	String qs = request.getParameter("q");
	
	if(qs==null||qs.equals("")){out.print("");}
	else{
		byte[] b = qs.getBytes("ISO8859_1");
		qs = new String(b, "UTF-8");
		WebSearch ws = new WebSearch();
		String res = ws.Search(qs);
		out.print(res);
	}
%>