package exercicio_AcademiaDev;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CsvExporter {

    public static <T> String export(List<T> data, Class<T> clazz) {

        return Optional.ofNullable(data)
                .filter(list -> !list.isEmpty())
                .map(list -> {
                    List<Field> annotatedFields = getAllFields(clazz).stream()
                            .filter(f -> f.isAnnotationPresent(CsvColumn.class))
                            .toList();

                    String header = annotatedFields.stream()
                            .map(f -> f.getAnnotation(CsvColumn.class).label())
                            .collect(Collectors.joining(","));

                    String rows = list.stream()
                            .map(obj -> annotatedFields.stream()
                                    .map(f -> getValueFromField(f, obj))
                                    .collect(Collectors.joining(",")))
                            .collect(Collectors.joining("\n"));

                    return header + "\n" + rows;
                })
                .orElse("");
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null && clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private static String getValueFromField(Field f, Object obj) {
        try {
            f.setAccessible(true);
            return String.valueOf(f.get(obj)).replace(",", ";");
        } catch (IllegalAccessException e) {
            return "";
        }

    }
}