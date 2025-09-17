package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadioService {
    @Autowired
    private EstadioRepository estadioRepository;

    @Transactional
    public EstadioEntity createEstadio(EstadioEntity estadio) throws IllegalOperationException{
        if (estadio.getCiudad().length() < 3){
            throw new IllegalOperationException("El nombre de la ciudad debe ser una cadena de caracteres de minimo 3 letras");
        }
        if (estadio.getAforo() <= 1000 || estadio.getAforo() >= 1000000){
            throw new IllegalOperationException("La capacidad del estadio debe ser superior a 1000 personas y menor a 1000000");
        }
        if (estadio.getPrecioAlquiler() <= 100000){
            throw new IllegalOperationException("El precio del alquiler debe ser superior a 100000");
        }

        estadioRepository.save(estadio);
        return estadio;
    }

    @Transactional
    public void getEstadio(Long id){
        estadioRepository.findById(id);
    }

    @Transactional
    public void getEstadios(){
        estadioRepository.findAll();
    }

    @Transactional
    public void updateEstadio(Long id, EstadioEntity estadio) throws EntityNotFoundException, IllegalOperationException{
        if (estadioRepository.findById(id) == null){
            throw new EntityNotFoundException("No se encontro el estadio");
        }
        
        if (estadio.getCiudad().length() < 3){
            throw new IllegalOperationException("El nombre de la ciudad debe ser una cadena de caracteres de minimo 3 letras");
        }
        if (estadio.getAforo() <= 1000 || estadio.getAforo() >= 1000000){
            throw new IllegalOperationException("La capacidad del estadio debe ser superior a 1000 personas y menor a 1000000");
        }
        if (estadio.getPrecioAlquiler() <= 100000){
            throw new IllegalOperationException("El precio del alquiler debe ser superior a 100000");
        }

        estadio.setId(id);
        estadioRepository.save(estadio);


    }
    @Transactional
    public void deleteEstadio(Long id){
        if (estadioRepository.findById(id) == null){
            throw new EntityNotFoundException("No se encontro el estadio");
        }

        estadioRepository.deleteById(id);

    }

}
