package com.tweetphoto.api;

import java.io.ByteArrayInputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

public class DomCommentParser {
	
	String m_Xml;
	
	public Comment parse() {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Comment comment = new Comment();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            ByteArrayInputStream in = new ByteArrayInputStream(m_Xml.getBytes());
            
            Document dom   			= builder.parse(in);
            NodeList items 			= dom.getElementsByTagName("Comment");
            
            for (int i=0;i<items.getLength();i++){
                Node item 			= items.item(i);
                NodeList properties = item.getChildNodes();
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    if (name.equalsIgnoreCase("Date")){
                    	comment.m_date = Long.parseLong(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("DateString")){
                    	comment.m_dateString = property.getFirstChild().getNodeValue(); 
                    } else if (name.equalsIgnoreCase("Id")){
                    	comment.m_id = Long.parseLong(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("ImageId")){
                    	comment.m_imageId = Long.parseLong(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("Message")){
                    	comment.m_message = property.getFirstChild().getNodeValue(); 
                    } else if (name.equalsIgnoreCase("ProfileId")){
                    	comment.m_profileId = Long.parseLong(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("ProfileImage")){
                    	comment.m_profileImage = property.getFirstChild().getNodeValue(); 
                    } else if (name.equalsIgnoreCase("ScreenName")){
                    	comment.m_screenName = property.getFirstChild().getNodeValue();
                    }
                }
            }
        } catch (Throwable tr) {
        	System.out.println(tr.toString()); 
			tr.printStackTrace();
        } 
        return comment;
    }
}
