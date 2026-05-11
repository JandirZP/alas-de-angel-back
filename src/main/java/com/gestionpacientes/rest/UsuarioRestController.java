package com.gestionpacientes.rest;

import com.gestionpacientes.dto.LoginRequestDto;
import com.gestionpacientes.dto.LoginResponseDto;
import com.gestionpacientes.dto.UsuarioDto;
import com.gestionpacientes.dto.UsuarioProfileDto;
import com.gestionpacientes.dto.EspecialidadesDto;
import com.gestionpacientes.entity.UsuarioEntity;
import com.gestionpacientes.service.CloudinaryService;
import com.gestionpacientes.service.UsuarioService;
import com.gestionpacientes.service.security.JwtService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuarioRest")
public class UsuarioRestController {

    @Autowired
    private UsuarioService servicio;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CloudinaryService cloudinaryService;

    // SEGURIDAD

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        var usuarioEncontradoOpt = servicio.findByCorreo(loginRequestDto.getCorreo());
        if (!usuarioEncontradoOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El correo ingresado no existe");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getCorreo(),
                            loginRequestDto.getPassword()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String tokenGenerado = jwtService.generateToken(userDetails);

            // 1. Obtenemos los roles desde el objeto de seguridad ya cargado
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .toList();

            // 2. Obtenemos el nombre real del usuario (opcional, por estética)
            String nombreBonito = usuarioEncontradoOpt.get().getNombres();

            // 3. Devolvemos todo junto
            return ResponseEntity.ok(new LoginResponseDto(tokenGenerado, nombreBonito, roles));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La contraseña es incorrecta");
        }

    }

    @GetMapping("/medicos/especialidad/{codigo}")
    public ResponseEntity<List<UsuarioDto>> getMedicosByEspecialidad(@PathVariable Long codigo) {
        List<UsuarioDto> medicos = servicio.findMedicosByEspecialidad(codigo);
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/perfil")
    public UsuarioEntity perfil(Authentication authentication) {
        String correo = authentication.getName();
        UsuarioEntity usuario = servicio.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));
        return usuario;
    }

    // Cloudinary

    @PostMapping("/upload/{id}")
    public ResponseEntity<?> subirFoto(@PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        // CORRECCIÓN 1: Tu servicio devuelve UsuarioEntity directo, no Optional.
        // Cambiamos el tipo de variable y la validación.
        UsuarioEntity usuario = servicio.findById(id);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        // 2. Validar que el archivo no esté vacío
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo está vacío");
        }

        try {
            // 3. Subir la imagen a Cloudinary
            Map resultado = cloudinaryService.upload(file, "fotos_perfil");
            String urlImagen = (String) resultado.get("secure_url");

            // 4. PREPARAMOS EL DTO (Para que el service lo entienda)
            UsuarioProfileDto dto = new UsuarioProfileDto();

            // Copiamos los datos actuales del usuario al DTO para no perder nada
            BeanUtils.copyProperties(usuario, dto, "idUsuario", "password", "roles", "ubigeoEntity");

            // Le ponemos la nueva URL de la foto
            dto.setFotoUrl(urlImagen);

            // El id del ubigeo hay que pasarlo manualmente porque en la entidad es objeto
            if (usuario.getUbigeoEntity() != null) {
                dto.setIdUbigeo(usuario.getUbigeoEntity().getIdUbigeo());
            }

            // Ahora sí le estamos pasando (Long, UsuarioProfileDto)
            servicio.update(id, dto);

            // 6. Responder con la nueva URL
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Foto actualizada con éxito");
            respuesta.put("url", urlImagen);

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la imagen: " + e.getMessage());
        }
    }

    @GetMapping("/paciente/documento/{documento}")
    public ResponseEntity<?> getPacientePorDocumento(@PathVariable String documento) {
        Optional<UsuarioDto> pacienteOpt = servicio.findPatientByDocumento(documento);

        if (pacienteOpt.isPresent()) {
            return ResponseEntity.ok(pacienteOpt.get());
        } else {
            // Devolvemos 404 para que el front sepa que no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un paciente con ese número de documento");
        }
    }

    // --- CRUD BÁSICO ---
    @GetMapping
    public List<UsuarioEntity> findAll() {
        return servicio.findAll();
    }

    @GetMapping("/custom")
    public List<UsuarioEntity> findAllCustom() {
        return servicio.findAllCustom();
    }

    @GetMapping("/{id}")
    public UsuarioEntity findById(@PathVariable long id) {
        return servicio.findById(id);
    }

    @PostMapping
    public UsuarioEntity add(@RequestBody UsuarioEntity obj) {
        return servicio.add(obj);
    }

    @PostMapping("/pacientes/registro")
    public ResponseEntity<?> registrarPaciente(@RequestBody com.gestionpacientes.dto.RegistroPacienteDto dto) {
        try {
            UsuarioEntity nuevoPaciente = servicio.registrarPaciente(dto);
            // Retornamos OK y podríamos devolver un mensaje o el usuario (o un DTO de respuesta)
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPaciente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint específico para actualizar perfil de paciente
    @PutMapping("/perfil/{id}")
    public UsuarioEntity updatePerfil(@PathVariable Long id, @RequestBody UsuarioProfileDto dto) {
        return servicio.update(id, dto);
    }

    // [EXPLICACIÓN ACTUALIZACIÓN]
    // Endpoint dedicado de actualización (PUT). Aislar esto fuera de updatePerfil
    // nos ayuda a mantener
    // limpio el DTO de perfil base, y tratar el guardado ManyToMany
    // (Medico_Especialidad) de forma independiente recibiendo un JSON Array de
    // DTOs.
    @PutMapping("/{id}/especialidades")
    public ResponseEntity<List<EspecialidadesDto>> updateEspecialidades(@PathVariable Long id,
            @RequestBody List<EspecialidadesDto> dtos) {
        List<EspecialidadesDto> actualizadas = servicio.updateEspecialidades(id, dtos);
        return ResponseEntity.ok(actualizadas);
    }

    @DeleteMapping("/{id}")
    public UsuarioEntity delete(@PathVariable long id) {
        return servicio.delete(id);
    }

    @PutMapping("/enable/{id}")
    public UsuarioEntity enable(@PathVariable long id) {
        return servicio.enable(id);
    }

    // --- BÚSQUEDAS PERSONALIZADAS ---

    // Buscar por Correo
    @GetMapping("/buscar-correo")
    public UsuarioEntity findByCorreo(@RequestParam String correo) {
        return servicio.findByCorreo(correo).orElse(null);
    }

    // Usuarios por Rol
    @GetMapping("/por-rol/{nombreRol}")
    public List<UsuarioEntity> findByRolesNombre(@PathVariable String nombreRol) {
        return servicio.findByRoles_Nombre(nombreRol);
    }

    // Usuarios Activos por Rol
    @GetMapping("/por-rol-activo/{nombreRol}")
    public ResponseEntity<List<UsuarioDto>> findByRolesNombreAndEstado(@PathVariable String nombreRol) {
        return ResponseEntity.ok(servicio.buscarPorRolActivo(nombreRol));
    }
}
