# Hash-And-Search
~
Mühendislik Projesi 1 - Bölüm 1 | Java Netbeans Projesi

Engineering Project 1 - Part 1  | Java Netbeans Project

Özet:
Programın bulunduğu dizinde bir txt dosyası bulunmaktadır. Program bu txt uzantılı dosayı açıp içerisinde bulunan her biri yeni satırda yazılı 100 kelimeyi okuyup bir kelime dizisinde tutar. Bu kelimeleri HF ile tam sayıya çevirir ve bulduğu değeri QP yaparak bir tam sayı dizisine saklar. Daha sonra program kullanıcıdan aranacak kelimeyi girmesini ister. Girilen kelime HF ile tam sayıya çevirilir. Oluşan değer QP ile tam sayı dizisinde aranır. Değer bulunuyorsa kullanıcıya “kelime vardır” bilgisi verilir. Kelime dizide yoksa ilk yöntem ile her seferinde bir harf eksiltilerek HF uygulanır ve QP ile arama yapılır. Aynı zamanda ikinci metod da çalışmalıdır. Bu metodda ise kelimenin bir harfi alınır yanındaki harfle yer değiştirilip HF uygulanır ve QP ile arama yapılır. İki metod da uygulandıktan sonra bulunan sonuçlar ”kelime bulunamadı fakat şunlar bulundu; ---“ şeklinde kullanıcıya gösterilir. Eğer bu metodlar uygulanarak da bir sonuç bulunamadıysa kullanıcıya “kelime veya türevleri bulunamadı” benzeri bir mesaj gösterilir.

Anahtar Kelimeler: Hash Fonksiyonu(HF), Quadratic Probing(QP), Hash Arama


Abstract:

There’s a txt file in the directory of the program. The program opens the  file that is an extension of txt,  reads the 100 words that each one is written on a new line and stores them in a word array. Converts these words into integer numbers using HF and storest the values it found into an integer number array using QP. Then it requests from user to enter the word to search for. The word entered is converted into an integer number by the HF. The output value is searched in the integer number array using QP method. If the value exists, it informs the user with “Word Exists” message. If the word isn’t in the array, first method runs; each time a character from the word gets deleted and new word gets converted to integer number using HF, then searched in the array using QP method. Second method must also run. On the second method; each time a character is swapped with the character next to it and converted to integer number, then searched in the array using QP method.When both methods are applied, found words are printed to screen to inform user as “Word’s not found. But these words were found;  ---“. If there’s none of the variations exist in the file then it prints “Word or its variations were not found” to the screen to inform user.

Keywords: Hash Function(HF), Quadratic Probing(QP), Hash Searching

