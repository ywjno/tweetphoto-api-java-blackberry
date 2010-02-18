package com.tweetphoto.api;

import java.io.ByteArrayInputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

public class DomTweetPhotoResponseParser {
	
	String m_Xml;
	
	public TweetPhotoResponse parse() {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        TweetPhotoResponse response = new TweetPhotoResponse();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            ByteArrayInputStream is = new ByteArrayInputStream(m_Xml.getBytes());
            
            Document dom   = builder.parse(is);
            NodeList items = dom.getElementsByTagName("SessionKeyOperationResponse");
            
            for (int i=0;i<items.getLength();i++){
                Node item 			= items.item(i);
                NodeList properties = item.getChildNodes();
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    
                    System.out.println("Response Keys: " + name);
                    
                    if (name.equalsIgnoreCase("Large")){
                    	if (property.getChildNodes().getLength()>0) {
                    		response.m_large = property.getFirstChild().getNodeValue();
                    		}
                    	else {
                    		response.m_large = "";
                    		}
                    } else if (name.equalsIgnoreCase("MediaId")){
                    	if (property.getChildNodes().getLength()>0) {
                    		response.m_mediaId = Long.parseLong(property.getFirstChild().getNodeValue());
                    	} else {
                        	response.m_mediaId = 0L;
                    		}
                    } else if (name.equalsIgnoreCase("MediaUrl")){
                    	if (property.getChildNodes().getLength()>0) {
                    		response.m_mediaUrl = property.getFirstChild().getNodeValue();
                    		}
                    	else {
                    		response.m_mediaUrl = "";
                    		}
                    } else if (name.equalsIgnoreCase("Medium")){
                    	if (property.getChildNodes().getLength()>0) {
                    		response.m_medium = property.getFirstChild().getNodeValue();
                    		}
                    	else {
                    		response.m_medium = "";
                    		}
                    } else if (name.equalsIgnoreCase("Original")){
                    	if (property.getChildNodes().getLength()>0) {
                    		response.m_original = property.getFirstChild().getNodeValue();
                    		}
                    	else {
                    		response.m_original = "";
                    		}
                    } else if (name.equalsIgnoreCase("PhotoId")){
                    	if (property.getChildNodes().getLength()>0) {
                    		response.m_photoId = Long.parseLong(property.getFirstChild().getNodeValue());
                    		}
                    	else {
                    		response.m_photoId = 0L;
                    		}
                    } else if (name.equalsIgnoreCase("SessionKeyResponse")){
                    	if (property.getChildNodes().getLength()>0) {
                    		response.m_sessionKeyResponse = property.getFirstChild().getNodeValue();
                    		}
                    	else {
                    		response.m_sessionKeyResponse = "";
                    		}
                    } else if (name.equalsIgnoreCase("Thumbnail")){
                    	if (property.getChildNodes().getLength()>0) {
                    		response.m_thumbnail = property.getFirstChild().getNodeValue();
                    		}
                    	else {
                    		response.m_thumbnail = "";
                    		}
                    } else if (name.equalsIgnoreCase("Status")){
                    	if (property.getChildNodes().getLength()>0) {
                    		response.m_status = property.getFirstChild().getNodeValue();
                    		}
                    	else {
                    		response.m_status = "";
                    		}
                    } else if (name.equalsIgnoreCase("UserId")){
                    	if (property.getChildNodes().getLength()>0) {
                    		response.m_userId = Long.parseLong(property.getFirstChild().getNodeValue());
                    		}
                    	else {
                    		response.m_userId = 0L;
                    		}
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
