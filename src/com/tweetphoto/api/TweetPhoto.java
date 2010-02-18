package com.tweetphoto.api;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.StreamConnection;

import net.rim.blackberry.api.browser.URLEncodedPostData;
import net.rim.device.api.io.Base64OutputStream;
import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.io.LineReader;
import net.rim.device.api.io.http.HttpHeaders;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.RadioInfo;
import net.rim.device.api.util.StringProvider;

public class TweetPhoto {

	String	m_APIKey         = null;
	boolean	m_isOAuth        = false;
	String	m_ConsumerKey    = null;
	String	m_ConsumerSecret = null;
	int		m_StatusCode     = 0;
	String	m_LastError      = null;
	
	String	m_tokenIdentifier = null;
	String	m_tokenSecret     = null;
	String	m_tokenVerifier   = null;
	
	String	m_ServiceName     = "Twitter";
	
	int		m_httpStatus	  = 0;
	
	// Logged in user's id
	long	m_userId		  = 0;
	String	m_screenName	  = "";
	
	// Other parms
	double	m_latitude;
	double	m_longitude;
	String	m_comment;
	String	m_tags;
	String	m_mimeType;
	
	public static final int CONNECTION_DEFAULT = 0;
	public static final int CONNECTION_BIS = 1;
	public static final int CONNECTION_BES = 2;
	public static final int CONNECTION_TCPIP = 3;
	public static final int CONNECTION_WIFI = 4;
	
	String describe() {
		String retVal = "APIKey: " + m_APIKey + "\nisOAuth: " + m_isOAuth + ",\nConsumer Key: " + m_ConsumerKey + ",\nConsumer Secret: " + m_ConsumerSecret;		
		retVal += "Token Identifier: " + m_tokenIdentifier + ",\nToken Secret: " + m_tokenSecret + ",\nService Name: " + m_ServiceName;
		return retVal;
	}
	
