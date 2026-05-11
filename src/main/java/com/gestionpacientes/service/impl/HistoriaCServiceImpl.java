package com.gestionpacientes.service.impl;

import com.gestionpacientes.dto.AlergiasDto;
import com.gestionpacientes.dto.AntecedentesPatologicosDto;
import com.gestionpacientes.dto.AntecedentesQuirurgicosDto;
import com.gestionpacientes.dto.EventosMedicosDto;
import com.gestionpacientes.dto.HistoriaClinicaDto;
import com.gestionpacientes.dto.HistorialDrogasDto;
import com.gestionpacientes.dto.TratamientoDto;
import com.gestionpacientes.entity.EventosMedicosEntity;
import com.gestionpacientes.entity.HistoriaClinicaEntity;
import com.gestionpacientes.entity.UsuarioEntity;
import com.gestionpacientes.repository.HistoriaClinicaRepository;
import com.gestionpacientes.repository.UsuarioRepository;
import com.gestionpacientes.service.HistoriaClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class HistoriaCServiceImpl implements HistoriaClinicaService {

        @Autowired
        private HistoriaClinicaRepository repositorio;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Override
        public List<HistoriaClinicaEntity> findAll() {
                return repositorio.findAll();

        }

        @Override
        @Transactional
        public List<HistoriaClinicaDto> findActivas() {

                var activas = repositorio.findAllCustom();

                return activas.stream().map(hc -> {
                        return HistoriaClinicaDto.builder()
                                        .idHC(hc.getCodigo())
                                        .idPaciente(hc.getPacienteEntity().getIdUsuario())
                                        .nombresPaciente(hc.getPacienteEntity().getNombres())
                                        .apellidoPaternoPaciente(hc.getPacienteEntity().getApellidoPaterno())
                                        .apellidoMaternoPaciente(hc.getPacienteEntity().getApellidoMaterno())
                                        .numeroDocumento(hc.getPacienteEntity().getNumeroDocumento())
                                        .tipoDocumento(hc.getPacienteEntity().getTipoDocumentoEntity().getNombre())
                                        .grupoSanquineo(hc.getGrupoSanguineo())
                                        .factorRH(hc.getFactorRH())
                                        .antecedentesFamiliares(hc.getAntecedentesFamiliares())
                                        .especifiqueAnteFamil(hc.getEspecifiqueAnteFamil())
                                        .estadoAlcohol(hc.getEstadoAlcohol())
                                        .frecuenciaAlcohol(hc.getFrecuenciaAlcohol())
                                        .estadoTabaco(hc.getEstadoTabaco())
                                        .frecuenciaTabaco(hc.getFrecuenciaTabaco())
                                        .consumeDrogas(hc.getConsumeDrogas())
                                        .sexualmenteActivo(hc.getSexualmenteActivo())
                                        .edadInicioSexual(hc.getEdadInicioSexual())
                                        .usaMetodoAnticonceptivo(hc.getUsaMetodoAnticonceptivo())
                                        .metodoPlanificacion(hc.getMetodoPlanificacion())
                                        .tuvoEmbarazos(hc.getTuvoEmbarazos())
                                        .cantidadGestaciones(hc.getCantidadGestaciones())
                                        .cantidadPartos(hc.getCantidadPartos())
                                        .cantidadAbortos(hc.getCantidadAbortos())
                                        .huboComplicaciones(hc.getHuboComplicacionesParto())
                                        .especifiqueComplicaciones(hc.getEspecifiqueComplicaciones())
                                        .estadoHC(hc.getEstado())
                                        .build();
                }).collect(Collectors.toList());

        }

        @Override
        public HistoriaClinicaEntity findById(Long id) {
                return repositorio.findById(id)
                                .orElseThrow(() -> new RuntimeException("La historia clinica no existe"));
        }

        @Override
        @Transactional(readOnly = true)
        public Optional<HistoriaClinicaDto> findDtoCompletoById(Long id) {
                return repositorio.findById(id).map(hc -> HistoriaClinicaDto.builder()
                                .idHC(hc.getCodigo())
                                .idPaciente(hc.getPacienteEntity().getIdUsuario())
                                .nombresPaciente(hc.getPacienteEntity().getNombres())
                                .apellidoPaternoPaciente(hc.getPacienteEntity().getApellidoPaterno())
                                .apellidoMaternoPaciente(hc.getPacienteEntity().getApellidoMaterno())
                                .numeroDocumento(hc.getPacienteEntity().getNumeroDocumento())
                                .tipoDocumento(hc.getPacienteEntity().getTipoDocumentoEntity().getNombre())
                                .sexoPaciente(hc.getPacienteEntity().getSexo())
                                .fechaNacimiento(hc.getPacienteEntity().getFechaNacimiento().toString())
                                .grupoSanquineo(hc.getGrupoSanguineo())
                                .factorRH(hc.getFactorRH())
                                .antecedentesFamiliares(hc.getAntecedentesFamiliares())
                                .especifiqueAnteFamil(hc.getEspecifiqueAnteFamil())
                                .estadoAlcohol(hc.getEstadoAlcohol())
                                .frecuenciaAlcohol(hc.getFrecuenciaAlcohol())
                                .estadoTabaco(hc.getEstadoTabaco())
                                .frecuenciaTabaco(hc.getFrecuenciaTabaco())
                                .consumeDrogas(hc.getConsumeDrogas())
                                .sexualmenteActivo(hc.getSexualmenteActivo())
                                .edadInicioSexual(hc.getEdadInicioSexual())
                                .usaMetodoAnticonceptivo(hc.getUsaMetodoAnticonceptivo())
                                .metodoPlanificacion(hc.getMetodoPlanificacion())
                                .tuvoEmbarazos(hc.getTuvoEmbarazos())
                                .cantidadGestaciones(hc.getCantidadGestaciones())
                                .cantidadPartos(hc.getCantidadPartos())
                                .cantidadAbortos(hc.getCantidadAbortos())
                                .huboComplicaciones(hc.getHuboComplicacionesParto())
                                .especifiqueComplicaciones(hc.getEspecifiqueComplicaciones())
                                .fechaCreacion(hc.getFechaCreacion())
                                .estadoHC(hc.getEstado())
                                .alergias(hc.getAlergias() == null ? null
                                                : hc.getAlergias().stream()
                                                                .map(a -> AlergiasDto.builder()
                                                                                .id(a.getIdAlergia())
                                                                                .historiaClinicaId(hc.getCodigo())
                                                                                .alergeno(a.getAlergeno())
                                                                                .reaccion(a.getReaccion())
                                                                                .observaciones(a.getObservaciones())
                                                                                .build())
                                                                .collect(Collectors.toList()))
                                .antecedentesPatologicos(hc.getEnfermedadeCronicas() == null ? null
                                                : hc.getEnfermedadeCronicas().stream()
                                                                .map(p -> AntecedentesPatologicosDto.builder()
                                                                                .id(p.getIdPatologia())
                                                                                .historiaClinicaId(hc.getCodigo())
                                                                                .nombre(p.getNombreEnfermedad())
                                                                                .fechaDiagnostico(
                                                                                                p.getFechaDiagnostico())
                                                                                .enTratamiento(p.getEstaEnTratamiento())
                                                                                .observaciones(p.getObservaciones())
                                                                                .build())
                                                                .collect(Collectors.toList()))
                                .antecedentesQuirurgicos(hc.getCirugiasPrevias() == null ? null
                                                : hc.getCirugiasPrevias().stream()
                                                                .map(q -> AntecedentesQuirurgicosDto.builder()
                                                                                .id(q.getIdOperacion())
                                                                                .historiaClinicaId(hc.getCodigo())
                                                                                .nombre(q.getNombreOperacion())
                                                                                .fecha(q.getFechaOperacion())
                                                                                .huboComplicaciones(q
                                                                                                .getHuboComplicaciones())
                                                                                .observaciones(q.getObservaciones())
                                                                                .build())
                                                                .collect(Collectors.toList()))
                                .drogas(hc.getDrogas() == null ? null
                                                : hc.getDrogas().stream()
                                                                .map(d -> HistorialDrogasDto.builder()
                                                                                .id(d.getIdDrogas())
                                                                                .historiaClinicaId(hc.getCodigo())
                                                                                .nombreDroga(d.getNombreDroga())
                                                                                .frecuencia(d.getFrecuencia())
                                                                                .observaciones(d.getObservaciones())
                                                                                .build())
                                                                .collect(Collectors.toList()))
                                .eventosMedicos(hc.getEventosMedicos() == null ? null
                                                : hc.getEventosMedicos().stream()
                                                                .sorted(Comparator.comparing(
                                                                                EventosMedicosEntity::getFechaHora)
                                                                                .reversed())
                                                                .map(e -> EventosMedicosDto.builder()
                                                                                .idEventoMedico(e.getIdEventoMedico())
                                                                                .tipoEvento(e.getTipoEvento())
                                                                                .descripcion(e.getDescripcion())
                                                                                .diagnostico(e.getDiagnostico())
                                                                                .medicamentos(e.getMedicamentos())
                                                                                .dieta(e.getDieta())
                                                                                .recomendaciones(e.getRecomendaciones())
                                                                                .fechaHora(e.getFechaHora())
                                                                                .idTriaje(e.getTriaje() != null ? e
                                                                                                .getTriaje()
                                                                                                .getIdTriaje() : null)
                                                                                .nombreDoctor(e.getTriaje() != null && e
                                                                                                .getTriaje()
                                                                                                .getCita() != null
                                                                                                && e.getTriaje().getCita()
                                                                                                                .getMedico() != null
                                                                                                                                ? e.getTriaje().getCita()
                                                                                                                                                .getMedico()
                                                                                                                                                .getNombres()
                                                                                                                                : null)
                                                                                .apellidoDoctor(e.getTriaje() != null
                                                                                                && e.getTriaje().getCita() != null
                                                                                                && e.getTriaje().getCita()
                                                                                                                .getMedico() != null
                                                                                                                                ? e.getTriaje().getCita()
                                                                                                                                                .getMedico()
                                                                                                                                                .getApellidoPaterno()
                                                                                                                                : null)
                                                                                .build())
                                                                .collect(Collectors.toList()))
                                .build());
        }

        @Override
        @Transactional(readOnly = true) // Importante para leer la lista Lazy de eventos
        public Optional<TratamientoDto> obtenerUltimoTratamiento(Long idPaciente) {

                // 1. Buscar la historia del paciente
                Optional<HistoriaClinicaEntity> historiaOpt = repositorio.findByPacienteEntity_IdUsuario(idPaciente);

                if (historiaOpt.isPresent()) {
                        HistoriaClinicaEntity historia = historiaOpt.get();

                        // 2. Buscar el evento más reciente de la lista
                        // Convertimos el Set a Stream -> Ordenamos por Fecha DESC -> Tomamos el primero
                        return historia.getEventosMedicos().stream()
                                        .sorted(Comparator.comparing(EventosMedicosEntity::getFechaHora).reversed())
                                        .findFirst()
                                        .map(evento -> TratamientoDto.builder()
                                                        .idEventoMedico(evento.getIdEventoMedico())
                                                        .medicamentos(evento.getMedicamentos())
                                                        .dieta(evento.getDieta())
                                                        .recomendaciones(evento.getRecomendaciones())
                                                        .fecha(evento.getFechaHora())
                                                        // OJO: Como no tenemos el médico directo en EventoMedico (está
                                                        // en Triaje),
                                                        // pondremos un texto genérico por ahora o "Ver detalle".
                                                        .nombreMedico(evento.getTriaje().getCita().getMedico().nombres)
                                                        .apellidoMedico(evento.getTriaje().getCita()
                                                                        .getMedico().apellidoPaterno)

                                                        .build());
                }

                return Optional.empty();
        }

        @Override
        @Transactional(readOnly = true)
        public Optional<HistoriaClinicaDto> buscarPorDocumento(String numeroDocumento) {
                return repositorio.findByPacienteEntity_NumeroDocumento(numeroDocumento)
                                .map(hc -> HistoriaClinicaDto.builder()
                                                .idHC(hc.getCodigo())
                                                .idPaciente(hc.getPacienteEntity().getIdUsuario())
                                                .nombresPaciente(hc.getPacienteEntity().getNombres())
                                                .apellidoPaternoPaciente(hc.getPacienteEntity().getApellidoPaterno())
                                                .apellidoMaternoPaciente(hc.getPacienteEntity().getApellidoMaterno())
                                                .numeroDocumento(hc.getPacienteEntity().getNumeroDocumento())
                                                .tipoDocumento(hc.getPacienteEntity().getTipoDocumentoEntity()
                                                                .getNombre())
                                                .grupoSanquineo(hc.getGrupoSanguineo())
                                                .factorRH(hc.getFactorRH())
                                                .antecedentesFamiliares(hc.getAntecedentesFamiliares())
                                                .especifiqueAnteFamil(hc.getEspecifiqueAnteFamil())
                                                .estadoAlcohol(hc.getEstadoAlcohol())
                                                .frecuenciaAlcohol(hc.getFrecuenciaAlcohol())
                                                .estadoTabaco(hc.getEstadoTabaco())
                                                .frecuenciaTabaco(hc.getFrecuenciaTabaco())
                                                .consumeDrogas(hc.getConsumeDrogas())
                                                .sexualmenteActivo(hc.getSexualmenteActivo())
                                                .edadInicioSexual(hc.getEdadInicioSexual())
                                                .usaMetodoAnticonceptivo(hc.getUsaMetodoAnticonceptivo())
                                                .metodoPlanificacion(hc.getMetodoPlanificacion())
                                                .tuvoEmbarazos(hc.getTuvoEmbarazos())
                                                .cantidadGestaciones(hc.getCantidadGestaciones())
                                                .cantidadPartos(hc.getCantidadPartos())
                                                .cantidadAbortos(hc.getCantidadAbortos())
                                                .huboComplicaciones(hc.getHuboComplicacionesParto())
                                                .especifiqueComplicaciones(hc.getEspecifiqueComplicaciones())
                                                .estadoHC(hc.getEstado())
                                                .build());
        }

        @Override
        @Transactional(readOnly = true)
        public Optional<HistoriaClinicaDto> buscarPorIdPaciente(Long idPaciente) {
                return repositorio.findByPacienteEntity_IdUsuario(idPaciente)
                                .map(hc -> HistoriaClinicaDto.builder()
                                                .idHC(hc.getCodigo())
                                                .idPaciente(hc.getPacienteEntity().getIdUsuario())
                                                .nombresPaciente(hc.getPacienteEntity().getNombres())
                                                .apellidoPaternoPaciente(hc.getPacienteEntity().getApellidoPaterno())
                                                .apellidoMaternoPaciente(hc.getPacienteEntity().getApellidoMaterno())
                                                .numeroDocumento(hc.getPacienteEntity().getNumeroDocumento())
                                                .tipoDocumento(hc.getPacienteEntity().getTipoDocumentoEntity()
                                                                .getNombre())
                                                .sexoPaciente(hc.getPacienteEntity().getSexo())
                                                .fechaNacimiento(hc.getPacienteEntity().getFechaNacimiento().toString())
                                                .grupoSanquineo(hc.getGrupoSanguineo())
                                                .factorRH(hc.getFactorRH())
                                                .antecedentesFamiliares(hc.getAntecedentesFamiliares())
                                                .especifiqueAnteFamil(hc.getEspecifiqueAnteFamil())
                                                .estadoAlcohol(hc.getEstadoAlcohol())
                                                .frecuenciaAlcohol(hc.getFrecuenciaAlcohol())
                                                .estadoTabaco(hc.getEstadoTabaco())
                                                .frecuenciaTabaco(hc.getFrecuenciaTabaco())
                                                .consumeDrogas(hc.getConsumeDrogas())
                                                .sexualmenteActivo(hc.getSexualmenteActivo())
                                                .edadInicioSexual(hc.getEdadInicioSexual())
                                                .usaMetodoAnticonceptivo(hc.getUsaMetodoAnticonceptivo())
                                                .metodoPlanificacion(hc.getMetodoPlanificacion())
                                                .tuvoEmbarazos(hc.getTuvoEmbarazos())
                                                .cantidadGestaciones(hc.getCantidadGestaciones())
                                                .cantidadPartos(hc.getCantidadPartos())
                                                .cantidadAbortos(hc.getCantidadAbortos())
                                                .huboComplicaciones(hc.getHuboComplicacionesParto())
                                                .especifiqueComplicaciones(hc.getEspecifiqueComplicaciones())
                                                .fechaCreacion(hc.getFechaCreacion())
                                                .estadoHC(hc.getEstado())
                                                .alergias(hc.getAlergias() == null ? null
                                                                : hc.getAlergias().stream()
                                                                                .map(a -> com.gestionpacientes.dto.AlergiasDto
                                                                                                .builder()
                                                                                                .id(a.getIdAlergia())
                                                                                                .historiaClinicaId(hc
                                                                                                                .getCodigo())
                                                                                                .alergeno(a.getAlergeno())
                                                                                                .reaccion(a.getReaccion())
                                                                                                .observaciones(a.getObservaciones())
                                                                                                .build())
                                                                                .collect(Collectors.toList()))
                                                .antecedentesPatologicos(hc.getEnfermedadeCronicas() == null ? null
                                                                : hc.getEnfermedadeCronicas().stream()
                                                                                .map(p -> com.gestionpacientes.dto.AntecedentesPatologicosDto
                                                                                                .builder()
                                                                                                .id(p.getIdPatologia())
                                                                                                .historiaClinicaId(hc
                                                                                                                .getCodigo())
                                                                                                .nombre(p.getNombreEnfermedad())
                                                                                                .fechaDiagnostico(p
                                                                                                                .getFechaDiagnostico())
                                                                                                .enTratamiento(p.getEstaEnTratamiento())
                                                                                                .observaciones(p.getObservaciones())
                                                                                                .build())
                                                                                .collect(Collectors.toList()))
                                                .antecedentesQuirurgicos(hc.getCirugiasPrevias() == null ? null
                                                                : hc.getCirugiasPrevias().stream()
                                                                                .map(q -> com.gestionpacientes.dto.AntecedentesQuirurgicosDto
                                                                                                .builder()
                                                                                                .id(q.getIdOperacion())
                                                                                                .historiaClinicaId(hc
                                                                                                                .getCodigo())
                                                                                                .nombre(q.getNombreOperacion())
                                                                                                .fecha(q.getFechaOperacion())
                                                                                                .huboComplicaciones(q
                                                                                                                .getHuboComplicaciones())
                                                                                                .observaciones(q.getObservaciones())
                                                                                                .build())
                                                                                .collect(Collectors.toList()))
                                                .drogas(hc.getDrogas() == null ? null
                                                                : hc.getDrogas().stream()
                                                                                .map(d -> com.gestionpacientes.dto.HistorialDrogasDto
                                                                                                .builder()
                                                                                                .id(d.getIdDrogas())
                                                                                                .historiaClinicaId(hc
                                                                                                                .getCodigo())
                                                                                                .nombreDroga(d.getNombreDroga())
                                                                                                .frecuencia(d.getFrecuencia())
                                                                                                .observaciones(d.getObservaciones())
                                                                                                .build())
                                                                                .collect(Collectors.toList()))
                                                .eventosMedicos(hc.getEventosMedicos() == null ? null
                                                                : hc.getEventosMedicos().stream()
                                                                                .sorted(Comparator.comparing(
                                                                                                EventosMedicosEntity::getFechaHora)
                                                                                                .reversed())
                                                                                .map(e -> com.gestionpacientes.dto.EventosMedicosDto
                                                                                                .builder()
                                                                                                .idEventoMedico(e
                                                                                                                .getIdEventoMedico())
                                                                                                .tipoEvento(e.getTipoEvento())
                                                                                                .descripcion(e.getDescripcion())
                                                                                                .diagnostico(e.getDiagnostico())
                                                                                                .medicamentos(e.getMedicamentos())
                                                                                                .dieta(e.getDieta())
                                                                                                .recomendaciones(e
                                                                                                                .getRecomendaciones())
                                                                                                .fechaHora(e.getFechaHora())
                                                                                                .idTriaje(e.getTriaje() != null
                                                                                                                ? e.getTriaje().getIdTriaje()
                                                                                                                : null)
                                                                                                .peso(e.getTriaje() != null
                                                                                                                ? e.getTriaje().getPeso()
                                                                                                                : null)
                                                                                                .altura(e.getTriaje() != null
                                                                                                                ? e.getTriaje().getAltura()
                                                                                                                : null)
                                                                                                .presionArterial(e
                                                                                                                .getTriaje() != null
                                                                                                                                ? e.getTriaje().getPresionArterial()
                                                                                                                                : null)
                                                                                                .temperatura(e.getTriaje() != null
                                                                                                                ? e.getTriaje().getTemperatura()
                                                                                                                : null)
                                                                                                .tieneFiebre(e.getTriaje() != null
                                                                                                                ? e.getTriaje().getTieneFiebre()
                                                                                                                : null)
                                                                                                .fechaUltimaRegla(e
                                                                                                                .getTriaje() != null
                                                                                                                                ? e.getTriaje().getFechaUltimaRegla()
                                                                                                                                : null)
                                                                                                .estaEmbarazada(e
                                                                                                                .getTriaje() != null
                                                                                                                                ? e.getTriaje().getEstaEmbarazada()
                                                                                                                                : null)
                                                                                                .semanasGestacion(e
                                                                                                                .getTriaje() != null
                                                                                                                                ? e.getTriaje().getSemanasGestacion()
                                                                                                                                : null)
                                                                                                .nombreDoctor(e.getTriaje() != null
                                                                                                                && e.getTriaje().getCita() != null
                                                                                                                && e.getTriaje().getCita()
                                                                                                                                .getMedico() != null
                                                                                                                                                ? e.getTriaje().getCita()
                                                                                                                                                                .getMedico()
                                                                                                                                                                .getNombres()
                                                                                                                                                : null)
                                                                                                .apellidoDoctor(e
                                                                                                                .getTriaje() != null
                                                                                                                && e.getTriaje().getCita() != null
                                                                                                                && e.getTriaje().getCita()
                                                                                                                                .getMedico() != null
                                                                                                                                                ? e.getTriaje().getCita()
                                                                                                                                                                .getMedico()
                                                                                                                                                                .getApellidoPaterno()
                                                                                                                                                : null)
                                                                                                .build())
                                                                                .collect(Collectors.toList()))
                                                .build());
        }

        @Override
        @Transactional
        public HistoriaClinicaDto add(HistoriaClinicaEntity obj) {

                Long idPaciente = obj.getPacienteEntity().getIdUsuario();
                UsuarioEntity pacienteReal = usuarioRepository.findById(idPaciente)
                                .orElseThrow(() -> new RuntimeException(
                                                "Error: Paciente no encontrado con ID " + idPaciente));
                obj.setPacienteEntity(pacienteReal); // Reemplazamos el objeto "falso" del JSON por el real de la BD

                obj.setEstado(true);
                obj.setFechaCreacion(LocalDateTime.now()); // Seteamos la fecha de creación actual

                if (obj.getAlergias() != null) {
                        obj.getAlergias().forEach(a -> {
                                if (a.getIdAlergia() != null && a.getIdAlergia() < 0)
                                        a.setIdAlergia(null);
                                a.setHistoriaClinica(obj);
                        });
                }
                if (obj.getEnfermedadeCronicas() != null) {
                        obj.getEnfermedadeCronicas().forEach(p -> {
                                if (p.getIdPatologia() != null && p.getIdPatologia() < 0)
                                        p.setIdPatologia(null);
                                p.setHistoriaClinicaEntity(obj);
                        });
                }
                if (obj.getCirugiasPrevias() != null) {
                        obj.getCirugiasPrevias().forEach(c -> {
                                if (c.getIdOperacion() != null && c.getIdOperacion() < 0)
                                        c.setIdOperacion(null);
                                c.setHistoriaClinica(obj);
                        });
                }
                if (obj.getDrogas() != null) {
                        obj.getDrogas().forEach(d -> {
                                if (d.getIdDrogas() != null && d.getIdDrogas() < 0)
                                        d.setIdDrogas(null);
                                d.setHistoriaClinica(obj);
                        });
                }

                HistoriaClinicaEntity objHC = repositorio.save(obj);

                return HistoriaClinicaDto.builder()
                                .idHC(objHC.getCodigo())
                                .idPaciente(objHC.getPacienteEntity().getIdUsuario())
                                .grupoSanquineo(objHC.getGrupoSanguineo())
                                .factorRH(objHC.getFactorRH())
                                .antecedentesFamiliares(objHC.getAntecedentesFamiliares())
                                .especifiqueAnteFamil(objHC.getEspecifiqueAnteFamil())
                                .estadoAlcohol(objHC.getEstadoAlcohol())
                                .frecuenciaAlcohol(objHC.getFrecuenciaAlcohol())
                                .estadoTabaco(objHC.getEstadoTabaco())
                                .frecuenciaTabaco(objHC.getFrecuenciaTabaco())
                                .consumeDrogas(objHC.getConsumeDrogas())
                                .sexualmenteActivo(objHC.getSexualmenteActivo())
                                .edadInicioSexual(objHC.getEdadInicioSexual())
                                .usaMetodoAnticonceptivo(objHC.getUsaMetodoAnticonceptivo())
                                .metodoPlanificacion(objHC.getMetodoPlanificacion())
                                .tuvoEmbarazos(objHC.getTuvoEmbarazos())
                                .cantidadGestaciones(objHC.getCantidadGestaciones())
                                .cantidadPartos(objHC.getCantidadPartos())
                                .cantidadAbortos(objHC.getCantidadAbortos())
                                .huboComplicaciones(objHC.getHuboComplicacionesParto())
                                .especifiqueComplicaciones(objHC.getEspecifiqueComplicaciones())
                                .estadoHC(objHC.getEstado())
                                .build();

        }

        @Override
        @Transactional
        public HistoriaClinicaDto update(HistoriaClinicaEntity obj, Long id) {
                HistoriaClinicaEntity objHC = repositorio.findById(id)
                                .orElseThrow(() -> new RuntimeException("La historia clinica no existe"));

                // Actualizamos los campos directos de la historia clínica
                // Omitimos pacienteEntity, estado, fechaCreacion
                // También omitimos las listas (alergias, patologías, cirugías, drogas)
                // para manejarlas en funciones y endpoints separados.
                objHC.setGrupoSanguineo(obj.getGrupoSanguineo());
                objHC.setFactorRH(obj.getFactorRH());
                objHC.setAntecedentesFamiliares(obj.getAntecedentesFamiliares());
                objHC.setEspecifiqueAnteFamil(obj.getEspecifiqueAnteFamil());
                objHC.setEstadoAlcohol(obj.getEstadoAlcohol());
                objHC.setFrecuenciaAlcohol(obj.getFrecuenciaAlcohol());
                objHC.setEstadoTabaco(obj.getEstadoTabaco());
                objHC.setFrecuenciaTabaco(obj.getFrecuenciaTabaco());
                objHC.setConsumeDrogas(obj.getConsumeDrogas());
                objHC.setSexualmenteActivo(obj.getSexualmenteActivo());
                objHC.setEdadInicioSexual(obj.getEdadInicioSexual());
                objHC.setUsaMetodoAnticonceptivo(obj.getUsaMetodoAnticonceptivo());
                objHC.setMetodoPlanificacion(obj.getMetodoPlanificacion());
                objHC.setTuvoEmbarazos(obj.getTuvoEmbarazos());
                objHC.setCantidadGestaciones(obj.getCantidadGestaciones());
                objHC.setCantidadPartos(obj.getCantidadPartos());
                objHC.setCantidadAbortos(obj.getCantidadAbortos());
                objHC.setHuboComplicacionesParto(obj.getHuboComplicacionesParto());
                objHC.setEspecifiqueComplicaciones(obj.getEspecifiqueComplicaciones());

                HistoriaClinicaEntity updatedObjHC = repositorio.save(objHC);

                return HistoriaClinicaDto.builder()
                                .idHC(updatedObjHC.getCodigo())
                                .idPaciente(updatedObjHC.getPacienteEntity().getIdUsuario())
                                .grupoSanquineo(updatedObjHC.getGrupoSanguineo())
                                .factorRH(updatedObjHC.getFactorRH())
                                .antecedentesFamiliares(updatedObjHC.getAntecedentesFamiliares())
                                .especifiqueAnteFamil(updatedObjHC.getEspecifiqueAnteFamil())
                                .estadoAlcohol(updatedObjHC.getEstadoAlcohol())
                                .frecuenciaAlcohol(updatedObjHC.getFrecuenciaAlcohol())
                                .estadoTabaco(updatedObjHC.getEstadoTabaco())
                                .frecuenciaTabaco(updatedObjHC.getFrecuenciaTabaco())
                                .consumeDrogas(updatedObjHC.getConsumeDrogas())
                                .sexualmenteActivo(updatedObjHC.getSexualmenteActivo())
                                .edadInicioSexual(updatedObjHC.getEdadInicioSexual())
                                .usaMetodoAnticonceptivo(updatedObjHC.getUsaMetodoAnticonceptivo())
                                .metodoPlanificacion(updatedObjHC.getMetodoPlanificacion())
                                .tuvoEmbarazos(updatedObjHC.getTuvoEmbarazos())
                                .cantidadGestaciones(updatedObjHC.getCantidadGestaciones())
                                .cantidadPartos(updatedObjHC.getCantidadPartos())
                                .cantidadAbortos(updatedObjHC.getCantidadAbortos())
                                .huboComplicaciones(updatedObjHC.getHuboComplicacionesParto())
                                .especifiqueComplicaciones(updatedObjHC.getEspecifiqueComplicaciones())
                                .estadoHC(updatedObjHC.getEstado())
                                .build();
        }

        @Override
        public HistoriaClinicaEntity delete(Long id) {
                HistoriaClinicaEntity objHC = repositorio.findById(id)
                                .orElseThrow(() -> new RuntimeException("La historia clinica no existe"));
                objHC.setEstado(false);
                return repositorio.save(objHC);
        }

        @Override
        public HistoriaClinicaEntity enable(Long id) {
                HistoriaClinicaEntity objHC = repositorio.findById(id)
                                .orElseThrow(() -> new RuntimeException("La historia clinica no existe"));
                objHC.setEstado(true);
                return repositorio.save(objHC);
        }
}
