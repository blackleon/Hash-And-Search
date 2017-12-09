package readhashandsearch;//üzerinde çalışılan paket

import java.io.BufferedReader;//korumalı okuyucu classı
import java.io.File;//dosya classı
import java.io.FileReader;//dosya okuycu class
import java.io.IOException;//I/O istisnaları classı
import java.util.ArrayList;//Dizi listesi classı
import java.util.List;//Liste classı
import java.util.Scanner;//Tarayıcı (Tuş okumak için) class
import java.util.logging.Level;//Hata seviyesi classı
import java.util.logging.Logger;//Hata tutucu class

/**
 *
 * @author Blackleon
 */

public class readhashandsearch //Ana Class
{
    private final String[] asWords = new String[100];//Okunan kelimelerin tutulduğu dizi
    private final String[] asPlacedWords = new String[211];//Hashlenen sıra ile tutulan kelime dizisi
    private final int[] aiHashWords= new int[211];//Hash değerlerini saklayan dizi
    
    //Dosyayı okuyan ve okuduğu verileri asWords kelime dizisne aktaran metod.
    public void readFile(String s)
    {
        try//dene
        {
            File f = new File(s);//Dosya objesi
            FileReader fr = new FileReader(f);//Dosya okuyucu obje
            BufferedReader br = new BufferedReader(fr);//Korumalı okuyucu obje
            int iCounter = 0;//dizi indis sayaci
            String sLine;//satır değişkeni
            
            while((sLine=br.readLine())!=null)//okunan satır boş değilse
            {
                asWords[iCounter]=sLine.toLowerCase();//satırı kelime dizisine at
                iCounter++;//indisi bir arttır
            }
        } catch (IOException ex)//deneme başarısız olursa
        {
            Logger.getLogger(readhashandsearch.class.getName()).log(Level.SEVERE, null, ex);//hatayı döndür
        }
    }
    
    //Okunan kelimeleri hashleyen ve diziye yerleştiren metod.
    public void hashAndPlaceInArray()
    {
        int iRetHash;//dönen hash değeri
        int iRetPlace;//dönen hash indisi 
        for (String asWord : asWords)//asWords dizisi eleman sayısınca asWord kelimesi oluştur
        {
            iRetHash = hashTheWord(asWord);//hashi hesapla ve değişkene aktar
            iRetPlace = putInHashReturnPlace(iRetHash);//hashi diziye yerleştir, hash indisini hesapla ve değişkene aktar
            asPlacedWords[iRetPlace] = asWord;//hash sırasına göre kelime tutan diziye kelimeyi ekle 
        }
    }
    
    //Girilen string değerini dizide ara
    public List<String> searchInput(String s)
    {
        List<String> liFound = new ArrayList<>();//Bulunan kelimeler listesi
        int iHashOfInput = hashTheWord(s);//kelimenin hash değerini hesapla
        int iPlaceToLook = searchQuadProb(iHashOfInput);//Kelimenin olabileceği hash indisini hesapla
        
        if(aiHashWords[iPlaceToLook]==iHashOfInput)//Eğer bakılan yerde aynı değer varsa
        {
            liFound.add(s);//Kelimeyi bulunanlara ekle
        }else{//Eğer aynı değer yoksa
            liFound.addAll(searchWoOneLetter(s));//Bir harf eksiltip ara ve bulunanları listeye ekle
            liFound.add("methodchanges");//iki metod arasına ayraç koy
            liFound.addAll(searchWLettersMixed(s)); //Harflerin yerlerini değiştirip ara ve bulunanları listeye ekle
        }
        return liFound;//Bulunanları geri döndür
    }
    
    //Harf eksiltip arayan metod
    private List<String> searchWoOneLetter(String s)
    {
        List<String> liWoOneLetter = new ArrayList<>();//Bulunan kelimeler listesi
        char[] cWord = new char[s.length()-1];//Kelimeyi değiştirmek için karakter dizisi
        int iCounter ; //Kelimeyi değiştirmek için sayaç
        String sMemoryString;//Karakter dizisinden string'e geçmek için değişken
        int iHashWoOneLetter;//Yeni stringin hash değeri
        int iFoundIndex;//Yeni stringin hash dizisnde olabileceği indis
        
        for(int i = 0; i<s.length(); i++)//kelime uzunluğunca çalışan for döngüsü
        {
            iCounter=0;//Sayaç sıfırlama
            sMemoryString = "";//Kelime sıfırlama
            for(int j = 0; j<s.length(); j++)//Kelime harf indisine ulaşmak için for döngüsü
            {
                if(i==j)//eğer indisler aynı ise ((kelime sırası)'ncı harf silinir)
                {
                    continue; //bu harfi aktarmayı atla
                }
                
                cWord[iCounter]=s.charAt(j);//stringden char çek ve diziye at
                iCounter++;//sayacı bir arttır
            }
            
            for(int j = 0; j<cWord.length; j++)//Dizi boyutu kadar çalışan döngü
            {
                sMemoryString +=cWord[j];//Diiyi stringe aktarma
            }
            
            iHashWoOneLetter = hashTheWord(sMemoryString);//Oluşan kelimenin hash değerini hesapla ve değişkene aktar
            iFoundIndex = searchQuadProb(iHashWoOneLetter);//Oluşan değişkenin dizide olabileceği indeksi ara ve değişkene aktar
            
            if(aiHashWords[iFoundIndex]==iHashWoOneLetter)//Eğer dizide belirtilen indisteki hash, kelimenin değerine eşitse
            {
                if(liWoOneLetter.contains(sMemoryString)!=true)//Eğer kelime geri dönecek stringler listesinde yoksa
                {
                    liWoOneLetter.add(sMemoryString);//Listeye ekle
                }
            }
        }
        return liWoOneLetter;//Bulunan kelimeleri liste olarak döndür
    }
    
