package Entities;
import javax.persistence.*;
import java.util.Date;
 
@Entity
@Table(name = "pokazateli")
@NamedQuery(name = "Pokazateli.getAll", query = "SELECT c from Pokazateli c")
public class Pokazateli {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
 
    @Column(name = "name")
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "group_pokaz_id")
    private Group_pokaz gp;
    
    @ManyToOne
    @JoinColumn(name= "measurement_units_id")
    private Measurement_units mu;
 
    public Pokazateli(String name,Group_pokaz gp, Measurement_units mu) {
        this.name = name;
        this.gp = gp;
        this.mu = mu;
    }
 
    public Pokazateli() {
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public Group_pokaz getGp() {
		return gp;
	}

	public void setGp(Group_pokaz gp) {
		this.gp = gp;
	}

	public Measurement_units getMu() {
		return mu;
	}

	public void setMu(Measurement_units mu) {
		this.mu = mu;
	}
}