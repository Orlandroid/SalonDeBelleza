# Skeduly - Professional Salon & Spa Management Platform

> A fully-featured Android appointment management application for beauty salons and spas, built with modern architecture and enterprise-grade practices.

---

## 🎯 Overview

**Skeduly** is a production-ready mobile application designed to streamline appointment scheduling and service management for beauty establishments (salons, spas, barbershops). The platform combines intuitive UX with powerful backend integration to deliver a seamless experience for both customers and staff.

**Key Value Proposition:**
- **For Customers:** Easy appointment booking, service browsing, staff management, and product purchasing
- **For Business:** Centralized branch management, staff scheduling, appointment history, and inventory management
- **Technical Excellence:** Enterprise architecture, scalable backend integration, offline-first design

---

## ✨ Core Features

### User Experience
- 🔐 **Secure Authentication** - Firebase Authentication with email/password & password recovery
- 📱 **Single Activity Architecture** - Smooth fragment-based navigation with Material Design 3
- 🎨 **Dark Mode Support** - Full dark theme implementation for accessibility
- ✨ **Smooth Animations** - Lottie animations for enhanced UX and transitions between screens
- 🌐 **Offline Support** - Works seamlessly with or without internet connectivity

### Appointment Management
- 📅 **Smart Booking System** - Multi-step appointment scheduling (service → staff → date/time)
- 🏢 **Multi-Branch Support** - Browse and book across multiple locations
- 👨‍💼 **Staff Management** - View staff profiles and book with preferred professionals
- 📋 **Appointment History** - Complete history of past and upcoming appointments with details
- ⏰ **Real-time Availability** - Dynamic availability based on staff schedules

### Product & Services
- 🛍️ **E-Commerce Integration** - Browse and purchase beauty products
- 📂 **Category Management** - Organized product categories and services
- 🛒 **Shopping Cart** - Full cart management with checkout integration
- 📸 **Service Showcase** - Detailed service descriptions with pricing and duration

### Location & Staff
- 🗺️ **Branch Information** - Multiple location details with maps integration
- 👥 **Staff Directory** - Browse salon professionals with expertise and availability
- 📞 **Contact Information** - Easy access to store details and communication channels

---

## 🏗️ Architecture & Tech Stack

### Architecture Pattern
- **MVI** (Model-View-Intent) - Unidirectional data flow for predictable state management
- **Clean Architecture** - Clear boundaries between domain, data, and presentation layers
- **Modular Design** - Organized into presentation, domain, and data modules for scalability

### Tech Stack
| Layer | Technology |
|-------|-----------|
| **UI/Presentation** | Jetpack Compose, Material Design 3, ViewBinding |
| **Architecture** | MVI, Dagger Hilt (Dependency Injection) |
| **Navigation** | Navigation Component with Safe Args |
| **Data Persistence** | Room Database (SQLite) |
| **Backend Integration** | Retrofit 2, OkHttp |
| **Authentication** | Firebase Authentication |
| **Async Operations** | Kotlin Coroutines |
| **Language** | Kotlin 2.2.21 |
| **Build System** | Gradle 9.3.0 with Kotlin DSL |

### Android Jetpack Components
✅ Navigation Component - Type-safe navigation
✅ Data Binding - Reactive UI updates
✅ Room Database - Local data persistence
✅ Dagger Hilt - Constructor injection & dependency management
✅ WorkManager - Background task scheduling

---

## 📊 Project Structure

```
SalonDeBelleza/
├── presentation/          # UI Layer - Jetpack Compose & MVI
│   ├── features/         # Feature-specific screens and logic
│   │   ├── auth/         # Login, SignUp, Authentication flow
│   │   ├── schedule_appointment/  # Appointment booking flow
│   │   ├── info/         # Services, Products, Staff, Locations
│   │   ├── profile/      # User profile & appointment history
│   │   └── app_navigation/  # Main navigation structure
│   ├── di/               # Dependency Injection modules
│   └── theme/            # Design system & theming
├── domain/               # Business Logic & Use Cases
│   ├── use_cases/        # Application business rules
│   ├── entities/         # Data models (local, remote, UI)
│   ├── validation/       # Form validation logic
│   └── state/            # Result/State management
└── data/                 # Data Layer - Repository pattern
    ├── remote/           # API integrations
    ├── local/            # Room Database
    └── mappers/          # Data transformation
```

---

## 🔑 Key Development Highlights

### Code Quality & Best Practices
- ✅ Type-safe navigation with Safe Args
- ✅ Reactive programming with coroutines
- ✅ Repository pattern for data abstraction
- ✅ Validation layer for business rules
- ✅ Proper error handling and state management
- ✅ Material Design 3 compliance

### Performance Optimizations
- Efficient RecyclerView implementations with proper view recycling
- Lazy loading of images and data
- Skeleton loading states for better perceived performance
- Network monitoring for offline/online transitions
- WorkManager for scheduled background tasks

### Scalability Features
- Modular architecture for feature expansion
- Dependency injection for easy testing and swapping implementations
- Multi-branch support built into data models
- API-driven content (services, products, staff) for dynamic updates

---

## 📸 Screenshots

