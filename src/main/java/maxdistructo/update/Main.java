package maxdistructo.update;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.json.*;

public class Main {
    public static void main(String[] args){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File file2 = new File (s + "/VERSION.json");
        URI uri2 = file2.toURI();
       JSONTokener tokener2 = null;
       try {
           tokener2 = new JSONTokener(uri2.toURL().openStream());
           System.out.println("Successfully read file VERSION.json");
       } catch (IOException e) {
           e.printStackTrace();
       }
       JSONObject root2 = new JSONObject(tokener2);
       System.out.println("Converted JSON file to JSONObject");
       String releaseChannel = root2.getString("releaseChannel");
        System.out.println("Updating " + root2.getString("name") + " to the latest " + releaseChannel + " release. Please Wait...");
        try {
            FileUtils.copyURLToFile(new URL(root2.getString("URLpreReleaseChannel") + releaseChannel + root2.getString("URLpostReleaseChannel")), new File(s + root2.getString("fileName")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished"+ root2.getString("name") + " update. Updating VERSION.properties file. Please Wait...");
        try {
            FileUtils.copyURLToFile(new URL(root2.getString("URLpreReleaseChannel") + releaseChannel + "/VERSION.json"), new File(s + "/VERSION.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished Updating " + root2.getString("name"));

    }
}
