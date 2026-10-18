package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Differ {
    public static String generate(String filePath1, String filePath2) throws  Exception{
        Path path1 = Paths.get(filePath1).toAbsolutePath().normalize();
        Path path2 = Paths.get(filePath2).toAbsolutePath().normalize();

        if (!Files.exists(path1)) {
            throw new Exception("File '" + path1 + "' does not exist");
        }

        if (!Files.exists(path2)) {
            throw new Exception("File '" + path2 + "' does not exist");
        }

        ObjectMapper mapper = new ObjectMapper();

        TreeMap<String, Object> content1 = mapper.readValue(Files.readString(path1), new TypeReference<>() {});
        TreeMap<String, Object> content2 = mapper.readValue(Files.readString(path2), new TypeReference<>() {});

//        System.out.println(content1);
//        System.out.println(content2);

        if (content1.equals(content2)) {
            return content1.toString();
        }

        Set<String> allKeys = new TreeSet<>(content1.keySet());
        allKeys.addAll(content2.keySet());

        StringBuilder sb = new StringBuilder("{\n");

        for (var key : allKeys) {
//            System.out.println(key);

            var hasKey1 = content1.containsKey(key);
            var hasKey2 = content2.containsKey(key);

            if (hasKey1 && hasKey2 && Objects.equals(content1.get(key), content2.get(key))) {
                sb.append("  ").append("  ").append(key).append(": ").append(content1.get(key)).append("\n");
            } else {
                if (hasKey1) sb.append("  ").append("- ").append(key).append(": ").append(content1.get(key)).append("\n");
                if (hasKey2) sb.append("  ").append("+ ").append(key).append(": ").append(content2.get(key)).append("\n");
            }
        }

        sb.append("}");

//        System.out.println(sb);

        return sb.toString();
    }
}
