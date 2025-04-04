# 💬  My Social Media Platform 👩‍💻🌈

> Built with Java, powered by snacks and determination.  
> (Yes, I made it all by myself 😅)

---

## 🌟 Introduction

Hi! This is my freshman-year solo project: a mini social media platform! 📱✨  
It lets users create accounts, make posts, comment, and interact — all in a friendly Java GUI.  
I wanted to challenge myself to build something that feels like a real app, not just homework.  
Spoiler: it took *a lot* of debugging 😵‍💫

---

## 🔑 What It Can Do

### 👤 Account Creation & Login

- Sign up with a username and password  
- Login securely (don’t worry, passwords are safe!) 🔐  
- Recover your password with security questions

### 📰 Feed & Posting

- Make posts and see them in your feed 📝  
- Your feed shows posts from you and your friends 💬  
- You can hide posts too if you’re not feeling them 🙈

### 💬 Interactions

- Upvote/downvote posts 👍👎  
- Comment on posts, and upvote/downvote those too 🗣️  
- Delete your own comments when needed (oops moments happen)  
- Add or remove friends 👯‍♀️  
- Block/unblock users for peace of mind 😌

---

## 🖥️ GUI Instructions

1. Run `Server.java` first 🧠  
2. Then run `ClientGUI.java` 🎨  
3. Login or create a new account from the GUI  
4. Use the menu buttons to explore everything!  
5. When you’re done, logout and close the app ✨

You’ll need Java 8 or higher to run everything properly!

---

## 🗃️ Database Details

- User accounts and posts are stored in text files (.txt)  
- Data includes usernames, passwords, friends, blocked users, posts, and comments  
- Everything is handled with file I/O in Java (classic beginner move, but it works 😎)

I made sure the system is thread-safe so multiple clients can connect at once. 🔄

---

## 🧪 Testing (Yes, I tested it... a lot 😭)

- I used `RunLocalTest.java` to automatically check my classes  
- `Main.java` and `MainPost.java` are manual test files for account and post creation  
- You can see results in the `.txt` files after each test!

---

## 📦 Key Java Files

### 🧾 `Account.java`  
Handles user data like login info, friends, blocklist, and security questions.  
Implements `IAccount`.

### 💾 `AccountMod.java`  
Controls account registration, login, and logout.  
Implements `IAccountDatabase`.  
Also takes care of saving data to files 📁

### 📝 `Post.java`  
Manages posts: upvotes/downvotes, view counts, comments, etc.  
Implements `IPost`.

### 💬 `Replies.java`  
Handles replies to posts and their upvotes/downvotes.  
Works together with `Post.java`.

### 🖼️ `ClientGUI.java`  
The interface users see! Supports post creation, commenting, voting, and more.

### 🖧 `Server.java`  
The brains behind everything! Manages client connections and data exchange.

### 🧰 `Utils.java`  
Reads and writes ArrayLists to/from strings in `.txt` files.

### 🚫 `BadDataException.java`  
Throws errors when bad input is detected (used mostly during testing).

---

## 🎁 Future Features

- Add user profile details like pronouns, birthdays, etc 🎂  
- Let users search for other users 🔍  
- Maybe even dark mode? (One day... 😅)

---

## 🧡 Final Thoughts

This project taught me *so much* about Java, files, GUIs, and debugging weird bugs at midnight.  
I'm proud that I made this from scratch as a freshman — and honestly, it was kinda fun 🧋💻

Thanks for reading! If you're a fellow CS student, good luck and keep building 💪🎉
