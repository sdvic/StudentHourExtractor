
public class StudentHourRecord
{
	private String studentName;
	private boolean processed;
	private double classLength;

	public StudentHourRecord(String studentName, double classLength, boolean processed)
	{
		if (!studentName.equals("Name"));
		{
			this.studentName = studentName;
			this.processed = processed;
			this.setClassLength(classLength);
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

	public boolean isProcessed()
	{
		return processed;
	}
	public void setProcessed(boolean processed)
	{
		this.processed = processed;
	}
	public double getClassLength()
	{
		return classLength;
	}
	public void setClassLength(double classLength)
	{
		this.classLength = classLength;
	}
}
