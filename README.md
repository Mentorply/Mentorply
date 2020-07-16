# MENTORPLY

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Schema](#Schema)

## Overview
### Description
Mentorply is an Android app that allows people to find mentorship groups and join them based on their interests. People can also start their own mentorship groups and easily create a mentorship program that works for their school/community. 

### App Evaluation
- **Category:** Social Networking / Education
- **Mobile:** This app would be primarily developed for mobile but would perhaps be just as viable on a computer, such as tinder or other similar apps. Functionality wouldn't be limited to mobile devices, however mobile version could potentially have more features.
- **Story:** Students who need advice on a certain topic or need help navigating the next steps of their life can go to this app, put in their information, and join mentorship programs and find mentors that can help them. They can also create their own mentorship programs/meeting groups and use it for their own purposes. 
- **Market:** Any university or company that has “senior” and “junior” members can use this as a way to connect and help the newer members.
- **Habit:** This can be used as a social platform for students so that they can connect with one another and find answers to specific questions. This would be used for communicating with mentors/mentees and read discussion boards, so this can be used on a daily basis.
- **Scope:** etc
## Product Spec
### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User logs in to access previous chats and preference settings
* Users can select from list of different menteesmentors and be paired with them
* Matches have a chat window to get to know each other.
* Profile pages for each user
* Settings (Accesibility, Notification, General, etc.)

**Optional Nice-to-have Stories**

* Program editing page
* Program profile pages

### 2. Screen Archetypes

* Login 
* Register - User signs up or logs into their account
   * Upon Download/Reopening of the application, the user is prompted to log in to gain access to their profile information to be properly matched with another person. 
   * ...
* Messaging Screen - Chat for users to communicate (direct 1-on-1)
   * Upon selecting music choice users matched and message screen opens
* Profile Screen 
   * Allows user to upload a photo and fill in information that is interesting to them and others
* Settings Screen
   * Lets people change language, and app notification settings.

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Programs
* Chats
* Settings

Optional:
* Explore

**Flow Navigation** (Screen to Screen)
* Forced Log-in -> Account creation if no log in is available
* Join Page -> Jumps to Entering a Program Code -> Pairing Page
* Profile -> Text field to be modified. 
* Settings -> Toggle settings

## Wireframes
<img src="https://i.imgur.com/dpmgKPU.jpg" width=800><br>

## Schema 
### Models
#### Post

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | author        | Pointer to User| image author |
   | image         | File     | image that user posts |
   | caption       | String   | image caption by author |
   | commentsCount | Number   | number of comments that has been posted to an image |
   | likesCount    | Number   | number of likes for the post |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |
   
#### User

   | Property | Type | Description |
| ------------ | ------------- | ------------- |
| First Name | String | Keeps a track of their first name. | 
| Last Name | String | Keeps a track of their last name. | 
| Tag | String | Identifies a feature associated with the user. |  
| ID | Integer| A unique ID that is associated with each user for identification. | 
| Profile Picture | Image (possibly string to keep the URL) | A picture associated with the user whenever the account is mentioned. |  
| LinkedIn Profile URL| String |Used for verification and can be used for API calls. | 
| Username | String | A name that users can use to associate with their profile (may replace userID) | 
| Password | String | A password that users can use to login with their account | 
| Description | String |A general description that users can use for their profiles and different areas | 
| Rooms Affiliated with | String | A list of all of the mentorship programs that this user is in.  | 

### Networking
#### List of network requests by screen
   - Home Feed Screen
      - (Read/GET) Query all posts where user is author
         ```swift
         let query = PFQuery(className:"Post")
         query.whereKey("author", equalTo: currentUser)
         query.order(byDescending: "createdAt")
         query.findObjectsInBackground { (posts: [PFObject]?, error: Error?) in
            if let error = error { 
               print(error.localizedDescription)
            } else if let posts = posts {
               print("Successfully retrieved \(posts.count) posts.")
           // TODO: Do something with posts...
            }
         }
         ```
      - (Create/POST) Create a new like on a post
      - (Delete) Delete existing like
      - (Create/POST) Create a new comment on a post
      - (Delete) Delete existing comment
   - Create Post Screen
      - (Create/POST) Create a new post object
   - Profile Screen
      - (Read/GET) Query logged in user object
      - (Update/PUT) Update user profile image