[Video Demo](https://www.youtube.com/watch?v=NiYT53pavk4)

 <table>
<table>
 <tr>
   <td><strong>Login</strong></td>
   <td><strong>Forgot Password</strong></td>
   <td><strong>Home</strong></td>
   <td><strong>Branches</strong></td>
 </tr>
 <tr>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/login.png" width="100%"></td>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/sign_up_alert.png" width="100%"></td>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/home.png" width="100%"></td>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/sucursales.png" width="100%"></td>
 </tr>
 <tr>
   <td><strong>Services</strong></td>
   <td><strong>Schedule Date</strong></td>
   <td><strong>Appointment Details</strong></td>
   <td><strong>Staff Directory</strong></td>
 </tr>
 <tr>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/servicio.png" width="100%"></td>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/fecha.png" width="100%"></td>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/detalle.png" width="100%"></td>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/staff.png" width="100%"></td>
 </tr>
 <tr>
   <td><strong>User Profile</strong></td>
   <td><strong>Sign Up</strong></td>
   <td><strong>Appointment Confirmed</strong></td>
   <td><strong>Categories</strong></td>
 </tr>
 <tr>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/perfil.png" width="100%"></td>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/sing_up.png" width="100%"></td>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/cita%20agendada.png" width="100%"></td>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/categorias.png" width="100%"></td>
 </tr>
 <tr>
   <td><strong>Products</strong></td>
   <td><strong>Shopping Cart</strong></td>
   <td><strong>Profile Info</strong></td>
   <td><strong>Appointment History</strong></td>
 </tr>
 <tr>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/productos.png" width="100%"></td>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/carrito.png" width="100%"></td>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/info_perfil.png" width="100%"></td>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/historial_de_citas.png" width="100%"></td>
 </tr>
 <tr>
   <td><strong>History Details</strong></td>
 </tr>
 <tr>
   <td><img src="https://github.com/Orlandroid/images_for_repos/blob/main/salondebelleza/detalle_historial.png" width="100%"></td>
 </tr>
</table>

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Koala or later
- Kotlin 2.2.21
- Android SDK 24 or higher
- Firebase Project (for authentication)

### Installation

1. **Clone the repository:**
  ```bash
  git clone https://github.com/Orlandroid/SalonDeBelleza.git
  cd SalonDeBelleza
  ```

2. **Setup Firebase:**
  - Create a Firebase project at [firebase.google.com](https://firebase.google.com)
  - Download `google-services.json` and place in `presentation/` directory
  - Enable Firebase Authentication (Email/Password)

3. **Build & Run:**
  ```bash
  ./gradlew build
  # Then run on emulator or device
  ```

### Test Credentials
| User Type | Email | Password |
|-----------|-------|----------|
| Admin | admin@gmail.com | admin1234 |
| Customer | usuario@gmail.com | usuario1234 |
| Root | root@gmail.com | root1234 |

---

## 💡 Use Cases & Business Logic

### Authentication Flow
- Email/password registration with validation
- Secure login with Firebase Authentication
- Password recovery via email
- Session persistence using Room Database

### Appointment Booking Flow
1. **Service Selection** - Browse available services
2. **Staff Selection** - Choose preferred professional
3. **Date/Time Selection** - Smart calendar with real-time availability
4. **Confirmation** - Review and confirm appointment
5. **History Tracking** - Access past appointments anytime

### Data Synchronization
- Real-time sync with backend API via Retrofit
- Local caching using Room Database
- Offline-first approach with background sync
- WorkManager for scheduled tasks

---

## 📋 Testing & Quality

The codebase follows SOLID principles and is structured for:
- **Unit Testing** - Domain layer business logic
- **Integration Testing** - Repository and ViewModel layers
- **UI Testing** - Compose-based UI components

---

## 📈 Performance Metrics

- **Target API Level:** 34+
- **Minimum API Level:** 24
- **App Size:** Optimized for mobile devices
- **Network:** Efficient API calls with caching
- **Memory:** Proper lifecycle management with coroutines

---

## 🔒 Security

- 🔐 Firebase Authentication for user credentials
- 🔒 Secure API communication via HTTPS
- 🛡️ Proper permission handling for Android
- 🚫 Input validation on all user inputs
- 📦 ProGuard rules for code obfuscation

---

## 📚 Learning Value

This project is an excellent reference for:
- **Android Developers** - Modern Android development practices
- **Kotlin Developers** - Coroutines, extension functions, DSL patterns
- **Architecture Enthusiasts** - Clean architecture & MVI implementation
- **Mobile Teams** - Enterprise app structure and scalability patterns

---

## 🎓 Created By

**Team:** Guadalupe, Karen, Rebeca, and Ailil  
**Program:** BEDU - Kotlin Intermediate Module  
**Year:** 2022

---

## 📹 Demo

[Watch on YouTube](https://www.youtube.com/watch?v=NiYT53pavk4)  
[Flow Diagram](screenshots/Flujos.png)

---

## 🤝 Contributing

This is an educational project. For any questions or improvements:
1. Create an issue to discuss changes
2. Fork and create a feature branch
3. Submit a pull request with clear descriptions

---

## 📄 License

This project is open source and available under the MIT License.

---

## 📞 Support

For questions about the project:
- 📧 Email: contact@salonapp.com
- 🐛 Issues: GitHub Issues
- 💬 Discussions: GitHub Discussions

---

**⭐ If you found this project useful, please give it a star!**
