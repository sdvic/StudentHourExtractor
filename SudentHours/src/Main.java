/****************************************************************************************
 * Application to extract Student hours by name from Quick Books Invoice dump
 * Vic Wintriss version 140813A Now getting class length from memo column
 ****************************************************************************************/

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Main
{
	public StudentHourRecord studentHourRecord;
	private ArrayList<StudentHourRecord> studentHourRecordList = new ArrayList<StudentHourRecord>();
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
				"/Users/VicMini/-WTS/StudentRecords/July2014StudentHours.xls"));
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
					if (classHours > 0)
					{
						studentHourRecordList.add(new StudentHourRecord(
								studentName, classHours, false));
					}
				}
			}
			classHours = 0;
		}
		for (int i = 0; i < studentHourRecordList.size(); i++)
		{
			if (!studentHourRecordList.get(i).isProcessed())
			{
				studentHourRecord = studentHourRecordList.get(i);
				studentName = studentHourRecord.getStudentName();
				studentHourRecordList.get(i).setProcessed(true);
				classHours = studentHourRecord.getClassLength();
				System.out.format("%-25s", studentName);
				double testDouble = accumulateStudentHours(studentName,
						classHours);
				System.out.format("%.1f", testDouble);
				System.out.println();
			}
		}
	}

	public double accumulateStudentHours(String studentNameToCheck,
			double studentHours)
	{
		studentName = studentNameToCheck;
		for (int i = 0; i < studentHourRecordList.size(); i++)
		{
			if (studentName.equals(studentHourRecordList.get(i)
					.getStudentName())
					&& !studentHourRecordList.get(i).isProcessed())
			{
				studentHours += studentHourRecordList.get(i).getClassLength();
				studentHourRecordList.get(i).setProcessed(true);
			}
		}
		return studentHours;
	}
}

// http://viralpatel.net/blogs/java-read-write-excel-file-apache-poi/
