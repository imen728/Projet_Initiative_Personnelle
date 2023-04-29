package com.example.AuthentificationService.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class userDTO {
    private long id;
    private String email;
    private String password;

}