package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({ConciertoEstadioService.class, EstadioService.class, ConciertoService.class})

public class ConciertoEstadioServiceTest {

    @Autowired
    ConciertoEstadioService conciertoEstadioService;
    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    private List<ConciertoEntity> conciertos = new ArrayList<>();
    private List<EstadioEntity> estadios = new ArrayList<>();

    @BeforeEach
    void SetUp(){
        clearData();
        insertData();
    }

    public void clearData(){
        entityManager.getEntityManager().createQuery("delete from EstadioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ConciertoEntity").executeUpdate();
    }
    
    public void insertData(){
        //crear estadios
        for (int i=0; i<2; i++){
            EstadioEntity estadio = factory.manufacturePojo(EstadioEntity.class);
            estadio.setNombre("nombre" + i);
            estadio.setCiudad("bogota");
            estadio.setAforo(1001 + i);
            estadio.setPrecioAlquiler(100001L + i);

            entityManager.persist(estadio);
            estadios.add(estadio);
        }   
        //crear conciertos
        for (int i=0; i<2; i++){
            ConciertoEntity concierto = factory.manufacturePojo(ConciertoEntity.class);
            concierto.setNombre("nombre"+i);
            concierto.setNombreArtista("artista"+i);
            concierto.setFechaConcierto(LocalDateTime.of(2025, 10, 22, 0, 0));
            concierto.setAforo(15);
            concierto.setPresupuesto(100005L);

            entityManager.persist(concierto);
            conciertos.add(concierto);
        }
    }

    @Test
    void testAddEstadio() throws IllegalOperationException, EntityNotFoundException{
        ConciertoEntity concierto = conciertos.get(0);
        EstadioEntity estadio = estadios.get(0);
        ConciertoEntity respuesta = conciertoEstadioService.addEstadio(concierto.getId(), estadio.getId());

        assertNotNull(respuesta);
        assertEquals(respuesta.getId(), concierto.getId());
        assertTrue(estadio.getConciertos().contains(concierto));
        
    }

    @Test
    void testAddInvalidEstadio() throws IllegalOperationException, EntityNotFoundException{
        ConciertoEntity concierto = conciertos.get(0);
        assertThrows(EntityNotFoundException.class, ()->{
            conciertoEstadioService.addEstadio(concierto.getId(), 22L);
        });
        
    }

    @Test
    void testAddInvalidConcierto() throws IllegalOperationException, EntityNotFoundException{
        EstadioEntity estadio = estadios.get(0);
        assertThrows(EntityNotFoundException.class, ()->{
            conciertoEstadioService.addEstadio(22L, estadio.getId());
        });
        
    }

    @Test
    void testAddInvalidFecha() throws IllegalOperationException, EntityNotFoundException{
        conciertoEstadioService.addEstadio(conciertos.get(0).getId(), estadios.get(0).getId());

        assertThrows(IllegalOperationException.class, ()->{
            conciertoEstadioService.addEstadio(conciertos.get(1).getId(), estadios.get(0).getId());
        });
        
    }

}
