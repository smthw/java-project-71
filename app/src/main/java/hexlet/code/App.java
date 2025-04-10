package hexlet.code;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")

public class App implements Runnable {
    @Parameters(index = "0", paramLabel = "filepath1", description = "path to first file")
    private File filepath1;

    @Parameters(index = "1", paramLabel = "filepath2", description = "path to second file")
    private File filepath2;

    @Option(names = {"-f", "--format"}, paramLabel = "format", description = "output format [default: stylish]")
    private String format;

    public static void main(String[] args) throws Exception {
        System.out.println(Arrays.toString(args));
//        System.out.println(Paths.get("./app/src/main/test/resourses/f1.json").toAbsolutePath().normalize());


        String readFilePath1 = args[0];
        Path path1 = Paths.get(readFilePath1).toAbsolutePath().normalize();

//        System.out.println(path1);
//        path1 = "\\\\wsl.localhost\\Ubuntu\\home\\smthwd\\java-project-71"

        String readFilePath2 = args[1];
        Path path2 = Paths.get(readFilePath2).toAbsolutePath().normalize();

//        System.out.println(path2);

        if (!Files.exists(path1)) {
            throw new Exception("File '" + path1 + "' does not exist");
        }

        if (!Files.exists(path2)) {
            throw new Exception("File '" + path2 + "' does not exist");
        }

        // Читаем файл
        String content1 = Files.readString(path1);
        String content2 = Files.readString(path2);

        // Выводим содержимое
        System.out.println(content1);
        System.out.println(" ");
        System.out.println(content2);

        new CommandLine(new App()).execute(args);

        ObjectMapper mapper = new ObjectMapper();

        Map<?, ?> j1 = mapper.readValue(content1, Map.class);
        Map<?, ?> j2 = mapper.readValue(content2, Map.class);

        System.out.println(j1);
        System.out.println(j2);
    }

    @Override
    public void run() {
//        System.out.println("Hello world!");
    }
}