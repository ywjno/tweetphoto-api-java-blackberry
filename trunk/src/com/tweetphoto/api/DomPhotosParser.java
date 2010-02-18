package com.tweetphoto.api;

import java.io.ByteArrayInputStream;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

public class DomPhotosParser {
	
	String m_Xml;
	
	public Photos parse() {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final Photos photos = new Photos();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            ByteArrayInputStream is = new ByteArrayInputStream(m_Xml.getBytes());
            
            Document dom   			= builder.parse(is);
            dom.normalize();
            NodeList items 		    = dom.getElementsByTagName("Photos");
            
            for (int i=0;i<items.getLength();i++){
                Node item 			= items.item(i);
                NodeList properties = item.getChildNodes();
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    if (name.equalsIgnoreCase("Count")){
                    	photos.m_count = Integer.parseInt(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("StartIndex")){
                    	photos.m_startIndex = Integer.parseInt(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("List")){
                    	
                    		photos.m_list = new Vector();
                    	
                            NodeList photoProperties 	= property.getChildNodes();
                            
                            for (int m=0;m<photoProperties.getLength();m++){
                                Node xitem 			= photoProperties.item(m);
                                NodeList xproperties = xitem.getChildNodes();
                                                                
                                Photo photo = new Photo();
                                
                                for (int n=0;n<xproperties.getLength();n++){
                                    Node xproperty = xproperties.item(n);
                                    String xname = xproperty.getNodeName();
                                    
                                    if (xname.equalsIgnoreCase("BigImageUrl")){
                                    	if (xproperty.getChildNodes().getLength()>0) {
                                    		photo.m_bigImageURL = xproperty.getFirstChild().getNodeValue();
                                    		}
                                    	else {
                                    		photo.m_bigImageURL = "";
                                    		}
                                    } else if (xname.equalsIgnoreCase("CommentCount")){
                                        photo.m_commentCount = Integer.parseInt(xproperty.getFirstChild().getNodeValue());
                                    } else if (xname.equalsIgnoreCase("DetailsUrl")){
                                    	if (xproperty.getChildNodes().getLength()>0) {
                                    		photo.m_detailsURL = xproperty.getFirstChild().getNodeValue();
                                    		}
                                    	else {
                                    		photo.m_detailsURL = "";
                                    		}
                                    } else if (xname.equalsIgnoreCase("GdAlias")){
                                        photo.m_gdAlias = Long.parseLong(xproperty.getFirstChild().getNodeValue());
                                    } else if (xname.equalsIgnoreCase("LargeImageUrl")){
                                    	if (xproperty.getChildNodes().getLength()>0) {
                                        photo.m_largeImageURL = xproperty.getFirstChild().getNodeValue();
                                    	}
                                    	else {
                                            photo.m_largeImageURL = "";
                                    		}
                                    } else if (xname.equalsIgnoreCase("LikedVotes")){
                                        photo.m_likedVotes = Integer.parseInt(xproperty.getFirstChild().getNodeValue());
                                    } else if (xname.equalsIgnoreCase("Location")){
                                    	
                                        NodeList locationList = xproperty.getChildNodes();
                                        
                                        photo.m_location = new Location();
                                        for (int p=0;p<locationList.getLength();p++){
                                            Node locationItem  = locationList.item(p);
                                            String yname       = locationItem.getNodeName();
                                            if (yname.equalsIgnoreCase("Latitude")){
                                                photo.m_location.m_latitude = Float.parseFloat(locationItem.getFirstChild().getNodeValue()); 
                                                }
                                            if (yname.equalsIgnoreCase("Longitude")){
                                                photo.m_location.m_longitude = Float.parseFloat(locationItem.getFirstChild().getNodeValue()); 
                                                }
                                        	}
                                    	
                                    } else if (xname.equalsIgnoreCase("MediumImageUrl")){
                                        photo.m_mediumImageURL = xproperty.getFirstChild().getNodeValue();
                                    } else if (xname.equalsIgnoreCase("Message")){
                                    	// Might be empty
                                    	if (xproperty.getChildNodes().getLength()>0) {
                                    		photo.m_message = xproperty.getFirstChild().getNodeValue();
                                    		}
                                    	else {
                                    		photo.m_message = "";
                                    		}
                                    } else if (xname.equalsIgnoreCase("Name")){
                                        photo.m_name = xproperty.getFirstChild().getNodeValue();
                                    } else if (xname.equalsIgnoreCase("Next")){
                                    	if (xproperty.getChildNodes().getLength()>0) {
                                    		photo.m_next = xproperty.getFirstChild().getNodeValue();
                                    		}
                                    	else {
                                            photo.m_next = "";
                                    		}
                                    } else if (xname.equalsIgnoreCase("Id")){
                                        photo.m_photoId = Long.parseLong(xproperty.getFirstChild().getNodeValue());
                                    } else if (xname.equalsIgnoreCase("Previous")){
                                    	if (xproperty.getChildNodes().getLength()>0) {
                                    		photo.m_previous = xproperty.getFirstChild().getNodeValue();
                                    		}
                                    	else {
                                    		photo.m_previous = "";
                                    		}
                                    } else if (xname.equalsIgnoreCase("ThumbnailUrl")){
                                        photo.m_thumbnailURL = xproperty.getFirstChild().getNodeValue();
                                    } else if (xname.equalsIgnoreCase("TinyAlias")){
                                        photo.m_tinyAlias = Long.parseLong(xproperty.getFirstChild().getNodeValue());
                                    } else if (xname.equalsIgnoreCase("UnlikedVotes")){
                                        photo.m_unlikedVotes = Integer.parseInt(xproperty.getFirstChild().getNodeValue());
                                    } else if (xname.equalsIgnoreCase("UploadDate")){
                                        photo.m_uploadDate = Long.parseLong(xproperty.getFirstChild().getNodeValue());
                                    } else if (xname.equalsIgnoreCase("UploadDateString")){
                                        photo.m_uploadDateString = xproperty.getFirstChild().getNodeValue();
                                    } else if (xname.equalsIgnoreCase("User")){
                                    	
                                        NodeList userList = xproperty.getChildNodes();
                                        
                                        photo.m_user = new Profile();
                                        for (int q=0;q<userList.getLength();q++){
                                            Node userItem  = userList.item(q);
                                            String zname   = userItem.getNodeName();
                                            	
                                            if (zname.equalsIgnoreCase("Comments")){
                                            	if (userItem.getChildNodes().getLength()>0) {
                                            		photo.m_user.m_comments = userItem.getFirstChild().getNodeValue();
                                            		}
                                            	else {
                                            		photo.m_user.m_comments = "";
                                            		}
                                                }
                                            if (zname.equalsIgnoreCase("Favorites")){
                                                photo.m_user.m_favorites = userItem.getFirstChild().getNodeValue(); 
                                                }
                                            if (zname.equalsIgnoreCase("Friends")){
                                                photo.m_user.m_friends = userItem.getFirstChild().getNodeValue(); 
                                                }
                                            if (zname.equalsIgnoreCase("Id")){
                                                photo.m_user.m_id = Long.parseLong(userItem.getFirstChild().getNodeValue()); 
                                                }
                                            if (zname.equalsIgnoreCase("MapTypeForProfile")){
                                                photo.m_user.m_mapTypeForProfile = userItem.getFirstChild().getNodeValue(); 
                                                }
                                            if (zname.equalsIgnoreCase("Photos")){
                                                photo.m_user.m_photos = userItem.getFirstChild().getNodeValue(); 
                                                }
                                            if (zname.equalsIgnoreCase("ProfileImage")){
                                                photo.m_user.m_profileImage = userItem.getFirstChild().getNodeValue(); 
                                                }
                                            if (zname.equalsIgnoreCase("ScreenName")){
                                                photo.m_user.m_screenName = userItem.getFirstChild().getNodeValue(); 
                                                }
                                            if (zname.equalsIgnoreCase("Settings")){
                                                photo.m_user.m_settings = userItem.getFirstChild().getNodeValue(); 
                                                }
                                            if (zname.equalsIgnoreCase("Views")){
                                                photo.m_user.m_views = userItem.getFirstChild().getNodeValue(); 
                                                }
                                        	}
                                        
                                    } else if (xname.equalsIgnoreCase("UserId")){
                                        photo.m_userId = Long.parseLong(xproperty.getFirstChild().getNodeValue());
                                    } else if (xname.equalsIgnoreCase("Views")){
                                        photo.m_views = Integer.parseInt(xproperty.getFirstChild().getNodeValue());
                                    }
                                }
                                
                            // TweetPhoto tweetPhoto 		= new TweetPhoto();
                            // VoteStatus vs         		= tweetPhoto.voteStatus(tweetPhoto.m_userId, photo.m_photoId);
                            // FavoriteQueryResponse fs	= tweetPhoto.favoriteStatus(tweetPhoto.m_userId, photo.m_photoId);  
                                
                            // Set our vote status
                            // photo.m_voteStatus = vs.m_status.equalsIgnoreCase("ThumbsUp") ? 1 : (vs.m_status.equalsIgnoreCase("ThumbsDown") ? 2 : 0);
                            // photo.m_favorite   = fs.m_isFavorite;
                            
                            photos.m_list.addElement(photo);
                    		}
                    	
                    	}
                	}
            }
        } catch (Throwable tr) {
        	System.out.println(tr.toString()); 
			tr.printStackTrace();
       		} 
        return photos;
    }
}
