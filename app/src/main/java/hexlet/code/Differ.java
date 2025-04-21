package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Differ {
    public static String generate(Path path1, Path path2, String format) throws Exception {
        if (!Files.exists(path1)) {
            throw new Exception("File '" + path1 + "' does not exist");
        }

        if (!Files.exists(path2)) {
            throw new Exception("File '" + path2 + "' does not exist");
        }

        String content1 = Files.readString(path1);
        String content2 = Files.readString(path2);

        ObjectMapper mapper = new ObjectMapper();

        Map<?, ?> j1 = mapper.readValue(content1, Map.class);
        Map<?, ?> j2 = mapper.readValue(content2, Map.class);

//        System.out.println(j1);
//        System.out.println(j2);

        Map<?, ?> sortedJ1 = new TreeMap<>(j1);
        Map<?, ?> sortedJ2 = new TreeMap<>(j2);

//        System.out.println(sortedJ1);
//        System.out.println(sortedJ2);

        if (sortedJ1.equals(sortedJ2)) {
            return sortedJ1.toString();
        }

        Set<String> allKeys = new TreeSet<>();

        var keys = sortedJ1.keySet();

        for (var key : keys) {
            allKeys.add(key.toString());
        }

        keys = sortedJ2.keySet();

        for (var key : keys) {
            allKeys.add(key.toString());
        }

//        System.out.println(allKeys);
//        System.out.println(" ");

        StringBuilder sb = new StringBuilder("{\n");

        for (var key : allKeys) {
//            System.out.println(key);

            boolean has1 = sortedJ1.containsKey(key);
            boolean has2 = sortedJ2.containsKey(key);

            if (has1 && has2 && sortedJ1.get(key).equals(sortedJ2.get(key))) {
                sb.append("  ").append("  " + key + ": " + sortedJ1.get(key).toString()).append("\n");
            } else {
                if (has1) sb.append("  ").append("- " + key + ": " + sortedJ1.get(key).toString()).append("\n");
                if (has2) sb.append("  ").append("+ " + key + ": " + sortedJ2.get(key).toString()).append("\n");
            }
        }

        sb.append("}");

//        System.out.println(sb);

        return sb.toString();
    }
}
