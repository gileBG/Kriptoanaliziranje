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

1. entropija nad sirovim bajtovima,
2. entropija nad Base64 reprezentacijom,
3. entropija nad n-gram transformacijom,
4-8. top 5 frekvencija karaktera,
9. mutual information između dve polovine teksta,
10. frekvencija identičnih bigrama,
11. korelacija 3-grama,
12. korelacija 5-grama.

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

Autor: GileBG  
Predmet: Osnove kriptoanalize  
Godina: 2026
