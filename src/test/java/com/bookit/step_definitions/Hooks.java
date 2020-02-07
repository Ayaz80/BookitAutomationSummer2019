package com.bookit.step_definitions;

import static io.restassured.RestAssured.*;

import com.bookit.utilities.Environment;
import io.cucumber.java.Before;

public class Hooks {
    /**
     * This hook will work,
     * only if scenario or feature has @api annotation
     */
    @Before("@api")
    public void setupAPI(){
        baseURI= Environment.BASE_URI;
    }
}
