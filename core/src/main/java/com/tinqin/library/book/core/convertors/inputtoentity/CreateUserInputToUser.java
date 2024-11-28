package com.tinqin.library.book.core.convertors.inputtoentity;

import com.tinqin.library.book.api.operations.create.createusers.CreateUserInput;
import com.tinqin.library.book.persistence.models.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateUserInputToUser implements Converter<CreateUserInput, User> {

    @Override
    public User convert(CreateUserInput source) {
        return User
                .builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .isAdmin(source.getIsAdmin())
                .build();
    }
}
