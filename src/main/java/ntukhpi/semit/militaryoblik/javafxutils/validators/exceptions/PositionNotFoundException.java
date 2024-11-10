package ntukhpi.semit.militaryoblik.javafxutils.validators.exceptions;

import javax.management.InstanceNotFoundException;

public class PositionNotFoundException extends InstanceNotFoundException {
    public PositionNotFoundException(String msg) {
        super(msg);
    }
}
