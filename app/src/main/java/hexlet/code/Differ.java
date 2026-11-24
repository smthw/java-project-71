package hexlet.code;

import hexlet.code.formatters.Stylish;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Differ {

    public static String generate(String filePath1, String filePath2) throws Exception {
        Path path1 = Paths.get(filePath1).toAbsolutePath().normalize();
        Path path2 = Paths.get(filePath2).toAbsolutePath().normalize();

        if (!Files.exists(path1)) {
            throw new Exception("File '" + path1 + "' does not exist");
        }

        if (!Files.exists(path2)) {
            throw new Exception("File '" + path2 + "' does not exist");
        }

        Map<String, Object> content1 = Parser.parse(path1);
        Map<String, Object> content2 = Parser.parse(path2);

        var diff = DiffBuilder.build(content1, content2);

        return Stylish.format(diff);
    }
}
