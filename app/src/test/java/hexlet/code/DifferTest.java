package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

class DifferTest {

    @Test
    void testGenerate() throws Exception {
        String expected =
                """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actual = Differ.generate("src/test/resources/f1.json", "src/test/resources/f2.json");

        assertEquals(expected, actual);
    }

    @Test
    void testFirstFileNotFound() {
        assertThrows(
                Exception.class,
                () ->
                        Differ.generate(
                                "src/test/resources/nonexistent.json",
                                "src/test/resources/f2.json"));
    }

    @Test
    void testSecondFileNotFound() {
        assertThrows(
                Exception.class,
                () ->
                        Differ.generate(
                                "src/test/resources/f1.json",
                                "src/test/resources/nonexistent.json"));
    }

    @Test
    void testGenerateYaml() throws Exception {
        String expected =
                """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actual = Differ.generate("src/test/resources/f1.yml", "src/test/resources/f2.yml");

        assertEquals(expected, actual);
    }

    @Test
    void testGenerateIdenticalFiles() throws Exception {
        String expected = """
            {
                follow: false
                host: hexlet.io
                proxy: 123.234.53.22
                timeout: 50
            }""";

        String actual = Differ.generate(
                "src/test/resources/f1.json",
                "src/test/resources/f1.json"
        );

        assertEquals(expected, actual);
    }

    @Test
    void testGenerateNested() throws Exception {
        String expected = Files.readString(
                Path.of("src/test/resources/expected-stylish.txt")
        ).stripTrailing();


        String actual = Differ.generate(
                "src/test/resources/f3.json",
                "src/test/resources/f4.json"
        );

        assertEquals(expected, actual);
    }
}
