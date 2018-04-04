package com.forsyslab.talquest10.constant;

/**
 * Created by LENOVO on 14/02/2017.
 */

public class PostCodeRegrex {
    private PostCodeRegrex(){}
    public static final String[] POSTCODE_REGREX = {
            "",
            "^(?:FI)*(\\d{5})$",
            "",
            "^(\\d{5})$",  //Algerie
            "",
            "",
            "",
            "",
            "",
            "",
            "^([A-Z]\\d{4}[A-Z]{3})$", //Argentina
            "",
            ""
    };
}
