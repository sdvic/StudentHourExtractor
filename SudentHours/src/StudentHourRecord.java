
public class StudentHourRecord
{
	private String studentName;
	private double hours;

	public StudentHourRecord(String studentName, double doublePrice)
	{
		if (!studentName.equals("Name"));
		{
			this.studentName = studentName;
			this.hours = doublePrice;
		}
		
	}
	public String getStudentName()
	{
		return studentName;
	}

	public void setStudentName(String studentName)
	{
		this.studentName = studentName;
	}

	public double getHours()
	{
		return hours;
	}

	public void setHours(double hours)
	{
		this.hours = hours;
	}
}
