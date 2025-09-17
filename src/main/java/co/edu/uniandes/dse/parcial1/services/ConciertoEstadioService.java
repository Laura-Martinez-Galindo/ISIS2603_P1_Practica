package co.edu.uniandes.dse.parcial1.services;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoEstadioService {

    @Autowired
    private ConciertoRepository conciertoRepository;

    @Autowired
    private EstadioRepository estadioRepository;

    @Transactional
    public ConciertoEntity addEstadio(Long conciertoId, Long estadioId) throws IllegalOperationException, EntityNotFoundException{
        Optional<ConciertoEntity> conciertoOptional = conciertoRepository.findById(conciertoId);
        if (conciertoOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro el concierto");
        }
    
        Optional<EstadioEntity> estadioOptional = estadioRepository.findById(estadioId);
        if (estadioOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro el estadio");
        }
        
        ConciertoEntity concierto = conciertoOptional.get();
        EstadioEntity estadio = estadioOptional.get();
        if (concierto.getAforo() > estadio.getAforo()){
            throw new IllegalOperationException("La capacidad de un concierto no debe superar la capacidad del estadio");
        }
        if (estadio.getPrecioAlquiler() > concierto.getPresupuesto()){
            throw new IllegalOperationException("El precio del alquiler del estadio no debe superar el presupuesto del concierto");
        }
        for (ConciertoEntity conciertoEstadio : estadio.getConciertos()){
            if (Duration.between(conciertoEstadio.getFechaConcierto(), concierto.getFechaConcierto()).toDays()<2){
                throw new IllegalOperationException("Debe existir un tiempo mínimo de 2 días entre los conciertos asociados a un estadio");
            }
        }
        concierto.setEstadio(estadio);
        estadio.getConciertos().add(concierto);
        return concierto;
    }


}
