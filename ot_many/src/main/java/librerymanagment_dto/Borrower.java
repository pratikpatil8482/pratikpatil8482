package librerymanagment_dto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Borrower {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int membership_ID;

	private String name;
	private long mobile;
	
	@OneToMany(cascade = CascadeType.ALL)
	List<Book>book;
	
	LocalDate date;

	LocalDate dueDate;
	
	//update
	double penalty;

}
