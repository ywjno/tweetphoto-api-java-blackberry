package com.tweetphoto.api;

import java.io.ByteArrayInputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

public class DomVoteStatusParser {
	
	String m_Xml;
	
	public VoteStatus parse() {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        VoteStatus voteStatus = new VoteStatus();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            ByteArrayInputStream is = new ByteArrayInputStream(m_Xml.getBytes());
            
            Document dom   = builder.parse(is);
            NodeList items = dom.getElementsByTagName("VoteStatus");
            
            for (int i=0;i<items.getLength();i++){
                Node item 			= items.item(i);
                NodeList properties = item.getChildNodes();
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    if (name.equalsIgnoreCase("PhotoId")){
                    	voteStatus.m_photoId = Long.parseLong(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("UserId")){
                    	voteStatus.m_userId = Long.parseLong(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("Status")){
                    	voteStatus.m_status = property.getFirstChild().getNodeValue(); 
                    	}
                }
            }
        } catch (Throwable tr) {
        	System.out.println(tr.toString()); 
			tr.printStackTrace();
        } 
        return voteStatus;
    }
}
