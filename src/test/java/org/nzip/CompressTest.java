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

public class CompressTest {

	Compressor compress;
	Extractor extract;
	
	@Before
	public void setup() {
		extract = new Extractor();
		extract.setZipExtensions(new String[]{"zip"});
		compress = new Compressor();
		compress.setZipExtensions(new String[]{"zip"});
	}
	
	
	@Test
	public void testNonNestedCompression() throws IOException {
		testCompression("readme");
		
		
	}
	
	@Test
	public void testNonNestedWithFolderCompression() throws IOException {
		testCompression("readmeWithFolder");
		
		
		
	}
	
	@Test
	public void testNestedCompression() throws IOException {
		testCompression("readmeNested");
		
		
	}
	@Test
	public void testDoubleNestedCompression() throws IOException {
		testCompression("readmeDoubleNested");
		
		
	}
	@Test
	public void testNestedWithFolderCompression() throws IOException {
		testCompression("readmeNestedWithFolder");
		
		
	}
	
	
	public void testCompression(String extractedDirectory) throws IOException {
		
		String zipName = extractedDirectory+".zip";
		String compressedFileName = extractedDirectory+"Compressed.zip";
		extract.setSourceFileName("target/test-classes/"+zipName);
		extract.setDestinationDirectory(extractedDirectory);
		extract.extract();
		
		compress.setSourceDirectory(extractedDirectory);
		compress.setDestinationFile(compressedFileName);
		compress.compress();
		FileUtils.deleteDirectory(new File(extractedDirectory));
		
		extract.setSourceFileName(compressedFileName);
		extract.setDestinationDirectory(extractedDirectory);
		extract.extract();
		Collection<File> files = FileUtils.listFiles(new File(extractedDirectory), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		List<File> fileList = new ArrayList<File>();
		fileList.addAll(files);
		Assert.assertTrue(fileList.get(0).getName().equals("readme2.txt")||fileList.get(0).getName().equals("readme.txt"));
		Assert.assertTrue(fileList.get(1).getName().equals("readme2.txt")||fileList.get(1).getName().equals("readme.txt"));
		FileUtils.deleteDirectory(new File(extractedDirectory));
		FileUtils.forceDelete(new File(compressedFileName));
	}
	
	
	
}
