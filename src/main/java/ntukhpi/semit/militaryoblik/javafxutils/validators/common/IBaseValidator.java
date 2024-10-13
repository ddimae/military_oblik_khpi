package ntukhpi.semit.militaryoblik.javafxutils.validators.common;

import ntukhpi.semit.militaryoblik.adapters.IBaseAdapter;

public interface IBaseValidator<T extends IBaseAdapter> {
    boolean validate(T adapter) throws Exception;
}
