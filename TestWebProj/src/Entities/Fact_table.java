package Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "fact_table")
@NamedQuery(name = "Fact_table.getAll", query = "SELECT c from Fact_table c")
public class Fact_table {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	@Column(name = "year")
	private int year;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "pokaz")
	private Pokazateli pokaz;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "data_type")
	private Data_type data_type;
	@Column(name = "value")
	private double value;

	public Fact_table(int year, Pokazateli pokaz, Data_type data, double value) {
		this.year = year;
		this.pokaz = pokaz;
		this.data_type = data;
		this.value = value;
	}

	public Fact_table() {
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String toString() {
		return data_type.getName() + " " + year + "год " + pokaz.getName() + " = " + Double.toString(value);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Pokazateli getPokaz() {
		return pokaz;
	}

	public void setPokaz(Pokazateli pokaz) {
		this.pokaz = pokaz;
	}

	public Data_type getData_type() {
		return data_type;
	}

	public void setData_type(Data_type data_type) {
		this.data_type = data_type;
	}
}