	// Upload a photo to TweetPhoto
	TweetPhotoResponse uploadPhoto(byte[] photo, String comment, String tags, double latitude, double longitude, String mimeType) {
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/upload2";		
		
		m_latitude  = latitude;
		m_longitude = longitude;
		m_comment   = comment;
		m_tags      = tags;
		m_mimeType  = mimeType;
		
		try {	        
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), photo, getCoverageBasedConnectionType(), "POST", this, true);
			
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 201) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomTweetPhotoResponseParser responseParser = new DomTweetPhotoResponseParser();
				responseParser.m_Xml = xml;
				TweetPhotoResponse response = (TweetPhotoResponse)responseParser.parse();	
				content.close();
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
	}
    
	// Delete a photo location
	TweetPhotoResponse deleteLocation(long photoId) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/photos/" + photoId + "/location";

		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "DELETE", this, false);
	        
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomTweetPhotoResponseParser responseParser = new DomTweetPhotoResponseParser();
				responseParser.m_Xml = xml;
				TweetPhotoResponse response = (TweetPhotoResponse)responseParser.parse();
				content.close();
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}
    
	// Set a photo location
	TweetPhotoResponse setLocation(long photoId, float latitude, float longitude) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/photos/" + photoId + "/location";

		try {
			
			String data = "" + latitude + "," + longitude;
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), data.getBytes(), getCoverageBasedConnectionType(), "PUT", this, false);

			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomTweetPhotoResponseParser responseParser = new DomTweetPhotoResponseParser();
				responseParser.m_Xml = xml;
				TweetPhotoResponse response = (TweetPhotoResponse)responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}
    
	// Set a map type
	Settings setMapType(long userId, int mapType) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/settings/maptype";

		try {
			String data = "" + mapType;
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), data.getBytes(), getCoverageBasedConnectionType(), "PUT", this, false);
			    	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomSettingsParser responseParser = new DomSettingsParser();
				responseParser.m_Xml = xml;
				Settings response = (Settings)responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}
    
	// Set PIN
	Settings setPIN(long userId, int pin) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/settings/pin";

		try {
			String data = "" + pin;
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), data.getBytes(), getCoverageBasedConnectionType(), "PUT", this, false);
    	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomSettingsParser responseParser = new DomSettingsParser();
				responseParser.m_Xml = xml;
				Settings response = (Settings)responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}
    
	// Set Shorten URL
	Settings setShortenUrl(long userId, int shortenUrl) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/settings/shortenurl";

		try {
			String data = "" + shortenUrl;
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), data.getBytes(), getCoverageBasedConnectionType(), "PUT", this, false);
	        m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomSettingsParser responseParser = new DomSettingsParser();
				responseParser.m_Xml = xml;
				Settings response = (Settings)responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}
    
	// Set Do Not Tweet Favorite Photo
	Settings setDoNotTweetFavoritePhoto(long userId, int flag) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/settings/donottweetfavoritephoto";

		try {
			String data = "" + flag;
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), data.getBytes(), getCoverageBasedConnectionType(), "PUT", this, false);
	        m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomSettingsParser responseParser = new DomSettingsParser();
				responseParser.m_Xml = xml;
				Settings response = (Settings)responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}
    
	// Set Hide Viewing Patterns
	Settings setHideViewingPatterns(long userId, int flag) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/settings/hideviewingpatterns";

		try {
			String data = "" + flag;
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), data.getBytes(), getCoverageBasedConnectionType(), "PUT", this, false);
	        m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomSettingsParser responseParser = new DomSettingsParser();
				responseParser.m_Xml = xml;
				Settings response = (Settings)responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}
    
	// Set Hide Votes
	Settings setHideVotes(long userId, int flag) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/settings/hidevotes";

		try {
			String data = "" + flag;
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), data.getBytes(), getCoverageBasedConnectionType(), "PUT", this, false);
	        m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomSettingsParser responseParser = new DomSettingsParser();
				responseParser.m_Xml = xml;
				Settings response = (Settings)responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}
    
	// Delete a photo
	TweetPhotoResponse deletePhoto(long photoId) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/photos/" + photoId;

		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "DELETE", this, false);
	        m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomTweetPhotoResponseParser responseParser = new DomTweetPhotoResponseParser();
				responseParser.m_Xml = xml;
				TweetPhotoResponse response = (TweetPhotoResponse)responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}
    
	// mark photo as "favorite" of a user
	TweetPhotoResponse favorite(long userId, long photoId) {
		
		String urlToRequest = "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/favorites/" + photoId;

		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "POST", this, false);
	        m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 201) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomTweetPhotoResponseParser responseParser = new DomTweetPhotoResponseParser();
				responseParser.m_Xml = xml;				
				TweetPhotoResponse response = (TweetPhotoResponse)responseParser.parse();
				content.close(); 				
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}
    
	// Delete a photo Favorite
	TweetPhotoResponse deleteFavorite(long userId, long photoId) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/favorites/" + photoId;

		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "DELETE", this, false);
	        m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomTweetPhotoResponseParser responseParser = new DomTweetPhotoResponseParser();
				responseParser.m_Xml = xml;
				TweetPhotoResponse response = (TweetPhotoResponse)responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}

	// Post a comment
	AddPhotoCommentResponse comment(long userId, long photoId, String commentText) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/comments/" + photoId + ";deviceside=true";

		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), commentText.getBytes(), getCoverageBasedConnectionType(), "POST", this, false);
	        m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 201) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomAddCommentResponseParser responseParser = new DomAddCommentResponseParser();
				responseParser.m_Xml = xml;				
				AddPhotoCommentResponse response = (AddPhotoCommentResponse)responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}
    
	// Delete a photo Comment
	TweetPhotoResponse deleteComment(long userId, long photoId, long commentId) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/comments/" + photoId + "/" + commentId + ";deviceside=true";

		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "DELETE", this, false);
	        m_httpStatus = httpConn.getResponseCode();

			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomTweetPhotoResponseParser responseParser = new DomTweetPhotoResponseParser();
				responseParser.m_Xml = xml;
				TweetPhotoResponse response = (TweetPhotoResponse)responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}
	  
    // Get friends
    Profiles friends(long userId, int ps, int ind, String sort) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/friends?ps=" + ps + "&ind=" + ind + "&sort=" + sort + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomProfilesParser profilesParser = new DomProfilesParser();
				profilesParser.m_Xml = xml;
				Profiles profiles = profilesParser.parse();
				content.close(); 
				return profiles;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
    }
	  
    // Link a service to the currently auth'd user
    TweetPhotoResponse linkService(String apiKey, String identityToken, String identitySecret, String serviceName) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/link" + ";deviceside=true";

		try {	        
			String xmlBlob = "<LinkedProfile xmlns=\"http://tweetphotoapi.com\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"><APIKey>" + apiKey + "</APIKey><IdentitySecret>" + identitySecret + "</IdentitySecret><IdentityToken>" + identityToken + "</IdentityToken><Service>" + serviceName + "</Service></LinkedProfile>";
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), xmlBlob.getBytes(), getCoverageBasedConnectionType(), "POST", this, false);
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomTweetPhotoResponseParser responseParser = new DomTweetPhotoResponseParser();
				responseParser.m_Xml = xml;
				TweetPhotoResponse response = responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
    }	  
	  
    // Un-Link a service connected to the currently auth'd user
    TweetPhotoResponse unlinkService(String serviceName) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/unlink/" + serviceName + ";deviceside=true";

		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "DELETE", this, false);
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomTweetPhotoResponseParser responseParser = new DomTweetPhotoResponseParser();
				responseParser.m_Xml = xml;
				TweetPhotoResponse response = responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
    }	  
	  
    // Get linked services profiles
    Profiles linkedServices() {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/profiles" + ";deviceside=true";

		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomProfilesParser profilesParser = new DomProfilesParser();
				profilesParser.m_Xml = xml;
				Profiles profiles = profilesParser.parse();
				content.close(); 
				return profiles;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
    }	  
    
    // Get all friends
    Profiles friends(long userId, String sort) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/friends?sort=" + sort + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomProfilesParser profilesParser = new DomProfilesParser();
				profilesParser.m_Xml = xml;
				Profiles profiles = profilesParser.parse();
				content.close(); 
				return profiles;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
    }
	  
    // Get photo viewers
    Profiles photoViewers(long photoId, int ps, int ind) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/photos/" + photoId + "/viewers?ps=" + ps + "&ind=" + ind + "&sort=desc" + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomProfilesParser profilesParser = new DomProfilesParser();
				profilesParser.m_Xml = xml;
				Profiles profiles = profilesParser.parse();
				content.close(); 
				return profiles;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
    }
	
    // Get photo comments by user
    Comments photoCommentsByUser(long userId, int ps, int ind, String sort) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/photos/comments?ps=" + ps + "&ind=" + ind + "&sort=" + sort + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomCommentsParser commentParser = new DomCommentsParser();
				commentParser.m_Xml = xml;				
				Comments comments = commentParser.parse();
				content.close(); 
				return comments;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
    }
		
    // Get photo comments
    Comments photoComments(long photoId, int ps, int ind) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/photos/" + photoId + "/comments?ps=" + ps + "&ind=" + ind + "&sort=desc" + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomCommentsParser commentParser = new DomCommentsParser();
				commentParser.m_Xml = xml;				
				Comments comments = commentParser.parse();
				content.close(); 				
				return comments;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
    }
    
    // Get user comments
    Comments userComments(long userId, int ps, int ind, String sort) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/comments?ps=" + ps + "&ind=" + ind + "&sort=" + sort + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomCommentsParser commentParser = new DomCommentsParser();
				commentParser.m_Xml = xml;				
				Comments comments = commentParser.parse();
				content.close(); 
				return comments;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
    }
    
    // Get all user comments
    Comments userComments(long userId, String sort) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/comments?sort=" + sort + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomCommentsParser commentParser = new DomCommentsParser();
				commentParser.m_Xml = xml;				
				Comments comments = commentParser.parse();
				content.close(); 
				return comments;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
    }
    
    // Get all photo comments
    Comments photoComments(long photoId, String sort) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/photos/" + photoId + "/comments?sort=" + sort + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomCommentsParser commentParser = new DomCommentsParser();
				commentParser.m_Xml = xml;				
				Comments comments = commentParser.parse();
				content.close(); 
				return comments;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
    }
    
    // Get photo comments
    Comments photoComments(long photoId, int ps, int ind, String sort) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/photos/" + photoId + "/comments?ps=" + ps + "&ind=" + ind + "&sort=" + sort + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomCommentsParser commentParser = new DomCommentsParser();
				commentParser.m_Xml = xml;				
				Comments comments = commentParser.parse();
				content.close(); 
				return comments;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
    }
	
    // Get favorites
    Favorites favorites(long userId, int ps, int ind, String sort) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/favorites?ind=" + ind + "&ps=" + ps + "&sort=" + sort + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomFavoritesParser favoritesParser = new DomFavoritesParser();
				favoritesParser.m_Xml = xml;				
				Favorites favorites = favoritesParser.parse();
				content.close(); 
				return favorites;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
    }
	
    // Get all favorites
    Favorites favorites(long userId, String sort) {
		
		String urlToRequest = "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/favorites?sort=" + sort + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomFavoritesParser favoritesParser = new DomFavoritesParser();
				favoritesParser.m_Xml = xml;				
				Favorites favorites = favoritesParser.parse();
				content.close(); 
				return favorites;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
    }
    
	// Sign In to TweetPhoto
	Profile signIn(String apiKey, String serviceName, boolean isOAuth, String userIdentity, String userSecret) {
		
		m_APIKey  			= apiKey;
		m_isOAuth 			= isOAuth;
		m_ServiceName 		= serviceName;
		m_tokenIdentifier	= userIdentity;
		m_tokenSecret		= userSecret;
		
		String urlToRequest 			= m_isOAuth ? "http://tweetphotoapi.com/api/tpapi.svc/oauthsignin" : "http://tweetphotoapi.com/api/tpapi.svc/signin";
		
		// Facebook connect
		if (m_ServiceName.equalsIgnoreCase("Facebook") ) {
			urlToRequest = "http://tweetphotoapi.com/api/tpapi.svc/facebooksignin";
			}
		
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);
    	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
		        System.out.println("Response: " + httpConn.getResponseMessage());
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomProfileParser profileParser = new DomProfileParser();
				profileParser.m_Xml = xml;
				Profile profile = (Profile)profileParser.parse();
				
				m_userId     = profile.m_id;
				m_screenName = profile.m_screenName;
				
				content.close(); 
				return profile;
    			}
        } catch (Throwable tr) {
        	System.out.println(tr.toString()); 
			tr.printStackTrace();
    		}
    
		return null;
		}

	// Get the public feed (all)
	SocialFeed publicFeed(String sort) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/socialfeed?sort=" + sort + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomSocialFeedParser profileParser = new DomSocialFeedParser();
				profileParser.m_Xml = xml;
				SocialFeed feed = profileParser.parse();
				content.close(); 
				return feed;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
	}

	// Get the social feed
	SocialFeed socialFeed(long userId, int ps, int ind, String sort) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/feed?ps=" + ps + "&ind=" + ind + "&sort=" + sort + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomSocialFeedParser profileParser = new DomSocialFeedParser();
				profileParser.m_Xml = xml;
				SocialFeed feed = profileParser.parse();
				content.close(); 
				return feed;
    			}
			}
		catch (Exception ex)
    		{
			ex.printStackTrace();
    		}
    
		return null;
	}

	// Get the public feed
	SocialFeed publicFeed(int ps, int ind, String sort) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/socialfeed?ps=" + ps + "&ind=" + ind + "&sort=" + sort + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomSocialFeedParser profileParser = new DomSocialFeedParser();
				profileParser.m_Xml = xml;
				SocialFeed feed = profileParser.parse();
				content.close(); 
				return feed;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
	}
	
	// Get the social feed (friends)
	SocialFeed socialFeed(long userId) {
		return socialFeed(userId, 10, 0, "desc");
	}

	// Get Next Photo
	Photo next(long photoId) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/photos/" + photoId + "/next" + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomPhotoParser photoParser = new DomPhotoParser();
				photoParser.m_Xml = xml;
				Photo photo = photoParser.parse();
				content.close(); 
				return photo;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
	}

	// Get Previous Photo
	Photo previous(long photoId) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/photos/" + photoId + "/previous" + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomPhotoParser photoParser = new DomPhotoParser();
				photoParser.m_Xml = xml;
				Photo photo = photoParser.parse();
				content.close(); 
				return photo;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
	}

	// Get Photo Metadata
	Photo photoMetaData(long photoId) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/photos/" + photoId + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomPhotoParser photoParser = new DomPhotoParser();
				photoParser.m_Xml = xml;
				Photo photo = photoParser.parse();
				content.close(); 
				return photo;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
	}

	// Get the user profile
	Profile userProfile(long userId) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200 && m_httpStatus != 201) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomProfileParser profileParser = new DomProfileParser();
				profileParser.m_Xml = xml;
				Profile profile = profileParser.parse();
				content.close(); 
				return profile;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
	}

	// Get the user profile
	Profile userProfile(String userName) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userName + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200 && m_httpStatus != 201) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomProfileParser profileParser = new DomProfileParser();
				profileParser.m_Xml = xml;
				Profile profile = profileParser.parse();
				content.close(); 
				return profile;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
	}

	// Get the user settings
	Settings settings(long userId) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/settings" + ";deviceside=true";
		
		try {	        
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200 && m_httpStatus != 201) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomSettingsParser settingsParser = new DomSettingsParser();
				settingsParser.m_Xml = xml;
				Settings settings = settingsParser.parse();
				content.close(); 
				return settings;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
	}
    
	// mark photo as "viewed" by the user
	TweetPhotoResponse view(long userId, long photoId) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/views/" + photoId + ";deviceside=true";
		
		try {	        
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "POST", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 201) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomTweetPhotoResponseParser responseParser = new DomTweetPhotoResponseParser();
				responseParser.m_Xml = xml;
				TweetPhotoResponse response = (TweetPhotoResponse)responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}

	// vote for a photo
	TweetPhotoResponse vote(long photoId, String voteCast) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/photos/" + photoId + "/" + voteCast + ";deviceside=true";
		
		try {	        
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "PUT", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomTweetPhotoResponseParser responseParser = new DomTweetPhotoResponseParser();
				responseParser.m_Xml = xml;
				TweetPhotoResponse response = (TweetPhotoResponse)responseParser.parse();
				content.close(); 
				return response;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
		}
	
	// Get favorite status for a photo
	FavoriteQueryResponse favoriteStatus(long userId, long photoId) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/favorites/" + photoId + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomFavoriteQueryResponseParser favoriteParser = new DomFavoriteQueryResponseParser();
				favoriteParser.m_Xml = xml;
				FavoriteQueryResponse favoriteStatus = favoriteParser.parse();
				content.close(); 
				return favoriteStatus;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
	}

	// Get all photos
	Photos photos(long userId, String sort) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/photos?sort=" + sort + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomPhotosParser photoParser = new DomPhotosParser();
				photoParser.m_Xml = xml;
				Photos photos = photoParser.parse();
				content.close(); 
				return photos;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
	}

	// Get photos
	Photos photos(long userId, int ps, int ind, String sort) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/photos?ps=" + ps + "&ind=" + ind + "&sort=" + sort + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomPhotosParser photoParser = new DomPhotosParser();
				photoParser.m_Xml = xml;
				Photos photos = photoParser.parse();
				content.close(); 
				return photos;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
	}
	
	// Get leaderboard (types are viewed, voted, or commented
	Photos leaderboard(String type) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/leaderboard/uploadedtoday/" + type + ";deviceside=true";
		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomPhotosParser photoParser = new DomPhotosParser();
				photoParser.m_Xml = xml;
				Photos photos = photoParser.parse();
				content.close(); 
				return photos;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
	}
	
	// Get vote status for a photo
	VoteStatus voteStatus(long userId, long photoId) {
		
		String urlToRequest 			= "http://tweetphotoapi.com/api/tpapi.svc/users/" + userId + "/votes/" + photoId + ";deviceside=true";

		try {
			HttpConnection httpConn = HttpUtils.makeHttpConnection(urlToRequest, new HttpHeaders(), null, getCoverageBasedConnectionType(), "GET", this, false);            	
			m_httpStatus = httpConn.getResponseCode();
   	
			if (m_httpStatus != 200) {
	           	System.out.println("Error: " + m_httpStatus); 
			} else {
				InputStream content = httpConn.openInputStream();
				String xml = convertStreamToString(content);
				DomVoteStatusParser voteParser = new DomVoteStatusParser();
				voteParser.m_Xml = xml;
				VoteStatus voteStatus = voteParser.parse();
				content.close(); 
				return voteStatus;
    			}
        } catch (Throwable tr) {
			tr.printStackTrace();
    		}
    
		return null;
	}
	public static int getCoverageBasedConnectionType() {
	    if (CoverageInfo.isCoverageSufficient( 
	         RadioInfo.WAF_WLAN)) 
	    {
	        return CONNECTION_WIFI;
	    }

	    if (CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_BIS_B)) {
	        return CONNECTION_BIS;
	    }

	    if (CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_MDS)) {
	        return CONNECTION_BES;
	    }

	    return CONNECTION_TCPIP;
	}	

	// Convert stream to string
	String convertStreamToString(InputStream is) throws IOException {
	
	if (is != null) {
		try {
			StringBuffer sb = new StringBuffer();
			LineReader reader = new LineReader(is);
            
		    for(;;)
		    	{
		        try
		        	{
		            String line = new String(reader.readLine());
		            sb.append(line);
		        	}
		        catch(EOFException eof)
		        	{
		            // We've reached the end of the file.
		            break;
		        	}
		        catch(IOException ioe)
		        	{
		            // Error reading data from file
		        	}                
		    	}
		    return sb.toString();
			}
		catch (Throwable tr) {
			} 
		return null;
		} 
	else {        
		return "";
		}   
	}

}
