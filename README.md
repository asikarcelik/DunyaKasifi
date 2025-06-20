# 🌍 Dünya Kaşifi (World Explorer)


# TANITIM VİDEOSUNU AŞAĞIDAKİ LİNKTEN İZLEYEBİLİRSİNİZ...
https://youtube.com/shorts/QiewYmFfEbI?feature=share


**Dünya Kaşifi**, çocukların dünya kültürlerini, coğrafyasını ve farklı ülkeleri eğlenceli bir şekilde keşfetmelerini sağlayan interaktif bir Android eğitim uygulamasıdır.

## 📱 Uygulama Özellikleri

### 🎓 Kaşif Akademisi
- **Hoş Geldin Ekranı**: Animasyonlu uçak efekti ile karşılama
- **Avatar Seçimi**: Kişiselleştirilmiş karakter oluşturma
- **Araç Seçimi**: Keşif araçları (uçak, gemi, araba) seçimi
- **Ekipman Seçimi**: Keşif ekipmanları seçimi
- **Kaşif Yemini**: Eğlenceli yemin töreni
- **Sertifika**: Akademi tamamlama sertifikası

### 🗺️ Dünya Haritası
- **İnteraktif Harita**: Dokunmatik ve zoom özellikli dünya haritası
- **Ülke Keşifleri**: Farklı ülkelerdeki destinasyonları ziyaret etme
- **Görev Sistemi**: Her ülke için özel görevler
- **Rozet Sistemi**: Başarıları ödüllendiren rozetler
- **Kültürel Bilgiler**: Ülkeler hakkında eğitici içerikler

### 🎮 Mini Oyunlar
- **Ünlü Binalar Oyunu**: Mimari yapıları tanıma
- **Harita Dedektifi**: Coğrafi bilgileri öğrenme

## 🛠️ Teknik Özellikler

### Teknolojiler
- **Kotlin**: Ana programlama dili
- **Jetpack Compose**: Modern UI framework
- **Material Design 3**: Güncel tasarım sistemi
- **Navigation Compose**: Sayfa geçişleri
- **Koin**: Dependency injection
- **Coroutines**: Asenkron işlemler
- **Animation**: Zengin animasyonlar

### Mimari
- **MVVM Pattern**: Model-View-ViewModel mimarisi
- **Repository Pattern**: Veri yönetimi
- **Clean Architecture**: Temiz kod yapısı
- **Modular Design**: Modüler tasarım

## 📋 Gereksinimler

### Sistem Gereksinimleri
- **Android API Level**: 24+ (Android 7.0+)
- **Target SDK**: 35
- **Kotlin Version**: 1.9+
- **Gradle Version**: 8.0+

### Geliştirme Ortamı
- **Android Studio**: Hedgehog | 2023.1.1 veya üzeri
- **JDK**: 11 veya üzeri
- **RAM**: En az 8GB önerilir

## 🚀 Kurulum

### 1. Projeyi Klonlayın
```bash
git clone https://github.com/your-username/dunyakaifi.git
cd dunyakaifi
```

### 2. Bağımlılıkları Yükleyin
```bash
./gradlew build
```

### 3. Uygulamayı Çalıştırın
```bash
./gradlew installDebug
```

### 4. Android Studio'da Açın
- Android Studio'yu açın
- "Open an existing project" seçeneğini tıklayın
- Proje klasörünü seçin
- Gradle sync işlemini bekleyin

## 📁 Proje Yapısı

```
app/
├── src/main/
│   ├── java/com/asikarcelik/dnyakaifi/
│   │   ├── di/                    # Dependency Injection
│   │   ├── navigation/            # Navigation
│   │   ├── ui/
│   │   │   ├── components/        # UI Bileşenleri
│   │   │   ├── model/            # Veri Modelleri
│   │   │   ├── screens/          # Ekranlar
│   │   │   │   ├── academy/      # Akademi Ekranları
│   │   │   │   └── worldmap/     # Harita Ekranları
│   │   │   ├── theme/            # Tema ve Stiller
│   │   │   └── viewmodel/        # ViewModels
│   │   └── ExplorerApplication.kt
│   ├── res/                      # Kaynaklar
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## 🎨 Tasarım Özellikleri

### Renk Paleti
- **Primary**: Mavi tonları
- **Secondary**: Turuncu vurgular
- **Background**: Açık tonlar
- **Surface**: Beyaz ve gri tonları

### Tipografi
- **Headline**: Büyük başlıklar için
- **Body**: Ana metin içeriği
- **Label**: Etiketler ve küçük metinler

### Animasyonlar
- **Fade In/Out**: Sayfa geçişleri
- **Slide**: Dikey kaydırma efektleri
- **Scale**: Büyütme/küçültme animasyonları
- **Rotation**: Döndürme efektleri

## 🔧 Geliştirme

### Yeni Özellik Ekleme
1. İlgili model sınıfını oluşturun
2. ViewModel'i güncelleyin
3. UI bileşenlerini ekleyin
4. Navigation'ı güncelleyin
5. Testleri yazın

### Kod Standartları
- **Kotlin Coding Conventions** takip edin
- **Compose Best Practices** uygulayın
- **Material Design Guidelines** kullanın
- **Clean Architecture** prensiplerini uygulayın

## 🧪 Test

### Unit Tests
```bash
./gradlew test
```

### Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Test Coverage
```bash
./gradlew jacocoTestReport
```

## 📦 Build

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

### APK Konumu
```
DunyaKasifi-main/dunyakasifi.apk
```

## 🤝 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'Add amazing feature'`)
4. Branch'inizi push edin (`git push origin feature/amazing-feature`)
5. Pull Request oluşturun

## 📄 Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasına bakın.

## 👨‍💻 Geliştirici

**Asikar Celik** **Bahadır Namlı** **Eren Ersönmez**

- GitHub: [@asikarcelik](https://github.com/asikarcelik)
- Email: asikarcelik4@gmail.com

## 🙏 Teşekkürler


⭐ Bu projeyi beğendiyseniz yıldız vermeyi unutmayın! 
