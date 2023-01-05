package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {
    private static final int IS_PET_BYTE = 0b1;
    private static final int IS_WILD_BYTE = 0b10;
    private static final int LEGS_COUNT_BYTE = 0b100;
    private static final int NAME_BYTE = 0b1000;
    private static final int AGE_BYTE = 0b10000;
    private static final int MOVE_TYPE_BYTE = 0b100000;
    private static final int ID_BYTE = 0b1000000;
    private static final int LIVING_ENVIRONMENT_BYTE = 0b10000000;

    private boolean isPet;
    private boolean isWild;
    private Integer legsCount;
    private String name;
    private Double age;
    private MoveType moveType;
    private Byte id;
    private LivingEnvironment livingEnvironment;

    public AnimalWithMethods(boolean isPet, boolean isWild, Integer legsCount, String name, Double age,
                                MoveType moveType, Byte id, LivingEnvironment livingEnvironment) {
        this.isPet = isPet;
        this.isWild = isWild;
        this.legsCount = legsCount;
        this.name = name;
        this.age = age;
        this.moveType = moveType;
        this.id = id;
        this.livingEnvironment = livingEnvironment;
    }

    public static class LivingEnvironment implements Serializable {
        private static final byte WEATHER_BYTE = 0b1;

        private double temperature;
        private Weather weather;

        public LivingEnvironment(double temperature, Weather weather) {
            this.temperature = temperature;
            this.weather = weather;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LivingEnvironment that = (LivingEnvironment) o;
            return Double.compare(that.temperature, temperature) == 0 && weather == that.weather;
        }

        @Override
        public int hashCode() {
            return Objects.hash(temperature, weather);
        }

        @Override
        public String toString() {
            return "LivingEnvironment{" +
                    "temperature=" + temperature +
                    ", weather=" + weather +
                    '}';
        }

        public void writeObject(ObjectOutput out) throws IOException {
            byte lEFieldsFlag = (weather != null) ? WEATHER_BYTE : 0;
            out.writeByte(lEFieldsFlag);
            out.writeDouble(temperature);
            if (weather != null) {
                for (int i = 0; i < Weather.values().length; i++) {
                    if (Weather.values()[i] == weather) {
                        out.writeByte(i);
                        break;
                    }
                }
            }
        }

        public void readObject(ObjectInput in) throws IOException {
            byte lEFieldsFlag = in.readByte();
            temperature = in.readDouble();
            if ((lEFieldsFlag & WEATHER_BYTE) > 0) {
                weather = Weather.values()[in.readByte()];
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods animal = (AnimalWithMethods) o;
        return isPet == animal.isPet &&
                isWild == animal.isWild &&
                Objects.equals(legsCount, animal.legsCount) &&
                Objects.equals(name, animal.name) &&
                Objects.equals(age, animal.age) &&
                Objects.equals(id, animal.id) &&
                moveType == animal.moveType &&
                Objects.equals(livingEnvironment, animal.livingEnvironment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPet, isWild, legsCount, name, age, id, moveType, livingEnvironment);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "isPet=" + isPet +
                ", isWild=" + isWild +
                ", legsCount=" + legsCount +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", moveType=" + moveType +
                ", id=" + id +
                ", livingEnvironment=" + livingEnvironment +
                '}';
    }

    private int getFieldsFlag() {
        int fieldsFlagByte = 0;
        fieldsFlagByte |= (isPet) ? IS_PET_BYTE : 0;
        fieldsFlagByte |= (isWild) ? IS_WILD_BYTE : 0;
        fieldsFlagByte |= (legsCount != null) ? LEGS_COUNT_BYTE : 0;
        fieldsFlagByte |= (name != null) ? NAME_BYTE : 0;
        fieldsFlagByte |= (age != null) ? AGE_BYTE : 0;
        fieldsFlagByte |= (moveType != null) ? MOVE_TYPE_BYTE : 0;
        fieldsFlagByte |= (id != null) ? ID_BYTE : 0;
        fieldsFlagByte |= (livingEnvironment != null) ? LIVING_ENVIRONMENT_BYTE : 0;
        return fieldsFlagByte;
    }

    public void writeObject(ObjectOutput out) throws IOException {
        int fieldsFlag = getFieldsFlag();
        out.writeByte(fieldsFlag);
        if (legsCount != null) {
            out.writeByte(legsCount);
        }
        if (name != null) {
            out.writeUTF(name);
        }
        if (age != null) {
            out.writeDouble(age);
        }
        if (moveType != null) {
            for (int i = 0; i < MoveType.values().length; i++) {
                if (MoveType.values()[i] == moveType) {
                    out.writeByte(i);
                    break;
                }
            }
        }
        if (id != null) {
            out.writeByte(id);
        }
        if (livingEnvironment != null) {
            out.writeObject(livingEnvironment);
        }
    }

    public void readObject(ObjectInput in) throws IOException, ClassNotFoundException {
        byte fieldsFlag = in.readByte();
        isPet = (IS_PET_BYTE & fieldsFlag) > 0;
        isWild = (IS_WILD_BYTE & fieldsFlag) > 0;
        if ((LEGS_COUNT_BYTE & fieldsFlag) > 0) {
            legsCount = (int) in.readByte();
        }
        if ((NAME_BYTE & fieldsFlag) > 0) {
            name = in.readUTF();
        }
        if ((AGE_BYTE & fieldsFlag) > 0) {
            age = in.readDouble();
        }
        if ((MOVE_TYPE_BYTE & fieldsFlag) > 0) {
            moveType = MoveType.values()[in.readByte()];
        }
        if ((ID_BYTE & fieldsFlag) > 0) {
            id = in.readByte();
        }
        if ((LIVING_ENVIRONMENT_BYTE & fieldsFlag) > 0) {
            livingEnvironment = (LivingEnvironment) in.readObject();
        }
    }
}