    private List<String> searchWLettersMixed(String s)//Harflerin yerini değiştirip ara
    {
        List<String> liWLettersMixed = new ArrayList<>();//Bulunan kelimeler listesi
        char[] cWord = new char[s.length()];//Kelimeyi değiştirmek için karakter dizisi
        String sMemoryString;//Karakter dizisinden string'e geçmek için değişken
        int iHashWLettersMixed;//Yeni stringin hash değeri
        int iFoundIndex;//Yeni stringin hash dizisnde olabileceği indis
        
        for(int i = 0; i<cWord.length-1; i++)//Dizi boyutu kadar dönen döngü
        {
            sMemoryString = "";//Kelime sıfırlama
            char yedek;//Yedek karakter
            cWord = s.toCharArray();//okunan kelimeyi diziye aktar
            yedek = cWord[i];//i indisindeki karakteri yedekle
            cWord[i]=cWord[i+1];//i indisindeki değeri i+1 indisindeki değerle değiştir
            cWord[i+1]=yedek;//i+1 indisindeki değeri yedek karakterle değiştir
            
            for(int j = 0; j<cWord.length; j++)//Dizi boyutu kadar dönen döngü
            {
                sMemoryString +=cWord[j];//Diiyi stringe aktarma
            }
            
            iHashWLettersMixed = hashTheWord(sMemoryString);//Oluşan kelimenin hash değerini hesapla ve değişkene aktar
            iFoundIndex = searchQuadProb(iHashWLettersMixed);//Oluşan değişkenin dizide olabileceği indeksi ara ve değişkene aktar
            
            if(aiHashWords[iFoundIndex]==iHashWLettersMixed)//Eğer dizide belirtilen indisteki hash, kelimenin değerine eşitse
            {
                if(liWLettersMixed.contains(sMemoryString)!=true)//Eğer kelime geri dönecek stringler listesinde yoksa
                {
                    liWLettersMixed.add(sMemoryString);//Listeye ekle
                }
            }
        }
        return liWLettersMixed;////Bulunan kelimeleri liste olarak döndür
    }
    
    //Quadratic Probing işlemi ile arama yapan metod
    private int searchQuadProb(int i)
    {
        int iHashValue = i;//hash değerini sakla
        int iFoundIndex = 0;//Kelimenin bulunabileceği index değişkeni
           
        i=i%aiHashWords.length;//kelimenin dizide olması gereken ilk indis
        
        for(int iFor = 0 ; Math.pow(iFor, 2)<aiHashWords.length; iFor++)//Hash dizi kadar dönen for döngüsü
        {
            iFoundIndex =(int) ((i+Math.pow(iFor, 2)) % aiHashWords.length);//Quadratic Probing işlemi
            if(aiHashWords[iFoundIndex]==iHashValue)//Eğer kelimenin hashi o konumdaki hash ile aynı ise
            {
                break;//döngüden çık
            }
        }
        return iFoundIndex;//Kelimenin bulunduğu en son idisi geri döndür
    }
     
    //Parametre olarak verilen string'i hash fonksiyonu ile int'e çeviren metod
    private int hashTheWord(String s)
    {
        int iHashResult=0;//Kelime hash değeri değişkeni
        
        if(s!=null)//Kelime değişkeni boş değilse
        {
            for(int i=1; i<=s.length(); i++)//kelime boyutunca dönecek for döngüsü
            {
                iHashResult+=(i+(i+1)*(i+2)*(i+3)) * (int)s.charAt(i-1);//Her karakterin ağırlık ve ascii kodununu hash değerine ekle
            }
        }
        
        return iHashResult%1000000;//Oluşan sayının modunu alma (En fazla 999999 kabul ediliyor)
    }
    
