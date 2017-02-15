package com.example.alok;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * Created by alokkulkarni on 14/02/17.
 */
public class UserDeserializer extends StdDeserializer<UserDetails>{

    public UserDeserializer() {
        this(null);
    }


    public UserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public UserDetails deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        UserDetails userDetails = new UserDetails();
        ObjectCodec codec = p.getCodec();
        String s = codec.readTree(p).traverse().getText();
        System.out.println(s);
//        System.out.println(node.get(0).asText());
//        System.out.println(node.get(2).asText());
//        System.out.println(node.get(3).asText());
//        JsonNode id = node.get(1);
//        JsonNode firstName = node.get(2);
//        JsonNode lastName = node.get(3);
//        userDetails.setLastName(id.asText());
//        userDetails.setLastName(firstName.asText());
//        userDetails.setLastName(lastName.asText());
        return userDetails;
    }
}
