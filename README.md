# CS-180-project
## Introduction

In the digital age, social interaction platforms have transcended traditional communication barriers, enabling people from all over the world to connect, share, and engage with content in real-time. Recognizing the pivotal role these platforms play in today's social fabric, our Java project team is excited to embark on creating a robust, user-friendly social interaction platform. This platform is designed to foster a dynamic community where users can effortlessly interact and express themselves. The project encapsulates several critical features that aim to provide a comprehensive social experience tailored to meet the diverse needs of its users.

**New User Account Creation and Secure Login:** At the heart of our platform is a user-friendly interface for account creation, ensuring a smooth and welcoming entry point for new users. Security is paramount; therefore, each account will be safeguarded with password-protected logins, ensuring that user data remains secure and private.

**Interactive Post and Feed System:** Central to our platform is the ability to make posts and display them in a personalized feed. This feed will not only show the user's own posts but also aggregate and display posts from their friends, creating a continuous stream of content that is relevant and engaging.

**Engagement Tools:** To foster interaction, our platform will include mechanisms for upvoting or downvoting posts, which helps in curating content quality and relevance. Users can also hide posts from their feed, allowing for a customizable content experience. Comments on posts further engage the community, allowing for discussions and interactions within the content shared. Any user can upvote or downvote comments, contributing to the community moderation of the content. Importantly, post owners and comment creators will have the authority to delete comments to manage the interactions on their contributions effectively.

In conclusion, our project is not just about coding a platform but about creating a vibrant community where users feel secure, connected, and engaged. By implementing these features, we aim to build a comprehensive social interaction platform that not only meets but exceeds the expectations of today’s digital natives. Through this project, we are excited to contribute to the evolving landscape of social media platforms, where technology meets human interaction.

## Submission
1: Ryan Hung submitted report and video on brightspace.

2: Yanan He submitted Vocareum workspace.

## GUI
### Instruction for compiling:

1. Click run button on your IDE for Server.java
2. Run CLientGUI.java to get started.
2. Run ClientGUI.java to get started.
3. You can check the userFile.txt to login with an exist account or create a new account.
4. Check the main menu on our GUI panel to play with all functions we have shown on the buttons.
5. After finishing all you want to do, you will go back to the main menu and click logout, then confirm the logout information to exist the whole program successfully.


## Database

### Testing Notes:
  #### AccountDatabase.java and AccountMod.java are the same thing, but one is for testing for the test case to make sure all methods work, one will be implemented as the primary code which runs in the main method.
  
  Testing will take place in two different locations for the user data for accounts. We will have UserDatabase.Java and Accounts.Java tested in the UserDatabaseTest class. The test handling checks if the methods in both classes work together, which helps assist in the confirmation  of all methods working. If you want to test it manually, the database for all modifications of the Account class will be accessible in the main method, and file output will still be present to be checked.
 
  ### Instructions for Compiling:
  1. Compile all the interface files
  2. Compile Account, Post, and Replies
  3. Compile the rest of the other classes (except for Main.java and MainPost.java)
  4. Compile the Main classes

  An automatic test through running the RunLocalTest.java file would check each method from the Account and AccountDatabase class as well as the test for the Post and Replies methods. We've also included two manual tests in the form of Main.java and MainPost.java that check for account/post creation success in the text files. The manual tests take inputs and create new Accounts/Posts/Replies that can be stored in the textfiles.


As our project aims to build a fully functional Social Media Platform from the ground up, we focus initially on the database side. Our goal is to create a robust and efficient database capable of managing user data, interactions, and content in a thread-safe manner. This database will serve as the backbone for a social media application, supporting features like user profiles, friend interactions, content posting, and more.

- Database Design Considerations
  - Data Storage: Deciding on the structure for storing user profiles, friend relationships, posts, comments, and reactions.
  - Access Patterns: Efficiently accessing and updating user data, posts, and interactions.
  - Concurrency Control: Techniques to ensure thread safety, allowing the server to interact with multiple clients simultaneously without data corruption or loss.
  - Scalability: Designing the database to efficiently handle growth in the number of users and their interactions.
    


### Server

This server is the backbone of our social interaction platform, designed to handle multiple client connections concurrently and manage interactions with the backend database. It supports a range of features from user management to post interactions, ensuring smooth and secure communication between the client applications and the database.

