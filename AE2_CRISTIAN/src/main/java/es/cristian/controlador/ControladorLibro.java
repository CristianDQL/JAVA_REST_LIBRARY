package es.cristian.controlador;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.cristian.modelo.entidad.Libro;
import es.cristian.modelo.persistencia.DaoLibro;

@RestController
public class ControladorLibro {
	
	/*
	 * Se realiza una inyección del objeto DaoLibro, así se conectan
	   ambos objetos para realizar las peticiones HTTP correspondientes.
	 */
	//
	@Autowired
	public DaoLibro daoLibro;
	
	
	
		//Dar de alta un libro
			
		@PostMapping(path = "libros", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Libro> altaLibro(@RequestBody Libro libroNuevo) {
			System.out.println("Alta del libro: objeto libro: " + libroNuevo);
			// Intenta agregar el libro a la base de datos y guarda el resultado
			boolean agregado = daoLibro.add(libroNuevo);

			// Verifica si el libro se agregó correctamente
			if (agregado) {
				// Si se agregó correctamente, devuelve el libro y un estado 201 CREATED
				return new ResponseEntity<>(libroNuevo, HttpStatus.CREATED);
			} else {
				// Si no se puede agregar el libro, devuelve un estado 400 BAD REQUEST
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		
		
		
		
	
	    //Borra un libro del listado
		@DeleteMapping(path="libros/{id}")
		public ResponseEntity<Libro> borrarPersona(@PathVariable("id")int id){
			System.out.println("Id a borrar: " + id);
			Libro l = daoLibro.delete(id);
			if(l != null) {
				return new ResponseEntity<Libro>(l,HttpStatus.OK);
				
			}else {
				return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
				
			}
		}
		
		//MOdificación del listado de libros
		@PutMapping(path="libros/{id}",consumes=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Libro> modificarLibro(
				@PathVariable("id") int id,
				@RequestBody Libro l){
			System.out.println("ID a modificar: " + id);
			System.out.println("Datos a modificar: " + l);
			l.setId(id);
			Libro lUpdate = daoLibro.update(l);
			if(lUpdate != null) {
				return new ResponseEntity<Libro>(HttpStatus.OK); //lUpdate en respuesta?
			}else {
				return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
			}
		}
	
		//get Libro por Id
		@GetMapping(path="libros/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Libro> getLibro(@PathVariable("id")int id) {
			System.out.println("Buscando libro por id: " + id);
			Libro l = daoLibro.get(id);
			
			if(l != null) {
				return new ResponseEntity<Libro>(l,HttpStatus.OK);
			}else {
				return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND); //404
			}
			
		}
		
		//Obtener toda la lista de libros + ?nombre=algo ??
		@GetMapping(path="libros", produces=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Libro>> listarLibros(){
				//(@RequestParam(name="nombre", required=false)String nombre){
			
		
				System.out.println("Listado de libros");
				List<Libro> listaLibros= daoLibro.list();
			return new ResponseEntity<List<Libro>>(listaLibros,HttpStatus.OK);
			
		}
	
	
	
	
	
	
	
	
	
	
	
}
