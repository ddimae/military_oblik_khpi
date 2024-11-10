package ntukhpi.semit.militaryoblik.javafxutils.validators.exceptions;

import javax.management.InstanceNotFoundException;

public class VoenkomatNotFoundException extends InstanceNotFoundException {
    public VoenkomatNotFoundException(String msg) {
        super(msg);
    }
}