The server component of our social interaction platform plays a critical role in maintaining robust communication and data management. Designed to support a multitude of client connections simultaneously, this server is the backbone that supports the seamless operation of our social network. Its capabilities ensure that each user interaction is processed efficiently and securely, facilitating a dynamic community environment. Our server ensures that all login processes are secure, managing session data meticulously to preserve user privacy and prevent unauthorized access.

### ClientGUI

This ClientGUI.java is tailored to enhance user engagement through a visually appealing and intuitive layout that supports real-time communication and dynamic content updates. Key features include a straightforward navigation system, real-time updates for posts and comments to ensure an engaging social experience, secure authentication for user logins, and interactive elements that allow users to easily post content, comment, and manage social interactions such as upvotes, downvotes, and friendships. To get started with this GUI, users should have the Java Runtime Environment (JRE) 8 or higher installed on their systems. Additionally, the server component of our platform must be operational to support all client interactions. To install the Client GUI, download the latest version from our repository, and follow the setup instructions to ensure the GUI operates correctly with our server backend, providing a smooth and responsive user experience.

### Account.java

Account.java is central to managing user accounts within our Social Media Platform. It facilitates creating user profiles, handling login credentials, managing friends and block lists, and securing accounts with security questions and answers. This class serves as the foundation for user interaction on the platform.
- Functionality:
  - Creation and Management of User Accounts: Users can sign up, set up their profiles, and update their account details.
  - Authentication: Manages password-protected user login, ensuring secure access to accounts.
  - Friendship and Block List Management: Allows users to add or remove friends and manage their block lists to customize their social experience.
  - Security Questions and Answers: Enhances account security by enabling password recovery through security questions.
- Testing:
  - Account Creation and Authentication: Verification of the signup process, login functionality, and security question integration for account recovery.
  - Friendship Management: Testing the addition and removal of friends, ensuring updates are reflected accurately.
  - Block List Management: Confirming that blocking and unblocking users function as intended.
  - Data Integrity and Security: Ensuring that user data, including passwords and security answers, is handled securely and that account recovery mechanisms are robust.
- Relationships
  - Implements IAccount interface
  - Interaction with Other Components:While primarily focused on user account management, Account.java indirectly interacts with posting and commenting functionalities by identifying user actions with their respective accounts.
  - Social Interactions: Through the friends and block lists, it determines the scope of social interactions a user can have, influencing how posts and comments are viewed and interacted with on the platform.
  - Enhanced Functionality with Additional Modules:
While Account.java provides the basis for user identity and security, integrating with modules like Post.java and Replies.java extends its functionality into the realm of content creation and interaction, allowing for a comprehensive social media experience.

### AccountMod.java

AccountMod.java is a comprehensive class designed to manage the accounts within our Social Media Platform. It offers a full suite of functionalities including login, registration, logout procedures, friendship and blocking mechanisms, security question verification, and data persistence to a file. This class acts as a crucial component of the platform's backend, ensuring secure and efficient management of user accounts. AccountMod.java is vital for the seamless operation of the social media platform, providing robust mechanisms for account management, user interaction, and data security. By handling the complexities of user account data, it enables a secure and engaging environment for users to connect and share content.
- Functionality
  - Account Management: Supports registering new accounts, logging in with existing credentials, and securely logging out.
  - Friendship and Blocking: Facilitates adding and removing friends, managing block lists to control user interactions within the platform.
  - Security Features: Incorporates security question verification for added account security, allowing users to recover access to their accounts.
  - Data Persistence: Implements functionality to save account data to a specified file, ensuring data persistence across sessions.
-Testing
  -The AccountMod class has been rigorously tested to ensure reliability and security:
  - Account Registration and Login: Verifies that new users can register and existing users can log in with their credentials.
  - Logout and Data Integrity: Ensures that logging out updates friend and block lists appropriately and maintains data integrity.
  - Friendship and Blocking Mechanisms: Confirms the correct functionality of adding, removing friends, and managing block lists.
  - Security Question Verification: Tests the security question and answer mechanism for account recovery.
  - Data Persistence: Validates the saving and loading of account data, ensuring that user information is correctly persisted across sessions.
