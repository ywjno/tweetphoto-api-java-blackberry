package com.tweetphoto.api;

import java.io.ByteArrayInputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

public class DomAddCommentResponseParser {
	
	String m_Xml;
	
	public AddPhotoCommentResponse parse() {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        AddPhotoCommentResponse response = new AddPhotoCommentResponse();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            ByteArrayInputStream is = new ByteArrayInputStream(m_Xml.getBytes());

            Document dom   			= builder.parse(is);
            NodeList items 			= dom.getElementsByTagName("AddPhotoCommentResponse");
            
            for (int i=0;i<items.getLength();i++){
                Node item 			= items.item(i);
                NodeList properties = item.getChildNodes();
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    if (name.equalsIgnoreCase("CommentId")){
                    	response.m_CommentId = Long.parseLong(property.getFirstChild().getNodeValue()); 
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
