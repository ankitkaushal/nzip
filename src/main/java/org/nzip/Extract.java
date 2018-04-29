package org.nzip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

import lombok.Data;

@Data
public class Extract {
	private String sourceFileName;
	private String destinationDirectory;
	private String[] zipExtensions;
	
	public void extract() {
		fetch(sourceFileName,destinationDirectory);
	}
	
	private void fetch(String zipFile,String destinationDir) {
		File file = new File(zipFile);
		FileInputStream fis = null;
		try {
			 fis = new FileInputStream(zipFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Sorry, cannot find the file: " +file.getAbsolutePath());
		}
		ZipInputStream zin = new ZipInputStream(fis);
		
		ZipEntry zEn;
		try {
			while((zEn=zin.getNextEntry())!=null) {
				if(!zEn.isDirectory()) {
				String destinationFile = destinationDir+File.separator+zEn;
				extractFile(zin,destinationFile);
				if(isZip(zEn.getName())) {
					String newDestinationDir = new File(destinationFile).getParentFile().getAbsolutePath()+ File.separator+ new File(destinationFile).getName()+".nzip";
					fetch(destinationFile,newDestinationDir);
					FileUtils.forceDelete(new File(destinationFile));
				}
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Sorry, following is not a valid zip file: " +zipFile);
		}
		finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("cannot close file stream:" +zipFile);
			}
		}
		
		
		
	}
	private boolean isZip(String zipFile) {
		// TODO Auto-generated method stub
		for (int i = 0; i < zipExtensions.length; i++) {
			if (zipFile.endsWith(zipExtensions[i])) {
				return true;
			}
		}
		return false;
	}

	private void extractFile(ZipInputStream zipIn,String filePath) {
		try {
			FileUtils.forceMkdir(new File(filePath).getParentFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot make directory: " +new File(filePath).getParentFile());
			return;
		}
		 
		 BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot write to the file: " +filePath);
			return;
		}
	        byte[] bytesIn = new byte[256];
	        int read = 0;
	        try {
				while ((read = zipIn.read(bytesIn)) != -1) {
				    bos.write(bytesIn, 0, read);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Cannot close the file stream: "+ filePath);
			}
	   }
	
	
}
