import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try{
            // read url from user
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader line = new BufferedReader(reader);
            System.out.println("Enter website url: ");
            URL url = new URL(line.readLine());

            //create folder for the website
            String name = url.getHost().substring(0, url.getHost().indexOf("."));
            Path path = Paths.get(name);
            Files.createDirectory(path);

            //download website codes and write them to index.html in created directory
            URLConnection connection = url.openConnection();
            BufferedReader websiteReader = new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter(name + "/" + "index.html"));
            String websiteLine;
            while((websiteLine = websiteReader.readLine()) != null){
                writer.write(websiteLine);
            }

            //extracting links in the downloaded pages
            File input = new File(name + "/" + "index.html");
            Document doc = Jsoup.parse(input, "UTF-8", url + "/");
            Elements links = doc.select("a[href]");


        } catch (FileAlreadyExistsException e){
            System.out.println("Website already downloaded");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
