# 📦 Envanter Yönetim Sistemi (Java)

Bu proje, **Java** dili kullanılarak geliştirilmiş **konsol tabanlı bir Envanter (Stok) Yönetim Sistemi**dir. Sistem; ürün, tedarikçi ve sipariş yönetimini desteklerken aynı zamanda otomatik stok yenileme gibi gelişmiş işlevler sunar.

---

## 🚀 Projenin Amacı

Bu projenin amacı:

* Nesne Yönelimli Programlama (OOP) prensiplerini uygulamak
* Dosya okuma / yazma işlemleriyle kalıcı veri yönetimi sağlamak
* Gerçek hayattaki bir envanter sisteminin temel mantığını modellemek
* Git & GitHub kullanımı ve proje dokümantasyonu pratiği kazanmaktır

---

## 📂 Proje Yapısı

```
📁 app
 └── Main.java

📁 business
 ├── StockManager.java
 └── IStockService.java

📁 entities
 ├── Product.java
 ├── Supplier.java
 ├── Order.java
 └── Inventory.java
```

---

## ⚙️ Sistem Özellikleri

### 📦 Ürün Yönetimi

* Ürün ekleme
* Ürün silme
* Stok güncelleme
* Ürünleri listeleme
* Fiyata göre artan / azalan sıralama
* Fiyat aralığına göre ürün listeleme

### 🚚 Tedarikçi Yönetimi

* Tedarikçi ekleme
* Tedarikçileri listeleme

### 📝 Sipariş Yönetimi

* Sipariş oluşturma
* Siparişleri listeleme

### 🤖 Otomatik Stok Yenileme

* Belirlenen eşik değerin altına düşen ürünler için
* Otomatik stok ekleme işlemi

### 📊 Analiz & Raporlama

* Ortalama ürün fiyatı hesaplama
* Kritik stok kontrolü
* Toplam envanter değeri hesaplama

### 💾 Kalıcı Veri

* Program kapanırken tüm veriler **dosyaya kaydedilir**
* Program açıldığında veriler otomatik yüklenir

---

## ▶️ Program Nasıl Çalıştırılır?

1. Projeyi IntelliJ IDEA ile açın
2. `Main.java` dosyasını çalıştırın
3. Konsol menüsünden işlemleri seçin

---

## 🧪 Örnek Kullanım (Case 13 – Tedarikçileri Listeleme)

**Menüden seçim:**

```
13
```

**Çıktı örneği:**

```
--- TEDARİKÇİLER ---
112233 | Apple | apple@gmail.com
223344 | Samsung | samsung@gmail.com
334455 | Xiaomi | xiaomi@gmail.com
```

Bu seçenek, sistemde kayıtlı tüm tedarikçileri listelemek için kullanılır.

---

## 🧩 UML & Diyagramlar

Projede aşağıdaki diyagramlar hazırlanmıştır:

* 📌 Class Diagram (UML)
* 📌 Use Case Diagram

## 🧠 OOP Prensipleri

* **Encapsulation:** Getter / Setter kullanımı
* **Abstraction:** `IStockService` arayüzü
* **Single Responsibility Principle**
* **Modüler yapı**

---

## 🧾 Git Commit Geçmişi

Proje geliştirme süreci boyunca yapılan tüm commitler düzenli ve açıklayıcı şekilde tutulmuştur.

---

## 👤 Geliştirici

* **Şahin Kara**
* Java & Yazılım Geliştirme

---

## 📌 Notlar

Bu proje eğitim amaçlı geliştirilmiştir.
Geri bildirim ve geliştirmeye açıktır.

---

⭐ Repo'yu beğendiyseniz yıldızlamayı unutmayın!
