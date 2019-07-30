package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class test {

	public static void main(String[] args) {
		Map<Long, ExpiredDate> dat = new TreeMap<Long, ExpiredDate>();
		Map<Long, Patient> patients = readFirstDoc();
		Map<Long, ExpiredDate> dates = readSecondDoc();
		// today date
		Date dateToday = new Date();
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		format.format(dateToday);

		for (Map.Entry<Long, ExpiredDate> date : dates.entrySet()) {
			// System.out.println(date.getKey() + ", " + date.getValue());
			if (date.getValue().getExpiredDate().compareTo(dateToday) >= 0) {
				System.out.println(date.getKey());
				dat.put(date.getKey(), date.getValue());
			}
		}
		System.out.println("size " + dat.size());
		writeIntoWord(patients, dat);

	}

	private static void writeIntoWord(Map<Long, Patient> pat, Map<Long, ExpiredDate> validDates) {
		XWPFDocument doc = new XWPFDocument();
		try {
			OutputStream fileOut = new FileOutputStream("c:\\Users\\User\\Documents\\new1.docx");
			XWPFParagraph paragraph = doc.createParagraph();

			// revert back date to string of dd-MM-yyyy format
			SimpleDateFormat print = new SimpleDateFormat("dd-MM-yyyy");
			// System.out.println(print.format(p.getExpiredDate()));
			String space = "          ";
			for (Map.Entry<Long, ExpiredDate> d : validDates.entrySet()) {
				Patient p = pat.get(d.getKey());
				XWPFRun run = paragraph.createRun();
				run.setText(p.getCode() + space + p.getPatientName() + space + p.getNameMedicine() + space
						+ p.getNoOfTablets() + space + d.getValue().getCode() + space
						+ print.format(d.getValue().getExpiredDate()) + space + d.getValue().getRemainingDate());
				run.addBreak();
			}
			doc.write(fileOut);
			System.out.println("File created");
		} catch (

		Exception e) {
			// System.out.println(e.getMessage());
		}

	}

	private static Map<Long, ExpiredDate> readSecondDoc() {
		try {
			File file = new File("c:\\Users\\User\\Documents\\date.docx");
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			XWPFDocument document = new XWPFDocument(fis);

			List<XWPFParagraph> paragraphs = document.getParagraphs();

			// System.out.println("Total no of paragraph " + paragraphs.size());
			Map<Long, ExpiredDate> dat = new TreeMap<Long, ExpiredDate>();
			int count = 0;
			for (XWPFParagraph para : paragraphs) {
				count++;
				if (count == 1)
					continue;
				ExpiredDate date = new ExpiredDate();
				String line = para.getText();
				String[] words = line.split("\\s{2,}");
				Long d = Long.parseLong(words[0]);
				date.setCode(d);
				String dateString = words[1];

				// String to date for 29-07-2019 format
				DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
				Date dateNew = format.parse(dateString);

				date.setExpiredDate(dateNew);
				date.setRemainingDate(words[2]);
				dat.put(date.getCode(), date);
			}
			System.out.println("----- " + dat.size());
			Map<Long, ExpiredDate> e = new LinkedHashMap<>();
			dat.entrySet().stream().sorted(Map.Entry.<Long, ExpiredDate>comparingByKey().reversed())
					.forEachOrdered(x -> e.put(x.getKey(), x.getValue()));

			// for(Map.Entry<String, >)
			fis.close();
			return dat;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private static Map<Long, Patient> readFirstDoc() {
		try {
			File file = new File("c:\\Users\\User\\Documents\\patient.docx");
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			XWPFDocument document = new XWPFDocument(fis);

			List<XWPFParagraph> paragraphs = document.getParagraphs();

			// System.out.println("Total no of paragraph " + paragraphs.size());
			Map<Long, Patient> pat = new TreeMap<Long, Patient>();
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
				pat.put(patient.getCode(), patient);
			}
			// to remove duplicate object
			// Set<Patient> p = new HashSet<Patient>();
			// p.addAll(patients);
			// patients = new ArrayList<Patient>();
			// patients.addAll(p);
			// System.out.println("--===--size " + patients.size());
			fis.close();
			return pat;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
