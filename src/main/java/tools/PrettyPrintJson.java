package tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PrettyPrintJson {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws IOException {
        File dir = new File("src/main/resources/localization");
        formatRecursive(dir);
    }

    private static void formatRecursive(File f) throws IOException {
        if (f.isDirectory()) {
            for (File child : f.listFiles()) {
                formatRecursive(child);
            }
        } else if (f.getName().endsWith(".json")) {
            String content = new String(Files.readAllBytes(f.toPath()), "UTF-8");
            Object parsed = gson.fromJson(content, Object.class);
            String pretty = gson.toJson(parsed);
            Files.write(f.toPath(), pretty.getBytes("UTF-8"));
            System.out.println("Formatted: " + f.getPath());
        }
    }
}
