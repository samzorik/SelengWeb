package Entities;
import javax.persistence.*;
import java.util.Date;
 
@Entity
@Table(name = "group_pokaz")
@NamedQuery(name = "Group_pokaz.getAll", query = "SELECT c from Group_pokaz c")
public class Group_pokaz {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
 
    @Column(name = "name")
    private String name;
    
    @Column(name = "is_sp")
    private int is_sp;
 
    public Group_pokaz(String name,int is_sp) {
        this.name = name;
        this.is_sp = is_sp;
    }
 
    public Group_pokaz() {
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
		return id+" "+name+" "+is_sp;
	}

	public int getIs_sp() {
		return is_sp;
	}

	public void setIs_sp(int is_sp) {
		this.is_sp = is_sp;
	}
}