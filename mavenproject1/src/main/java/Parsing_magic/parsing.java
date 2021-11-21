/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Parsing_magic;

import java.io.IOException;
import java.net.URL;
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
    
    private final static Pattern pattern = Pattern.compile("^\\S+");
    private static Elements names;
    
    public static Document getPage(String value) throws IOException{
        String url = value;
        Document page = Jsoup.parse(new URL(url), 30000);
        return page;
    }
    
   public static Elements getNames(String value) throws IOException{
       Document doc = getPage(value);
       names = doc.select("a[class=header]");
       names.forEach(name -> {
           System.out.println(name.text());
       });
       return names;
   }
   
   public static void getCreatorFromName(Elements value){
       names = value;
           names.forEach(name -> {
               Matcher matcher = pattern.matcher(name.text());
               if(matcher.find()){
               System.out.println(matcher.group());
           }
           });
           
   }
    
    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
        
       //getNames("https://www.regard.ru/catalog/group11000.htm");
       getCreatorFromName(getNames("https://www.regard.ru/catalog/group11000.htm"));
       /* for(int i = 2; i < 25; i++){
            System.out.println(getPage("https://www.regard.ru/catalog/group11000/page" + i + ".htm"));
        }*/
    }
}
    
