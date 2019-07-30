package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class pdf {

	public static void main(String[] args) {
		List<Patient> pat = readerPdf();

		writerPdf(pat);

	}

	private static void writerPdf(List<Patient> pat) {
		String filename = "c:\\Users\\User\\Documents\\result.pdf";
		try (PDDocument doc = new PDDocument()) {

			PDPage myPage = new PDPage();
			doc.addPage(myPage);

			try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {
				cont.beginText();
				cont.setFont(PDType1Font.TIMES_ROMAN, 12);
				cont.setLeading(14.5f);

				cont.newLineAtOffset(25, 700);
				String space = "                   ";
				for (Patient p : pat) {
					cont.showText(p.getCode() + space + p.getPatientName() + space + p.getNameMedicine() + space
							+ p.getNoOfTablets());
					cont.newLine();
				}
				cont.endText();
			}
			doc.save(filename);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static List<Patient> readerPdf() {
		System.out.println("hello");
		try (PDDocument document = PDDocument.load(new File("c:\\Users\\User\\Documents\\patient.pdf"))) {

			document.getClass();
			PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			stripper.setSortByPosition(true);
			PDFTextStripper tStripper = new PDFTextStripper();
			String pdfFileInText = tStripper.getText(document);
			// split by whitespace(Enter)
			String lines[] = pdfFileInText.split("\\r?\\n");
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
			// to remove duplicate object
			Set<Patient> p = new HashSet<Patient>();
			p.addAll(patients);
			patients = new ArrayList<Patient>();
			patients.addAll(p);
			return patients;
		} catch (Exception e) {
			return null;
		}

	}

}
