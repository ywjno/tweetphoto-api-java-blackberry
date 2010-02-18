package com.tweetphoto.api;

import java.io.ByteArrayInputStream;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

public class DomProfilesParser {
	
	String m_Xml;
	
	public Profiles parse() {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final Profiles profiles = new Profiles();
        
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            ByteArrayInputStream is = new ByteArrayInputStream(m_Xml.getBytes());
            
            Document dom   			= builder.parse(is);
            dom.normalize();
            NodeList items 		    = dom.getElementsByTagName("Profiles");
            
            for (int i=0;i<items.getLength();i++){
                Node item 			= items.item(i);
                NodeList properties = item.getChildNodes();
                
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    
                    if (name.equalsIgnoreCase("Count")){
                    	profiles.m_count = Integer.parseInt(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("StartIndex")){
                    	if (property.getChildNodes().getLength()>0) {
                    		profiles.m_startIndex = Integer.parseInt(property.getFirstChild().getNodeValue());
                    		}
                    	else {
                    		profiles.m_startIndex = 0;
                    		}
                    } else if (name.equalsIgnoreCase("Filter")){
                    	if (property.getChildNodes().getLength()>0) {
                    		profiles.m_filter = property.getFirstChild().getNodeValue();
                    	} else {
                        	profiles.m_filter = "";
                    		}
                    } else if (name.equalsIgnoreCase("LinkedServices")){
                    	if (property.getChildNodes().getLength()>0) {
                    		profiles.m_linkedServices = property.getFirstChild().getNodeValue();
                    	} else {
                        	profiles.m_linkedServices = "";
                    	}
                    } else if (name.equalsIgnoreCase("List")){
                    	
                    		profiles.m_list = new Vector();
                    	
                            NodeList photoProperties 	= property.getChildNodes();
                            
                            for (int z=0;z<photoProperties.getLength();z++){
                                Node xitem 			= photoProperties.item(z);
                                NodeList xproperties = xitem.getChildNodes();
                                
                                Profile profile = new Profile();
                                
                                    for (int n=0;n<xproperties.getLength();n++){
                                        Node xproperty = xproperties.item(n);
                                        String xname = xproperty.getNodeName();
                                        
                                        if (xname.equalsIgnoreCase("Comments")){
                                        	if (property.getChildNodes().getLength()>0) {
                                        		profile.m_comments = xproperty.getFirstChild().getNodeValue();
                                        		}
                                        	else {
                                        		profile.m_comments = "";
                                        		}
                                        } else if (xname.equalsIgnoreCase("Description")){
                                        	if (xproperty.getChildNodes().getLength()>0) {
                                        		profile.m_description = xproperty.getFirstChild().getNodeValue();
                                        		}
                                        	else {
                                        		profile.m_description = "";
                                        		}
                                        } else if (xname.equalsIgnoreCase("Favorites")){
                                            profile.m_favorites = xproperty.getFirstChild().getNodeValue();
                                        } else if (xname.equalsIgnoreCase("FirstName")){
                                        	if (xproperty.getChildNodes().getLength()>0) {
                                        		profile.m_firstName = xproperty.getFirstChild().getNodeValue();
                                        		}
                                        	else {
                                                profile.m_firstName = "";
                                        		}
                                        } else if (xname.equalsIgnoreCase("Friends")){
                                            profile.m_friends = xproperty.getFirstChild().getNodeValue();
                                        } else if (xname.equalsIgnoreCase("Homepage")){
                                        	if (xproperty.getChildNodes().getLength()>0) {
                                        		profile.m_homepage = xproperty.getFirstChild().getNodeValue();
                                        		}
                                        	else {
                                                profile.m_homepage = "";
                                        		}
                                        } else if (xname.equalsIgnoreCase("HomePage")){
                                        	if (xproperty.getChildNodes().getLength()>0) {
                                        		profile.m_homepage = xproperty.getFirstChild().getNodeValue();
                                        		}
                                        	else {
                                                profile.m_homepage = "";
                                        		}
                                        } else if (xname.equalsIgnoreCase("Id")){
                                            profile.m_id = Long.parseLong(xproperty.getFirstChild().getNodeValue());
                                        } else if (xname.equalsIgnoreCase("MapTypeForProfile")){
                                            profile.m_mapTypeForProfile = xproperty.getFirstChild().getNodeValue();
                                        } else if (xname.equalsIgnoreCase("Photos")){
                                            profile.m_photos = xproperty.getFirstChild().getNodeValue();
                                        } else if (xname.equalsIgnoreCase("ProfileImage")){
                                            profile.m_profileImage = xproperty.getFirstChild().getNodeValue();
                                        } else if (xname.equalsIgnoreCase("ScreenName")){
                                        	if (xproperty.getChildNodes().getLength()>0) {
                                        		profile.m_screenName = xproperty.getFirstChild().getNodeValue();
                                        		} else {
                                            	profile.m_screenName = "";
                                        		}
                                        } else if (xname.equalsIgnoreCase("ServiceId")){
                                            profile.m_serviceId = Long.parseLong(xproperty.getFirstChild().getNodeValue());
                                        } else if (xname.equalsIgnoreCase("Settings")){
                                            profile.m_settings = xproperty.getFirstChild().getNodeValue();
                                        } else if (xname.equalsIgnoreCase("Views")){
                                            profile.m_views = xproperty.getFirstChild().getNodeValue();
                                        	}
                                    	}
                                    
                                    profiles.m_list.addElement(profile);
                                	}
                            	}
                    		}
                		}
        	} catch (Throwable tr) {
        		System.out.println(tr.toString()); 
        		tr.printStackTrace();
       			} 
        	return profiles;
			}
}
