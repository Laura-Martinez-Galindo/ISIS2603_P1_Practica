package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoService {
    @Autowired
    private ConciertoRepository conciertoRepository;
    //Create
    @Transactional
    public ConciertoEntity createConcierto(ConciertoEntity concierto) throws IllegalOperationException{
        log.info("Empieza proceso de crear un concierto");
        if (concierto.getFechaConcierto().isBefore(LocalDateTime.now())){
            throw new IllegalOperationException("La fecha de un concierto no puede ser en el pasado");
        }

        if (concierto.getAforo() <= 10){
            throw new IllegalOperationException("La capacidad del concierto debe ser un número superior a 10 personas");
        }

        if (concierto.getPresupuesto() <= 1000){
            throw new IllegalOperationException("El presupuesto del concierto debe ser un número superior a 1000 dolares");
        }
        
        conciertoRepository.save(concierto);
        log.info("Finaliza proceso de crear un concierto");
        return concierto;
    }

    @Transactional
    public void getConcierto(Long conciertoId){
        conciertoRepository.findById(conciertoId);
    }

    @Transactional
    public void getConciertos(){
        conciertoRepository.findAll();
    }

    @Transactional
    public void updateConcierto(Long id, ConciertoEntity concierto) throws EntityNotFoundException, IllegalOperationException{
        log.info("Empieza proceso de actualizar un concierto");
        if (conciertoRepository.findById(id) == null){
            throw new EntityNotFoundException("El concierto no existe");
        }

        if (concierto.getFechaConcierto().isBefore(LocalDateTime.now())){
            throw new IllegalOperationException("La fecha de un concierto no puede ser en el pasado");
        }

        if (concierto.getAforo() <= 10){
            throw new IllegalOperationException("La capacidad del concierto debe ser un número superior a 10 personas");
        }

        if (concierto.getPresupuesto() <= 1000){
            throw new IllegalOperationException("El presupuesto del concierto debe ser un número superior a 1000 dolares");
        }

        concierto.setId(id);
        conciertoRepository.save(concierto);
        log.info("Finaliza proceso de actualizar un concierto");
    }

    @Transactional
    public void deleteConcierto(Long id) throws EntityNotFoundException{
        if (conciertoRepository.findById(id) == null){
            throw new EntityNotFoundException("El concierto no existe");
        }
        conciertoRepository.deleteById(id);
    }

}
