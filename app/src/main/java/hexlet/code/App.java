package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")

public class App implements Callable<Integer> {
    @Parameters(index = "0", paramLabel = "filepath1", description = "path to first file")
    private Path filepath1;

    @Parameters(index = "1", paramLabel = "filepath2", description = "path to second file")
    private Path filepath2;

    @Option(names = {"-f", "--format"}, paramLabel = "format", description = "output format [default: stylish]")
    private String format;

    @Override
    public Integer call() throws Exception {
        if (!Files.exists(filepath1)) {
            throw new Exception("File '" + filepath1 + "' does not exist");
        }

        if (!Files.exists(filepath2)) {
            throw new Exception("File '" + filepath2 + "' does not exist");
        }

        var compareResult = Differ.generate(filepath1, filepath2, format);

        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}