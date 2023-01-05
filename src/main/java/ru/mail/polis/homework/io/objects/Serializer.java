package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Нужно реализовать методы этого класса, и реализовать тестирование 4-ех способов записи.
 * Для тестирования надо создать список из 1000+ разных объектов (заполнять объекты можно рандомом,
 * с помощью класса Random). Важно, чтобы в списке животных попадались null-ы
 * Потом получившийся список записать в файл (необходимо увеличить размер списка, если запись происходит менее 5 секунд).
 * НЕ должно быть ссылок на одни и те же объекты
 * <p>
 * Далее этот список надо прочитать из файла.
 * <p>
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста,
 * за каждый тест 1 балл)  и 3 балла за правильное объяснение результатов
 * Для тестов создайте класс в соответствующем пакете в папке тестов. Используйте другие тесты - как примеры.
 * <p>
 * В конце теста по чтению данных, не забывайте удалять файлы
 */
public class Serializer {

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        Path filePath = Paths.get(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            for (Animal animal : animals) {
                out.writeObject(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1 тугрик
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> defaultDeserialize(String fileName) {
        Path filePath = Paths.get(fileName);
        List<Animal> animalList = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            while (inputStream.available() > 0) {
                animalList.add((Animal) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return animalList;
    }


    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        Path filePath = Paths.get(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            for (AnimalWithMethods animal : animals) {
                out.writeObject(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и специальных методов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<AnimalWithMethods> deserializeWithMethods(String fileName) {
        Path filePath = Paths.get(fileName);
        List<AnimalWithMethods> animalList = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            while (inputStream.available() > 0) {
                animalList.add((AnimalWithMethods) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return animalList;
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        Path filePath = Paths.get(fileName);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            for (AnimalExternalizable animal : animals) {
                objectOutputStream.writeObject(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и интерфейса Externalizable
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) {
        Path filePath = Paths.get(fileName);
        List<AnimalExternalizable> animalList = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            while (inputStream.available() > 0) {
                animalList.add((AnimalExternalizable) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return animalList;
    }

    /**
     * 2 тугрика
     * Реализовать ручную сериализацию, с помощью высокоуровневых потоков. Сами ручками пишем поля,
     * без использования методов writeObject
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    private static final int WEATHER_BYTE = 0b1;
    private static final int IS_PET_BYTE = 0b1;
    private static final int IS_WILD_BYTE = 0b10;
    private static final int LEGS_COUNT_BYTE = 0b100;
    private static final int NAME_BYTE = 0b1000;
    private static final int AGE_BYTE = 0b10000;
    private static final int MOVE_TYPE_BYTE = 0b100000;
    private static final int ID_BYTE = 0b1000000;
    private static final int LIVING_ENVIRONMENT_BYTE = 0b10000000;

    private int getAnimalFieldsFlag(Animal animal) {
        int fieldsFlagByte = 0;
        fieldsFlagByte |= (animal.isPet()) ? IS_PET_BYTE : 0;
        fieldsFlagByte |= (animal.isWild()) ? IS_WILD_BYTE : 0;
        fieldsFlagByte |= (animal.getLegsCount() != null) ? LEGS_COUNT_BYTE : 0;
        fieldsFlagByte |= (animal.getName() != null) ? NAME_BYTE : 0;
        fieldsFlagByte |= (animal.getAge() != null) ? AGE_BYTE : 0;
        fieldsFlagByte |= (animal.getMoveType() != null) ? MOVE_TYPE_BYTE : 0;
        fieldsFlagByte |= (animal.getId() != null) ? ID_BYTE : 0;
        fieldsFlagByte |= (animal.getLivingEnvironment() != null) ? LIVING_ENVIRONMENT_BYTE : 0;
        return fieldsFlagByte;
    }

    public void customSerialize(List<Animal> animals, String fileName) {
        Path filePath = Paths.get(fileName);
        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(filePath))) {
            for (Animal animal : animals) {
                if (animal == null) {
                    out.writeByte(0);
                    continue;
                }

                int animalFieldsFlag = getAnimalFieldsFlag(animal);
                out.writeByte(animalFieldsFlag);

                Integer legsCount = animal.getLegsCount();
                if (legsCount != null) {
                    out.writeByte(legsCount);
                }

                String name = animal.getName();
                if (name != null) {
                    out.writeUTF(name);
                }

                Double age = animal.getAge();
                if (age != null) {
                    out.writeDouble(age);
                }

                MoveType moveType = animal.getMoveType();
                if (moveType != null) {
                    for (int i = 0; i < MoveType.values().length; i++) {
                        if (MoveType.values()[i] == moveType) {
                            out.writeByte(i);
                            break;
                        }
                    }
                }

                Byte id = animal.getId();
                if (id != null) {
                    out.writeByte(id);
                }

                Animal.LivingEnvironment livingEnvironment = animal.getLivingEnvironment();
                if (livingEnvironment != null) {
                    int livingEnvironmentFieldsFlag = (livingEnvironment.getWeather() != null) ? WEATHER_BYTE : 0b0;
                    out.writeByte(livingEnvironmentFieldsFlag);
                    out.writeDouble(livingEnvironment.getTemperature());
                    Weather weather = livingEnvironment.getWeather();
                    if (livingEnvironment.getWeather() != null) {
                        for (int i = 0; i < Weather.values().length; i++) {
                            if (Weather.values()[i] == weather) {
                                out.writeByte(i);
                                break;
                            }
                        }
                    }
                }

                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2 тугрика
     * Реализовать ручную дисериализацию, с помощью высокоуровневых потоков. Сами ручками читаем поля,
     * без использования методов readObject
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> customDeserialize(String fileName) {
        Path filePath = Paths.get(fileName);
        List<Animal> animalList = new ArrayList<>();
        try (DataInputStream in = new DataInputStream(Files.newInputStream(filePath))) {
            while (in.available() > 0) {
                int animalFieldsFlag = in.readByte();
                if (animalFieldsFlag == 0) {
                    animalList.add(null);
                    continue;
                }

                boolean isPet = (animalFieldsFlag & IS_PET_BYTE) > 0;
                boolean isWild = (animalFieldsFlag & IS_WILD_BYTE) > 0;
                Integer legsCount = null;
                String name = null;
                Double age = null;
                MoveType moveType = null;
                Byte id = null;

                if ((animalFieldsFlag & LEGS_COUNT_BYTE) > 0) {
                    legsCount = (int) in.readByte();
                }
                if ((animalFieldsFlag & NAME_BYTE) > 0) {
                    name = in.readUTF();
                }
                if ((animalFieldsFlag & AGE_BYTE) > 0) {
                    age = in.readDouble();
                }
                if ((animalFieldsFlag & MOVE_TYPE_BYTE) > 0) {
                    moveType = MoveType.values()[in.readByte()];
                }
                if ((animalFieldsFlag & ID_BYTE) > 0) {
                    id = in.readByte();
                }

                Animal.LivingEnvironment lE = null;
                if ((animalFieldsFlag & LIVING_ENVIRONMENT_BYTE) > 0) {
                    int livingEnvironmentFieldsFLag = in.readByte();
                    double temperature = in.readDouble();
                    Weather weather = null;
                    if ((livingEnvironmentFieldsFLag & WEATHER_BYTE) > 0) {
                        weather = Weather.values()[in.readByte()];
                    }
                    lE = new Animal.LivingEnvironment(temperature, weather);
                }

                Animal newAnimal = new Animal(isPet, isWild, legsCount, name, age, moveType, id, lE);
                animalList.add(newAnimal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animalList;
    }
}
