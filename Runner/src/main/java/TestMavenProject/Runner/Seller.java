package TestMavenProject.Runner;
import javax.persistence.*;
import java.util.Date;
@Entity
public class Seller {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column
    private String fullName; // Полное имя
    @Column
	private int age;         // Возвраст
    @Column
    private float salary;    // Зар. плата 
    @ManyToOne
    @JoinColumn
    private Car car;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	

    public Seller(String fullName, int age, float salary,Car car) {
        this.fullName = fullName;
        this.age = age;
        this.salary = salary;
        this.car=car;
    }
 
    public Seller() {
    }
 
    public String getFullName() {
        return fullName;
    }
 
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
 
    public int getAge() {
        return age;
    }
 
    public void setAge(int age) {
        this.age = age;
    }
 
    public float getSalary() {
        return salary;
    }
 
    public void setSalary(float salary) {
        this.salary = salary;
    }
 
    @Override
    public String toString() {
        return "Seller{" +
                "fullName='" + fullName + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}