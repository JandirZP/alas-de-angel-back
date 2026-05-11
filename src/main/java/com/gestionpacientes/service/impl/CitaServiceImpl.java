package com.gestionpacientes.service.impl;

import com.gestionpacientes.dto.CitaDto;
import com.gestionpacientes.entity.CitaEntity;
import com.gestionpacientes.entity.EspecialidadEntity;
import com.gestionpacientes.entity.UsuarioEntity;
import com.gestionpacientes.repository.CitaRepository;
import com.gestionpacientes.repository.EspecialidadRepository;
import com.gestionpacientes.repository.TriajeRepository;
import com.gestionpacientes.repository.UsuarioRepository;
import com.gestionpacientes.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository repositorio;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private TriajeRepository triajeRepository;

    @Override
    public List<CitaEntity> findAll() {

        return repositorio.findAll();
    }

    @Override
    public List<CitaEntity> findAllCustom() {
        return repositorio.findAllCustom();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaDto> buscarTodasLasCitas() {
        var citasEntidad = repositorio.findAll();
        return citasEntidad.stream().map(cita -> {
            boolean tieneTriaje = triajeRepository.existsByCita_IdCita(cita.getIdCita());

            return CitaDto.builder()
                    .idCita(cita.getIdCita())
                    .fechaHora(cita.getFechaHora())
                    .motivo(cita.getMotivo())
                    .estado(cita.getEstado())
                    .idMedico(cita.getMedico().getIdUsuario())
                    .nombreMedico(cita.getMedico().getNombres())
                    .apellidoMedico(cita.getMedico().getApellidoPaterno())
                    .especialidadMedico(cita.getEspecialidad().getNombre())
                    .sexoMedico(cita.getMedico().getSexo())
                    .idPaciente(cita.getPaciente().getIdUsuario())
                    .nombrePaciente(cita.getPaciente().getNombres())
                    .apellidoPatPaciente(cita.getPaciente().getApellidoPaterno())
                    .apellidoMatPaciente(cita.getPaciente().getApellidoMaterno())
                    .numeroDocumento(cita.getPaciente().getNumeroDocumento())
                    .sexoPaciente(cita.getPaciente().getSexo())
                    .tipoDocumento(cita.getPaciente().getTipoDocumentoEntity().getNombre())
                    .atendidoEnTriaje(tieneTriaje)
                    .build();
        }).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true) // ESTO EVITA EL ERROR ConcurrentModificationException
    public List<CitaDto> findByPacienteId(Long idPaciente) {

        var citasEntidad = repositorio.findByPaciente_IdUsuarioAndEstadoTrueOrderByFechaHoraAsc(idPaciente);

        return citasEntidad.stream().map(cita -> {
            return CitaDto.builder()
                    .idCita(cita.getIdCita())
                    .fechaHora(cita.getFechaHora())
                    .motivo(cita.getMotivo())
                    .estado(cita.getEstado())
                    .idMedico(cita.getMedico().getIdUsuario())
                    .nombreMedico(cita.getMedico().getNombres())
                    .apellidoMedico(cita.getMedico().getApellidoPaterno())

                    // AHORA ES DIRECTO Y EXACTO
                    .especialidadMedico(cita.getEspecialidad().getNombre())
                    .sexoMedico(cita.getMedico().getSexo())
                    .idPaciente(cita.getPaciente().getIdUsuario())
                    .nombrePaciente(cita.getPaciente().getNombres())
                    .apellidoPatPaciente(cita.getPaciente().getApellidoPaterno())
                    .apellidoMatPaciente(cita.getPaciente().getApellidoMaterno())
                    .numeroDocumento(cita.getPaciente().getNumeroDocumento())
                    .sexoPaciente(cita.getPaciente().getSexo())
                    .tipoDocumento(cita.getPaciente().getTipoDocumentoEntity().getNombre())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaDto> findAllCitasPacienteId(Long idPaciente) {

        var citasEntidad = repositorio.findByPaciente_IdUsuarioOrderByFechaHoraAsc(idPaciente);

        return citasEntidad.stream().map(cita -> {
            return CitaDto.builder()
                    .idCita(cita.getIdCita())
                    .fechaHora(cita.getFechaHora())
                    .motivo(cita.getMotivo())
                    .estado(cita.getEstado())
                    .idMedico(cita.getMedico().getIdUsuario())
                    .nombreMedico(cita.getMedico().getNombres())
                    .apellidoMedico(cita.getMedico().getApellidoPaterno())

                    // AHORA ES DIRECTO Y EXACTO
                    .especialidadMedico(cita.getEspecialidad().getNombre())
                    .sexoMedico(cita.getMedico().getSexo())
                    .idPaciente(cita.getPaciente().getIdUsuario())
                    .nombrePaciente(cita.getPaciente().getNombres())
                    .apellidoPatPaciente(cita.getPaciente().getApellidoPaterno())
                    .apellidoMatPaciente(cita.getPaciente().getApellidoMaterno())
                    .numeroDocumento(cita.getPaciente().getNumeroDocumento())
                    .sexoPaciente(cita.getPaciente().getSexo())
                    .tipoDocumento(cita.getPaciente().getTipoDocumentoEntity().getNombre())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaDto> findByMedicoId(Long idMedico, LocalDateTime fechaHora) {

        // Cambiamos al nuevo método del repositorio (funcionará bien en SQL Server)
        LocalDateTime fechaInicio = fechaHora.toLocalDate().atStartOfDay();
        LocalDateTime fechaFin = fechaInicio.plusDays(1);

        var citasEntidad = repositorio.findCitasPorMedicoYRangoFecha(idMedico, fechaInicio, fechaFin);

        return citasEntidad.stream().map(cita -> {
            return CitaDto.builder()
                    .idCita(cita.getIdCita())
                    .fechaHora(cita.getFechaHora())
                    .motivo(cita.getMotivo())
                    .estado(cita.getEstado())
                    .idMedico(cita.getMedico().getIdUsuario())
                    .nombreMedico(cita.getMedico().getNombres())
                    .apellidoMedico(cita.getMedico().getApellidoPaterno())

                    // AHORA ES DIRECTO Y EXACTO
                    .especialidadMedico(cita.getEspecialidad().getNombre())
                    .sexoMedico(cita.getMedico().getSexo())
                    .idPaciente(cita.getPaciente().getIdUsuario())
                    .nombrePaciente(cita.getPaciente().getNombres())
                    .apellidoPatPaciente(cita.getPaciente().getApellidoPaterno())
                    .apellidoMatPaciente(cita.getPaciente().getApellidoMaterno())
                    .numeroDocumento(cita.getPaciente().getNumeroDocumento())
                    .sexoPaciente(cita.getPaciente().getSexo())
                    .tipoDocumento(cita.getPaciente().getTipoDocumentoEntity().getNombre())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaDto> findAllActiveByMedicoId(Long idMedico) {

        var citasEntidad = repositorio.findByMedico_IdUsuarioAndEstadoTrueOrderByFechaHoraAsc(idMedico);

        return citasEntidad.stream().map(cita -> {
            boolean tieneTriaje = triajeRepository.existsByCita_IdCita(cita.getIdCita());
            return CitaDto.builder()
                    .idCita(cita.getIdCita())
                    .fechaHora(cita.getFechaHora())
                    .motivo(cita.getMotivo())
                    .estado(cita.getEstado())
                    .idMedico(cita.getMedico().getIdUsuario())
                    .nombreMedico(cita.getMedico().getNombres())
                    .apellidoMedico(cita.getMedico().getApellidoPaterno())
                    .especialidadMedico(cita.getEspecialidad().getNombre())
                    .sexoMedico(cita.getMedico().getSexo())
                    .idPaciente(cita.getPaciente().getIdUsuario())
                    .nombrePaciente(cita.getPaciente().getNombres())
                    .apellidoPatPaciente(cita.getPaciente().getApellidoPaterno())
                    .apellidoMatPaciente(cita.getPaciente().getApellidoMaterno())
                    .numeroDocumento(cita.getPaciente().getNumeroDocumento())
                    .sexoPaciente(cita.getPaciente().getSexo())
                    .tipoDocumento(cita.getPaciente().getTipoDocumentoEntity().getNombre())
                    .atendidoEnTriaje(tieneTriaje)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CitaDto> buscarCitasByDocumento(String numeroDocumento) {
        var CitasBd = repositorio.findCitasByDoc(numeroDocumento);

        return CitasBd.stream().map(c -> {
            return CitaDto.builder()
                    .idCita(c.getIdCita())
                    .fechaHora(c.getFechaHora())
                    .motivo(c.getMotivo())
                    .estado(c.getEstado())
                    .idMedico(c.getMedico().getIdUsuario())
                    .nombreMedico(c.getMedico().getNombres())
                    .apellidoMedico(c.getMedico().getApellidoPaterno())
                    .especialidadMedico(c.getEspecialidad().getNombre())
                    .sexoMedico(c.getMedico().getSexo())
                    .idPaciente(c.getPaciente().getIdUsuario())
                    .nombrePaciente(c.getPaciente().getNombres())
                    .apellidoPatPaciente(c.getPaciente().getApellidoPaterno())
                    .apellidoMatPaciente(c.getPaciente().getApellidoMaterno())
                    .numeroDocumento(c.getPaciente().getNumeroDocumento())
                    .sexoPaciente(c.getPaciente().getSexo())
                    .tipoDocumento(c.getPaciente().getTipoDocumentoEntity().getNombre())
                    .build();

        }).collect(Collectors.toList());
    }

    // Buscar Citas por fecha
    @Override
    @Transactional
    public List<CitaDto> buscarCitasPorFecha(Date fechaInicio, Date fechaFin) {
        var citasBd = repositorio.findCitasByFechaHoraBetween(fechaInicio, fechaFin);

        return citasBd.stream().map(c -> {
            return CitaDto.builder()
                    .idCita(c.getIdCita())
                    .fechaHora(c.getFechaHora())
                    .motivo(c.getMotivo())
                    .estado(c.getEstado())
                    .idMedico(c.getMedico().getIdUsuario())
                    .nombreMedico(c.getMedico().getNombres())
                    .apellidoMedico(c.getMedico().getApellidoPaterno())
                    .especialidadMedico(c.getEspecialidad().getNombre())
                    .sexoMedico(c.getMedico().getSexo())
                    .idPaciente(c.getPaciente().getIdUsuario())
                    .nombrePaciente(c.getPaciente().getNombres())
                    .apellidoPatPaciente(c.getPaciente().getApellidoPaterno())
                    .apellidoMatPaciente(c.getPaciente().getApellidoMaterno())
                    .numeroDocumento(c.getPaciente().getNumeroDocumento())
                    .sexoPaciente(c.getPaciente().getSexo())
                    .tipoDocumento(c.getPaciente().getTipoDocumentoEntity().getNombre())
                    .build();

        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CitaDto add(CitaEntity obj) {

        // 1. VALIDAR Y CARGAR PACIENTE REAL
        // Obtenemos el ID que viene del JSON
        Long idPaciente = obj.getPaciente().getIdUsuario();
        UsuarioEntity pacienteReal = usuarioRepository.findById(idPaciente)
                .orElseThrow(() -> new RuntimeException("Error: Paciente no encontrado con ID " + idPaciente));
        obj.setPaciente(pacienteReal); // Reemplazamos el objeto "falso" del JSON por el real de la BD

        // 2. VALIDAR Y CARGAR MÉDICO REAL
        Long idMedico = obj.getMedico().getIdUsuario();
        UsuarioEntity medicoReal = usuarioRepository.findById(idMedico)
                .orElseThrow(() -> new RuntimeException("Error: Médico no encontrado con ID " + idMedico));
        obj.setMedico(medicoReal);

        // 3. VALIDAR Y CARGAR ESPECIALIDAD REAL (Aquí estaba fallando)
        Long idEspecialidad = obj.getEspecialidad().getCodigo();
        EspecialidadEntity especialidadReal = especialidadRepository.findById(idEspecialidad)
                .orElseThrow(() -> new RuntimeException("Error: Especialidad no encontrada con ID " + idEspecialidad));
        obj.setEspecialidad(especialidadReal); // ¡Esto soluciona el TransientPropertyValueException!

        // 4. GUARDAR LA CITA
        // Ahora Hibernate sabe que todos los objetos relacionados YA existen en la BD.
        obj.setEstado(true); // Aseguramos que se guarde activa
        // 5. GUARDAMOS LA ENTIDAD

        boolean horaOcupada = repositorio
                .existsByMedico_IdUsuarioAndFechaHoraAndEstadoTrue(obj.getMedico().getIdUsuario(), obj.getFechaHora());
        if (horaOcupada) {
            throw new RuntimeException("El turno seleccionado ya no se encuentra disponible.");
        }

        CitaEntity citaGuardada = repositorio.save(obj);

        // 6. CONVERTIMOS A DTO (Para responder seguro)
        return CitaDto.builder()
                .idCita(citaGuardada.getIdCita())
                .fechaHora(citaGuardada.getFechaHora())
                .motivo(citaGuardada.getMotivo())
                .estado(citaGuardada.getEstado())
                // Médico
                .idMedico(citaGuardada.getMedico().getIdUsuario())
                .nombreMedico(citaGuardada.getMedico().getNombres())
                .apellidoMedico(citaGuardada.getMedico().getApellidoPaterno())
                .especialidadMedico(citaGuardada.getEspecialidad().getNombre()) // Directo de la especialidad
                // Paciente
                .idPaciente(citaGuardada.getPaciente().getIdUsuario())
                .nombrePaciente(citaGuardada.getPaciente().getNombres())
                .build();
    }

    @Override
    @Transactional
    public Optional<CitaDto> buscarPorCodigo(Long idCodigo) {
        var citaEncontrada = repositorio.findById(idCodigo);

        return citaEncontrada.map(c -> {
            return CitaDto.builder()
                    .idCita(c.getIdCita())
                    .fechaHora(c.getFechaHora())
                    .motivo(c.getMotivo())
                    .estado(c.getEstado())
                    .idMedico(c.getMedico().getIdUsuario())
                    .nombreMedico(c.getMedico().getNombres())
                    .apellidoMedico(c.getMedico().getApellidoPaterno())
                    .especialidadMedico(c.getEspecialidad().getNombre())
                    .sexoMedico(c.getMedico().getSexo())
                    .idPaciente(c.getPaciente().getIdUsuario())
                    .nombrePaciente(c.getPaciente().getNombres())
                    .apellidoPatPaciente(c.getPaciente().getApellidoPaterno())
                    .apellidoMatPaciente(c.getPaciente().getApellidoMaterno())
                    .numeroDocumento(c.getPaciente().getNumeroDocumento())
                    .sexoPaciente(c.getPaciente().getSexo())
                    .tipoDocumento(c.getPaciente().getTipoDocumentoEntity().getNombre())
                    .build();
        });

    }

    @Override
    @Transactional
    public CitaDto update(CitaEntity obj, Long id) {
        CitaEntity objUpdate = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("La cita no existe"));

        // 1. CARGAMOS EL MÉDICO REAL (el front nos manda solo el ID dentro del objeto
        // medico)
        if (obj.getMedico() != null && obj.getMedico().getIdUsuario() != null) {
            UsuarioEntity medicoReal = usuarioRepository.findById(obj.getMedico().getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Error: Médico no encontrado"));
            objUpdate.setMedico(medicoReal);
        }

        // 2. CARGAMOS LA ESPECIALIDAD REAL
        if (obj.getEspecialidad() != null && obj.getEspecialidad().getCodigo() != null) {
            EspecialidadEntity especialidadReal = especialidadRepository.findById(obj.getEspecialidad().getCodigo())
                    .orElseThrow(() -> new RuntimeException("Error: Especialidad no encontrada"));
            objUpdate.setEspecialidad(especialidadReal);
        }

        // Actualizamos los campos directos que se pueden editar
        objUpdate.setFechaHora(obj.getFechaHora());
        objUpdate.setMotivo(obj.getMotivo());

        // Verificamos disponibilidad del médico
        boolean horaOcupada = repositorio
                .existsByMedico_IdUsuarioAndFechaHoraAndEstadoTrue(objUpdate.getMedico().getIdUsuario(),
                        objUpdate.getFechaHora());

        // Excluimos la cita que estamos editando de la validación
        if (horaOcupada && !objUpdate.getFechaHora().equals(obj.getFechaHora())) {
            throw new RuntimeException("El turno seleccionado ya no se encuentra disponible.");
        }

        CitaEntity citaGuardada = repositorio.save(objUpdate);

        // Convertir a DTO para devolver
        return CitaDto.builder()
                .idCita(citaGuardada.getIdCita())
                .fechaHora(citaGuardada.getFechaHora())
                .motivo(citaGuardada.getMotivo())
                .estado(citaGuardada.getEstado())
                // Médico
                .idMedico(citaGuardada.getMedico().getIdUsuario())
                .nombreMedico(citaGuardada.getMedico().getNombres())
                .apellidoMedico(citaGuardada.getMedico().getApellidoPaterno())
                .especialidadMedico(
                        citaGuardada.getEspecialidad() != null ? citaGuardada.getEspecialidad().getNombre() : null)
                // Paciente
                .idPaciente(citaGuardada.getPaciente().getIdUsuario())
                .nombrePaciente(citaGuardada.getPaciente().getNombres())
                .build();
    }

    @Override
    public String delete(Long id) {
        CitaEntity objCita = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("La cita no existe"));
        objCita.setEstado(false);
        repositorio.save(objCita);
        return "Cita eliminada correctamente.";
    }

    @Override
    public String enable(Long id) {
        CitaEntity objCita = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("La cita no existe"));
        objCita.setEstado(true);
        repositorio.save(objCita);
        return "Cita habilitada correctamente.";
    }
}
