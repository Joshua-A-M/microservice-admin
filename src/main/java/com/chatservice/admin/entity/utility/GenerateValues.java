package com.chatservice.admin.entity.utility;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class GenerateValues {
    public static String generatePersonalId() {
        String personalId = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        return personalId;
    }

    public static String generateUsername(String firstname, String lastname) {
        String username;

            StringBuilder generateUsername = new StringBuilder();
            //  capitalize first letter of first name
            generateUsername.append(Character.toUpperCase(firstname.charAt(0)));

            //  capitalize first letter of last name and then append
            generateUsername.append(Character.toUpperCase(lastname.charAt(0)));
            //  append the rest of the last name
            generateUsername.append(lastname.substring(1));

            Random random = new Random();
            for (int i = 0; i < 4; i++) {
                generateUsername.append(random.nextInt(10));
            }
            username = generateUsername.toString();

        return username;
    }

}
