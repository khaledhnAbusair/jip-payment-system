package com.progressoft.jip.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

import com.progressoft.jip.exception.DataBaseConfigurationFileDoesNotExist;

public class DataBaseSettings {

	private static DataBaseSettings dataBaseSettings;

	private Properties properties;

	private DataBaseSettings() {
		try {
			properties = new Properties();
			String path = DataBaseSettings.class.getClassLoader().getResource("configurations/database.properties")
					.getPath();
			File file = Paths.get(path).toFile();
			if (!file.exists())
				throw new DataBaseConfigurationFileDoesNotExist("File Path: " + path);
			properties.load(
					DataBaseSettings.class.getClassLoader().getResourceAsStream("configurations/database.properties"));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public static DataBaseSettings getInstance() {
		if (Objects.isNull(dataBaseSettings)) {
			synchronized (DataBaseSettings.class) {
				if (Objects.isNull(dataBaseSettings)) {
					dataBaseSettings = new DataBaseSettings();
				}
			}
		}
		return dataBaseSettings;
	}

	public String username() {
		return properties.getProperty("username");
	}

	public String password() {
		return properties.getProperty("password");
	}

	public String url() {
		return properties.getProperty("url");
	}

	public String driverClassName() {
		return properties.getProperty("driverClassName");
	}

}
