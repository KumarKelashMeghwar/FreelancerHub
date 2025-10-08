# 💼 FreelancerHub

FreelancerHub is a full-stack platform where freelancers can showcase their gigs and clients can hire them easily.  
It is built with **React (frontend)** and **Spring Boot (backend)**, following a clean **Layered Architecture**.

---

## ✨ Features

- 🔑 **Authentication & Authorization** with JWT (Users & Admins)
- 👨‍💻 **Freelancer Gigs**: add, edit, and manage gigs (title, tags, price, description, images, category)
- 📂 **Admin Dashboard**: manage categories and tags for freelancers to choose from
- 🔍 **Search & Filter** gigs by category, tags, or keywords
- 🗄 **Secure Backend** with Spring Boot & JPA
- 🎨 **Modern UI** using Chakra UI v3
- 🗂 **REST APIs** for all operations (gigs, categories, tags, users, auth)

---

## 🏛 Architecture

FreelancerHub follows a **Layered Architecture (N-tier)**:

- **Frontend (React + Chakra UI)** – User-facing interface for freelancers and clients
- **Controller Layer** – REST API endpoints (`@RestController`)
- **Service Layer** – Business logic (`@Service`)
- **Repository Layer** – Database access (`@Repository`, Spring Data JPA)
- **Database Layer** – Persistent storage (e.g., MySQL/PostgreSQL)

