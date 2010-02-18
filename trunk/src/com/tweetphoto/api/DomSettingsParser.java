package com.tweetphoto.api;

import java.io.ByteArrayInputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

public class DomSettingsParser {
	
	String m_Xml;
	
	public Settings parse() {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Settings settings = new Settings();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            ByteArrayInputStream is = new ByteArrayInputStream(m_Xml.getBytes());
            
            Document dom   = builder.parse(is);
            NodeList items = dom.getElementsByTagName("ProfileSettings");
            
            for (int i=0;i<items.getLength();i++){
                Node item 			= items.item(i);
                NodeList properties = item.getChildNodes();
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    if (name.equalsIgnoreCase("DoNotTweetFavoritePhoto")){
                    	settings.m_doNotTweetFavoritePhoto = property.getFirstChild().getNodeValue().equalsIgnoreCase("true")?true:false; 
                    } else if (name.equalsIgnoreCase("Email")){
                    	settings.m_email = property.getFirstChild().getNodeValue(); 
                    } else if (name.equalsIgnoreCase("HideViewingPatterns")){
                    	settings.m_hideViewingPatterns = property.getFirstChild().getNodeValue().equalsIgnoreCase("true")?true:false; 
                    } else if (name.equalsIgnoreCase("HideVotes")){
                    	settings.m_hideVotes = property.getFirstChild().getNodeValue().equalsIgnoreCase("true")?true:false; 
                    } else if (name.equalsIgnoreCase("MapType")){
                    	settings.m_mapType = property.getFirstChild().getNodeValue(); 
                    } else if (name.equalsIgnoreCase("PIN")){
                    	settings.m_pin = Long.parseLong(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("ShortenUrl")){
                    	settings.m_shortenUrl = property.getFirstChild().getNodeValue().equalsIgnoreCase("true")?true:false; 
                    	} 
                    }
            }
        } catch (Throwable tr) {
        	System.out.println(tr.toString()); 
			tr.printStackTrace();
        } 
        return settings;
    }
}
