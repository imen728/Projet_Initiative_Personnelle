package com.examenService.examenService.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EtudiantDto {
    private long id;

    private int cin;
    private String nom;
    private String prenom;
}
