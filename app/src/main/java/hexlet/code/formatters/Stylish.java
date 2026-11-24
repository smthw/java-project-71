package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Stylish {

    public static String format(List<Map<String, Object>> diff) {
        StringBuilder sb = new StringBuilder("{\n");

        for (Map<String, Object> entry : diff) {
            String key = (String) entry.get("key");
            String type = (String) entry.get("type");

            Object oldValue = entry.get("oldValue");
            Object newValue = entry.get("newValue");

            switch (type) {
                case "added" ->
                        sb.append("  + ").append(key).append(": ").append(newValue).append("\n");

                case "removed" ->
                        sb.append("  - ").append(key).append(": ").append(oldValue).append("\n");

                case "unchanged" ->
                        sb.append("    ").append(key).append(": ").append(oldValue).append("\n");

                case "changed" -> {
                    sb.append("  - ").append(key).append(": ").append(oldValue).append("\n");

                    sb.append("  + ").append(key).append(": ").append(newValue).append("\n");
                }

                default -> throw new IllegalArgumentException("Unknown diff type: " + type);
            }
        }

        sb.append("}");

        return sb.toString();
    }
}
