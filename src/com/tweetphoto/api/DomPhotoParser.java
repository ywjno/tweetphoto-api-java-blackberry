package com.tweetphoto.api;

import java.io.ByteArrayInputStream;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

public class DomPhotoParser {
	
	String m_Xml;
		
	public Photo parse() {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Photo photo = new Photo();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            ByteArrayInputStream is = new ByteArrayInputStream(m_Xml.getBytes());
            
            Document dom   = builder.parse(is);
            NodeList items = dom.getElementsByTagName("Photo");
            
            for (int i=0;i<items.getLength();i++){
                Node item 			= items.item(i);
                NodeList properties = item.getChildNodes();
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    
                    if (name.equalsIgnoreCase("BigImageUrl")){
                    	photo.m_bigImageURL = property.getFirstChild().getNodeValue(); 
                    } else if (name.equalsIgnoreCase("CommentCount")){
                        photo.m_commentCount = Integer.parseInt(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase("DetailsUrl")){
                        photo.m_detailsURL = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("GdAlias")){
                        photo.m_gdAlias = Long.parseLong(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase("LargeImageUrl")){
                        photo.m_largeImageURL = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("LikedVotes")){
                        photo.m_likedVotes = Integer.parseInt(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase("Location")){
                    	
                        NodeList locationList = property.getChildNodes();
                        
                        photo.m_location = new Location();
                        for (int p=0;p<locationList.getLength();p++){
                            Node locationItem  = locationList.item(p);
                            String xname       = locationItem.getNodeName();
                            
                            if (xname.equalsIgnoreCase("Latitude")){
                                photo.m_location.m_latitude = Float.parseFloat(locationItem.getFirstChild().getNodeValue()); 
                                }
                            if (xname.equalsIgnoreCase("Longitude")){
                                photo.m_location.m_longitude = Float.parseFloat(locationItem.getFirstChild().getNodeValue()); 
                                }
                        	}
                    	
                    } else if (name.equalsIgnoreCase("MediumImageUrl")){
                        photo.m_mediumImageURL = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("Message")){
                    	// Might be empty
                    	if (property.getChildNodes().getLength()>0) {
                    		photo.m_message = property.getFirstChild().getNodeValue();
                    		}
                    } else if (name.equalsIgnoreCase("Name")){
                        photo.m_name = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("Next")){
                        photo.m_next = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("PhotoComments")){
                    	
                    	photo.m_photoComments      = new Vector();
                    	
                        NodeList commentProperties = property.getChildNodes();
                        
                        for (int k=0;k<commentProperties.getLength();k++){
                            Node commentProperty = commentProperties.item(k);
                            NodeList zproperties  = commentProperty.getChildNodes();
                            
                            Comment comment = new Comment();
                            
                            for (int m=0;m<zproperties.getLength();m++){
                                Node zitem 			= zproperties.item(m);
                                NodeList yproperties = zitem.getChildNodes();
                                for (int n=0;n<yproperties.getLength();n++){
                                    Node xproperty = yproperties.item(n);
                                    String xname = xproperty.getNodeName();
                                    
                                    System.out.println("Comment Keys: " + xname);
                            
                                    if (xname.equalsIgnoreCase("Date")){
                                    	comment.m_date = Long.parseLong(xproperty.getFirstChild().getNodeValue()); 
                                    } else if (xname.equalsIgnoreCase("DateString")){
                                        comment.m_dateString = xproperty.getFirstChild().getNodeValue(); 
                                    } else if (xname.equalsIgnoreCase("Id")){
                                        comment.m_id = Long.parseLong(xproperty.getFirstChild().getNodeValue()); 
                                    } else if (xname.equalsIgnoreCase("ImageId")){
                                        comment.m_imageId = Long.parseLong(xproperty.getFirstChild().getNodeValue()); 
                                    } else if (xname.equalsIgnoreCase("Message")){
                                        comment.m_message = xproperty.getFirstChild().getNodeValue(); 
                                    } else if (xname.equalsIgnoreCase("ProfileId")){
                                        comment.m_profileId = Long.parseLong(xproperty.getFirstChild().getNodeValue()); 
                                    } else if (xname.equalsIgnoreCase("ProfileImage")){
                                        comment.m_profileImage = xproperty.getFirstChild().getNodeValue(); 
                                    } else if (xname.equalsIgnoreCase("ScreenName")){
                                        comment.m_screenName = xproperty.getFirstChild().getNodeValue(); 
                                    	}
                                    }
                            	}
                            
                            photo.m_photoComments.addElement(comment);
                        	}
                    	
                    } else if (name.equalsIgnoreCase("Id")){
                        photo.m_photoId = Long.parseLong(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase("Previous")){
                        photo.m_previous = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("ThumbnailUrl")){
                        photo.m_thumbnailURL = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("TinyAlias")){
                        photo.m_tinyAlias = Long.parseLong(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase("UnlikedVotes")){
                        photo.m_unlikedVotes = Integer.parseInt(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase("UploadDate")){
                        photo.m_uploadDate = Long.parseLong(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase("UploadDateString")){
                        photo.m_uploadDateString = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase("User")){
                    	
                        NodeList userList = property.getChildNodes();
                        
                        photo.m_user = new Profile();
                        
                        for (int q=0;q<userList.getLength();q++){
                            Node userItem  = userList.item(q);
                            String zname   = userItem.getNodeName();
                            	
                            if (zname.equalsIgnoreCase("Comments")){
                                photo.m_user.m_comments = userItem.getFirstChild().getNodeValue(); 
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
                        
                    } else if (name.equalsIgnoreCase("UserId")){
                        photo.m_userId = Long.parseLong(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase("Views")){
                        photo.m_views = Integer.parseInt(property.getFirstChild().getNodeValue());
                    }
                }
                
                // TweetPhoto tweetPhoto 		= new TweetPhoto();
                // VoteStatus vs         		= tweetPhoto.voteStatus(tweetPhoto.m_userId, photo.m_photoId);
                // FavoriteQueryResponse fs	= tweetPhoto.favoriteStatus(tweetPhoto.m_userId, photo.m_photoId);  
                
                // Set our vote status
                // photo.m_voteStatus = vs.m_status.equalsIgnoreCase("ThumbsUp") ? 1 : (vs.m_status.equalsIgnoreCase("ThumbsDown") ? 2 : 0);
                // photo.m_favorite   = fs.m_isFavorite;                
            }
        } catch (Throwable tr) {
        	System.out.println(tr.toString()); 
			tr.printStackTrace();
        } 
        return photo;
    }
}
