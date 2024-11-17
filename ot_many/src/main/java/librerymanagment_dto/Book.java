package librerymanagment_dto;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Book {

	// isbn is a primary key for book
	@Id
	private long isbn;

	private String title;
	private String auther;
	private String genre;
	private int quantity;

}
