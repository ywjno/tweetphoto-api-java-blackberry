package com.tweetphoto.api;

import java.io.ByteArrayInputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

public class DomProfileParser {
	
	String m_Xml;
	
	public Profile parse() {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Profile profile = new Profile();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            ByteArrayInputStream is = new ByteArrayInputStream(m_Xml.getBytes());
            
            Document dom   			= builder.parse(is);
            
            dom.getDocumentElement().normalize();
            
            NodeList items          = dom.getElementsByTagName("Profile");
            
        	System.out.println("items: " + items.getLength()); 
            
            for (int i=0;i<items.getLength();i++){
                Node item 			= items.item(i);
                NodeList properties = item.getChildNodes();
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    // System.out.println("Profile Keys: " + name);
                    if (name.equalsIgnoreCase("Comments")){
                    	if (property.getChildNodes().getLength()>0) {
                    		profile.m_comments = property.getFirstChild().getNodeValue();
                    		}
                    	else {
                    		profile.m_comments = "";
                    		}
                    } else if (name.equalsIgnoreCase("Description")){
                    	if (property.getChildNodes().getLength()>0) {
                    		profile.m_description = property.getFirstChild().getNodeValue();
                    		}
                    	else {
                    		profile.m_description = "";
                    		}
                    } else if (name.equalsIgnoreCase("Favorites")){
                        profile.m_favorites = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("FirstName")){
                    	if (property.getChildNodes().getLength()>0) {
                        profile.m_firstName = property.getFirstChild().getNodeValue();
                    		}
                    	else {
                            profile.m_firstName = "";
                    		}
                    } else if (name.equalsIgnoreCase("Friends")){
                        profile.m_friends = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("Homepage")){
                    	if (property.getChildNodes().getLength()>0) {
                    		profile.m_homepage = property.getFirstChild().getNodeValue();
                    		}
                    	else {
                            profile.m_homepage = "";
                    		}
                    } else if (name.equalsIgnoreCase("HomePage")){
                    	if (property.getChildNodes().getLength()>0) {
                    		profile.m_homepage = property.getFirstChild().getNodeValue();
                    		}
                    	else {
                            profile.m_homepage = "";
                    		}
                    } else if (name.equalsIgnoreCase("Id")){
                        profile.m_id = Long.parseLong(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase("MapTypeForProfile")){
                        profile.m_mapTypeForProfile = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("Photos")){
                        profile.m_photos = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("ProfileImage")){
                        profile.m_profileImage = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("ScreenName")){
                        profile.m_screenName = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("ServiceId")){
                        profile.m_serviceId = Long.parseLong(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase("Settings")){
                        profile.m_settings = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("Views")){
                        profile.m_views = property.getFirstChild().getNodeValue();
                    }
                }
            }
        } catch (Throwable tr) {
        	System.out.println(tr.toString()); 
			tr.printStackTrace();
        } 
        return profile;
    }
	
}
