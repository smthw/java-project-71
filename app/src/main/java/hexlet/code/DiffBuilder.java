package hexlet.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class DiffBuilder {
    public static List<Map<String, Object>> build(
            Map<String, Object> content1,
            Map<String, Object> content2) {

        List<Map<String, Object>> diff = new ArrayList<>();

        Set<String> allKeys = new TreeSet<>(content1.keySet());
        allKeys.addAll(content2.keySet());

        for (String key : allKeys) {
            Map<String, Object> entry = new HashMap<>();

            entry.put("key", key);

            boolean hasKey1 = content1.containsKey(key);
            boolean hasKey2 = content2.containsKey(key);

            if (!hasKey1) {
                entry.put("type", "added");
                entry.put("newValue", content2.get(key));

            } else if (!hasKey2) {
                entry.put("type", "removed");
                entry.put("oldValue", content1.get(key));

            } else if (Objects.equals(content1.get(key), content2.get(key))) {
                entry.put("type", "unchanged");
                entry.put("oldValue", content1.get(key));

            } else {
                entry.put("type", "changed");
                entry.put("oldValue", content1.get(key));
                entry.put("newValue", content2.get(key));
            }

            diff.add(entry);
        }

        return diff;
    }
}