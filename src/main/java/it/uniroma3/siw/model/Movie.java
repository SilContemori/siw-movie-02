package it.uniroma3.siw.model;

import java.util.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
@Entity
public class Movie {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    private String title;
    private Integer year;
    private String urlImage;
    @ManyToMany
    private List<Artist> actors; 
    @ManyToOne
    private Artist director;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	public boolean equals(Object o) {
		Movie m=(Movie)o;
		return this.getTitle().equals(m.getTitle()) && this.getYear()==m.getYear();
	}
	
	public int hashCode() {
		return this.getYear()+this.getTitle().hashCode();
	}
	public List<Artist> getActors() {
		return actors;
	}
	public void setActors(List<Artist> actors) {
		this.actors = actors;
	}
	public Artist getDirector() {
		return director;
	}
	public void setDirector(Artist director) {
		this.director = director;
	}
}

