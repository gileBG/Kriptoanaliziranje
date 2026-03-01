package projekat_2022204048;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Projekat_2022204048 {

    public static void main(String[] args) throws IOException {
        String weka_header = "@relation Kriptoanaliza1\n"
                + "@attribute p_1 numeric\n"
                + "@attribute p_2 numeric\n"
                + "@attribute p_3 numeric\n"
                + "@attribute f_1 numeric\n"
                + "@attribute f_2 numeric\n"
                + "@attribute f_3 numeric\n"
                + "@attribute f_4 numeric\n"
                + "@attribute f_5 numeric\n"
                + "@attribute MI numeric\n"
                + "@attribute bigram numeric\n"
                + "@attribute k_3 numeric\n"
                + "@attribute k_5 numeric\n"
                + "@attribute klasifikacija {enigma, cezar, sigaba, typex, purple}\n"
                + "@data\n";

        String[] naziv_klase = {"enigma", "cezar", "sigaba", "typex", "purple"};

        for (int c = 0; c < naziv_klase.length; c++) {
            for (int i = 1; i <= 50; i++) {
                byte[] cipher;
                if (naziv_klase[c].equals("enigma")) {
                    cipher = Files.readAllBytes(Paths.get(String.format("src/" + naziv_klase[c] + "/1 (%s).txt", i)));
                } else {
                    cipher = Files.readAllBytes(Paths.get(String.format("src/" + naziv_klase[c] + "/" + naziv_klase[c] + "_%s.txt", i)));
                }

                double[] features = FeatureExtractor.extractFeatures(cipher);

                String instanca = "";
                for (int j = 0; j < features.length; j++) {
                    instanca += features[j] + ", ";
                }
                instanca += naziv_klase[c];

                System.out.println(instanca);
                weka_header += instanca + "\n";
            }
        }

        Files.write(Paths.get("C:\\Users\\gile\\Desktop\\test_weka.arff"), weka_header.getBytes());
        
    }
}
