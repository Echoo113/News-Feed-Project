# Social Media Platform in Java

> A full-stack interactive application built with Java — focused on user interaction, GUI development, and file-based data persistence.

---

## Introduction

This project is a standalone social media platform developed as a solo effort during my freshman year.  
It features core social networking functionalities such as user registration, posting, commenting, voting, and friend management — all implemented with a graphical interface using Java Swing.  
The backend is file-based, emphasizing beginner-friendly techniques while maintaining modularity and thread safety for concurrent users.

---

## Features

### Account Creation & Login
- Register a new account with a username and password
- Secure login process
- Password recovery via security questions

### Feed & Posting
- Create and view posts
- Feed displays posts from self and friends
- Option to hide individual posts

### Interaction System
- Upvote and downvote posts and comments
- Add comments and delete your own
- Manage friendships (add/remove)
- Block or unblock users

---

## GUI Instructions

1. Run `Server.java`
2. Then launch `ClientGUI.java`
3. Log in or create a new account through the interface
4. Use menu buttons to access posts, friends, and account options
5. Logout and close the client when finished

> Requires Java 8 or higher.

---

## Data Storage

- User data and posts are stored in `.txt` files
- Handles usernames, passwords, friend lists, blocklists, posts, and comments
- All file I/O is implemented manually
- Thread-safe to support multiple clients

---

## Testing

- `RunLocalTest.java`: Unit tests for core functionalities
- `Main.java`, `MainPost.java`: Manual testing tools for user and post creation
- Test results are written to local data files

---

## Key Java Files

| File              | Purpose                                                             |
|-------------------|---------------------------------------------------------------------|
| `Account.java`     | Stores user info, friends, blocklist, and security questions       |
| `AccountMod.java`  | Handles registration, login, and data persistence                  |
| `Post.java`        | Manages posts, voting, and comment tracking                        |
| `Replies.java`     | Controls post replies and their interactions                       |
| `ClientGUI.java`   | Main graphical interface                                            |
| `Server.java`      | Manages client-server communication and request handling           |
| `Utils.java`       | Provides helper functions for file I/O                             |
| `BadDataException.java` | Custom error class for input validation                        |

---

## Planned Improvements

- Add user profiles with custom fields (e.g., pronouns, birthdays)
- Implement user search functionality
- Optional dark mode

---

## Final Thoughts

This project taught me valuable lessons in Java development, GUI design, file I/O, and debugging.  
What started as a challenge turned into a rewarding experience that strengthened my understanding of full-stack design principles.

Feel free to explore the codebase or reach out with feedback or questions.
