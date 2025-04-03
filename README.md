# 💬 CS-180 Project: Social Interaction Platform

## 🚀 Introduction

In today’s digital world, social platforms break communication barriers and bring people together 🌍. This Java-based project aims to create a robust, user-friendly **social interaction platform** where users can post, connect, and engage in meaningful ways 💬🤝.

### 🔑 Key Features

- **👤 New User Account Creation & Secure Login**  
  Smooth sign-up and password-protected login system ensures safe access to personalized content 🔐.

- **📰 Interactive Post & Feed System**  
  Create posts and see them appear in a personalized feed — with friends’ updates too 🧑‍🤝‍🧑.

- **⚙️ Engagement Tools**  
  - Upvote/downvote posts 👍👎  
  - Comment, upvote/downvote comments 💬  
  - Hide posts 🙈  
  - Delete your own comments/trash control 🗑️  

We’re not just building code — we’re crafting a **community** 💖.

---

## 🖥️ GUI Instructions

1. ▶️ Click **Run** on `Server.java`
2. ▶️ Then run `ClientGUI.java`  
3. 📂 Log in using an existing account from `userFile.txt` or create a new one.
4. 🧭 Explore the main menu GUI to access all features.
5. 🚪 When done, click **Logout** to safely exit the program.

---

## 🗄️ Database

We focus first on building a powerful, thread-safe backend that manages:

- 👥 User profiles & authentication
- 🤝 Friend/block relationships
- 📝 Post and comment data
- 🔒 Concurrency control for multi-client usage

### 🧪 Testing Notes

- `AccountDatabase.java` and `AccountMod.java` offer the same functionality; one is for testing.
- Testing is done via:
  - `UserDatabaseTest.java`
  - Manual: `Main.java`, `MainPost.java`
  - Auto: `RunLocalTest.java`

### ⚙️ Compilation Steps

1. Compile all interface files
2. Compile `Account.java`, `Post.java`, `Replies.java`
3. Compile remaining classes (excluding `Main.java`, `MainPost.java`)
4. Compile `Main.java`, `MainPost.java` last

---

## 🧩 Component Breakdown

### 🌟 Server.java

The core of multi-client interaction — manages sessions, security, and database operations. Handles login, post actions, and ensures secure, concurrent access 🔁🔐.

---

### 🪟 ClientGUI.java

Provides a visually appealing and intuitive interface. Key highlights:

- 🔄 Real-time updates
- 🖱️ Easy navigation
- ✅ Secure login
- 🗳️ Interactive buttons (post/comment/upvote)
- 💻 Requires Java Runtime Environment 8+

---

### 👤 Account.java

Manages users and their social connections:

- Sign up / login / recover password 🔑  
- Add/remove friends 🙋‍♂️❌  
- Block/unblock users 🚫  
- Secure Q&A for password recovery ❓  
- Implements `IAccount` interface

---

### 🛠️ AccountMod.java

Handles file-based storage of account data:

- Save/load from files 📁  
- Friend/block list updates 🤝🚫  
- Used in backend operations  
- Implements `IAccountDatabase` interface  

---

### 📝 Post.java

Represents each post in the platform:

- Upvote/downvote 👍👎  
- Commenting 🗨️  
- Track views 🔢  
- Implements `IPost` interface  
- Links with `Replies.java` for threaded discussion

---

### 💬 Replies.java

Handles replies to posts:

- Add/edit/delete replies 🗨️🛠️  
- Upvote/downvote replies 🎯  
- Links with posts using `postID`  
- Implements `IReplies` interface

---

### 👤 AccountInfo.java *(Coming Soon)*

Will include extended user details like pronouns, birthday, etc. 🎂⚧️

---

### 🔐 LogIn.java

Facilitates login flow by checking accounts stored in `AccountDatabase`. Implements `ILogIn` interface.

---

### 🔄 Utils.java

Utility functions for converting between text and object lists (like parsing `.txt` into `ArrayList`). 📃🔁📦

---

### 🚫 BadDataException.java

Custom exception for reporting bad inputs (used in testing). Extends `Exception` and improves reliability 🔍🚨

---

> 🎓 **Built by aspiring engineers to create real-world impact through social technology.**
> Let’s connect, share, and grow together! 🌱💻
