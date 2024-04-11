package PCD.BACKEND.RECRAFTMARKET.dto.user;



import PCD.BACKEND.RECRAFTMARKET.model.role.Role;

import java.util.UUID;

//The record keyword in Java is used to create a concise class that automatically
// generates useful methods like constructors, getters, equals, hashCode, and toString.
//Records are particularly useful for simple data-holding classes like DTOs.

public record UserEntityDTO (
        UUID id,
        String username,
        Role role,

       // Number phoneNumber,

        String address,
        Long points


){
}
