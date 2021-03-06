/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Parsing_magic;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author User
 */
public class parsing {
    
    private final static Pattern pattern1 = Pattern.compile("^\\S+");
    private final static Pattern pattern2 = Pattern.compile("[a-zA-Z]+");
    
    public static Document getPage(String value) throws IOException{
        String url = value;
        Document page = Jsoup.parse(new URL(url), 30000);
        return page;
    }
    
   public static Elements getText(String value) throws IOException{
       Document doc = getPage(value);
       Elements names = doc.select("a[class=header]");
       return names;
   }
   
   public static Elements getParameters(String value) throws IOException{
       Document doc = getPage(value);
       Elements param = doc.getElementsByClass("bcontent");
       return param;
   }
   
   public static ArrayList getTextParameters(Elements value) throws IOException{
       
       ArrayList<String> params = new ArrayList<String>();
       value.forEach(values -> {
           params.add(values.ownText());
           //System.out.println(values.ownText());
       });
       
       return params;
   }
   
    public static String getNames(String value) throws IOException{
       String prod_names = getText(value).text();
       /*names.forEach(name -> {
           System.out.println(name.text());
       });*/
       return prod_names;
   }
   
   public static String getCreatorFromName(Element value) throws Exception{
       Matcher matcher = pattern1.matcher(value.text());
       if(matcher.find())
           return matcher.group();
       throw new Exception("Cant extract date from string");
           /*names.forEach(name -> {
               Matcher matcher = pattern.matcher(name.text());
               if(matcher.find()){
               System.out.println(matcher.group());
           }
           });*/
   }
   
   public static Elements getLink(String value) throws IOException{
       
       Document doc = getPage(value);
       Elements link = doc.select("a[href]");
       
       return link;
       
   }
   
   public static String getTextLink(Element value) throws IOException{
       
       String prod_links = "https://www.regard.ru" + value.attr("href");
       return prod_links;
   }
}
    
