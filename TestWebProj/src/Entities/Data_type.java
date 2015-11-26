package Entities;

import javax.persistence.*;
import java.util.Date;
 
@Entity
@Table(name = "data_type")
@NamedQuery(name = "Data_type.getAll", query = "SELECT c from Data_type c")
public class Data_type {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
 
    @Column(name = "name")
    private String name;
 
    public Data_type(String name) {
        this.name = name;
    }
 
    public Data_type() {
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
}