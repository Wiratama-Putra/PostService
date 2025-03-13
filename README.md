# 📝 Blog API - Quarkus

This is a **Quarkus-based REST API** for a blog system where users can create posts, add tags, and search posts by tag.  
The API also includes **authentication & authorization** to ensure only **admins can publish posts** while **users can only draft posts**.

---

## 📌 Features
✔ Create and retrieve blog posts  
✔ Assign **tags** to posts  
✔ **Search posts** by tag  
✔ **Authentication & Authorization** (User vs. Admin roles)  
✔ **Deployed on Cloud** for easy access

---

## 🚀 Live API (Cloud Deployment)
**Base URL:**
```plaintext
https://your-app-name.onrender.com/api
```

### 🎯 **Example Endpoints**
| Method | Endpoint                       | Description |
|--------|--------------------------------|-------------|
| **GET** | `/posts` | Retrieve all posts |
| **GET** | `/posts?tag=java` | Get posts by tag |
| **POST** | `/posts` | Create a new post (User: Draft, Admin: Published) |

---

## 💻 Running Locally

### 🔹 **1. Prerequisites**
Ensure you have the following installed:
- **Java 21**
- **Maven**
- **PostgreSQL** *(or use Docker)*

### 🔹 **2. Clone the Repository**
```bash
git clone https://github.com/yourname/project.git
cd project
```

### 🔹 **3. Set Up Database**
Run PostgreSQL and create a database:
```sql
CREATE DATABASE blog_db;
```

Or use **Docker**:
```bash
docker run --name blog_db -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres
```

### 🔹 **4. Configure Environment Variables**
Create a `.env` file with:
```ini
DATABASE_URL=jdbc:postgresql://localhost:5432/blog_db
QUARKUS_OIDC_AUTH_SERVER_URL=http://your-auth-server
```

### 🔹 **5. Run the Application**
```bash
mvn compile quarkus:dev
```
The API will be available at:
```
http://localhost:8080/api/posts
```

---

## 🔑 Authentication & Authorization

### 🔹 **User Roles**
| Role  | Permissions |
|-------|------------|
| **User** | Can **draft** posts |
| **Admin** | Can **publish** posts |

### 🔹 **Adding Authorization to Requests**
Include **JWT Token** in your API requests:
```bash
curl -H "Authorization: Bearer YOUR_ACCESS_TOKEN" http://localhost:8080/api/posts
```

---

## 📖 API Endpoints Documentation

### 🔹 **1. Get All Posts**
**Request:**
```http
GET /api/posts
```
**Response:**
```json
[
    {
        "id": 1,
        "title": "First Post",
        "content": "This is the first blog post.",
        "tags": ["java", "quarkus"],
        "status": "published"
    }
]
```

### 🔹 **2. Get Posts by Tag**
**Request:**
```http
GET /api/posts?tag=java
```
**Response:**
```json
[
    {
        "id": 2,
        "title": "Java Guide",
        "content": "Introduction to Java",
        "tags": ["java"],
        "status": "draft"
    }
]
```

### 🔹 **3. Create Post**
**Request:**
```http
POST /api/posts
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```
```json
{
    "title": "New Post",
    "content": "This is a new post",
    "tags": ["java", "quarkus"]
}
```
**Response:**
```json
{
    "id": 3,
    "title": "New Post",
    "status": "draft"
}
```

### 🔹 **4. Admin Publishes a Post**
**(Only for Admins)**  
**Request:**
```http
PUT /api/posts/3/publish
Authorization: Bearer <ADMIN_TOKEN>
```
**Response:**
```json
{
    "id": 3,
    "title": "New Post",
    "status": "publish"
}
```

---
