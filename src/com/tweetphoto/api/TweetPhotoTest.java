package com.tweetphoto.api;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;

public class TweetPhotoTest extends UiApplication {
	public TweetPhotoTest() {
		MainScreen screen = new MainScreen();
		
        TweetPhoto tweetPhoto = new TweetPhoto();
        
        // Clear screen
        System.out.print("\f");
        System.out.println("/// TweetPhoto Started ///");
        
        // --- TweetPhoto Sign In
        // --- APIKey, Service Name (Twitter, Foursquare, Facebook), is OAuth (true/false), username / userKey, password / userSecret
        Profile profile = tweetPhoto.signIn("your_tweetphoto_api_key", "Facebook_or_Twitter_or_Foursquare", false, "identityToken", "identitySecret");
        if (profile!=null) { 
    		System.out.println("Login succeeded: " + profile.describe()); 
    		screen.add(new LabelField("TweetPhoto Sign In: " + profile.describe()));
    		pushScreen(screen);
        	}
        else {
    		System.out.println("Login error: " + tweetPhoto.m_httpStatus); 
    		screen.add(new LabelField("TweetPhoto Sign In Error: " + tweetPhoto.m_httpStatus));
    		pushScreen(screen);
        	}
        
        // Link a service to the currently auth'd user.
        //TweetPhotoResponse response = tweetPhoto.linkService(tweetPhoto.m_APIKey, "identityToken", "identitySecret", "Facebook_or_Twitter_or_Foursquare");
        //if (response!=null) { 
        //	System.out.println("TweetPhoto Link Service: " + response.describe());
        //	}
        
        // Un-Link a service linked to the currently auth'd user.
        // TweetPhotoResponse response = tweetPhoto.unlinkService("Facebook");
        // if (response!=null) { 
        //	System.out.println("TweetPhoto Un-Link Service: " + response.describe());
        //	}
        
        // Query for linked services connected to the currently auth'd user
        //Profiles profiles = tweetPhoto.linkedServices();
        //if (profiles!=null) {
    	//	System.out.println("TweetPhoto Profiles with Linked Services: " + profiles.describe()); 
        //    for (int i=0; i<profiles.m_list.size(); i++) {
        //     	Profile profile2 = (Profile)profiles.m_list.elementAt(i);
        //		System.out.println("TweetPhoto Profiles with Linked Services: " + profile2.describe()); 
        //     	}            
        //	}
        
        // --- Add Photo Comment
        // AddPhotoCommentResponse comment = tweetPhoto.comment(2733857, 11702762, "Test... will be deleted next.");
        // if (comment!=null) {
     	//	System.out.println("TweetPhoto Photo Comment: " + comment.describe()); 
        //	}
        
        // --- Delete a Photo Comment
        //TweetPhotoResponse delete = tweetPhoto.deleteComment(2733857, 11702762, 3202484);
        // if (delete!=null) {
        //  	System.out.println("TweetPhoto Photo Delete Comment: " + delete.describe()); 
        //	}
        
        // --- Favorite a Photo
        // TweetPhotoResponse favorite = tweetPhoto.favorite(2733857, 11702762);
        // if (favorite!=null) {
        //   	System.out.println("TweetPhoto Photo Favorite: " + favorite.describe()); 
        //	}
        
        // --- Delete a Photo Favorite
        // TweetPhotoResponse delete = tweetPhoto.deleteFavorite(2733857, 11702762);
        // if (delete!=null) {
        //	System.out.println("TweetPhoto Photo Delete Favorite: " + delete.describe()); 
        //	}
        
        // --- Delete a Photo
        // TweetPhotoResponse delete2 = tweetPhoto.deletePhoto(11689869);
        // if (delete2!=null) {
        //    System.out.println("TweetPhoto Photo Delete: " + delete2.describe()); 
        //	}
        
        // --- Get All User Comments, sort asc / desc
        //Comments comments = tweetPhoto.userComments(2733857, "desc");
        //if (comments!=null) {
        //	System.out.println("TweetPhoto Photo Comments: " + comments.describe()); 
        //	for (int i=0; i<comments.m_list.size(); i++) {
        //		Comment comment = (Comment)comments.m_list.elementAt(i);
        //		System.out.println("TweetPhoto All Photo Comment: " + comment.describe()); 
        //		}
        //	}
        
        // --- Get User Comments (page size 10, offset 0, sort asc / desc
        //Comments comments2 = tweetPhoto.userComments(2733857, 10, 0, "desc");
        //if (comments2!=null) {
        //    System.out.println("TweetPhoto Photo Comments: " + comments2.describe()); 
        //    for (int i=0; i<comments2.m_list.size(); i++) {
        //     	Comment comment = (Comment)comments2.m_list.elementAt(i);
        //        System.out.println("TweetPhoto All Photo Comment: " + comment.describe()); 
        //    	}
        //    }
        
        // --- Get All Photo Comments, sort asc / desc
        //Comments comments = tweetPhoto.photoComments(11702762, "desc");
        //if (comments!=null) {
        //    System.out.println("TweetPhoto Photo Comments: " + comments.describe()); 
        //    for (int i=0; i<comments.m_list.size(); i++) {
        //     	Comment comment = (Comment)comments.m_list.elementAt(i);
        //        System.out.println("TweetPhoto All Photo Comment: " + comment.describe()); 
        //    	}
        //    }
        
        // --- Get Photo Comments, page size=10, offset=0, sort asc / desc
        // Comments comments = tweetPhoto.photoComments(11702762, 10, 0, "desc");
        //if (comments!=null) {
        //    System.out.println("TweetPhoto Photo Comments: " + comments.describe()); 
        //    for (int i=0; i<comments.m_list.size(); i++) {
        //     	Comment comment = (Comment)comments.m_list.elementAt(i);
        //        System.out.println("TweetPhoto All Photo Comment: " + comment.describe()); 
        //    	}
        //    }
        
        // --- Get Photo Comments for User, sort asc / desc
        //Comments comments = tweetPhoto.photoCommentsByUser(2733857, 10, 0, "desc");
        //if (comments!=null) {
        //    System.out.println("TweetPhoto Photo Comments: " + comments.describe()); 
        //    for (int i=0; i<comments.m_list.size(); i++) {
        //    	Comment comment = (Comment)comments.m_list.elementAt(i);
        //    	System.out.println("TweetPhoto All Photo Comment: " + comment.describe()); 
        //    	}
        //    }
        
        // --- Get Favorites for User, page size=10, index=0, sort asc / desc
        //Favorites favorites = tweetPhoto.favorites(2733857, 10, 0, "desc");
        //if (favorites!=null) {
        //	System.out.println("TweetPhoto User Favorites: " + favorites.describe()); 
        // 	for (int i=0; i<favorites.m_list.size(); i++) {
        //  	Favorite favorite = (Favorite)favorites.m_list.elementAt(i);
        //    	System.out.println("TweetPhoto User Favorite: " + favorite.describe()); 
        //    	System.out.println("TweetPhoto User Favorite Photo: " + favorite.m_photo.describe()); 
        //    	System.out.println("TweetPhoto User Favorite Photo User: " + favorite.m_photo.m_user.describe()); 
        // 		}
        //	}
         
        // --- Get All Favorites for User, sort asc / desc
        //Favorites favorites2 = tweetPhoto.favorites(2733857, "asc");
        //if (favorites2!=null) {
        //	System.out.println("TweetPhoto User Favorites: " + favorites2.describe()); 
        //	for (int i=0; i<favorites2.m_list.size(); i++) {
        //		Favorite favorite = (Favorite)favorites2.m_list.elementAt(i);
        //		System.out.println("TweetPhoto User Favorite: " + favorite.describe()); 
        //		System.out.println("TweetPhoto User Favorite Photo: " + favorite.m_photo.describe()); 
        //		System.out.println("TweetPhoto User Favorite Photo User: " + favorite.m_photo.m_user.describe()); 
     	//		}
    	//	}
        
        // --- Get Friends (page size 10, offset 0, sort asc / desc)
        //Profiles friends = tweetPhoto.friends(2733857, 10, 0, "desc");
        //if (friends!=null) {
        //	System.out.println("TweetPhoto Friends: " + friends.describe()); 
        //	for (int i=0; i<friends.m_list.size(); i++) {
        //		Profile friend = (Profile)friends.m_list.elementAt(i);
        //    	System.out.println("TweetPhoto Friend: " + friend.describe()); 
        //		}
        //	}
        
        // --- Get All Friends (sort asc / desc) --- CAREFUL: USER COULD HAVE THOUSANDS OF FRIENDS!!!
        //Profiles friends2 = tweetPhoto.friends(2733857, "asc");
        //if (friends2!=null) {
        //	System.out.println("TweetPhoto Friends: " + friends2.describe()); 
        //	for (int i=0; i<friends2.m_list.size(); i++) {
        //		Profile friend = (Profile)friends2.m_list.elementAt(i);
        //		System.out.println("TweetPhoto Friend: " + friend.describe()); 
    	//		}
    	//	}
        
        // --- Get Leaderboard (viewed, voted, commented)
        //Photos photos = tweetPhoto.leaderboard("viewed");
        //if (photos!=null) {
        //  System.out.println("TweetPhoto Leaderboard: " + photos.describe()); 
        // 	for (int i=0; i<photos.m_list.size(); i++) {
        // 		Photo photo = (Photo)photos.m_list.elementAt(i);
        //      System.out.println("TweetPhoto Photo: " + photo.describe()); 
        // 		}
        //	}
        
        // --- Get Next Photo
        //Photo photo = tweetPhoto.next(11689869);
        //if (photo!=null) {
        //    System.out.println("TweetPhoto Next: " + photo.describe()); 
        //	}
         
        // --- Get Previous Photo
        //Photo photo2 = tweetPhoto.previous(11689869);
        //if (photo2!=null) {
        //    System.out.println("TweetPhoto Previous: " + photo2.describe()); 
        //	}
        
        // --- Get All Photos (sort asc/desc)
        //Photos photos = tweetPhoto.photos(2733857, "asc");
        //if (photos!=null) {
        //	System.out.println("TweetPhoto All Photos: " + photos.describe()); 
        //	for (int i=0; i<photos.m_list.size(); i++) {
        //		Photo photo3 = (Photo)photos.m_list.elementAt(i);
        //    	System.out.println("TweetPhoto Photo: " + photo3.describe()); 
        // 		}
        //	}
         
        // --- Get Photos (page size=10, index=0, sort asc/desc)
        // Photos photos = tweetPhoto.photos(2733857, 10, 0, "asc");
        // if (photos!=null) {
        //	System.out.println("TweetPhoto All Photos: " + photos.describe()); 
        //	for (int i=0; i<photos.m_list.size(); i++) {
        //		Photo photo = (Photo)photos.m_list.elementAt(i);
        //    	System.out.println("TweetPhoto Photo: " + photo.describe()); 
        // 		}
        //	}
        
        // --- Get Photo Metadata
        //Photo photo = tweetPhoto.photoMetaData(11497217);
        //if (photo!=null) {
        //	System.out.println("TweetPhoto Photo Metadata: " + photo.describe()); 
        //	for (int i=0; i<photo.m_photoComments.size(); i++) {
        //		Comment comment = (Comment)photo.m_photoComments.elementAt(i);
        //    	System.out.println("TweetPhoto Photo Comment (" + i + "): " + comment.describe()); 
        //    	}
        //	}
        
        // --- Get Public Feed
        //SocialFeed social = tweetPhoto.publicFeed(10, 0, "desc");
        //if (social!=null) {
        //	System.out.println("TweetPhoto Public Feed: " + social.describe()); 
        //	for (int i=0; i<social.m_list.size(); i++) {
        //		SocialFeedEvent event = (SocialFeedEvent)social.m_list.elementAt(i);
        //    	System.out.println("TweetPhoto Social Feed Event: " + event.describe()); 
        //   		}
        //	}
        
        // --- Get Social Feed by User Id
        //SocialFeed social2 = tweetPhoto.socialFeed(2733857, 10, 0, "desc");
        //if (social2!=null) {
        //	System.out.println("TweetPhoto Social Feed: " + social2.describe()); 
        //	for (int i=0; i<social2.m_list.size(); i++) {
        //		SocialFeedEvent event = (SocialFeedEvent)social2.m_list.elementAt(i);
        //		System.out.println("TweetPhoto Social Feed Event: " + event.describe()); 
       	//		}
    	//	}
        
        // --- Get Profile by User Id
        //Profile userProfile = tweetPhoto.userProfile(2733857);
        //if (userProfile!=null) {
        //    System.out.println("TweetPhoto Profile: " + userProfile.describe()); 
        //	}
        
        // --- Get Profile by User Name
        //Profile userProfile2 = tweetPhoto.userProfile("davidjhinson");
        //if (userProfile2!=null) {
        //    System.out.println("TweetPhoto Profile: " + userProfile2.describe()); 
        //	}
        
        // --- Mark Photo as Viewed
        //TweetPhotoResponse view = tweetPhoto.view(2733857, 11742972);
        //if (view!=null) {
        //	System.out.println("TweetPhoto Viewed: " + view.describe()); 
        //	}
        
        // --- Delete Photo Location
        // TweetPhotoResponse view = tweetPhoto.deleteLocation(11702762);
        //	if (view!=null) {
        //	System.out.println("TweetPhoto Delete Location: " + view.describe());
        //	}
        
        // --- Set Photo Location
        // TweetPhotoResponse view2 = tweetPhoto.setLocation(11702762, (float)24.558521, (float)-81.78257);
        // if (view2!=null) {
        //	System.out.println("TweetPhoto Set Location: " + view2.describe()); 
        //	}
        
        // --- Get User Settings
        // Settings settings = tweetPhoto.settings(2733857);
        // if (settings!=null) {
        //	System.out.println("TweetPhoto Settings: " + settings.describe()); 
        //	}
        
        // --- Set Map Type
        // Settings view = tweetPhoto.setMapType(2733857, 1);
        // if (view!=null) {
        //	System.out.println("TweetPhoto Set Map Type: " + view.describe());
        //	}
        
        // --- Set PIN
        // Settings view2 = tweetPhoto.setPIN(2733857, 9999);
        // if (view2!=null) {
        //	System.out.println("TweetPhoto Set PIN: " + view2.describe());
        //	}
        
        // --- Set Shorten Url (0=No, 1=Yes)
        // Settings view3 = tweetPhoto.setShortenUrl(2733857, 1);
        // if (view3!=null) {
        //	System.out.println("TweetPhoto Set Shorten Url: " + view3.describe());
        //	}
        
        // --- Set Do Not Tweet Favorite Photo (0=No, 1=Yes)
        // Settings view4 = tweetPhoto.setDoNotTweetFavoritePhoto(2733857, 1);
       	// if (view4!=null) {
    	//	System.out.println("TweetPhoto Set Do Not Tweet Favorite Photo: " + view4.describe());
        //	}
        
        // --- Set Hide Viewing Patterns (0=No, 1=Yes)
        // Settings view5 = tweetPhoto.setHideViewingPatterns(2733857, 1);
        // if (view5!=null) {
       	//	System.out.println("TweetPhoto Set Hide Viewing Patterns: " + view5.describe());
        //	}
        
        // --- Set Hide Votes (0=No, 1=Yes)
        // Settings view6 = tweetPhoto.setHideVotes(2733857, 1);
        // if (view6!=null) {
        //	System.out.println("TweetPhoto Set Hide Votes: " + view6.describe());
        //	}

        // --- Upload a photo
		// try {	        
		//	EncodedImage image = EncodedImage.getEncodedImageResource("testshot.jpg");
			 
	        // --- Upload a photo
	    //	TweetPhotoResponse response2 = tweetPhoto.uploadPhoto(image.getData(), "This is a Blackberry library sample", "Test, Java API, Demo", 24.558521, -81.78257, "image/jpg");
	        
	    //   if (response2!=null) {
	    //        System.out.println("TweetPhoto Upload: " + response2.describe());
	    //    	}
		//	}
		// catch (Throwable tr) {
		//	tr.printStackTrace();
		// }
        
        // --- Vote (thumbsup / thumbsdown)
        // TweetPhotoResponse vote = tweetPhoto.vote(11702762, "thumbsup");
        // if (vote!=null) {
        //	System.out.println("TweetPhoto Vote: " + vote.describe());
        //	}
        
        // --- Vote Status (thumbsup / thumbsdown)
        // VoteStatus vote2 = tweetPhoto.voteStatus(11702762, 11702762);
        //if (vote2!=null) {
        //	System.out.println("TweetPhoto Vote Status: " + vote2.describe());
        //	}
 	}
 
	public static void main(String[] args) {
		new TweetPhotoTest().enterEventDispatcher();
	}
}