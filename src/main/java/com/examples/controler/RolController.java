package com.examples.controler;

import com.examples.DTO.RolDTO;
import com.examples.DTO.RolDTOConverter;
import com.examples.modelo.Rol;
import com.examples.repositorio.RolRepositorio;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/apiRoles")
@RequiredArgsConstructor
public class RolController {

	@Autowired
    private RolRepositorio rolRepositorio;
    
    @Autowired
    private RolDTOConverter rolDTOConverter;

    @GetMapping("/rol")
    public ResponseEntity<?> obtenerTodos() {
        List<Rol> result = rolRepositorio.findAll();
        if(result.isEmpty()) {
        	return ResponseEntity.notFound().build();
        } else {
        	List<RolDTO> dtoList = result.stream().map(rolDTOConverter::convertirADto).collect(Collectors.toList());
        	return ResponseEntity.ok(dtoList);
        }
        
    }

}
