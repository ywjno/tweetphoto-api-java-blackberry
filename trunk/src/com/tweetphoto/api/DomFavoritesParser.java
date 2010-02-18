package com.tweetphoto.api;

import java.io.ByteArrayInputStream;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

public class DomFavoritesParser {
	
	String m_Xml;
	
	public Favorites parse() {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final Favorites favorites = new Favorites();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
                        
            ByteArrayInputStream is = new ByteArrayInputStream(m_Xml.getBytes());
            
            Document dom   			= builder.parse(is);
            dom.normalize();
            NodeList items 		    = dom.getElementsByTagName("Favorites");
            
            for (int i=0;i<items.getLength();i++){
                Node item 			= items.item(i);
                NodeList properties = item.getChildNodes();
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    if (name.equalsIgnoreCase("Count")){
                    	favorites.m_count = Integer.parseInt(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("StartIndex")){
                    	favorites.m_startIndex = Integer.parseInt(property.getFirstChild().getNodeValue()); 
                    } else if (name.equalsIgnoreCase("List")){
                    	
                    		favorites.m_list = new Vector();
                    	
                            NodeList faveProperties 	= property.getChildNodes();
                            
                            for (int m=0;m<faveProperties.getLength();m++){
                                Node xitem 			= faveProperties.item(m);
                                NodeList xproperties = xitem.getChildNodes();
                                                                
                                Favorite favorite = new Favorite();
                                
                                for (int n=0;n<xproperties.getLength();n++){
                                    Node xproperty = xproperties.item(n);
                                    String xname = xproperty.getNodeName();
                                    
                                    if (xname.equalsIgnoreCase("FavoriteDate")){
                                    	favorite.m_favoriteDate = Long.parseLong(xproperty.getFirstChild().getNodeValue());
                                    } else if (xname.equalsIgnoreCase("FavoriteDateString")){
                                        favorite.m_favoriteDateString = xproperty.getFirstChild().getNodeValue();
                                    } else if (xname.equalsIgnoreCase("ImageId")){
                                        favorite.m_imageId = Long.parseLong(xproperty.getFirstChild().getNodeValue());
                                    } else if (xname.equalsIgnoreCase("UserId")){
                                        favorite.m_userId = Long.parseLong(xproperty.getFirstChild().getNodeValue());
                                    } else if (xname.equalsIgnoreCase("Photo")){
                                    	
                                        NodeList photoProperties 	= xproperty.getChildNodes();
                                        
                                            favorite.m_photo = new Photo();
                                            
                                            for (int q=0;q<photoProperties.getLength();q++){
                                                Node yproperty = photoProperties.item(q);
                                                String yname = yproperty.getNodeName();
                                                
                                                if (yname.equalsIgnoreCase("BigImageUrl")){
                                                	if (yproperty.getChildNodes().getLength()>0) {
                                                		favorite.m_photo.m_bigImageURL = yproperty.getFirstChild().getNodeValue();
                                                		}
                                                	else {
                                                		favorite.m_photo.m_bigImageURL = "";
                                                		}
                                                } else if (yname.equalsIgnoreCase("CommentCount")){
                                                	favorite.m_photo.m_commentCount = Integer.parseInt(yproperty.getFirstChild().getNodeValue());
                                                } else if (yname.equalsIgnoreCase("DetailsUrl")){
                                                	if (yproperty.getChildNodes().getLength()>0) {
                                                		favorite.m_photo.m_detailsURL = yproperty.getFirstChild().getNodeValue();
                                                		}
                                                	else {
                                                		favorite.m_photo.m_detailsURL = "";
                                                		}
                                                } else if (yname.equalsIgnoreCase("GdAlias")){
                                                	favorite.m_photo.m_gdAlias = Long.parseLong(yproperty.getFirstChild().getNodeValue());
                                                } else if (yname.equalsIgnoreCase("LargeImageUrl")){
                                                	if (yproperty.getChildNodes().getLength()>0) {
                                                		favorite.m_photo.m_largeImageURL = yproperty.getFirstChild().getNodeValue();
                                                	}
                                                	else {
                                                		favorite.m_photo.m_largeImageURL = "";
                                                		}
                                                } else if (yname.equalsIgnoreCase("LikedVotes")){
                                                	favorite.m_photo.m_likedVotes = Integer.parseInt(yproperty.getFirstChild().getNodeValue());
                                                } else if (yname.equalsIgnoreCase("Location")){
                                                	
                                                    NodeList locationList = yproperty.getChildNodes();
                                                    
                                                    favorite.m_photo.m_location = new Location();
                                                    
                                                    favorite.m_photo.m_location.m_latitude  = (float)0.0;
                                                    favorite.m_photo.m_location.m_longitude = (float)0.0;
                                                    
                                                    for (int z=0;z<locationList.getLength();z++){
                                                        Node locationItem  = locationList.item(z);
                                                        String wname       = locationItem.getNodeName();
                                                        if (wname.equalsIgnoreCase("Latitude")){
                                                        	favorite.m_photo.m_location.m_latitude = Float.parseFloat(locationItem.getFirstChild().getNodeValue()); 
                                                            }
                                                        if (wname.equalsIgnoreCase("Longitude")){
                                                        	favorite.m_photo.m_location.m_longitude = Float.parseFloat(locationItem.getFirstChild().getNodeValue()); 
                                                            }
                                                    	}
                                                	
                                                } else if (yname.equalsIgnoreCase("MediumImageUrl")){
                                                	favorite.m_photo.m_mediumImageURL = yproperty.getFirstChild().getNodeValue();
                                                } else if (yname.equalsIgnoreCase("Message")){
                                                	// Might be empty
                                                	if (yproperty.getChildNodes().getLength()>0) {
                                                		favorite.m_photo.m_message = yproperty.getFirstChild().getNodeValue();
                                                		}
                                                	else {
                                                		favorite.m_photo.m_message = "";
                                                		}
                                                } else if (yname.equalsIgnoreCase("Name")){
                                                	favorite.m_photo.m_name = yproperty.getFirstChild().getNodeValue();
                                                } else if (yname.equalsIgnoreCase("Next")){
                                                	if (xproperty.getChildNodes().getLength()>0) {
                                                		favorite.m_photo.m_next = yproperty.getFirstChild().getNodeValue();
                                                		}
                                                	else {
                                                		favorite.m_photo.m_next = "";
                                                		}
                                                } else if (yname.equalsIgnoreCase("Id")){
                                                	favorite.m_photo.m_photoId = Long.parseLong(yproperty.getFirstChild().getNodeValue());
                                                } else if (yname.equalsIgnoreCase("Previous")){
                                                	if (yproperty.getChildNodes().getLength()>0) {
                                                		favorite.m_photo.m_previous = yproperty.getFirstChild().getNodeValue();
                                                		}
                                                	else {
                                                		favorite.m_photo.m_previous = "";
                                                		}
                                                } else if (yname.equalsIgnoreCase("ThumbnailUrl")){
                                                	favorite.m_photo.m_thumbnailURL = yproperty.getFirstChild().getNodeValue();
                                                } else if (yname.equalsIgnoreCase("TinyAlias")){
                                                	favorite.m_photo.m_tinyAlias = Long.parseLong(yproperty.getFirstChild().getNodeValue());
                                                } else if (xname.equalsIgnoreCase("UnlikedVotes")){
                                                	favorite.m_photo.m_unlikedVotes = Integer.parseInt(yproperty.getFirstChild().getNodeValue());
                                                } else if (yname.equalsIgnoreCase("UploadDate")){
                                                	favorite.m_photo.m_uploadDate = Long.parseLong(yproperty.getFirstChild().getNodeValue());
                                                } else if (yname.equalsIgnoreCase("UploadDateString")){
                                                	favorite.m_photo.m_uploadDateString = yproperty.getFirstChild().getNodeValue();
                                                } else if (yname.equalsIgnoreCase("User")){
                                                	
                                                    NodeList userList = yproperty.getChildNodes();
                                                    
                                                    favorite.m_photo.m_user = new Profile();
                                                    for (int r=0;r<userList.getLength();r++){
                                                        Node userItem  = userList.item(r);
                                                        String zname   = userItem.getNodeName();
                                                        	
                                                        if (zname.equalsIgnoreCase("Comments")){
                                                        	if (userItem.getChildNodes().getLength()>0) {
                                                        		favorite.m_photo.m_user.m_comments = userItem.getFirstChild().getNodeValue();
                                                        		}
                                                        	else {
                                                        		favorite.m_photo.m_user.m_comments = "";
                                                        		}
                                                            }
                                                        if (zname.equalsIgnoreCase("Favorites")){
                                                        	favorite.m_photo.m_user.m_favorites = userItem.getFirstChild().getNodeValue(); 
                                                            }
                                                        if (zname.equalsIgnoreCase("Friends")){
                                                        	favorite.m_photo.m_user.m_friends = userItem.getFirstChild().getNodeValue(); 
                                                            }
                                                        if (zname.equalsIgnoreCase("Id")){
                                                        	favorite.m_photo.m_user.m_id = Long.parseLong(userItem.getFirstChild().getNodeValue()); 
                                                            }
                                                        if (zname.equalsIgnoreCase("MapTypeForProfile")){
                                                        	favorite.m_photo.m_user.m_mapTypeForProfile = userItem.getFirstChild().getNodeValue(); 
                                                            }
                                                        if (zname.equalsIgnoreCase("Photos")){
                                                        	favorite.m_photo.m_user.m_photos = userItem.getFirstChild().getNodeValue(); 
                                                            }
                                                        if (zname.equalsIgnoreCase("ProfileImage")){
                                                        	favorite.m_photo.m_user.m_profileImage = userItem.getFirstChild().getNodeValue(); 
                                                            }
                                                        if (zname.equalsIgnoreCase("ScreenName")){
                                                        	if (userItem.getChildNodes().getLength()>0) {
                                                        		favorite.m_photo.m_user.m_screenName = userItem.getFirstChild().getNodeValue();
                                                        		}
                                                        	else {
                                                        		favorite.m_photo.m_user.m_screenName = "";
                                                        		}
                                                            }
                                                        if (zname.equalsIgnoreCase("Settings")){
                                                        	favorite.m_photo.m_user.m_settings = userItem.getFirstChild().getNodeValue(); 
                                                            }
                                                        if (zname.equalsIgnoreCase("Views")){
                                                        	favorite.m_photo.m_user.m_views = userItem.getFirstChild().getNodeValue(); 
                                                            }
                                                    	}
                                                    
                                                } else if (yname.equalsIgnoreCase("UserId")){
                                                	favorite.m_photo.m_userId = Long.parseLong(yproperty.getFirstChild().getNodeValue());
                                                } else if (yname.equalsIgnoreCase("Views")){
                                                	favorite.m_photo.m_views = Integer.parseInt(yproperty.getFirstChild().getNodeValue());
                                                }
                                            }
                                            
                                        // TweetPhoto tweetPhoto 		= new TweetPhoto();
                                        // VoteStatus vs         		= tweetPhoto.voteStatus(tweetPhoto.m_userId, favorite.m_photo.m_photoId);
                                        // FavoriteQueryResponse fs	= tweetPhoto.favoriteStatus(tweetPhoto.m_userId, favorite.m_photo.m_photoId);  
                                            
                                        // Set our vote status
                                        // favorite.m_photo.m_voteStatus = vs.m_status.equalsIgnoreCase("ThumbsUp") ? 1 : (vs.m_status.equalsIgnoreCase("ThumbsDown") ? 2 : 0);
                                        // favorite.m_photo.m_favorite   = fs.m_isFavorite;
                                    } 
                                }
                            
                            favorites.m_list.addElement(favorite);
                    		}
                    	
                    	}
                }
            }
        } catch (Throwable tr) {
        	System.out.println(tr.toString()); 
			tr.printStackTrace();
       		} 
        return favorites;
    }
}
