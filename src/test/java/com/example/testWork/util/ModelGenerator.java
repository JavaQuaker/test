package com.example.testWork.util;

import com.example.testWork.model.Url;
import com.example.testWork.model.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ModelGenerator {
    private Model<User> userModel;
    private Model<Url> urlModel;
    @Autowired
    private Faker faker;
    @PostConstruct
    public void init() {
        userModel = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getName), () -> faker.name().name())
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .supply(Select.field(User::getPasswordDigest), () -> faker.internet().password(3, 100))
                .toModel();
        System.out.println("userModel " + urlModel);

        urlModel = Instancio.of(Url.class)
                .ignore(Select.field(Url::getId))
                .supply(Select.field(Url::getOriginalUrl), () -> faker.internet().domainWord())
                .toModel();
    }
}
