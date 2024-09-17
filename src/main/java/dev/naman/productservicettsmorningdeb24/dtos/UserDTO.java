package dev.naman.productservicettsmorningdeb24.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String email;
    private List<Role> rolesList;
    private boolean isEmailVerified;
}
