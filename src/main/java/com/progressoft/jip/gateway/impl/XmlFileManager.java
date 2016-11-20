package com.progressoft.jip.gateway.impl;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.progressoft.jip.gateway.FileManager;
import com.thoughtworks.xstream.XStream;

@SuppressWarnings("unchecked")
public class XmlFileManager<T> implements FileManager<T> {

	private String path;
	private XStream xStream;

	public XmlFileManager(String path, Class<?> toProcess) {
		this.path = path;
		this.xStream = new XStream();
		this.xStream.processAnnotations(toProcess);
		this.xStream.autodetectAnnotations(true);
	}

	@Override
	public T loadFileContent() {
		try (FileReader fileReader = new FileReader(path)) {
			return (T) xStream.fromXML(fileReader);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Can't see file");
		} catch (IOException e) {
			throw new RuntimeException("Can't see file");
		}
	}

	@Override
	public void updateFileContent(Object content) {
		try (FileWriter fileWriter = new FileWriter(path); BufferedWriter writer = new BufferedWriter(fileWriter)) {
			writer.write(xStream.toXML(content));
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException("Can't write on file");
		}
	}
}
