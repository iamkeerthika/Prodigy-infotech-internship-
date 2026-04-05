import java.io.*;
import java.net.URL;
import java.util.regex.*;

public class WebScraper {
    public static void main(String[] args) {
        String url = "http://books.toscrape.com/";

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new URL(url).openStream()));

            StringBuilder html = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                html.append(line);
            }

            reader.close();

            String content = html.toString();

            Pattern pattern = Pattern.compile(
                    "title=\"(.*?)\".*?price_color\">(.*?)<.*?star-rating (\\w+)",
                    Pattern.DOTALL);

            Matcher matcher = pattern.matcher(content);

            FileWriter writer = new FileWriter("products.csv");
            writer.write("Name,Price,Rating\n");

            while (matcher.find()) {
                String name = matcher.group(1);
                String price = matcher.group(2);
                String rating = matcher.group(3);

                writer.write(name + "," + price + "," + rating + "\n");
            }

            writer.close();
            System.out.println("Data saved to products.csv");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}