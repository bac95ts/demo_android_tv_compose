# Android TV Architecture Guidelines

## Overview
This project is an Android TV application built using modern Android development practices. We follow a simplified Clean Architecture approach, primarily divided into Presentation and Data layers, to keep the codebase maintainable, scalable, and testable.

## 1. Presentation Layer (MVVM)
- **UI Framework**: Jetpack Compose for TV (`androidx.tv.material3`, `androidx.tv.foundation`). Always prefer TV-specific components (e.g., `Carousel`, `TvLazyRow`, `TvLazyColumn`) over standard mobile components to ensure native TV behavior.
- **Architecture Pattern**: MVVM (Model-View-ViewModel) combined with Unidirectional Data Flow (UDF).
- **State Management**: 
  - ViewModels should manage UI state using `StateFlow`.
  - One-off events (like navigation or showing a toast) should be handled via `SharedFlow` or Compose side-effects.
- **TV Specifics (10-foot UI)**:
  - **Focus Management**: D-pad navigation is the primary input method. Utilize `Modifier.focusRequester()`, `Modifier.onFocusChanged()`, and handle focus state visually (e.g., scale up, change border/background on focus).
  - **Readability**: Ensure high contrast, large typography, and sufficient padding to be legible from a distance.

## 2. Data Layer (Repository Pattern)
- **Repositories**: Serve as the single source of truth for data. The ViewModel should only interact with Repositories, never directly with Data Sources.
- **Data Sources**:
  - **Remote**: Retrofit with OkHttp for consuming REST APIs (e.g., fetching channels, VOD content, EPG).
  - **Local**: Room Database or DataStore for caching data, saving user preferences, or maintaining watch history.
- **Data Mapping**: Use Kotlin Data Classes. Map raw Data Transfer Objects (DTOs) from the network into UI-friendly models within the repository before passing them to the ViewModel.

## 3. Reference & Inspiration
When implementing features, refer to the following standard implementations for best practices:
- [Google Android TV Samples](https://github.com/android/tv-samples)
- [Compose TV Community Sample](https://github.com/UmairKhalid786/ComposeTv)

## AI Assistant Directives
- **Separation of Concerns**: Strictly separate business/data logic from UI logic.
- **Component Reusability**: Build modular Compose components. Extract reusable UI elements into a `components` package.
- **Dependency Injection**: Utilize Koin for providing ViewModel dependencies, Repositories, and Network Singletons across the app. `DemoTVApplication` initializes Koin with `startKoin`. Use `koinViewModel()` inside Composable screens to inject ViewModels.
