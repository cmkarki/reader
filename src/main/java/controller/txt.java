package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/** ===== Required dependency ===== **/
// <dependency>
// <groupId>commons-io</groupId>
// <artifactId>commons-io</artifactId>
// <version>2.5</version>
// </dependency>
public class txt {
	public static void main(String[] args) {

		List<Patient> pat = txtReader();
		txtWriter(pat);

	}

	private static List<Patient> txtReader() {
		try {

			File f = new File("c:\\\\Users\\\\User\\\\Documents\\\\patient.txt");

			System.out.println("Reading files using Apache IO:");

			List<String> lines = FileUtils.readLines(f, "UTF-8");
			List<Patient> patients = new ArrayList<Patient>();
			int count = 0;
			for (String line : lines) {
				count++;
				if (count == 1)
					continue;
				Patient patient = new Patient();
				String[] words = line.split("\\s{2,}");
				Long d = Long.parseLong(words[0]);
				patient.setCode(d);
				patient.setPatientName(words[1]);
				patient.setNameMedicine(words[2]);
				patient.setNoOfTablets(words[3]);
				patients.add(patient);
			}
			return patients;

		} catch (IOException e) {
			return null;
		}

	}

	private static void txtWriter(List<Patient> pat) {
		try {

			File file = new File("c:\\Users\\User\\Documents\\txtResult.txt");
			final String newLine = System.getProperty("line.separator");
			String space = "               ";
			for (Patient p : pat) {
				String string = p.getCode() + space + p.getPatientName() + space + p.getNameMedicine() + space
						+ p.getNoOfTablets();
				FileUtils.writeStringToFile(file, string + newLine, true);
			}
			System.out.println("generated");
		} catch (Exception e) {

		}

	}

}
