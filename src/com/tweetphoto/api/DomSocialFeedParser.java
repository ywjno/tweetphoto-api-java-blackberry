package com.tweetphoto.api;

import java.io.ByteArrayInputStream;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

public class DomSocialFeedParser {
	
	String m_Xml;
	
	public SocialFeed parse() {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final SocialFeed social = new SocialFeed();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            ByteArrayInputStream is = new ByteArrayInputStream(m_Xml.getBytes());
            
            Document dom   			= builder.parse(is);
            dom.normalize();
            NodeList items 		    = dom.getElementsByTagName("SocialFeed");
            
            for (int i=0;i<items.getLength();i++){
                Node item 			= items.item(i);
                NodeList properties = item.getChildNodes();
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    if (name.equalsIgnoreCase("Count")){
                    	social.m_count = Integer.parseInt(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("StartIndex")){
                    	social.m_startIndex = Integer.parseInt(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("Filter")){
                    	social.m_filter = property.getFirstChild().getNodeValue(); 
                    } else if (name.equalsIgnoreCase("List")){
                    	
                        NodeList eventList = property.getChildNodes();
                    	
                    	social.m_list = new Vector();
                        
                        for (int k=0;k<eventList.getLength();k++){
                            Node sfeItem 			= eventList.item(k);
                            NodeList sfeProperties = sfeItem.getChildNodes();
                            
                            SocialFeedEvent sfe = new SocialFeedEvent();
                            
                            for (int m=0;m<sfeProperties.getLength();m++){
                                Node xproperty = sfeProperties.item(m);
                                String xname   = xproperty.getNodeName();
                                if (xname.equalsIgnoreCase("Content")){
                                	NodeList contentNodes = xproperty.getChildNodes();
                                	if (contentNodes.getLength()>0) {
                                	sfe.m_content = xproperty.getFirstChild().getNodeValue();
                                	}
                                }  else if (xname.equalsIgnoreCase("EventType")){
                                	sfe.m_eventType = xproperty.getFirstChild().getNodeValue(); 
                                } else if (xname.equalsIgnoreCase("ImageThumbnail")){
                                	sfe.m_imageThumbnail = xproperty.getFirstChild().getNodeValue(); 
                                } else if (xname.equalsIgnoreCase("PhotoId")){
                                	sfe.m_photoId = Long.parseLong(xproperty.getFirstChild().getNodeValue()); 
                                	

                                	// Good place to read in photo data
                                	// TweetPhoto tweetPhoto = new TweetPhoto();
                                	// sfe.m_photo = tweetPhoto.photoMetaData(sfe.m_photoId);
                                	
                                } else if (xname.equalsIgnoreCase("User")){
                                	
                                    NodeList userList = xproperty.getChildNodes();
                                    
                                    sfe.m_user = new Profile();
                                    for (int q=0;q<userList.getLength();q++){
                                        Node userItem  = userList.item(q);
                                        String zname   = userItem.getNodeName();
                                        	
                                        if (zname.equalsIgnoreCase("Comments")){
                                            sfe.m_user.m_comments = userItem.getFirstChild().getNodeValue(); 
                                            }
                                        if (zname.equalsIgnoreCase("Favorites")){
                                            sfe.m_user.m_favorites = userItem.getFirstChild().getNodeValue(); 
                                            }
                                        if (zname.equalsIgnoreCase("Friends")){
                                            sfe.m_user.m_friends = userItem.getFirstChild().getNodeValue(); 
                                            }
                                        if (zname.equalsIgnoreCase("Id")){
                                            sfe.m_user.m_id = Long.parseLong(userItem.getFirstChild().getNodeValue()); 
                                            }
                                        if (zname.equalsIgnoreCase("MapTypeForProfile")){
                                            sfe.m_user.m_mapTypeForProfile = userItem.getFirstChild().getNodeValue(); 
                                            }
                                        if (zname.equalsIgnoreCase("Photos")){
                                            sfe.m_user.m_photos = userItem.getFirstChild().getNodeValue(); 
                                            }
                                        if (zname.equalsIgnoreCase("ProfileImage")){
                                            sfe.m_user.m_profileImage = userItem.getFirstChild().getNodeValue(); 
                                            }
                                        if (zname.equalsIgnoreCase("ScreenName")){
                                            sfe.m_user.m_screenName = userItem.getFirstChild().getNodeValue(); 
                                            }
                                        if (zname.equalsIgnoreCase("Settings")){
                                            sfe.m_user.m_settings = userItem.getFirstChild().getNodeValue(); 
                                            }
                                        if (zname.equalsIgnoreCase("Views")){
                                            sfe.m_user.m_views = userItem.getFirstChild().getNodeValue(); 
                                            }
                                    	}
                                    
                                    // Log.i("TweetPhoto", "Profile: " + sfe.m_user.describe());
                                	
                                } else if (xname.equalsIgnoreCase("Date")){
                                	
                                    NodeList dateList = xproperty.getChildNodes();
                                	
                                    sfe.m_date = new PhotoDate();
                                    for (int n=0;n<dateList.getLength();n++){
                                        Node dateItem 			= dateList.item(n);
                                        String yname   = dateItem.getNodeName();
                                        	
                                        if (yname.equalsIgnoreCase("UploadDate")){
                                            sfe.m_date.m_uploadDate = Long.parseLong(dateItem.getFirstChild().getNodeValue()); 
                                            }
                                        if (yname.equalsIgnoreCase("UploadDateString")){
                                            sfe.m_date.m_uploadDateString = dateItem.getFirstChild().getNodeValue(); 
                                            }
                                    	}
                                    }                                    
                            	}
                            
                            // TweetPhoto tweetPhoto 		= new TweetPhoto();
                            // VoteStatus vs         		= tweetPhoto.voteStatus(tweetPhoto.m_userId, sfe.m_photoId);
                            // FavoriteQueryResponse fs	= tweetPhoto.favoriteStatus(tweetPhoto.m_userId, sfe.m_photoId);  
                            
                            // Set our vote status
                            // sfe.m_voteStatus = vs.m_status.equalsIgnoreCase("ThumbsUp") ? 1 : (vs.m_status.equalsIgnoreCase("ThumbsDown") ? 2 : 0);
                            // sfe.m_favorite   = fs.m_isFavorite;
                            
                            social.m_list.addElement(sfe);
                    		}
                    	
                    	}
                	}
            	}
        } catch (Throwable tr) {
        	System.out.println(tr.toString()); 
			tr.printStackTrace();
       		} 
        return social;
    }
}
