package ru.mail.polis.homework.collections.structure;

import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final Map<String, String> map = new HashMap<>();

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(N)
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        } else if (map.containsKey(value)) { // T(N) = O(1)
            return false;
        }
        String sortLowerCaseValue = sortedLowerCaseStr(value);
        map.put(value, sortLowerCaseValue); // T(N) = O(N)  <-- (худший случай при перезаписи элементов)
        return true;
    }

    private String sortedLowerCaseStr(String value) {
        char[] chars = value.toLowerCase().toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(1)
     */
    public boolean contains(String value) {
        return map.containsKey(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(1)
     */
    public boolean remove(String value) {
        return Objects.nonNull(map.remove(value)); // T(N) = O(1)
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из того же набора букв что нам передали строку.
     * Примеры:
     * сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     * <p>
     * сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     * <p>
     * Сложность - O(N)
     */
    public List<String> getSimilarWords(String value) {
        ArrayList<String> list = new ArrayList<>();
        String inputStr = sortedLowerCaseStr(value);
        for (Map.Entry<String, String> entry : map.entrySet()) { // T(N) = O(N)
            if (entry.getValue().equals(inputStr)) { // T(N) = O(1)
                list.add(entry.getKey()); // T(N) = O(1)
            }
        }
        return list;
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - O(1)
     */
    public int size() {
        return map.size();
    }


}
