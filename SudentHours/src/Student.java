
public class Student implements Comparable<Student>
{
	private String name;
	private double hours;

	public Student(String name, double hours)
	{
			this.name = name;
			this.hours = hours;
	}
	public String getName()
	{
		return name;
	}

	public double getHours()
	{
		return hours;
	}
	public void setClassLength(double classLength)
	{
		this.hours = classLength;
	}
	@Override
	public int compareTo(Student o)
	{
		return name.compareTo(o.getName());
	}
	
	@Override
	public String toString()
	{
		return String.format("%-25s %.1f", name, hours);
	}
	
	public double addTime(double time)
	{
		hours += time;
		return hours;
	}
}