-Relationships
  - Implements IAccountDatabase interface
  - User and Account Interaction: Directly manages user accounts, serving as the primary interface for user interactions within the platform.
  - Social Features Integration: Works closely with other components (like Post.java and Replies.java) to link account actions with social interactions, such as posting, commenting, and reacting to content.
  -Security and Data Management: Interacts with system security features to protect user data and with file management systems to persist account information.
-How to Use
  - Initialization: Create an instance of AccountDatabase with the path to the data file where account information will be stored.
  - Registering Accounts: Use RegisterAccount to add new users to the platform.
  - Login and Logout: Manage user sessions with Login and Logout methods.
  - Friendship and Blocking: Utilize AddFriend, DeleteFriend, AddBlack, and DeleteBlack to manage user relationships.
Data Persistence: Regularly call SaveData to ensure all changes are persisted to the file system.










### Post.java

Post.java is a Java class designed to model a social media post, encapsulating functionalities such as upvoting, downvoting, commenting, and view counting. It implements the IPost interface, offering a structured way to manage post data and user interactions. The class supports basic post operations like creation, deletion, and updates, along with managing upvotes, downvotes, and comments. 
- Functionality: Allows creating, modifying, upvoting, downvoting, and commenting on posts. Each post tracks its viewers and interactions (upvotes/downvotes) uniquely.
- Testing: Tested to ensure functionality like upvoting/downvoting behaves as expected, comments are added/removed correctly, and view counts are accurate. Relationships between posts and comments are verified for consistency.
- Features:
  - Creation, deletion, and modification of posts.
  - Upvote and downvote mechanisms.
  - Tracking of view numbers.
  - Management of comments, which can be extended to include replies when used in conjunction with Replies.java.
- Relationships:
  - Implements the **IPost** interface. Uses the javax.xml.stream.events. Comment for managing comments within a post.
  - Interact with Replies.java. While **Post.java** manages the core details and interactions of a social media post, **Replies.java** complements it by managing the replies associated with each post. Each reply in **Replies.java** is linked to a post through the postID, allowing for a structured and interconnected social media interaction model. This allows for a hierarchical structure where posts can have multiple replies, and each reply is directly associated with its parent post.

 ### Replies.java

 Replies.java is a Java class dedicated to managing replies to social media posts. It supports operations such as creating, deleting, and modifying replies, in addition to handling upvotes and downvotes on each reply. This class is designed to work in conjunction with Post.java to offer a comprehensive system for post and reply interactions within a social media framework.

- Functionality: Manages the lifecycle of replies (create, delete, modify) and interactions (upvote, downvote).
- Testing: Conducted with JUnit to verify accurate functionality for creating, deleting, and modifying replies. Ensures upvote and downvote actions are correctly recorded and associated with the right user and reply.
- Features:
  - Creation, deletion, and modification of replies.
  - Mechanisms for upvoting and downvoting replies.
  - Association of replies to their respective posts via postID.
- Relationships:
  - Implements the **IReplies** interface. Uses the javax.xml.stream.events. Comment for managing comments within a post.
  - Directly associated with **Post.java** through the post, establishing a clear connection between replies and their parent posts.
  - Enhances Post.java's functionality by adding depth to post interactions, allowing users to respond to posts with replies, which can themselves be upvoted or downvoted.

  ### AccountInfo
  AccountInfo.java is a Java class that extends off of Account.java. AccountInfo includes all the miscellaneous details of the Account such as birthdays and pronouns. We will implement this to our project in the future when we ask for user information in the later phases.
  
  ### LogIn
  LogIn.java is a Java class that is used to help facilitate the process of managing Accounts that would be stored via the AccountDatabase class. It's used in conjunction with the Main.java file to create, login, and to check whether the account exists the text file or not. This class also implements the ILogIn interface.

  ### Utils
  Utils.java is a Java class that we use to translate data stored as Strings in the database into an ArrayList and vice versa. It has methods that are used throughout other classes that need to access .txt files.

  ### BadDataException
  BadDataException was created to report bad data when handling inputs. It extends off of the Exception class and has a String field that says what input error caused the exception to be thrown, this is only for the test case since all data will be processed by the server before entering the txt file.

### MainMerge
The main merge is the most basic and important function to get combined functionality.It can login or create an account, and also post and reply to the post. We put all required functions for this project in readme and **we will merge it into GUI later for phase 3.**


