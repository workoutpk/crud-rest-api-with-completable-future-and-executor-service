package com.pk.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name ="book")
@Accessors(chain = true)
public class Book {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
	@NotBlank(message = "/ name / is mandatory")
    @Size(max = 20)
    private String name;
	public Long getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + "]";
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
