package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({EstadioService.class})
public class EstadioServiceTest {

    @Autowired
    private EstadioService estadioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl(); 

    @BeforeEach
    void setUp(){
        clearData();
    }

    public void clearData(){
        entityManager.getEntityManager().createQuery("Delete from EstadioEntity").executeUpdate();
    }

    @Test
    void testCreateEstadio() throws IllegalOperationException{
        EstadioEntity estadio = factory.manufacturePojo(EstadioEntity.class);
        estadio.setNombre("nombre");
        estadio.setCiudad("bogota");
        estadio.setAforo(1200);
        estadio.setPrecioAlquiler(122000L);

        EstadioEntity estadioCrear = estadioService.createEstadio(estadio);
        assertNotNull(estadioCrear);
        EstadioEntity estadioCreado = entityManager.find(EstadioEntity.class, estadioCrear.getId());
        assertEquals(estadioCreado.getId(), estadio.getId());
        assertEquals(estadioCreado.getNombre(), estadio.getNombre());
        assertEquals(estadioCreado.getCiudad(), estadio.getCiudad());
        assertEquals(estadioCreado.getAforo(), estadio.getAforo());
        assertEquals(estadioCreado.getPrecioAlquiler(), estadio.getPrecioAlquiler());
    }

    @Test
    void testCreateEstadioWithInvalidCiudad() throws IllegalOperationException{
        EstadioEntity estadio = factory.manufacturePojo(EstadioEntity.class);
        estadio.setNombre("nombre");
        estadio.setCiudad("bo");
        estadio.setAforo(1200);
        estadio.setPrecioAlquiler(122000L);

        assertThrows(IllegalOperationException.class, ()->{
            estadioService.createEstadio(estadio);
        });

    }

}
