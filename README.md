# Newsly
Newsly is a modern, clean, and intuitive news feed application for Android, built entirely with Jetpack Compose. It showcases a robust implementation of Clean Architecture principles, making the codebase scalable, maintainable, and testable. Stay up-to-date with the latest headlines from around the world with a seamless and fluid user experience.

# ✨Features

1. **Top Headlines**: Browse the latest top headlines from various news sources.

2. **Categorized News**: Filter articles by categories like Business, Technology, Sports, Health, and more.

3. **Search Functionality**: Easily search for articles on any topic.

4. **Article Details**: Tap on any article to view the full story in a clean, readable format.

5. **Bookmark Articles**: Save your favorite articles to read later.

6. **Offline Support**: Caches recently loaded articles for offline reading.
   --   Material 3 Design: A beautiful and dynamic UI that supports light/dark themes.

# 🛠 Tech Stack & Libraries
1. This project leverages a modern tech stack to deliver a high-quality Android application.

2. **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) - A modern, declarative UI toolkit for building native Android apps.

3. **Architecture**: [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) - A layered architecture that separates concerns, making the app modular and testable.

4. **Asynchronous Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html) - For managing background tasks and handling streams of data.

5. **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - A pragmatic dependency injection library for Android.

6. **State Management**: [Jetpack ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - For managing UI-related data in a lifecycle-conscious way.

7. **Networking**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/) - For making efficient and clean API requests.

8. **Data Serialization**: [Gson](https://github.com/google/gson) / [Kotlinx.Serialization](https://github.com/Kotlin/kotlinx.serialization) - For parsing JSON data from the API.

9. **Database Caching**: [Room](https://developer.android.com/training/data-storage/room) - For persisting data and enabling offline access.

10. **Navigation**: [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation) - For navigating between screens in a Compose-based app.

11. **Image Loading**: [Coil](https://coil-kt.github.io/coil/) - A fast, lightweight, and modern image loading library for Android.

# 🏗️ Architecture
This project follows the principles of Clean Architecture, ensuring a clear separation of concerns between different layers of the application.

Data Flow: *UI Layer -> Domain Layer -> Data Layer*

Layers
1. UI Layer (app module)
    1. Contains all UI-related components like Composable screens, ViewModels, and UI state holders.

    2. The UI observes state changes from the ViewModel (using Kotlin Flow) and renders the appropriate components.

    3. User interactions from the UI are sent to the ViewModel as events.

    4. This layer depends only on the Domain layer.

2. Domain Layer (domain module)
    1. This is the core business logic of the application. It is completely independent of the Android framework.

    2. Contains Use Cases (e.g., GetTopHeadlinesUseCase), Repository Interfaces, and core Models.

    3. Use Cases orchestrate the flow of data between the UI and Data layers.

    4. This layer has no dependencies on other layers.

3. Data Layer (data module)
   Responsible for providing data to the application. It contains the Repository Implementations.

    1. It decides whether to fetch data from a remote Data Source (API) or a local Data Source (Room Database).

    2. Handles data mapping between API models (DTOs) and domain models.

    3. This layer depends only on the Domain layer.