/****************************************************************************************
 * Application to extract Student hours by name from Quick Books Invoice dump
 * Vic Wintriss version 140901A Working with report date, total Student Hours,
 * Scholarship Hours???? and Average Class Hours per Student
 ****************************************************************************************/

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class TimeReporter {
	private final Map<String, Student> database = new HashMap<String, Student>();

	private static final int NAME_CELL = 5;// column 5 Name
	private static final int MEMO_CELL = 4;// column 4 Memo
	private static final int DATE_CELL = 2;// column 3 Date
	private static final Pattern TUITION_PATTERN = Pattern
			.compile("tuition - (\\d*\\.?\\d+) hr session");
	private static Date reportDate;
	private static double totalMonthStudentHours;
	private static Student student = new Student("initial");
	private static String studentName;
	private static double totalMonthScholarshipHours;

	public static void main(String[] args) throws IOException {
		TimeReporter reporter = new TimeReporter();
		reporter.readStudentHours();
		reporter.printStudents();
	}

	private void readStudentHours() throws IOException {
		InputStream file = getClass().getResourceAsStream(
				"July2014StudentHours.xls");
		String userInput = JOptionPane
				.showInputDialog("What is the location of your Student Record File?");
		userInput = "July2014StudentHours.xls";
		file = getClass().getResourceAsStream(userInput);
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			Cell name = row.getCell(NAME_CELL);
			Cell memo = row.getCell(MEMO_CELL);
			Cell dateSring = row.getCell(DATE_CELL);

			double classHours = 0.0;
			if (memo != null && name != null) {

				String classHoursString = memo.toString();
				String scholarshipString = memo.toString();
				Matcher m = TUITION_PATTERN.matcher(classHoursString);
				if (m.matches()) {
					classHours = Double.parseDouble(m.group(1));
				} else if (classHoursString.contains("workshop")) {
					classHours = 10.0;
				}
				if (scholarshipString.contains("scholarship")) {
					totalMonthScholarshipHours += 10;
				}

				if (classHours != 0.0) {
					studentName = name.getStringCellValue();
					reportDate = dateSring.getDateCellValue();
					student = database.get(studentName);
					if (student == null) {
						student = new Student(studentName);
						database.put(studentName, student);
					}
					student.addTime(classHours);
				}
			}
		}
	}

	private void printStudents() {
		System.out.println("Monthly Student Hour Summary as of " + reportDate
				+ "\n");
		Student[] students = new Student[database.size()];
		students = database.values().toArray(students);
		Arrays.sort(students);
		for (Student s : students) {
			System.out.println(s);

			totalMonthStudentHours += s.getHours();
		}
		System.out.println("\nTotal Student hours for this month "
				+ totalMonthStudentHours);
		System.out.println("\nAverage Class Hours per Student for this Month "
				+ (int)totalMonthStudentHours/database.size());
		System.out.println("\nTotal Scholarship hours for this month "
				+ totalMonthScholarshipHours);
	}
}