package com.gestionpacientes.service.impl;

import com.gestionpacientes.dto.UsuarioDto;
import com.gestionpacientes.dto.UsuarioProfileDto;
import com.gestionpacientes.dto.EspecialidadesDto;
import com.gestionpacientes.entity.*;
import com.gestionpacientes.repository.*;
import com.gestionpacientes.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repositorio;

    // 1. INYECTAMOS LOS REPOSITORIOS NECESARIOS
    @Autowired
    private TipoDocumentoRepository tipoDocRepo;
    @Autowired
    private UbigeoRepository ubigeoRepo;
    @Autowired
    private NivelProfesionalRepository nivelProfRepo;
    @Autowired
    private RolRepository rolRepo;
    @Autowired
    private EspecialidadRepository especialidadRepo;

    @Autowired
    private HistoriaClinicaRepository hcRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioEntity> findAll() {
        return repositorio.findAll();
    }

    @Override
    public List<UsuarioEntity> findAllCustom() {
        return repositorio.findAllCustom();
    }

    @Override
    public List<UsuarioEntity> findByRoles_Nombre(String nombre) {
        return repositorio.findByRoles_Nombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDto> buscarPorRolActivo(String nombreRol) {
        List<UsuarioEntity> usuarios = repositorio.findByRoles_NombreAndEstadoTrue(nombreRol);
        return usuarios.stream().map(usuario -> UsuarioDto.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombres(usuario.getNombres())
                .apellidoPaterno(usuario.getApellidoPaterno())
                .apellidoMaterno(usuario.getApellidoMaterno())
                .numeroDocumento(usuario.getNumeroDocumento())
                .correo(usuario.getCorreo())
                .fotoUrl(usuario.getFotoUrl())
                .especialidades(usuario.getEspecialidades() == null ? null
                        : usuario.getEspecialidades().stream()
                                .map(e -> EspecialidadesDto.builder().codigo(e.getCodigo())
                                        .nombre(e.getNombre()).build())
                                .collect(Collectors.toList()))
                .build()).toList();
    }

    @Override
    public Optional<UsuarioEntity> findByCorreo(String correo) {
        return repositorio.findByCorreo(correo);
    }

    @Override
    public UsuarioEntity findById(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));
    }

    @Override
    public UsuarioEntity add(UsuarioEntity obj) {

        System.out.println("--- INICIANDO REGISTRO DE USUARIO ---");

        // 1. ENCRIPTAR CONTRASEÑA
        if (obj.getPassword() != null) {
            obj.setPassword(passwordEncoder.encode(obj.getPassword()));
        }

        // 2. TIPO DE DOCUMENTO
        if (obj.getTipoDocumentoEntity() != null) {
            Long idTipDoc = obj.getTipoDocumentoEntity().getIdTipoDoc(); // <--- OJO AQUÍ
            System.out.println("Buscando TipoDoc ID: " + idTipDoc);

            if (idTipDoc == null)
                throw new RuntimeException("El ID del Tipo de Documento es NULL");

            TipoDocumentoEntity tipoDocReal = tipoDocRepo.findById(idTipDoc)
                    .orElseThrow(
                            () -> new RuntimeException("Error: Tipo de Documento no encontrado con ID: " + idTipDoc));
            obj.setTipoDocumentoEntity(tipoDocReal);
        }

        // 3. UBIGEO
        if (obj.getUbigeoEntity() != null) {
            Long idUbigeo = obj.getUbigeoEntity().getIdUbigeo(); // <--- OJO AQUÍ (Verifica si es String o Long en tu
                                                                 // Entity)
            System.out.println("Buscando Ubigeo ID: " + idUbigeo);

            if (idUbigeo == null)
                throw new RuntimeException("El ID del Ubigeo es NULL");

            UbigeoEntity ubigeoReal = ubigeoRepo.findById(idUbigeo)
                    .orElseThrow(() -> new RuntimeException("Error: Ubigeo no encontrado con ID: " + idUbigeo));
            obj.setUbigeoEntity(ubigeoReal);
        }

        // 4. NIVEL PROFESIONAL
        if (obj.getNivelProfesionalEntity() != null) {
            Long idNiv = obj.getNivelProfesionalEntity().getIdNivelProfesional();
            System.out.println("Buscando NivelProf ID: " + idNiv);

            // Permitimos null si no es obligatorio, pero si viene el objeto, debe tener ID
            if (idNiv != null) {
                NivelProfesionalEntity nivelReal = nivelProfRepo.findById(idNiv)
                        .orElseThrow(() -> new RuntimeException("Error: Nivel Profesional no encontrado"));
                obj.setNivelProfesionalEntity(nivelReal);
            } else {
                obj.setNivelProfesionalEntity(null);
            }
        }

        // 5. ROLES
        Set<RolEntity> rolesReales = new HashSet<>();
        if (obj.getRoles() != null) {
            for (RolEntity rol : obj.getRoles()) {
                Long idRol = rol.getIdRol();
                System.out.println("Buscando Rol ID: " + idRol);

                if (idRol == null)
                    throw new RuntimeException("Uno de los Roles tiene ID NULL");

                RolEntity rolBD = rolRepo.findById(idRol)
                        .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado (ID: " + idRol + ")"));
                rolesReales.add(rolBD);
            }
        }
        obj.setRoles(rolesReales);

        // 6. ESPECIALIDADES
        Set<EspecialidadEntity> especialidadesReales = new HashSet<>();
        if (obj.getEspecialidades() != null) {
            for (EspecialidadEntity esp : obj.getEspecialidades()) {
                Long idEsp = esp.getCodigo();
                System.out.println("Buscando Especialidad ID: " + idEsp);

                if (idEsp == null)
                    throw new RuntimeException("Una de las Especialidades tiene ID NULL");

                EspecialidadEntity espBD = especialidadRepo.findById(idEsp)
                        .orElseThrow(
                                () -> new RuntimeException("Error: Especialidad no encontrada (ID: " + idEsp + ")"));
                especialidadesReales.add(espBD);
            }
        }
        obj.setEspecialidades(especialidadesReales);

        System.out.println("--- TODO VALIDADO, GUARDANDO... ---");
        return repositorio.save(obj);
    }

    @Override
    @Transactional
    public UsuarioEntity registrarPaciente(com.gestionpacientes.dto.RegistroPacienteDto dto) {
        
        // 1. Validaciones
        if (repositorio.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }
        
        if (repositorio.findByNombreUsuario(dto.getNombreUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        UsuarioEntity usuario = new UsuarioEntity();
        
        // Datos Personales
        usuario.setNombres(dto.getNombres());
        usuario.setApellidoPaterno(dto.getApellidoPaterno());
        usuario.setApellidoMaterno(dto.getApellidoMaterno());
        usuario.setFotoUrl(dto.getFotoUrl());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setNumeroDocumento(dto.getNumeroDocumento());
        usuario.setSexo(dto.getSexo());
        usuario.setPaisOrigen(dto.getPaisOrigen());
        
        // Contacto
        usuario.setCelular(dto.getCelular());
        usuario.setContactoEmergencia(dto.getContactoEmergencia());
        usuario.setCelularContacto(dto.getCelularContacto());
        usuario.setDireccion(dto.getDireccion());
        usuario.setCorreo(dto.getCorreo());
        
        // Acceso
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setEstado(true); // Activo por defecto
        
        // Tipo Documento
        if (dto.getTipoDocumentoId() != null) {
            TipoDocumentoEntity tipoDoc = tipoDocRepo.findById(dto.getTipoDocumentoId())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));
            usuario.setTipoDocumentoEntity(tipoDoc);
        } else {
            throw new RuntimeException("El tipo de documento es obligatorio");
        }
        
        // Ubigeo
        if (dto.getIdUbigeo() != null) {
            UbigeoEntity ubigeo = ubigeoRepo.findById(dto.getIdUbigeo())
                .orElseThrow(() -> new RuntimeException("Ubigeo no encontrado"));
            usuario.setUbigeoEntity(ubigeo);
        } else {
            throw new RuntimeException("El ubigeo es obligatorio");
        }
        
        // Rol "Paciente"
        // Buscamos el rol de Paciente (asumimos que existe un método findByNombre o lo buscamos por ID, si tienes findByNombre usaremos ese, sino buscaremos todos y filtramos).
        // Como no veo un findByNombre en el plan, asumiré que podemos obtenerlo buscando.
        // Mejor usar el método que tengas en RolRepository. Asumo que es el ID 2 para Paciente o puedo iterar.
        // Lo buscaré por nombre si tienes un query, o iterando.
        RolEntity rolPaciente = rolRepo.findAll().stream()
            .filter(r -> "Paciente".equalsIgnoreCase(r.getNombre()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Rol 'Paciente' no encontrado en la base de datos"));
            
        usuario.getRoles().add(rolPaciente);
        
        return repositorio.save(usuario);
    }

    @Override
    @Transactional
    public UsuarioEntity update(Long id, UsuarioProfileDto dto) {
        // 1. Buscamos el usuario real en la BD
        UsuarioEntity usuarioDb = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Mapeo de datos personales desde el DTO
        usuarioDb.setNombres(dto.getNombres());
        usuarioDb.setApellidoPaterno(dto.getApellidoPaterno());
        usuarioDb.setApellidoMaterno(dto.getApellidoMaterno());
        usuarioDb.setFotoUrl(dto.getFotoUrl());
        usuarioDb.setFechaNacimiento(dto.getFechaNacimiento());
        usuarioDb.setNumeroDocumento(dto.getNumeroDocumento());
        usuarioDb.setSexo(dto.getSexo());
        usuarioDb.setPaisOrigen(dto.getPaisOrigen());

        // 3. Mapeo de contacto
        usuarioDb.setCorreo(dto.getCorreo());
        usuarioDb.setCelular(dto.getCelular());
        usuarioDb.setContactoEmergencia(dto.getContactoEmergencia());
        usuarioDb.setCelularContacto(dto.getCelularContacto());

        // 4. Dirección (Campo directo en Usuario)
        usuarioDb.setDireccion(dto.getDireccion());

        // 5. Ubigeo (Relación con la tabla Ubigeo usando el ID del DTO)
        if (dto.getIdUbigeo() != null) {
            UbigeoEntity ubigeoReal = ubigeoRepo.findById(dto.getIdUbigeo())
                    .orElseThrow(() -> new RuntimeException("Ubigeo no encontrado con ID: " + dto.getIdUbigeo()));
            usuarioDb.setUbigeoEntity(ubigeoReal);
        }

        if (usuarioDb.getPassword() != null && !usuarioDb.getPassword().isEmpty()) {
        }

        // OJO: No tocamos 'password' ni 'roles' aquí.
        // Al no estar en el DTO, el 'usuarioDb' mantiene sus valores originales de la
        // BD.

        return repositorio.save(usuarioDb);
    }

    @Override
    @Transactional
    public List<EspecialidadesDto> updateEspecialidades(Long idUsuario, List<EspecialidadesDto> especialidadesDtoList) {
        // [EXPLICACIÓN ACTUALIZACIÓN] 1. Buscamos el Médico (Usuario) en la BD.
        UsuarioEntity usuarioDb = repositorio.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // [EXPLICACIÓN ACTUALIZACIÓN] 2. Preparamos un nuevo Set de especialidades
        // buscando cada una por su código
        // a partir de los DTOs que llegaron desde el frontend.
        Set<EspecialidadEntity> especialidadesNuevas = new HashSet<>();
        if (especialidadesDtoList != null) {
            for (EspecialidadesDto dto : especialidadesDtoList) {
                if (dto.getCodigo() != null) {
                    EspecialidadEntity espBD = especialidadRepo.findById(dto.getCodigo())
                            .orElseThrow(() -> new RuntimeException("Error: Especialidad no encontrada"));
                    especialidadesNuevas.add(espBD);
                }
            }
        }

        // [EXPLICACIÓN ACTUALIZACIÓN] 3. Al limpiar la lista (clear) y añadir los
        // nuevos,
        // Hibernate automáticamente hace un DELETE e INSERT en la tabla intermedia
        // Medico_Especialidad
        // cuando ejecutamos el .save() debajo, asegurando consistencia pura al modelo
        // relacional.
        usuarioDb.getEspecialidades().clear();
        usuarioDb.getEspecialidades().addAll(especialidadesNuevas);
        repositorio.save(usuarioDb);

        // [EXPLICACIÓN ACTUALIZACIÓN] 4. Por buenas prácticas, convertimos nuestro Set
        // guardado
        // en una Lista de DTOs, para no exponer nuestras entidades pesadas al front y
        // respetar la arquitectura.
        return especialidadesNuevas.stream().map(e -> EspecialidadesDto.builder()
                .codigo(e.getCodigo())
                .nombre(e.getNombre())
                .estado(e.getEstado())
                .build()).toList();
    }

    @Override
    @Transactional
    public Optional<UsuarioDto> findPatientByDocumento(String documento) {
        var pacienteEncontrado = repositorio.findByNumeroDocumentoAndRoles_Nombre(documento, "Paciente");

        return pacienteEncontrado.map(paciente -> {
            boolean pacienteConHC = hcRepository.existsByPacienteEntity_IdUsuario(paciente.getIdUsuario());

            return UsuarioDto.builder()
                    .idUsuario(paciente.getIdUsuario())
                    .nombres(paciente.getNombres())
                    .apellidoPaterno(paciente.getApellidoPaterno())
                    .numeroDocumento(paciente.getNumeroDocumento())
                    .tieneHistoriaClinica(pacienteConHC)
                    .build();
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDto> findMedicosByEspecialidad(Long codigoEspecialidad) {
        List<UsuarioEntity> medicosEntity = repositorio.findByEspecialidades_CodigoAndEstadoTrue(codigoEspecialidad);
        return medicosEntity.stream().map(medico -> UsuarioDto.builder()
                .idUsuario(medico.getIdUsuario())
                .nombres(medico.getNombres())
                .apellidoPaterno(medico.getApellidoPaterno())
                .apellidoMaterno(medico.getApellidoMaterno())
                .numeroDocumento(medico.getNumeroDocumento())
                .correo(medico.getCorreo())
                .fotoUrl(medico.getFotoUrl())
                .especialidades(medico.getEspecialidades() == null ? null
                        : medico.getEspecialidades().stream()
                                .map(e -> EspecialidadesDto.builder().codigo(e.getCodigo())
                                        .nombre(e.getNombre()).build())
                                .collect(Collectors.toList()))
                .build()).toList();
    }

    @Override
    public UsuarioEntity delete(Long id) {
        UsuarioEntity objUsuario = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));
        objUsuario.setEstado(false);
        return repositorio.save(objUsuario);
    }

    @Override
    public UsuarioEntity enable(Long id) {
        UsuarioEntity objUsuario = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));
        objUsuario.setEstado(true);
        return repositorio.save(objUsuario);
    }
}
