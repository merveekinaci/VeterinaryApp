# VeterinaryApp

Kullanılan Teknolojiler : 

Spring

JPA/Hibernate

Maven

Thymeleaf


Proje Senaryosu

-Hayvan tanıtımı ( ad , tür, cins, isim, yaş, açıklama )

-Hayvan Sahibi tanıtımı ( ad soyad, iletişim bilgileri, telefon, e-posta )

-Hayvan adı ya da hayvan sahibi adı üzerinden aranabilmeli

-Bir hayvan sahibine n adet hayvan tanımı yapılabilmeli

-Var olan bir kayıt üzerinde değişiklik yapılabilmeli

-Var olan kayıtlar incelebilmeli

-Var olan bir kayıt silinebilmeli

Kurulum -VeterinaryApp bir Java uygulamasıdır. Geliştirme yapmak ve çalıştırmak icin JDK ve JRE sisteminizde kurulu olmalıdır:

sudo apt-get install mysql-server

-Bu komut ile Java Development Kit (JDK) ve Java Runtime Environment (JRE) sisteminize kurulacaktır.

-Kurulum yapıldıktan sonra src/main/resources/application.properties dosyası uygun şekilde degiştirilmelidir:

spring.datasource.url = jdbc:mysql://localhost:3306/veternaryapp?useSSL=false spring.datasource.username = root spring.datasource.password = password

-MySQL'in komut satirina eristikten sonra application.properties dosyasında belirtilen kullanıcı ve parola icin:

GRANT ALL PRIVILEGES ON . TO 'root'@'localhost' IDENTIFIED BY 'password' WITH GRANT OPTION;

-Komutunu çalıştırarark gerekli izinleri veriniz. Son olarak, uygulamanın veritabanini MySQL uzerinde olusturun:

CREATE DATABASE veterinaryapp;

-	Bağımlılıkların yonetilmesi ve insa icin Apache Maven kullanir. Kok dizindeki mvnw isimli betik projeyi calisir hale getirmek icin kullanilabilir:

./mvnw package

-Bu komut pom.xml dosyasını okuyarak bağımlılıkları indirir, projeyi derler ve paketler. Eger bu asamalar başarı ile sonuçlanmışsa, program aşağıdaki komutla calıştırılabilir:

java -jar target/veterinaryapp-{VERSION}.jar
