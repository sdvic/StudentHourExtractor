/****************************************************************************************
 * Application to extract Student hours by name from Quick Books Invoice dump
 * Vic Wintriss version 140821B Works perfectly with Erik's corrections
 ****************************************************************************************/

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Main
{
	private final Map<String, Student> database = new HashMap<String, Student>();
	private String classHoursString;
	private static final int NAME_CELL = 5;// column 5 Name
	private static final int MEMO_CELL = 4;
	private static final Pattern MEMO_CELL_PATTERN = Pattern.compile(
			"\\d{4} tuition - ([+-]?\\d*\\.?\\d+)\\s+hr"
			);

	public static void main(String[] args) throws IOException
	{
		new Main().getGoing();
	}

	private void getGoing() throws IOException
	{
		FileInputStream file = new FileInputStream(new File(
				"/Users/Carl/git/StudentHourExtractor/SudentHours/src/July2014StudentHours.xls"));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet)
		{
			Cell name = row.getCell(NAME_CELL);
			Cell memo = row.getCell(MEMO_CELL);
			double classHours = 0.0;
			if (memo != null && name != null)
			{
				
				classHoursString = memo.toString();
				if (classHoursString.contains("- 1 hr"))
				{
					classHours = 1.0;
				}
				if (classHoursString.contains("- 1.5 hr"))
				{
					classHours = 1.5;
				}
				if (classHoursString.contains("- 2 hr"))
				{
					classHours = 2.0;
				}
				if (classHoursString.contains("workshop"))
				{
					classHours = 10.0;
				}

				String studentName = name.getStringCellValue();
				if (!studentName.equals("Name"))
				{
					Student student = database.get(studentName);
					if(student == null){
						student = new Student(studentName, 0.0);
						database.put(studentName, student);
					}
					student.addTime(classHours);
				}
			}
		}

		printStudents();
	}

	public void printStudents()
	{
		Student[] students = new Student[database.size()];
		students = database.values().toArray(students);
		Arrays.sort(students);
		for (Student s : students) {
			System.out.println(s);
		}
	}
}