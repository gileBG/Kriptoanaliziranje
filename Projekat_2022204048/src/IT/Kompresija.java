/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IT;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.Deflater;
import static java.util.zip.Deflater.BEST_COMPRESSION;

/**
 *
 * @author sadamovic
 */
public class Kompresija {
     public double kompresija(String tekst) throws FileNotFoundException, UnsupportedEncodingException {
        int compressedDataLength;
        byte[] compressedData;
        double kompresija;
        byte[] input_byte = null;
        Deflater compresser;
        //kompresujemo string
        input_byte = tekst.getBytes("UTF-8");
        compressedData = new byte[tekst.length()];
        compresser = new Deflater(BEST_COMPRESSION);
        compresser.setInput(input_byte);
        compresser.finish();
        compressedDataLength = compresser.deflate(compressedData);
        compresser.end();
        int komp_duzina = compressedDataLength;
        //racunamo stepen kompresije, tj. odnos komprimovane duzine i originalne duzine stringa
        kompresija = 100.0 * komp_duzina / tekst.length();
        //String tekstNakonKompresije = new String(compressedData, 0, tekst.length(), "UTF-8");
        String tekstNakonKompresije = Base64.getEncoder().encodeToString(Arrays.copyOfRange(compressedData, 0, komp_duzina));
        //System.out.println(Arrays.toString(compressedData));
        return kompresija;
    }
    public double kompresija2(byte []tekst) throws FileNotFoundException, UnsupportedEncodingException {
        int compressedDataLength;
        byte[] compressedData;
        double kompresija;      
        Deflater compresser;
        //kompresujemo string       
        compressedData = new byte[tekst.length];
        compresser = new Deflater(BEST_COMPRESSION);
        compresser.setInput(tekst);
        compresser.finish();
        compressedDataLength = compresser.deflate(compressedData);
        compresser.end();
        int komp_duzina = compressedDataLength;
        //racunamo stepen kompresije, tj. odnos komprimovane duzine i originalne duzine stringa
        kompresija = 100.0 * komp_duzina / tekst.length;
        //String tekstNakonKompresije = new String(compressedData, 0, tekst.length(), "UTF-8");
        String tekstNakonKompresije = Base64.getEncoder().encodeToString(Arrays.copyOfRange(compressedData, 0, komp_duzina));
        //System.out.println(Arrays.toString(compressedData));
        return kompresija;
    }
}
