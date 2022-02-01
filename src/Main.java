import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            Elements links = doc.select("a[href*='https://']");

            //iterate every link and create its file
            int iterator = 0;
            for (Element element: links){
                String link = element.attr("href");
                URL newUrl = new URL(link);
                URLConnection newConnection = newUrl.openConnection();
                BufferedReader newWebsiteReader = new BufferedReader(new InputStreamReader(newUrl.openStream()));
                BufferedWriter newWebsiteWriter = new BufferedWriter(new FileWriter(name+ "/" + "index" + iterator + ".html"));
                String newWebsiteLine;

                while((newWebsiteLine = newWebsiteReader.readLine()) != null){
                    newWebsiteWriter.write(newWebsiteLine);
                }
                iterator += 1;
            }


        } catch (FileAlreadyExistsException e){
            System.out.println("Website already downloaded");
        } catch (MalformedURLException e){
            System.out.println("Invalid url detected");
        } catch (IOException e){
            System.out.println("Server refused to respond");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
