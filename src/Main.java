import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Main {
    public static void main(String[] args) {
        try{
            // read url from user
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader line = new BufferedReader(reader);
            System.out.println("Enter website url: ");
            URL url = new URL(line.readLine());

            //create file for the website
            File file = new File("download.html");

            //download website codes and write them to download.html
            URLConnection connection = url.openConnection();
            BufferedReader websiteReader = new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter("download.html"));

            String websiteLine;
            while((websiteLine = websiteReader.readLine()) != null){
                writer.write(websiteLine);
            }

            //open file
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);

            //close writer and reader
            websiteReader.close();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
