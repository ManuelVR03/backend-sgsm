package com.examples.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examples.DTO.AuthRequestDTO;
import com.examples.DTO.AuthResponseDTO;
import com.examples.seguridad.JwtUtil;
import com.examples.services.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequest) {
		System.out.println("Intentando autenticar usuario: " + authRequest.getDni());

		try {
			Authentication auth = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getDni(), authRequest.getPass())
			);

			final UserDetails userDetails = usuarioService.loadUserByUsername(authRequest.getDni());

			final String jwt = jwtUtil.generateToken(
				userDetails.getUsername(),
				userDetails.getAuthorities().iterator().next().getAuthority()
		 );

			var usuario = usuarioService.getUsuarioByDni(authRequest.getDni());
			Integer barId = (usuario.getBar() != null) ? usuario.getBar().getId() : null;

			return ResponseEntity.ok(
				new AuthResponseDTO(
					jwt,
					userDetails.getAuthorities().iterator().next().getAuthority(),
					usuario.getId(),
					barId
				)
			);
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
		}
	}
}
