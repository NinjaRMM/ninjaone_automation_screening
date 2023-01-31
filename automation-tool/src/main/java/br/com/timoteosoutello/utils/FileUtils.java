package br.com.timoteosoutello.utils;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

	public static Path getFilePath(String fileName) throws FileNotFoundException {
		Path filePath = Paths.get(fileName);
		if (!Files.exists(filePath))
			throw new FileNotFoundException();
		return filePath;
	}
}
