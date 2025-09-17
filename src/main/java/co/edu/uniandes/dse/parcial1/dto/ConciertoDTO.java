package co.edu.uniandes.dse.parcial1.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ConciertoDTO {
    private String nombre;
    private Long presupuesto;
    private String nombreArtista;
    private LocalDateTime fechaConcierto;
    private int aforo;
    private EstadioDTO estadio;

}
