package com.tweetphoto.api;

import java.io.IOException;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import net.rim.device.api.io.Base64OutputStream;
import net.rim.device.api.io.http.HttpHeaders;
import net.rim.device.api.io.http.HttpProtocolConstants;
import net.rim.device.api.util.StringUtilities;

class HttpUtils {
  public static final int CONNECTION_DEFAULT = 0;
  public static final int CONNECTION_BIS = 1;
  public static final int CONNECTION_BES = 2;
  public static final int CONNECTION_TCPIP = 3;
  public static final int CONNECTION_WIFI = 4;

  /**
   * This method opens a HTTP connection to the given url. The method used is
   * GET or POST depending on whether postData is null or not. Only the
   * provided connType is used. For example, if the connType is
   * CONNECTION_BES, the connection is tried using the BES only.
   * The only time provided connection type is not used is when the URL
   * contains ";deviceside=".
   *
   * @param url            The url to connect to.
   * @param requestHeaders The headers in the request. May be null or empty.
   * @param postData       Data to be posted to the server. If null, the GET method used
   *                       for the http connection.
   * @param connType       The type of transport (BES / BIS / WIFI / Default) to be used
   *                       for opening connection.
   *
   * @return Opened HttpConnection object or null if some error occurs.
   */
  public static HttpConnection makeHttpConnection(String url,
                                                  HttpHeaders requestHeaders, 
                                                  byte[] postData, 
                                                  int connType, 
                                                  String requestMethod, 
                                                  TweetPhoto tweetPhoto,
                                                  boolean uploadData)
  {
    HttpConnection conn = null;
    OutputStream out    = null;

    if (StringUtilities.startsWithIgnoreCase(url, "www.")) {
    	url = "http://" + url;
    	}
	
	String login = tweetPhoto.m_tokenIdentifier+":"+tweetPhoto.m_tokenSecret;

    try {
      byte[] encoded = Base64OutputStream.encode(login.getBytes(), 0, login.length(), false, false);
      
      if (url.indexOf(";deviceside=") == -1) {
        switch (connType) {
          case CONNECTION_BES:
            url = url + ";deviceside=false";
            break;
          case CONNECTION_BIS:
            url = url + ";XXXXXXXXXXXXXXXX";
            break;
          case CONNECTION_TCPIP:
            url = url + ";deviceside=true";
            break;
          case CONNECTION_WIFI:
            url = url + ";interface=wifi";
        }
      }

      conn = (HttpConnection) Connector.open(url);

      if (requestHeaders != null) {
        String referer = requestHeaders.getPropertyValue("referer");

        boolean sendReferrer = true;

        if (referer != null &&

            StringUtilities.startsWithIgnoreCase(referer, "https:") &&

            !StringUtilities.startsWithIgnoreCase(url, "https:"))
        {
          sendReferrer = false;
        }

        int size = requestHeaders.size();
        for (int i = 0; i < size;) {
          String header = requestHeaders.getPropertyKey(i);
          if (!sendReferrer && header.equals("referer")) {
            requestHeaders.removeProperty(i);
            --size;
            continue;
          }

          String value = requestHeaders.getPropertyValue(i++);
          if (value != null) {
            conn.setRequestProperty(header, value);
          }
        }
      }

      conn.setRequestProperty("Authorization", "Basic " + new String(encoded));     
      conn.setRequestProperty("TPSERVICE", tweetPhoto.m_ServiceName);
      conn.setRequestProperty("TPISOAUTH", tweetPhoto.m_isOAuth ? "True" : "False");
      conn.setRequestProperty("TPAPIKEY", tweetPhoto.m_APIKey);
      conn.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.0");
      conn.setRequestMethod(requestMethod);
      
      System.out.println("URL: " + url); 
      System.out.println("Request Method: " + requestMethod); 
      System.out.println("Service Name: " + tweetPhoto.m_ServiceName); 
      System.out.println("is OAuth?: " + (tweetPhoto.m_isOAuth ? "True" : "False")); 
      System.out.println("TweetPhoto API Key: " + tweetPhoto.m_APIKey); 
      System.out.println("Authorization: " + "Basic " + new String(encoded)); 

      if (requestMethod.equalsIgnoreCase("PUT") || requestMethod.equalsIgnoreCase("POST")) {
    	  
    	  if (uploadData) {
    	    conn.setRequestProperty("TPPOST",    "True");
    	    conn.setRequestProperty("TPMIMETYPE", tweetPhoto.m_mimeType);
    	    conn.setRequestProperty("TPUTF8",    "True");
    	    conn.setRequestProperty("TPMSG",     Base64Coder.encodeString(tweetPhoto.m_comment));
    				
    		if (tweetPhoto.m_latitude!=0.0 && tweetPhoto.m_longitude!=0.0) {
    			conn.setRequestProperty("TPLAT", "" +tweetPhoto.m_latitude);
    			conn.setRequestProperty("TPLONG", "" + tweetPhoto.m_longitude);
    			}
    				
    		if (tweetPhoto.m_tags!=null && tweetPhoto.m_tags.length()>0) {
    			conn.setRequestProperty("TPTAGS", Base64Coder.encodeString(tweetPhoto.m_tags));
    			}
    	  	}
    	  
    	  if (postData!=null) {
    		conn.setRequestProperty(HttpProtocolConstants.HEADER_CONTENT_LENGTH, String.valueOf(postData.length));
      	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        	out = conn.openOutputStream();
        	out.write(postData);
        	out.flush();
        	}
      	}
    }
    catch (IOException e1) {
    	close(conn, null); // Close the connection
      	conn = null;
    	}
    finally {
    	close(null, out); // Close the output, but keep connection open
    	}

    return conn;
  }

  private static void close(HttpConnection con, OutputStream out) {
    if (out != null) {
      try {
        out.close();
      }
      catch (IOException e2) {
      }
    }
    if (con != null) {
      try {
        con.close();
      }
      catch (IOException e) {
      }
    }
  }
}