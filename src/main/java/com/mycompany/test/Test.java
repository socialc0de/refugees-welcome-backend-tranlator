package com.mycompany.test;

import java.io.*;
import java.net.URLEncoder;
import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class Test {

    public static void main(String[] args) throws IOException {

        String api = "AIzaSyBksaHC12RDWOrx2mtcL7ncIRiY4PzK304";
        
        
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader brd = new BufferedReader(isr);
        String benutzereingabe = brd.readLine();
 
        String encode = URLEncoder.encode(benutzereingabe);
        String encoded = encode;

        HttpClient google = new HttpClient();
        HttpMethod method = new GetMethod("https://www.googleapis.com/language/translate/v2/detect?key=" + api + "&q=" + encoded);

        try {
            google.executeMethod(method);
        } catch (IOException ex) {
            System.out.println("Fehler!");
            return;
        }
        
        byte[] antwort = method.getResponseBody(); // stehen die eigentlichen daten

        String antwort_antwort = new String(antwort);
        int ausgelesen = antwort_antwort.indexOf("language");

        // Ermitteln und schneiden
        int pos = antwort_antwort.indexOf("language");
        String posTeil = antwort_antwort.substring(pos);
        pos = posTeil.indexOf("\"");                 // Anführungszeichen in nen Anführungszeichen (Escapen)
        posTeil = posTeil.substring(pos + 1);
        pos = posTeil.indexOf("\"");
        posTeil = posTeil.substring(pos + 1);
        pos = posTeil.indexOf("\"");
        String sprache = posTeil.substring(0, pos);

    //SCHLEIFE ANFANG 
        String[] zielsprachen = new String[]{"de", "it", "fr", "tr", "en", "es"};
     
        
        System.out.println(sprache + "," + benutzereingabe+ "\n");
        
        
        for (String zielsprache : zielsprachen) 
        {
            
            if (zielsprache.equals(sprache))
            {
                continue;
            }
            
            
            
            
            HttpClient spracheWeb = new HttpClient();
            HttpMethod methodWeb = new GetMethod("https://www.googleapis.com/language/translate/v2?key=" + api + "&q=" + encoded + "&source=" + sprache + "&target=" + zielsprache);

            try {
                google.executeMethod(methodWeb);
            } catch (IOException ex) {
                System.out.println("Keine Übersetzung wurde gefunden!");
                return;
            }
            
            byte[] antwortWeb = methodWeb.getResponseBody();

            String neueSprache;
            String Daten = new String(antwortWeb);

            pos = Daten.indexOf("translatedText");
            String Schnipp = Daten.substring(pos);
            pos = Schnipp.indexOf("\"");                 // Anführungszeichen in nen Anführungszeichen (Escapen)
            Schnipp = Schnipp.substring(pos + 1);
            pos = Schnipp.indexOf("\"");
            Schnipp = Schnipp.substring(pos + 1);
            pos = Schnipp.indexOf("\"");
            String übersetzung = Schnipp.substring(0, pos);

            
            System.out.println(zielsprache + "," +  übersetzung);
        }
    // SCHLEIFE ENDE

        
 

    }
}
