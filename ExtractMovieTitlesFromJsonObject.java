package com.github;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;


import com.google.gson.*;

public class ExtractMovieTitlesFromJsonObject {

			  static  String jsonLine = "https://jsonmock.hackerrank.com/api/movies/search/";
			  static List<String> titles = new ArrayList<String>();
			  
			  /**
				 * Extract movie titles from the rest api url given
				 * @param Title and page
				 * @author gunjan
				 */
			  
			  public static void main(String[] args) throws IOException {		
				  String _subStr = "spiderman";
				 
				  String[] res = getMovieTitles(_subStr);
			
				  for(int i=0;i<res.length;i++) 
				     System.out.println(res[i]);				  
			  }

			
			public static String[] getMovieTitles(String str) throws IOException {
				URL url = new URL(jsonLine);
				String query = String.format("Title=%s",URLEncoder.encode(str, "UTF-8"));
				String newURL = url + "?" + query;

				JsonObject json1 = readJsonFromUrl(newURL);
			    int total_pages = json1.getAsJsonPrimitive("total_pages").getAsInt(); 

			    for(int i=1;i<=total_pages;i++) {
				    String param = String.format("page=%s",URLEncoder.encode(String.valueOf(i), "UTF-8"));
					String newQuery = newURL + "&" + param;
						  
					JsonObject json = readJsonFromUrl(newQuery);
					titles=subArrayTitles(json);
			    }
					  
				Collections.sort(titles);
				String[] result = new String[titles.size()];
				int count=0;
				for(String t1: titles) {
					  result[count++]=t1;
				}
				return result;
			  	
			}
			
			public static List<String> subArrayTitles(JsonObject json)  {
				  JsonArray arr = json.getAsJsonArray("data");
			      for(int i=0;i<arr.size();i++) {
			    	  JsonObject obj = arr.get(i).getAsJsonObject();
			    	  String t = obj.getAsJsonPrimitive("Title").getAsString();
			    	  titles.add(t);
			      }
			      return titles;
			}
			
			 public static JsonObject readJsonFromUrl(String url) throws IOException {
				    InputStream input = new URL(url).openStream();
				    try {
				      BufferedReader rd = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
				      String jsonText = readAll(rd);
				      JsonParser parser = new JsonParser();
				      JsonObject json = parser.parse(jsonText).getAsJsonObject();
				      return json;
				    } finally {
				    	input.close();
				    }
		     }
			 
			 private static String readAll(Reader rd) throws IOException {
				    StringBuilder sb = new StringBuilder();
				    int cp;
				    while ((cp = rd.read()) != -1) {
				      sb.append((char) cp);
				    }
				    return sb.toString();
				  }

	}



