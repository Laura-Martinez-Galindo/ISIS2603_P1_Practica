package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ConciertoService.class)
public class ConciertoServiceTest {

    @Autowired
    private ConciertoService conciertoService;
    
    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl(); 

    private List<ConciertoEntity> conciertos = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData(){
        entityManager.getEntityManager().createQuery("delete from ConciertoEntity").executeUpdate();
    }

    private void insertData(){
        //crear 4 conciertos, uno perfecto y 3 de casos malos
        for (int i=0; i<3; i++){
            ConciertoEntity concierto = factory.manufacturePojo(ConciertoEntity.class);
            concierto.setNombre("nombre"+i);
            concierto.setNombreArtista("artista"+i);
            concierto.setFechaConcierto(LocalDateTime.of(2025 + i, 2, 22, 0, 0));
            concierto.setAforo(9+i);
            concierto.setPresupuesto(998L+i);
            entityManager.persist(concierto);
            conciertos.add(concierto);
        }
    }

    @Test
    void testCreateConcierto() throws IllegalOperationException{
        ConciertoEntity concierto = factory.manufacturePojo(ConciertoEntity.class);
        concierto.setNombre("nombre"+3);
        concierto.setNombreArtista("artista"+3);
        concierto.setFechaConcierto(LocalDateTime.of(2025 + 3, 2, 22, 0, 0));
        concierto.setAforo(9+3);
        concierto.setPresupuesto(998L+3);

        //creo pa ver
        ConciertoEntity conciertoCrear = conciertoService.createConcierto(concierto);
        assertNotNull(conciertoCrear); //que en efecto se haya creado una entidad
        //saco el que creo base de datos
        ConciertoEntity conciertoCreado = entityManager.find(ConciertoEntity.class, conciertoCrear.getId());
        assertEquals(conciertoCreado.getId(), concierto.getId());
        assertEquals(conciertoCreado.getNombre(), concierto.getNombre());
        assertEquals(conciertoCreado.getNombreArtista(), concierto.getNombreArtista());
        assertEquals(conciertoCreado.getFechaConcierto(), concierto.getFechaConcierto());
        assertEquals(conciertoCreado.getAforo(), concierto.getAforo());
        assertEquals(conciertoCreado.getPresupuesto(), concierto.getPresupuesto());

    }   

    @Test
    void testCreateConciertoWithInvalidFecha() throws IllegalOperationException{
        ConciertoEntity concierto = factory.manufacturePojo(ConciertoEntity.class);
        concierto.setNombre("nombre"+4);
        concierto.setNombreArtista("artista"+4);
        concierto.setFechaConcierto(LocalDateTime.of(2025, 2, 22, 0, 0));
        concierto.setAforo(11);
        concierto.setPresupuesto(1000L);

        assertThrows(IllegalOperationException.class, ()->{
            conciertoService.createConcierto(concierto);
        });
    }   

}
