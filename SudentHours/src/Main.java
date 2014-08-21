/****************************************************************************************
 * Application to extract Student hours by name from Quick Books Invoice dump
 * Vic Wintriss version 140821A Trying to change to Erik's sort
 ****************************************************************************************/

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Main
{
	public Student student;
	private List<Student> students = new ArrayList<Student>();
	private String studentName = "";
	private Cell memo;
	private double classHours;
	private String classHoursString;
	private int nameCell = 5;// column 5 Name
	private int memoCell = 4;
	private FileInputStream file;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private Cell name;

	public static void main(String[] args) throws IOException
	{
		new Main().getGoing();
	}

	private void getGoing() throws IOException
	{
		file = new FileInputStream(new File(
				"/Users/VicMini/git/StudentHour/SudentHours/src/July2014StudentHours.xls"));
		workbook = new HSSFWorkbook(file);
		sheet = workbook.getSheetAt(0);
		for (Row row : sheet)
		{
			name = row.getCell(nameCell);
			memo = row.getCell(memoCell);
			if (memo != null)
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
			}
			if (name != null)
			{
				studentName = name.getStringCellValue();
				if (!studentName.equals("Name"))
				{
					students.add(new Student(studentName, classHours));
				}
			}
		}

		printStudents(students);
	}

	public static void printStudents(List<Student> students)
	{
		for (Student shr : students)
		{
			System.out.println(shr.getName() + "         "
					+ shr.getHours());
		}
	}
}