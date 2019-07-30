package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/** ===== Required dependencies ===== **/
// <dependency>
// <groupId>org.apache.poi</groupId>
// <artifactId>poi</artifactId>
// <version>3.9</version>
// </dependency>
//
// <dependency>
// <groupId>org.apache.poi</groupId>
// <artifactId>poi-scratchpad</artifactId>
// <version>3.9</version>
// </dependency>
//
// <dependency>
// <groupId>org.apache.poi</groupId>
// <artifactId>poi-ooxml</artifactId>
// <version>3.9</version>
// </dependency>

public class doc {
	public static void main(String[] args) {
		List<ExpiredDate> validDates = new ArrayList<ExpiredDate>();
		List<Patient> patients = readFirstDoc();
		List<ExpiredDate> dates = readSecondDoc();
		// today date
		Date dateToday = new Date();
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		format.format(dateToday);
		for (ExpiredDate date : dates) {
			if (date.getExpiredDate().compareTo(dateToday) >= 0) {
				ExpiredDate d = new ExpiredDate();
				d.setCode(date.getCode());
				d.setExpiredDate(date.getExpiredDate());
				d.setRemainingDate(date.getRemainingDate());
				validDates.add(d);
			}
		}
		Comparator<ExpiredDate> compareByCode = (ExpiredDate o1, ExpiredDate o2) -> o1.getCode()
				.compareTo(o2.getCode());
		validDates.sort(compareByCode);
		writeIntoWord(patients, validDates);

	}

	private static void writeIntoWord(List<Patient> patients, List<ExpiredDate> validDates) {
		XWPFDocument doc = new XWPFDocument();
		try {
			OutputStream fileOut = new FileOutputStream("c:\\Users\\User\\Documents\\new.docx");
			XWPFParagraph paragraph = doc.createParagraph();

			// revert back date to string of dd-MM-yyyy format
			SimpleDateFormat print = new SimpleDateFormat("dd-MM-yyyy");
			// System.out.println(print.format(p.getExpiredDate()));

			List<Overall> over = new ArrayList<Overall>();
			for (ExpiredDate d : validDates) {
				for (Patient p : patients) {
					if (d.getCode().equals(p.getCode())) {
						XWPFRun run = paragraph.createRun();
						run.setText(p.getCode() + "      " + p.getPatientName() + "    " + p.getNameMedicine() + "    "
								+ p.getNoOfTablets() + "    " + print.format(d.getExpiredDate()) + "     "
								+ d.getRemainingDate());
						run.addBreak();

					}
				}
			}

			doc.write(fileOut);
			System.out.println("File created");
		} catch (

		Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static List<ExpiredDate> readSecondDoc() {
		try {
			File file = new File("c:\\Users\\User\\Documents\\date.docx");
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			XWPFDocument document = new XWPFDocument(fis);

			List<XWPFParagraph> paragraphs = document.getParagraphs();

			System.out.println("Total no of paragraph " + paragraphs.size());
			List<ExpiredDate> dates = new ArrayList<ExpiredDate>();
			int count = 0;
			for (XWPFParagraph para : paragraphs) {
				count++;
				if (count == 1)
					continue;
				ExpiredDate date = new ExpiredDate();
				String line = para.getText();
				String[] words = line.split("\\s{2,}");
				System.out.println(words[1]);
				Long d = Long.parseLong(words[0]);
				date.setCode(d);
				String dateString = words[1];

				// String to date for 29-07-2019 format
				DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
				Date dateNew = format.parse(dateString);

				date.setExpiredDate(dateNew);
				date.setRemainingDate(words[2]);
				dates.add(date);
			}
			fis.close();
			return dates;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private static List<Patient> readFirstDoc() {
		try {
			File file = new File("c:\\Users\\User\\Documents\\patient.docx");
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			XWPFDocument document = new XWPFDocument(fis);

			List<XWPFParagraph> paragraphs = document.getParagraphs();

			System.out.println("Total no of paragraph " + paragraphs.size());
			List<Patient> patients = new ArrayList<Patient>();
			int count = 0;
			for (XWPFParagraph para : paragraphs) {
				count++;
				if (count == 1)
					continue;
				Patient patient = new Patient();
				String line = para.getText();
				String[] words = line.split("\\s{2,}");
				Long d = Long.parseLong(words[0]);
				patient.setCode(d);
				patient.setPatientName(words[1]);
				patient.setNameMedicine(words[2]);
				patient.setNoOfTablets(words[3]);
				patients.add(patient);
			}
			// to remove duplicate object
			Set<Patient> p = new HashSet<Patient>();
			p.addAll(patients);
			patients = new ArrayList<Patient>();
			patients.addAll(p);
			System.out.println("--===--size " + patients.size());
			fis.close();
			return patients;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
