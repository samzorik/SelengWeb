package Entities;
import javax.persistence.*;
import java.util.Date;
 
@Entity
@Table(name = "group_pokaz")
@NamedQuery(name = "Measurement_units.getAll", query = "SELECT c from Measurement_units c")
public class Measurement_units {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
 
    @Column(name = "name")
    private String name;
    
   
 
    public Measurement_units(String name) {
        this.name = name;
    }
 
    public Measurement_units() {
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