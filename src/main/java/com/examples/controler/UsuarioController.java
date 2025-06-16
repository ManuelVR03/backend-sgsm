package com.examples.controler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examples.DTO.UsuarioDTO;
import com.examples.DTO.UsuarioDTOConverter;
import com.examples.modelo.Usuario;
import com.examples.repositorio.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apiUsuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private UsuarioDTOConverter usuarioDTOConverter;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/usuario")
	public ResponseEntity<?> obtenerTodos() {
		List<Usuario> result = usuarioRepositorio.findAll();
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			List<UsuarioDTO> dtoList = result.stream().map(usuarioDTOConverter::convertirADto)
					.collect(Collectors.toList());
			return ResponseEntity.ok(dtoList);
		}
	}

	@PostMapping("/usuario")
	public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDTO userDto) {
	    Usuario user = usuarioDTOConverter.convertirAUsu(userDto);

	    if (user.getPass() != null && !user.getPass().isBlank()) {
	        user.setPass(passwordEncoder.encode(user.getPass()));
	    }

	    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepositorio.save(user));
	}

	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
		if (usuarioRepositorio.existsById(id)) {
			Usuario result = usuarioRepositorio.findById(id).get();
			usuarioRepositorio.delete(result);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/usuario/{id}")
	public ResponseEntity<?> editarUsuario(@PathVariable Integer id, @RequestBody UsuarioDTO user) {
		if (usuarioRepositorio.existsById(id)) {
			Optional<Usuario> old = usuarioRepositorio.findById(id);
			Usuario nuevo = usuarioDTOConverter.convertirAUsu(user);
			nuevo.setId(id);
			if (!nuevo.getPass().isBlank()) {
				nuevo.setPass(passwordEncoder.encode(nuevo.getPass()));
			} else {
				nuevo.setPass(old.get().getPass());
			}
			Usuario actualizado = usuarioRepositorio.save(nuevo);
			UsuarioDTO dto = usuarioDTOConverter.convertirADto(actualizado);
			return ResponseEntity.ok(dto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
