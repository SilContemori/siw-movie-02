package it.uniroma3.siw.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.MovieRepository;
import it.uniroma3.siw.service.ArtistService;
import it.uniroma3.siw.service.MovieService;

@Controller
public class MovieController {
	@Autowired MovieService movieService;
	@Autowired MovieRepository movieRepository;
	@Autowired ArtistService artistService;
	@Autowired ArtistRepository artistRepository;

	@GetMapping("/")
	public String showHome(Model model) {
		return "index.html";
	}
	
	@GetMapping("/formNewMovie")
	public String formNewMovie(Model model) {
		model.addAttribute("movie", new Movie()); // serve 
		return "formNewMovie.html";
	}
	
	@GetMapping("/addDirector/{id}")
	public String addDirector(Model model) {
		model.addAttribute("artists", this.artistService.findAll());
	    return "addDirector.html";
	}
	
	@GetMapping("/formUpdateMovie/{id}")
	public String formUpdateMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("movie", this.movieService.findById(id));
		return "formUpdateMovie.html";
	}
	
	@GetMapping("/setDirectorToMovie/{directorId}/{movieId}")
	public String setDirectorToMovie(@PathVariable("directorId") Long directorId, @PathVariable("movieId") Long movieId, Model model) {
		
		Artist director = this.artistRepository.findById(directorId).get();
		Movie movie = this.movieRepository.findById(movieId).get();
		movie.setDirector(director);
		this.movieRepository.save(movie);
		
		model.addAttribute("movie", movie);
		return "formUpdateMovie.html";
	}
	
	
		
	


	@PostMapping("/movie")
	public String newMovie(@ModelAttribute("movie") Movie movie, Model model) {
		System.out.println("-----------------------------------------------------------------");
		if (!movieRepository.existsByTitleAndYear(movie.getTitle(), movie.getYear())) {
			this.movieService.save(movie);
			return "redirect:movie/"+movie.getId();
		} else {
			model.addAttribute("messaggioErrore", "Questo film esiste già");
			return "formNewMovie.html";
		}
	}

	@GetMapping("/movie/{id}")
	public String getMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("movie", this.movieService.findById(id));
		return "movie.html";
	}

	@GetMapping("/movie")
	public String showMovies(Model model) {
		model.addAttribute("movies", this.movieService.findAll());
		return "movies.html";
	}

	@GetMapping("/menageMovies")
	public String showManageMovies(Model model) {
		model.addAttribute("movies", this.movieService.findAll());
		return "menageMovies.html";
	}
	
	@GetMapping("/formSearchMovies")
	public String formSearchMovies(Model model) {
		model.addAttribute("movie", new Movie()); // serve 
		return "formSearchMovies.html";
	}
	

	@PostMapping("/searchMovies")
	public String movieAnno(@ModelAttribute("movie") Movie movie, Model model) {
		return "redirect:movieOfYear/"+movie.getYear();
	}
	
	@GetMapping("/movieOfYear/{year}")
	public String getMovieOfYear(@PathVariable("year") Integer year, Model model) {
		model.addAttribute("movies", this.movieService.findByYear(year));
		return "movies.html";
	}
	
	
	@GetMapping("/*")
	public String prova(Model model) {
		model.addAttribute("movies", this.movieService.findAll());
		return "notFound.html";
	}
}

