package co.edu.uniandes.dse.parcial1.dto;

import java.util.List;

import lombok.Data;

@Data
public class EstadioDTO {
    private String nombre;
    private Long precioAlquiler;
    private String ciudad;
    private int aforo;
    private List<ConciertoDTO> conciertos;
}