    //Parametre olarak verilen hash değerini tabloda uygun indise yerleştiren ve yeri döndüren metod.
    private int putInHashReturnPlace(int i)
    {
        int iHashValue=i;//hash değerini sakla
        int iPlace = 0;//Hashin dizide yerleştirileceği indisi tutan değişken
        
        i=i%aiHashWords.length;//Hashin yerleşebileceği ilk indis değeri
        for(int iFor = 0; Math.pow(iFor, 2)<aiHashWords.length; iFor++)//Hash dizisi boyunca çalışacak for döngüsü
        {
            iPlace=(int) ((i+Math.pow(iFor, 2)) % aiHashWords.length);//Quadratic Probing işlemi
            
            if(aiHashWords[iPlace]==0)//Bulunan indisteki hash değeri sıfırsa (boşsa)
            {
                aiHashWords[iPlace] = iHashValue;//Hash değerini indise yerleştir
                break;//döngüden çık
            }
        }
        return iPlace;//Yerleştirilen konumu geri döndür
    }
    
    //asPlacedWords dizisinin i indisindeki stringi döndüren metod.
    public String getPlacedWordAtIndex(int i)
    {
        return asPlacedWords[i];//i indisindeki stringi döndür
    }
    
    //asPlacedWords dizisinin i indisindeki stringi döndüren metod.
    public int getHashWordAtIndex(int i)
    {
        return aiHashWords[i];//i indisindeki stringi döndür
    }
    
    public static void main(String[] args)
    {
        String s;//Okunacak kelime değişkeni
        int iCounter=1;//Sayaç değişkeni
        Scanner sc = new Scanner(System.in);//Klavye input tarayıcı obje
        
        readhashandsearch rs = new readhashandsearch();//Dosya okuyup hasleyecek objeyi oluşturma
        rs.readFile("kelimeler.txt");//Dosya okuma metodunu çağırma
        rs.hashAndPlaceInArray();//Hash fonksiyonunu hesaplayan ve diziye yerleştiren metodu çağırma
        
        System.out.println("no -  sıra  -  hash  -    kelime  -  ilk sıra");//Ekrana yazılacak başlık
        System.out.println("---------------------------------------------");//Ekrana yazılacak çizgi
        for(int i = 0; i<211; i++)//Dizi boyunca dönecek for döngüsü (keliemeleri ve hashleri yazdırma)
        {
            if(rs.getHashWordAtIndex(i)!= 0)//eğer indisteki değer sıfır değilse
            {
                s = String.format("%3d: %4s  | %6d  | %10s  | %7s ",//formatlı string oluşturma
                                                            iCounter,//sayaç
                                                            i,//indis
                                                            rs.getHashWordAtIndex(i),//Hash değeri
                                                            rs.getPlacedWordAtIndex(i),//String kelime
                                                            rs.getHashWordAtIndex(i)%211);//Dizide ilk yerleştirilecek indis
                
                System.out.println( s);//formatlı stringi ekrana yaz
                iCounter++;//Sayacı arttır
            }
        }
        
        while(true)//Break satırını okuyana kadar (nokta girilmeli) kullanıcıdan String girdisi al
        {
            List<String> liFoundStrings= new ArrayList<>();//Bulunan kelimeler listesi
            System.out.println("Lütfen bir kelime giriniz(cıkmak icin (.) nokta): ");//Kullanıcıya uyarı
            s = sc.nextLine();//Kullanıcının girdiği satırı string olarak al
            
            if(s.startsWith("."))//Eğer kullanıcı girdiye nokta ile başladıysa
            {
                break;//Döngüyü kır
            }
            
            liFoundStrings.addAll(rs.searchInput(s));//Bulunan kelimeler dizisine objenin arama metodundan dönen listeyi ekle
            
            if(liFoundStrings.size()==1)//Eğer 1 kelime varsa
            {
                if(!"methodchanges".equals(liFoundStrings.get(0)))//kelime metod ayracı değilse
                {
                    System.out.println("Aranan "+ s +" kelimesi bulundu.");//Kelime bulundu yaz
                }else{
                    System.out.println("Aranan "+ s +" kelimesi bulunamadı");//Yoksa bulunamadı yaz
                }
                
            }else if(liFoundStrings.size()>1){//Eğer birden fazla kelime bulunduysa
                System.out.println("Aranan "+ s +" kelimesi bulunamadı. Bulunan kelimeler :");//Kelime bulunamadı bulunan kelimeler yaz
                System.out.println("1. Metod:");//ilk metodun döndürdüğü değerşer
                for(String str: liFoundStrings)//liste boyutu kadar dönecek döngü
                {
                    if(str.startsWith("methodchanges"))//metod ayracını okursan ikinci metod moduna geç
                    {
                        System.out.println();//ekrana bir satır boşluk yaz
                        System.out.println("2. Metod:");//ekrana ikinci metod yaz
                        continue;//bir sonraki iterator değeri ile devam et
                    }
                    
                    System.out.print(" "+str);//listedeki kelimeyi ekrana yaz
                }
                System.out.println();//ekrana bir satır boşluk bırak
                System.out.println("-----");//ekrana bitiş çizgisi çiz
            }
        }
    }    
}