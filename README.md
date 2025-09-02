Project Setup Instructions
•	Android Studio: Latest stable version
•	Minimum SDK: 26 (Android 8.0)
•	Target SDK: 36
•	Java: 17
•	Kotlin: 2.1.20
•	Gradle: 8.13
________________________________________
Library List with Justifications
•	Android Core – UI components and compatibility support
•	Hilt (DI) – Dependency injection for clean and testable architecture
•	Room Database – Local persistence and offline-first strategy
•	Retrofit + Gson – REST API calls with JSON parsing
•	Coroutines – Asynchronous programming with structured concurrency
•	Glide – Efficient image loading and caching
•	Testing Libraries – JUnit, Espresso, Truth for unit and UI testing
________________________________________
Architecture Explanation
•	Pattern: Clean Architecture + MVVM
•	Provides clear separation of concerns: UI, domain, and data layers can be tested independently.
•	Uses StateFlow for reactive UI. Future migration to Jetpack Compose would only require presentation layer changes, making the design future-proof.
________________________________________
Justification for Added Fields
•	Gender – to fetch opposite gender profiles
•	Results Count – supports pagination of API results
•	Offset – enables page-wise data loading
________________________________________
Match Score Logic
A match score (0–100) is computed based on:
•	Age Compatibility: ±2 years (20 points)
•	City Match: same city (20 points)
•	State Match: same state (20 points)
•	Country Match: same country (20 points)
•	Nationality Match: same nationality (20 points)
Currently limited to available data. With more attributes (hobbies, interests, lifestyle), scoring can be made more accurate.
________________________________________
Offline & Error Handling Strategy
•	Offline-first: Room as the single source of truth
•	Cache-first: always load from DB, refresh from API if available
•	Persist decisions: Accept/Decline stored locally
•	Graceful fallback: cached data shown if network fails
•	Error handling: try-catch in repositories, network state checks, and UI state management with StateHandler
________________________________________
Reflection & Design Constraint Response
•	Current UI uses a simple RecyclerView for displaying matches
•	If profile images were restricted, fallback could show initials or placeholders
________________________________________
Limitations & Future Updates
•	UI can be improved with swipable card-based design
•	Database migrations not implemented yet (not required for current scope)
•	Retry logic for failed API calls missing
•	No centralized error handling system
•	Match flow could improve with richer user data
•	Local data not encrypted, though feasible to add

