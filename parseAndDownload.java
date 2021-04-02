package patchDownloader;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class parseAndDownload {

	static Logger logger;

	public boolean parse(String sysreportLocation, String folderLocation, String sysreportname,
			List<Integer> moduleslist) throws IOException {

		String dirName = "C:\\PatchDownloader\\patches";
		String filePath;
		System.out.print(dirName);
		String downloadedpatches = "", failedpatches = "";
		int count = moduleslist.size();
		System.out.print(count);
		boolean ok = true, flag = true;
		logger = Logger.getLogger("patchDownloader");

		// Simple file logging Handler.
		FileHandler fileHandler;
		File file;
		boolean exists = false;
		UnzipUtility unzipper = new UnzipUtility();
		fileHandler = new FileHandler("/PatchDownloader/logs/patchlist.log", true);
		logger.addHandler(fileHandler);

		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);

		File dir = new File("C:\\PatchDownloader\\patches");
		if (!dir.exists()) {
			if (dir.mkdirs()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Directory is already avilable");
			}
		}

		for (int i = 0; i < count; i++) {
			if (moduleslist.get(i) == 0) {
				break;
			}
			flag = true;
			String[] patch = Files.readAllLines(Paths.get(sysreportLocation), Charset.forName("UTF-8"))
					.get(moduleslist.get(i) - 4).split(" ");
			System.out.print(patch);
			try {
				filePath = dirName + "\\pat" + patch[2].substring(3) + "_CS64_WIN.zip";
				file = new File(filePath);
				exists = file.exists();
				if (!exists) {
					saveFileFromUrlWithJavaIO(filePath,
							"https://cspatchdb.opentext.com/PatchAdmin/FileTransferHandler.ashx?f="
									+ patch[2].substring(3) + "/pat" + patch[2].substring(3) + "_CS64_WIN.zip");
					System.out.println("PATCH " + patch[2].substring(3) + "_CS64_WIN.zip" + " is Downloaded");
					logger.info("PATCH " + patch[2].substring(3) + "_CS64_WIN.zip" + " is Downloaded");

					file = new File(filePath);
					exists = file.exists();
					if (exists) {
						logger.info("Extracting " + file.getName() + " ...");
						try {
							unzipper.unzip(file.getAbsolutePath(), folderLocation);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							failedpatches += patch[2].substring(3) + "  \n";
							flag = false;
							ok = false;
						}

					} else {
						failedpatches += patch[2].substring(3) + "  \n";
					}
				} else {
					logger.info("Extracting " + file.getName() + " ...");
					try {
						unzipper.unzip(file.getAbsolutePath(), folderLocation);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						logger.info(e.toString());
						failedpatches += patch[2].substring(3) + "  \n";
						ok = false;
						flag = false;
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("PATCH " + patch[2].substring(3) + "_CS64_WIN.zip" + " is Not Found");
				logger.info("PATCH " + patch[2].substring(3) + "_CS64_WIN.zip" + " is Not Found");
				failedpatches += patch[2].substring(3) + "  \n";
				ok = false;
				flag = false;
			}
			if (flag == true) {
				downloadedpatches += patch[2].substring(3) + "  \n";
			}

		}

		logger.info("List of successful downloaded and extracted patches \n" + downloadedpatches);
		logger.info("List of failed patches \n" + failedpatches);
		fileHandler.close();
		return ok;

	}

	public static void saveFileFromUrlWithJavaIO(String fileName, String fileUrl)
			throws MalformedURLException, RuntimeException, IOException {
		System.out.println(fileName);
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			in = new BufferedInputStream(new URL(fileUrl).openStream());
			fout = new FileOutputStream(fileName);

			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
		} finally {
			if (in != null)
				in.close();
			if (fout != null)
				fout.close();
		}
	}

}
