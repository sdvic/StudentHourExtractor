/*********************************************************************
 * Application to extract Student hours by name from Quick Books Invoice dump
 * Vic Wintriss version 140811A
 *********************************************************************/

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

	public static void main(String[] args) throws IOException
	{
		new Main().getGoing();
	}

	private void getGoing() throws IOException
	{
		String studentName = "";
		double studentHours = 0;
		ArrayList<String> studentNameList = new ArrayList<String>();
		ArrayList<StudentHourRecord> studentHourRecordList = new ArrayList<StudentHourRecord>();
		int nameCell = 5;// column 5 Name
		int priceCell = 7;// column 7 SalesPrice
		FileInputStream file = new FileInputStream(new File(
				"/Users/VicMini/-WTS/StudentRecords/July2014StudentHours.xls"));
		// Get the workbook instance for XLS file
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		// Get first sheet from the workbook
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet)
		{
			Cell name = row.getCell(nameCell);
			Cell price = row.getCell(priceCell);
			if (name != null && price != null)
			{
				String stringPrice = price.toString();
				studentName = name.getStringCellValue();
				if (!studentName.equals("Name")
						|| !stringPrice.equals("Sales Price"))
				{
					double doublePrice = Double.parseDouble(stringPrice);
					if (doublePrice > 0)
					{
						studentHourRecordList.add(new StudentHourRecord(
								studentName, doublePrice));
					}
				}
			}
		}

		for (StudentHourRecord sr : studentHourRecordList)
		{
			System.out.print(sr.getStudentName() + "\t\t\t\t\t");
			if (sr.getHours() == 37.5)
			{
				System.out.println(1);
			}
			if (sr.getHours() == 60.0)
			{
				System.out.println(1.5);
			}
			if (sr.getHours() == 75.0)
			{
				System.out.println(2);
			}
			if (sr.getHours() == 350.0)
			{
				System.out.println(10);
			}
		}
	}
}

// http://viralpatel.net/blogs/java-read-write-excel-file-apache-poi/
// cell.setCellType(Cell.CELL_TYPE_STRING);
// Cell mycell = myrow.createCell(0);
//
// //Make it numeric by default.
// int cellType = Cell.CELL_TYPE_NUMERIC;
//
// if (someCondition) {
// cellType = Cell.CELL_TYPE_STRING;
// }
//
// mycell.setCellType(cellType);