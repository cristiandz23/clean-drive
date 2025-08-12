package com.cleandriver.configuration;

import io.github.cdimascio.dotenv.Dotenv;

public class ApplicationEnvironment {

    public static final Dotenv dotenv = Dotenv.load();

    public static final String MP_ACCESS_TOKEN = dotenv.get("MP_TOKEN");


}
