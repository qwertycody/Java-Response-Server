# Java SOAP/JSON Response Server
A simple Java HTTP server that allows for mock responses for application development.

---

**Problem:**

1. Soap UI Sucks. (sometimes)

**Solution:**
1. By using the functionality in Java we can create a server that immediately responds with the latest responses - no extra fidgeting required

---

#  Features:
- Ability to create ANY folder on the root of the project and it become a soap endpoint
- Ability to create any number of XML files on the root of the folder and it become the response
    - Only the latest modified file is the returned response
- Debug Information Page
    - Request/Responses/Path/Timestamp all on the /debug endpoint
---

# Instructions

**Step 1:**
Clone

**Step 1A:**
Change Port Declarations in "run.sh" and Response Directory Declaration

**Step 2:**
Open Command Prompt in Root Directory

**Step 3:**
Run "run.sh" file using Git-Bash or Terminal

**Step 4:**
Create any directory you want and add a sample xml response to the directory

**Step 5:**
Test by navigating to localhost:1337/FolderName and see response

---

## Authors

* **Cody Garrett** - [cody@garrett.ms](mailto:cody@garrett.ms)