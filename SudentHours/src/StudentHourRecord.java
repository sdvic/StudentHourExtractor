
public class StudentHourRecord
{
	private String studentName;
	private double hours;
	private boolean processed;

	public StudentHourRecord(String studentName, double doublePrice, boolean processed)
	{
		if (!studentName.equals("Name"));
		{
			this.studentName = studentName;
			this.hours = doublePrice;
			this.processed = processed;
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
	public boolean isProcessed()
	{
		return processed;
	}
	public void setProcessed(boolean processed)
	{
		this.processed = processed;
	}
}
