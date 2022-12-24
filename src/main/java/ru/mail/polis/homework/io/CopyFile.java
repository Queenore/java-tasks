package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) throws IOException {
        File fileFrom = new File(pathFrom);
        Path destinationPath = Paths.get(pathTo);
        if (fileFrom.isFile()) {
            Files.createDirectories(destinationPath.getParent());
            fileCopy(Paths.get(pathFrom), destinationPath);
            return;
        }
        if (!fileFrom.isDirectory()) {
            return;
        }
        Files.createDirectories(destinationPath);
        Files.walkFileTree(Paths.get(pathFrom), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path newPathPart = Paths.get(pathFrom).relativize(dir);
                Path newDirPath = destinationPath.resolve(newPathPart);
                File newDir = new File(newDirPath.toString());
                if (!newDir.exists()) {
                    Files.createDirectory(newDirPath);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                Path newPathPart = Paths.get(pathFrom).relativize(file);
                Path newFilePath = destinationPath.resolve(newPathPart);
                fileCopy(file, newFilePath);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void fileCopy(Path pathFrom, Path pathTo) {
        try (Writer writer = new OutputStreamWriter(
                        new FileOutputStream(pathTo.toAbsolutePath().toString()), StandardCharsets.UTF_8
        )) {
            try (BufferedReader reader = new BufferedReader(new FileReader(pathFrom.toAbsolutePath().toString()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
