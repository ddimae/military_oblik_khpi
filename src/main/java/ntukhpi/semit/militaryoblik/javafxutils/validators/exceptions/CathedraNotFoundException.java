package ntukhpi.semit.militaryoblik.javafxutils.validators.exceptions;

import javax.management.InstanceNotFoundException;

public class CathedraNotFoundException extends InstanceNotFoundException {
    public CathedraNotFoundException(String msg) {
        super(msg);
    }
}
