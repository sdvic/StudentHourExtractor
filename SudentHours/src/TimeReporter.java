/****************************************************************************************
 * Application to extract Student hours by name from Quick Books Invoice dump
 * Vic Wintriss version 140822A Incorporating Erik's clean-up...trying to extract report date
 ****************************************************************************************/

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class TimeReporter {
	private final Map<String, Student> database = new HashMap<String, Student>();

	private static final int NAME_CELL = 5;// column 5 Name
	private static final int MEMO_CELL = 4;// column 4 Memo
	private static final int DATE_CELL = 5;// column 4 Date
	private static final Pattern TUITION_PATTERN = Pattern
			.compile("tuition - (\\d*\\.?\\d+) hr session");
	private static String reportDate;

	public static void main(String[] args) throws IOException {
		TimeReporter reporter = new TimeReporter();
		reporter.readStudentHours();
		reporter.printStudents();
	}

	private void readStudentHours() throws IOException {
		InputStream file = getClass().getResourceAsStream(
				"July2014StudentHours.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			Cell name = row.getCell(NAME_CELL);
			Cell memo = row.getCell(MEMO_CELL);
			Cell date = row.createCell(DATE_CELL);
		
			double classHours = 0.0;
			if (memo != null && name != null) {

				String classHoursString = memo.toString();
				Matcher m = TUITION_PATTERN.matcher(classHoursString);
				if (m.matches()) {
					classHours = Double.parseDouble(m.group(1));
				} else if (classHoursString.contains("workshop")) {
					classHours = 10.0;
				}

				if (classHours != 0.0) {
					String studentName = name.getStringCellValue();
					Student student = database.get(studentName);
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
		// System.out.println("Month Student Hour Summary as of \n\n" +
		// reportDate);
		Student[] students = new Student[database.size()];
		students = database.values().toArray(students);
		Arrays.sort(students);
		for (Student s : students) {
			System.out.println(s);
		}
	}
}