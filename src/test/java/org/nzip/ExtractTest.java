package org.nzip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExtractTest {

	Extractor extract;
	
	@Before
	public void setup() {
		extract = new Extractor();
		extract.setZipExtensions(new String[]{"zip"});
	}
	@Test
	public void unzipNonNestedFile() throws IOException {
		testUnzip("readme");
		
	}
	@Test
	public void unzipNonNestedFileWithFolder() throws IOException {
		testUnzip("readmeWithFolder");
	}
	@Test
	public void unzipNested() throws IOException {
		testUnzip("readmeNested");

	}
	
	
	@Test
	public void unzipNestedWithFolder() throws IOException {
		testUnzip("readmeNestedWithFolder");

	}
	@Test
	public void unzipDoubleNested() throws IOException {
		testUnzip("readmeDoubleNested");

	}
	
	public void testUnzip(String extractedDirectory) throws IOException {
		
		String zipName = extractedDirectory+".zip"; 
		extract.setSourceFileName("target/test-classes/"+zipName);
		extract.setDestinationDirectory(extractedDirectory);
		extract.extract();
		Collection<File> files = FileUtils.listFiles(new File(extractedDirectory), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		List<File> fileList = new ArrayList<File>();
		fileList.addAll(files);
		Assert.assertTrue(fileList.get(0).getName().equals("readme2.txt")||fileList.get(0).getName().equals("readme.txt"));
		Assert.assertTrue(fileList.get(1).getName().equals("readme2.txt")||fileList.get(1).getName().equals("readme.txt"));
		FileUtils.deleteDirectory(new File(extractedDirectory));
		
	}
}
