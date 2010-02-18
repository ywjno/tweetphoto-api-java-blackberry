package com.tweetphoto.api;

import java.io.ByteArrayInputStream;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

public class DomCommentsParser {
	
	String m_Xml;
	
	public Comments parse() {
		
        try {
            final Comments comments = new Comments();
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
                        
            ByteArrayInputStream is = new ByteArrayInputStream(m_Xml.getBytes());
            
            Document dom   			= builder.parse(is);
            NodeList items 			= dom.getElementsByTagName("Comments");
            
            for (int i=0;i<items.getLength();i++){
                Node item 			= items.item(i);
                NodeList properties = item.getChildNodes();
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    if (name.equalsIgnoreCase("Count")){
                    	comments.m_count = Integer.parseInt(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("startIndex")){
                    	comments.m_startIndex = Integer.parseInt(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("PhotoId")){
                    	comments.m_photoId = Long.parseLong(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("List")){
                    	
                    	NodeList commentList = property.getChildNodes();
                    	
                    	comments.m_list = new Vector();
                    	
                        for (int k=0;k<commentList.getLength();k++){
                        	
                            Node xitem 			  = commentList.item(k);
                            NodeList xproperties = xitem.getChildNodes();
                        	
                        	Comment co = new Comment();
                            
                            for (int m=0;m<xproperties.getLength();m++){
                                Node xproperty = xproperties.item(m);
                                String xname   = xproperty.getNodeName();
                                
                                if (xname.equalsIgnoreCase("Date")){
                                	co.m_date = Long.parseLong(xproperty.getFirstChild().getNodeValue()); 
                                } else if (xname.equalsIgnoreCase("DateString")){
                                	co.m_dateString = xproperty.getFirstChild().getNodeValue(); 
                                } else if (xname.equalsIgnoreCase("Id")){
                                	co.m_id = Long.parseLong(xproperty.getFirstChild().getNodeValue()); 
                                } else if (xname.equalsIgnoreCase("ImageId")){
                                	co.m_imageId = Long.parseLong(xproperty.getFirstChild().getNodeValue()); 
                                } else if (xname.equalsIgnoreCase("Message")){
                                	co.m_message = xproperty.getFirstChild().getNodeValue(); 
                                } else if (xname.equalsIgnoreCase("ProfileId")){
                                	co.m_profileId = Long.parseLong(xproperty.getFirstChild().getNodeValue()); 
                                } else if (xname.equalsIgnoreCase("ProfileImage")){
                                	if (xproperty.getChildNodes().getLength()>0) {
                                		co.m_profileImage = xproperty.getFirstChild().getNodeValue();
                                	} else {
                                    	co.m_profileImage = "";
                                	}
                                } else if (xname.equalsIgnoreCase("ScreenName")){
                                	if (xproperty.getChildNodes().getLength()>0) {
                                    	co.m_screenName = xproperty.getFirstChild().getNodeValue(); 
                                	} else {
                                    	co.m_screenName = ""; 
                                	}
                            	}
                            }
            				comments.m_list.addElement(co);
                        	}
                    	}
                	}
            	}
            	
            return comments;
            
        } catch (Throwable tr) {
        	System.out.println(tr.toString()); 
			tr.printStackTrace();
        	} 
    	return null;
		}
}
