package co.edu.uniandes.dse.parcial1.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;

@Repository
public interface ConciertoRepository extends JpaRepository<ConciertoEntity, Long> {
    List<ConciertoEntity> findByNombre(String nombre);
    List<ConciertoEntity> findByPresupuesto(Long presupuesto);
    List<ConciertoEntity> findByNombreArtista(String nombreArtista);
    List<ConciertoEntity> findByFechaConcierto(LocalDate fechaConcierto);

}
