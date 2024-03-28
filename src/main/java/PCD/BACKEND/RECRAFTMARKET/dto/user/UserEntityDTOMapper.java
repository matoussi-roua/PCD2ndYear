package PCD.BACKEND.RECRAFTMARKET.dto.user;

import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

//converting UserEntity objects to UserEntityDTO objects
@Service
public class UserEntityDTOMapper implements Function<UserEntity, UserEntityDTO> {
    @Override
    public UserEntityDTO apply(UserEntity userEntity) {
        return new UserEntityDTO(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getRole(),
                userEntity.getAddress(),
                userEntity.getPoints()

        );
    }
}

//A Function represents a function that accepts one argument and produces a result.
//It’s a functional interface, which means it has a single abstract method called apply.
//The apply method takes an input (of type T) and returns an output (of type R).
