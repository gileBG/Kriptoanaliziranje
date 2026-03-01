package projekat_2022204048;

import IT.MutualInformation;
import IT.Entropy;
import java.util.Arrays;


import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class FeatureExtractor {

    public static double[] extractFeatures(byte[] cipher) {
        double[] features = new double[12];

        // Entropije
        features[0] = Entropy.calculateEntropy(byte2double(cipher));
        features[1] = Entropy.calculateEntropy(byte2double2(cipher));
        features[2] = Entropy.calculateEntropy(byte2double3(cipher, 2));

        // Top 5 karaktera
        double[] top5 = getTop5CharacterFrequencies(new String(cipher));
        System.arraycopy(top5, 0, features, 3, 5);

        // Mutualna informacija - meri koliku kolicinu informacija jedna polovina sadrzi o drugoj
        int len = cipher.length;
        if (len % 2 != 0) len--;
        int midpoint = len / 2;
        byte[] left = Arrays.copyOfRange(cipher, 0, midpoint);
        byte[] right = Arrays.copyOfRange(cipher, midpoint, midpoint * 2);
        features[8] = MutualInformation.calculateMutualInformation(
                byte2double3(left, 3),
                byte2double3(right, 3));

        // Bigram frekvencija - broji koliko se dva ista karaktera se pojavljujuju za redom
        features[9] = calculateIdenticalbiigramFrequency(new String(cipher));

        // Korelacije - gledanje ucestalosti ngrama
        features[10] = calculateCorrelation(new String(cipher), 3);
        features[11] = calculateCorrelation(new String(cipher), 5);

        return features;
    }

    public static double[] byte2double(byte[] podaci) {
        double[] r = new double[podaci.length];
        for (int i = 0; i < r.length; i++) r[i] = podaci[i];
        return r;
    }

        //enkoduju se bajtovi u b64
    public static double[] byte2double2(byte[] podaci) {
        String b64 = Base64.getEncoder().encodeToString(podaci);
        byte[] podaci2 = b64.getBytes();
        return byte2double(podaci2);
    }

    public static double[] byte2double3(byte[] podaci, int ngram) {
        return byte2double(convertBytesToCharacterString(podaci, ngram).getBytes());
    }

    public static String convertBytesToCharacterString(byte[] inputBytes, int ngram) {
        StringBuilder binaryStringBuilder = new StringBuilder();
        for (byte b : inputBytes) {
            String binaryString = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            binaryStringBuilder.append(binaryString);
        }
        String binaryString = binaryStringBuilder.toString();
        int mod = binaryString.length() % ngram;
        if (mod != 0) binaryString += "0".repeat(ngram - mod);
        StringBuilder resultStringBuilder = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i += ngram) {
            String group = binaryString.substring(i, i + ngram);
            char character = (char) ('a' + Integer.parseInt(group, 2) % 26);
            resultStringBuilder.append(character);
        }
        return resultStringBuilder.toString();
    }

    public static double[] getTop5CharacterFrequencies(String text) {
        int[] freq = new int[256];
        for (char c : text.toCharArray()) if (c < 256) freq[c]++;
        Integer[] indices = new Integer[256];
        for (int i = 0; i < 256; i++) indices[i] = i;
        Arrays.sort(indices, (a, b) -> Integer.compare(freq[b], freq[a]));
        double[] top5 = new double[5];
        int total = text.length();
        for (int i = 0; i < 5; i++) top5[i] = (double) freq[indices[i]] / total;
        return top5;
    }

    public static double calculateIdenticalbiigramFrequency(String text) {
        if (text.length() < 2) return 0.0;
        int count = 0;
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) == text.charAt(i + 1)) count++;
        }
        return (double) count / (text.length() - 1);
    }

    public static double calculateCorrelation(String text, int ngram) {
        if (text.length() < ngram + 1) return 0.0;
        Map<String, Integer> freq = new HashMap<>();
        for (int i = 0; i <= text.length() - ngram; i++) {
            String gram = text.substring(i, i + ngram);
            freq.put(gram, freq.getOrDefault(gram, 0) + 1);
        }
        double mean = freq.values().stream().mapToInt(i -> i).average().orElse(0.0);
        double variance = 0.0;
        for (int val : freq.values()) variance += Math.pow(val - mean, 2);
        return (variance / freq.size()) / (mean == 0 ? 1 : mean);
    }
}
