package ntukhpi.semit.militaryoblik.javafxutils.validators.exceptions;

import javax.management.InstanceNotFoundException;

public class PrepodNotFoundException extends InstanceNotFoundException {
    public PrepodNotFoundException(String msg) {
        super(msg);
    }
}
