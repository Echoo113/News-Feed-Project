import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Has to add all of these in the account.java to implement properly,
 * not sure if someone already worked on it so dont wanna change in github yet
 * <p>
 * //for posts implementation
 * <p>
 * private ArrayList<Post> posts = new ArrayList<>();
 * <p>
 * private int currentPostID = 0;
 * <p>
 * <p>
 * public void addPost(Post post) {
 * posts.add(post);
 * }
 * <p>
 * public void removePost(Post post) {
 * posts.remove(post);
 * }
 * public void setPosts(ArrayList<Post> posts) {
 * this.posts = posts;
 * }
 * <p>
 * public ArrayList<Post> getPosts() {
 * return posts;
 * }
 * <p>
 * public int postID() {
 * return ++currentPostID;
 * }
 * <p>
 * <p>
 * //even more methods to add
 * <p>
 * this.friends = new ArrayList<>(); //in account constructor
 * <p>
 * //in Post
 * private int commentID = 0;
 * <p>
 * public int getCurrentCommentID() {
 * return ++commentID;
 * }
 * <p>
 * ///
 */


import java.util.*;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
public class MainPost {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ans1;
        boolean newsFeed = false;
        Account user = new Account(1, "Leo", "lovecs180", true,
                "What am I getting in CS180?", "A+");
        Account friend = new Account(2, "Echo", "Herpassword", true,
                "whats my discord pfp", "bunny");

        Post friendsPost = new Post(friend.getName(), friend.getId(),
                friend.postID(), "whats up, hows your day");
        Replies friendsReply = new Replies(friend.getName(), friend.getId(),
                friendsPost.getNewRepliesID(), "good wbu");
        friendsPost.addReply(friendsReply);
        friend.addPost(friendsPost);
        user.addFriend(friend.getId());
        boolean doIt = false;

