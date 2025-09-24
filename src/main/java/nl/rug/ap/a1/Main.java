package nl.rug.ap.a1;

import java.io.*;
import java.nio.charset.Charset;

public class Main {

    public static void main(String[] args) {
        InputStream stream = Main.class.getClassLoader().getResourceAsStream("BPI_Challenge_2019.csv");
        if (stream == null){
            System.out.println("Resource file not found");
            return;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream, Charset.forName("WINDOWS-1252")))) {
            String line;
            while ((line = br.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
