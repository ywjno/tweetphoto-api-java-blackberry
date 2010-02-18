package com.tweetphoto.api;

import java.io.ByteArrayInputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

public class DomFavoriteQueryResponseParser {
	
	String m_Xml;
	
	public FavoriteQueryResponse parse() {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        FavoriteQueryResponse response = new FavoriteQueryResponse();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
                        
            ByteArrayInputStream is = new ByteArrayInputStream(m_Xml.getBytes());
            
            Document dom   			= builder.parse(is);
            NodeList items 			= dom.getElementsByTagName("FavoriteQueryResponse");
            
            for (int i=0;i<items.getLength();i++){
                Node item 			= items.item(i);
                NodeList properties = item.getChildNodes();
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    if (name.equalsIgnoreCase("IsFavorite")){
                    	response.m_isFavorite =  property.getFirstChild().getNodeValue().equalsIgnoreCase("true") ? true : false; 
                    } else if (name.equalsIgnoreCase("PhotoId")){
                    	response.m_photoId = Long.parseLong(property.getFirstChild().getNodeValue()); 
                    }
                }
            }
        } catch (Throwable tr) {
        	System.out.println(tr.toString()); 
			tr.printStackTrace();
        } 
        return response;
    }
}
