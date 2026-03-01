# Kriptoanaliziranje

## Opis projekta
Cilj je klasifikacija šifrovanih tekstova prema tipu šifre korišćenjem:

- ekstrakcije statističkih i informacionih obeležja iz šifrata (Java),
- i testiranja modela mašinskog učenja u **Weka** programu.

## Skupovi podataka
U projektu se koriste šifrati za više klasa:

- `enigma`
- `cezar`
- `sigaba`
- `typex`
- `purple`

Organizovani su kroz foldere u projektu (`src/...`) i dataset folder `SIFRATI/`.

## Kako radi pipeline
### 1) Generisanje obeležja (Java)
Glavni program:
- prolazi kroz klase (`enigma`, `cezar`, `sigaba`, `typex`, `purple`),
- učitava po 50 fajlova po klasi,
- poziva `FeatureExtractor.extractFeatures(cipher)`,
- formira ARFF instancu sa obeležjima + klasom.

### 2) Obeležja koja se računaju
Za svaku instancu se računa 12 feature-a:

1. **`p_1` – entropija nad sirovim bajtovima**  
	Mera nepredvidivosti direktno nad originalnim bajtovima šifrata. Veća vrednost uglavnom znači ravnomerniju raspodelu simbola i manju regularnost.

2. **`p_2` – entropija nad Base64 reprezentacijom**  
	Računa se entropija nakon Base64 kodiranja istog sadržaja. Ovaj pogled često uhvati drugačiji statistički obrazac nego rad sa sirovim bajtovima.

3. **`p_3` – entropija nad n-gram transformacijom**  
	Bajtovi se mapiraju u karaktere kroz binarnu n-gram transformaciju, pa se nad tim nizom računa entropija. Time se meri kompleksnost nakon dodatne simboličke projekcije podataka.

4. **`f_1` – najčešća frekvencija karaktera**  
	Udeo najzastupljenijeg karaktera u tekstu. Pomaže da se prepozna koliko je raspodela “koncentrisana” oko jednog simbola.

5. **`f_2` – druga najčešća frekvencija karaktera**  
	Udeo drugog najčešćeg karaktera. U kombinaciji sa `f_1` opisuje pad frekvencija i oblik distribucije.

6. **`f_3` – treća najčešća frekvencija karaktera**  
	Udeo trećeg najčešćeg karaktera. Doprinosi finijem razlikovanju između klasa koje imaju sličnu prvu frekvenciju.

7. **`f_4` – četvrta najčešća frekvencija karaktera**  
	Udeo četvrtog najčešćeg karaktera. Dodaje stabilnost modelu pri poređenju tekstova različite dužine.

8. **`f_5` – peta najčešća frekvencija karaktera**  
	Udeo petog najčešćeg karaktera. Zajedno sa `f_1`–`f_4` formira kompaktan “frekvencijski potpis” šifrata.

9. **`MI` – mutual information između dve polovine teksta**  
	Tekst se deli na levu i desnu polovinu, pa se meri koliko informacija jedna polovina nosi o drugoj. Više vrednosti ukazuju na jaču međuzavisnost i moguće strukturne pravilnosti.

10. **`bigram` – frekvencija identičnih susednih karaktera**  
	 Broji se koliko puta se isti karakter pojavljuje uzastopno (npr. "AA") u odnosu na sve susedne parove. Ovo hvata lokalna ponavljanja koja su često karakteristična za određene šeme šifrovanja.

11. **`k_3` – korelacija raspodele 3-grama**  
	 Posmatra varijansu učestalosti svih 3-grama u tekstu. Veća vrednost znači neravnomerniju raspodelu i izraženije obrasce na nivou trojki karaktera.

12. **`k_5` – korelacija raspodele 5-grama**  
	 Analogan pokazatelj za 5-grame, koji hvata dugodometnije obrasce nego `k_3`. Koristan je za razlikovanje šifara koje imaju sličnu lokalnu, ali različitu globalnu strukturu.

### 3) Izvoz za Weka
Program pravi ARFF fajl sa relacijom i atributima, i upisuje ga na putanju:

`C:\Users\gile\Desktop\test_weka.arff`

## Weka deo (testiranje i rezultati)
Nakon generisanja ARFF fajla, klasifikacija i evaluacija su rađene u **Weka** alatu.

To znači:
- Java deo služi za pripremu podataka i feature engineering,
- Weka deo služi za trening/test i merenje performansi modela.

## Pokretanje
Projekat je NetBeans/Ant Java projekat (`build.xml` u folderu `Projekat_2022204048`).

Tipičan tok:
1. otvoriti projekat u NetBeans-u,
2. pokrenuti `Projekat_2022204048.java`,
3. dobiti `test_weka.arff`,
4. otvoriti ARFF u Weka i uraditi klasifikaciju/evaluaciju.

## Napomena
Ako želiš da ARFF ide na drugu lokaciju, izmeni putanju za upis u:

- `src/projekat_2022204048/Projekat_2022204048.java`

---

Autor: gileBG  


