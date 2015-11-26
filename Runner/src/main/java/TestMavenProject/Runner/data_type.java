package TestMavenProject.Runner;
import javax.persistence.*;
import java.util.Date;
 
@Entity
@Table(name = "data_type")
@NamedQuery(name = "Data_type.getAll", query = "SELECT c from data_type c")
public class data_type {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
 
    @Column(name = "name")
    private String name;
 
    public data_type(String name) {
        this.name = name;
    }
 
    public data_type() {
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
    public String toString() {
		return id+" "+name;
	}
}