        do {
            System.out.println("Would you like to \n" +
                    " -> make a post? -- 1\n" +
                    " -> delete a post? -- 2\n" +
                    " -> alter a post? -- 3\n" +
                    " -> read newsfeed? -- 4\n" +
                    " -> do nothing -- 5\n");
            ans1 = scanner.nextLine();

            switch (ans1) {
                case "1":
                    System.out.print("Content: ");
                    String content = scanner.nextLine();
                    try {
                        Post newPost = new Post(user.getName(), user.getId(), user.postID(), content);
                        user.addPost(newPost);
                        System.out.println("Posted!");
                    } catch (Exception e) {
                        System.out.println("Could not be posted...");
                    }

                    break;
                case "2":
                    String ansDeleting;
                    do {
                        if ((user.getPosts() == null) || (user.getPosts().isEmpty())) {
                            System.out.println("No posts to be deleted");
                            ansDeleting = "2";
                        } else {
                            System.out.println("Which post would you like to delete?");
                            int count = 0;
                            for (Post current : user.getPosts()) {
                                count++;
                                System.out.println(count + ": " + current.getPostID() +
                                        "_" + current.getContent());
                            }
                            try {
                                int indexDeleting = Integer.parseInt(scanner.nextLine()) - 1;

                                System.out.println("Delete post: <" + user.getPosts().get(indexDeleting).getPostID() +
                                        "_" + user.getPosts().get(indexDeleting).getContent()
                                        + ">? Put: yes or y if you want:");
                                String delPostConfirmation = scanner.nextLine();

                                if ((delPostConfirmation.equalsIgnoreCase("yes"))
                                        || (delPostConfirmation.equalsIgnoreCase("y"))
                                        || (delPostConfirmation.equalsIgnoreCase("1"))) {
                                    Post remPost = user.getPosts().get(indexDeleting);
                                    user.removePost(remPost);
                                    System.out.println("Deleted...");
                                }
                            } catch (Exception e) {
                                System.out.println("Failed...");
                            }

                            System.out.println("Try deleting another post? \nY - 1 || N - any #");
                            ansDeleting = scanner.nextLine();

                        }

                    } while (ansDeleting.equals("1"));

                    break;
                case "3":
                    //alter a post
                    String ansAltering;
                    do {
                        if ((user.getPosts() == null) || (user.getPosts().isEmpty())) {
                            System.out.println("No posts to be altered");
                            ansAltering = "2";
                        } else {

                            System.out.println("Which post would you like to alter?");
                            int count = 0;
                            for (Post current : user.getPosts()) {
                                count++;
                                System.out.println(count + ": " + current.getPostID() +
                                        "_" + current.getContent());
                            }
                            try {
                                int indexaltering = Integer.parseInt(scanner.nextLine()) - 1;

                                System.out.println("Alter post: <" + user.getPosts().get(indexaltering).getPostID() +
                                        "_" + user.getPosts().get(indexaltering).getContent() + ">?");
                                String altPostConfirmation = scanner.nextLine();

                                if ((altPostConfirmation.equalsIgnoreCase("yes"))
                                        || (altPostConfirmation.equalsIgnoreCase("y"))
                                        || (altPostConfirmation.equalsIgnoreCase("1"))) {
                                    Post remPost = user.getPosts().get(indexaltering);

                                    System.out.println("Set content to: ");
                                    String newContent = scanner.nextLine();
                                    remPost.setContent(newContent);
                                    user.getPosts().set(indexaltering, remPost);
                                    System.out.println("Altered!");
                                }
                            } catch (Exception e) {
                                System.out.println("Failed...");
                            }

                            System.out.println("Try altering another post? \nY - 1 || N - any #");
                            ansAltering = scanner.nextLine();

                        }

                    } while (ansAltering.equals("1"));
                    break;
                case "4":
                    System.out.println("going to news feed...");
                    newsFeed = true;
                    break;
                case "5":
                    //just say thank you for running, do nothing, get out
                    break;

                default:
                    System.out.println("Invalid Input -- Try again");
            }

            System.out.println("Now your post number is: " + user.getPosts().size());

            String ans2;
            do {
                System.out.println("Would you like to do anything else in this part?");
                System.out.println("Y - 1 || N - 2 (Only type integer please:)");
                ans2 = scanner.nextLine();
                if (ans2.equals("1")) {
                    doIt = true;
                } else if (ans2.equals("2")) {
                    doIt = false;
                } else {
                    System.out.println("Invalid Input -- Try again");
                }
            } while (!(ans2.equals("1") || ans2.equals("2")));

        } while (doIt);


        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(user.getId()
                + "post.txt", true))) {
            for (int i = 0; i < user.getPosts().size(); i++) {
                bfw.write(user.getPosts().get(i).toString());
            }
            bfw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //reading the newsfeed and implementing properly
        if (newsFeed && (!user.getFriends().isEmpty() && user.getFriends() != null)) {

            int countFriendIndex = -1;
            for (int currentFriend : user.getFriends()) {
                countFriendIndex++;
                int count = 0;
                //Account friendAccount = currentFriend.getAccount();
                Account friendAccount = friend;
                for (Post posts : friendAccount.getPosts()) {
                    count++;
                    System.out.print(count + "----");
                    System.out.println(posts.toString());

                    System.out.println("Upvote -- 1 || Downvote -- 2 || None -- Any Other #");
                    String ansUPDOWNPosts = scanner.nextLine();
                    if (ansUPDOWNPosts.equals("1")) {
                        posts.upvotePost();
                        System.out.println("Upvoted - Current upvote count ("
                                + posts.getUpvoteUsers() + ")");
                    } else if (ansUPDOWNPosts.equals("2")) {
                        posts.downvotePost();
                        System.out.println("Downvoted - Current downvote count ("
                                + posts.getDownvoteUsers() + ")");
                    }

                    System.out.println("Comments --------------");
                    int commentCount = -1;
                    for (Replies comment : posts.getReplies()) {
                        System.out.println(comment.toString());
                        commentCount++;
                        if (comment.getUserID() == user.getId()) {
                            int ansOwnComment;
                            do {
                                System.out.println("Your Comment || Would you like to delete or edit? \n" +
                                        "Delete -- 1 || Edit -- 2 || None -- 3");
                                ansOwnComment = Integer.parseInt(scanner.nextLine());
                                if (ansOwnComment == 1) {
                                    posts.deleteReply(comment);
                                } else if (ansOwnComment == 2) {
                                    System.out.println("What would you like to set the content to?");
                                    String newCont = scanner.nextLine();
                                    comment.setContent(newCont);
                                    posts.getReplies().set(commentCount, comment);
                                } else if (ansOwnComment == 3) {
                                    System.out.println("No Changes...");
                                } else {
                                    System.out.println("Try again -- Not a possibility");
                                }
                            } while ((ansOwnComment != 1) && (ansOwnComment != 2) && (ansOwnComment != 3));

                        } else {
                            System.out.println("Upvote -- 1 || Downvote -- 2 || None -- Any Other #");
                            String ansUPDOWNComments = scanner.nextLine();
                            if (ansUPDOWNComments.equals("1")) {
                                comment.upvotePost();
                                posts.getReplies().set(commentCount, comment);
                                System.out.println("Upvoted - Current upvote count ("
                                        + comment.getUpvoteUsers() + ")");
                            } else if (ansUPDOWNComments.equals("2")) {
                                comment.downvotePost();
                                posts.getReplies().set(commentCount, comment);
                                System.out.println("Downvoted - Current downvote count ("
                                        + comment.getDownvoteUsers() + ")");
                            }
                        }
                    }


                    System.out.println("Would you like to comment on the post? Y || N");
                    String ansCommentOnPost = scanner.nextLine();
                    if ((ansCommentOnPost.equalsIgnoreCase("y"))
                            || (ansCommentOnPost.equalsIgnoreCase("yes"))) {
                        System.out.println("What would you like the message to be?");
                        String newContComment = scanner.nextLine();

                        //just realized, it's not user.postid. What should i put for comments??
                        Replies newComment = new Replies(user.getName(), user.getId(),
                                posts.getNewRepliesID(), newContComment);
                        posts.addReply(newComment);
                    }

                    friendAccount.getPosts().set(count - 1, posts);

                }

                user.getFriends().set(countFriendIndex, friendAccount.getId());

            }
            for (Post one : friend.getPosts()) {
                System.out.println(one.toString());
            }
        } else if (newsFeed) {
            System.out.println("No friends in list\n");
        }

        System.out.println("\n");

        System.out.println("Check the new post in new txt file\n");
        System.out.println("Thank you -- Goodbye");


    }

}