package ntukhpi.semit.militaryoblik.utils.exportimport;

import ntukhpi.semit.militaryoblik.adapters.EIAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.validators.*;
import ntukhpi.semit.militaryoblik.service.entitycrud.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EIDataSaveService {
    private final EmployeeCRUD employeeCRUD;
    private final EmployeeValidator employeeValidator;
    private final MilitaryRegistrationCRUD militaryRegistrationCRUD;
    private final MilitaryRegistrationValidator militaryRegistrationValidator;
    private final ContactsCRUD contactInfoCRUD;
    private final ContactInfoValidator contactInfoValidator;
    private final EducationCRUD educationCRUD;
    private final EducationValidator educationValidator;
    private final EducationPostgraduateCRUD posteducationCRUD;
    private final PosteducationValidator posteducationValidator;
    private final FamilyMemberCRUD familyCRUD;
    private final FamilyValidator familyValidator;
    private final DocumentCRUD documentCRUD;
    private final DocumentValidator documentValidator;

    @Autowired
    public EIDataSaveService(EmployeeCRUD employeeCRUD, EmployeeValidator employeeValidator,
                             MilitaryRegistrationCRUD militaryRegistrationCRUD, MilitaryRegistrationValidator militaryRegistrationValidator,
                             ContactsCRUD contactInfoCRUD, ContactInfoValidator contactInfoValidator,
                             EducationCRUD educationCRUD, EducationValidator educationValidator,
                             EducationPostgraduateCRUD posteducationCRUD, PosteducationValidator posteducationValidator,
                             FamilyMemberCRUD familyCRUD, FamilyValidator familyValidator,
                             DocumentCRUD documentCRUD, DocumentValidator documentValidator) {
                this.employeeCRUD = employeeCRUD;
                this.employeeValidator = employeeValidator;
                this.militaryRegistrationCRUD = militaryRegistrationCRUD;
                this.militaryRegistrationValidator = militaryRegistrationValidator;
                this.contactInfoCRUD = contactInfoCRUD;
                this.contactInfoValidator = contactInfoValidator;
                this.educationCRUD = educationCRUD;
                this.educationValidator = educationValidator;
                this.posteducationCRUD = posteducationCRUD;
                this.posteducationValidator = posteducationValidator;
                this.familyCRUD = familyCRUD;
                this.familyValidator = familyValidator;
                this.documentCRUD = documentCRUD;
                this.documentValidator = documentValidator;
    }

    public void save(EIAdapter importAdapter) {

    }
}
