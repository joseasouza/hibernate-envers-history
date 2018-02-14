package br.com.logique.hibernatehistory.business.util;

import lombok.Data;

/**
 * Created by Joilson on 08/02/2018.
 */
@Data
public class PropertiesUtil {

    private static final String APLICATION_SPRING = "spring";

    private String typeAplication;

    private static PropertiesUtil propertiesUtil;

    public static PropertiesUtil getInstance(){
        if(propertiesUtil == null){
            propertiesUtil = new PropertiesUtil();
        }
        return propertiesUtil;
    }

    public boolean isSpring(){
        return typeAplication.equals(APLICATION_SPRING);
    }
}
