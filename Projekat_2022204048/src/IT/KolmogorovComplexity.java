/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IT;

import java.util.ArrayList;

/**
 *
 * @author sadamovic
 */
public class KolmogorovComplexity {
    public double kComplexity(String tekst, int duzinaDelova) {             
        tekst = tekst.substring(0, tekst.length() - tekst.length() % duzinaDelova);
        ArrayList<String> recnik = new ArrayList<>();
        StringBuilder rec = new StringBuilder();
        double kompleksnostDela = 0;
        double sumaKompleksnosti = 0;
        int brojDelova = tekst.length() / duzinaDelova;
        for (int i = 0; i <= tekst.length(); i++) {
            if (i % duzinaDelova == 0) {
                kompleksnostDela = recnik.size() / Math.sqrt(2 * duzinaDelova);
                sumaKompleksnosti = sumaKompleksnosti + kompleksnostDela;
                kompleksnostDela = 0;
                recnik = new ArrayList<>();
            } else {
                char temp = tekst.charAt(i);
                if (recnik.contains(rec.toString() + temp)) {
                    rec.append(temp);
                } else {
                    recnik.add(rec.toString() + temp);
                    rec = new StringBuilder();
                }
            }
        }
        return sumaKompleksnosti / brojDelova;
    }
}
