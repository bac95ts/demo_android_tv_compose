# 📺 Android TV Compose Demo Application

Chào mừng bạn đến với dự án **DemoTVCompose** - Ứng dụng demo giải trí truyền hình chuyên nghiệp dành riêng cho nền tảng **Android TV / Smart TV**, được phát triển 100% bằng **Jetpack Compose for TV (Compose Material3 & TV Foundation)**.

---

## 📸 Hình ảnh Demo ứng dụng

> *Dưới đây là một số ảnh chụp thực tế màn hình giao diện demo ứng dụng trên Android TV:*

|               Màn hình Trang Chủ (Home Dashboard)               |                        Màn hình Player                        |                Màn hình Tìm Kiếm (TV Keyboard)                |           Màn hình Đăng Nhập (Account Login Flow)           |
|:---------------------------------------------------------------:|:-------------------------------------------------------------:|:-------------------------------------------------------------:|:-----------------------------------------------------------:|
| <img src="screenshots/home.png" width="100%" alt="Trang Chủ"/>  | <img src="screenshots/player.png" width="100%" alt="Player"/> | <img src="screenshots/search.png" width="100%" alt="Search"/> | <img src="screenshots/login.png" width="100%" alt="Login"/> |
| <img src="screenshots/home1.png" width="100%" alt="Trang Chủ"/> |                                                               |                                                               |                                                             |

---

## 🛠️ Công nghệ & Kiến trúc dự án

Dự án áp dụng mô hình kiến trúc chuẩn mực **Clean Architecture** kết hợp mô hình **MVVM (Model-View-ViewModel)** để phân tách luồng dữ liệu, nâng cao khả năng bảo trì và viết Unit Test.

```mermaid
graph TD
    UI[Màn hình UI - Compose TV] -->|Đọc State / Gửi Event| VM[ViewModel - HomeViewModel]
    VM -->|Yêu cầu Data| Repo[Repository - HomeRepository / AccountRepository]
    Repo -->|Lấy dữ liệu mạng| API[Data Source - Retrofit / ApiService]
    Repo -->|Lưu trữ cục bộ| Cache[SharedPreferences / Local Memory]
```

### 1. Jetpack Compose TV Elements
* **CompositionLocal (`LocalNavController`)**: Khởi tạo và cung cấp `NavController` toàn cục xuyên suốt Compose Tree. Loại bỏ hoàn toàn **Prop Drilling**.
* **Theme, Design system ()**: Cấu hình theme (Dark Mode/Light Mode), color/typograohy cho theme mặc định và extended custom theme .
* **Canvas Drawing (Zero-Dependency Custom Icons)**: Sử dụng canvas.
* **Side Effects & State Management**:
  * Sử dụng `remember { mutableStateOf(...) }` cho việc kiểm soát dữ liệu tức thì.
  * Sử dụng `derivedStateOf` để tối ưu hóa hiệu năng render.
  * Áp dụng `LaunchedEffect` để bắt các sự kiện.
* **TV Navigation System**: Hệ thống điều hướng bằng `NavHost` an toàn .

### 2. Dependency Injection (DI) với Koin
Dự án sử dụng thư viện **Koin** (Dependency Injection) :
* **`appModule`**: Định nghĩa và cấu hình Network Client (Retrofit, ApiService), lớp lưu trữ dữ liệu (Repositories) và đăng ký ViewModel.
* **`koinInject()`**: Inject trực tiếp các Repository (như `AccountRepository`) vào các Composable UI .

### 3. Mạng & Caching
* **Retrofit & GSON**: Kết nối và bóc tách dữ liệu từ API .
* **AccountRepository**: Quản lý an toàn và đóng gói luồng dữ liệu cục bộ (Local Storage SharedPreferences) giúp lưu trữ trạng thái đăng nhập `isLogined` của người dùng.

---

## 📂 Cấu trúc mã nguồn chính

```text
app/src/main/java/vn/vtv/vtvgotv/
│
├── core/                  # Tầng lõi dùng chung (Core Layer)
│   ├── theme/             # Cấu hình Theme, Color, Typography cho TV
│   ├── network/           # Cấu hình OkHttpClient, Retrofit, Interceptors
│   ├── di/                # Koin Core Injection Module
│   └── utils/             # Các Extension hữu ích & DateTimeFormatter
│
├── features/              # Tầng tính năng (Feature Layer - Clean Architecture)
│   ├── auth/              # Tính năng Đăng nhập & Xác thực 2 bước
│   ├── home/              # Tính năng Trang chủ (Carousel, Channels, DVR)
│   ├── player/            # Tính năng Trình phát Video (Media3 ExoPlayer)
│   └── search/            # Tính năng Tìm kiếm & Custom TV Keyboard
│
├── navigation/            # Định tuyến Điều hướng (AppNavigation, Screen)
│
└── di/                    # AppModule tổng hợp các Koin DI Modules
```


