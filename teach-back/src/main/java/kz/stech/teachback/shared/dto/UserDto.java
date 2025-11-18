package kz.stech.teachback.shared.dto;


import lombok.*;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private UUID id;
    private String username;
    private Set<String> roles;
}
