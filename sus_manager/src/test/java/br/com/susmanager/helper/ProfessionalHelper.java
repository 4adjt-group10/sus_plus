package br.com.susmanager.helper;

import br.com.susmanager.controller.dto.professional.AddressFormDTO;
import br.com.susmanager.controller.dto.professional.ProfessionalCreateForm;
import br.com.susmanager.controller.dto.professional.ProfessionalManagerOut;
import br.com.susmanager.controller.dto.professional.ProfessionalType;
import br.com.susmanager.controller.dto.speciality.SpecialityForm;
import br.com.susmanager.model.Address;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.model.SpecialityModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfessionalHelper {

    public ProfessionalModel createProfessionalModel(){
        AddressFormDTO addressForm = new AddressFormDTO("Street", 123, "neighborhood", "City", "State", "Zip");
        List<UUID> specialityIds = new ArrayList<>();
        specialityIds.add(UUID.randomUUID());
        List<SpecialityModel> specialities = new ArrayList<>();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", new ArrayList<>()), new ArrayList<>());
        specialities.add(speciality);
        ProfessionalCreateForm form = new ProfessionalCreateForm("Name", "123456",  addressForm, ProfessionalType.DOCTOR, specialityIds);
        ProfessionalModel professional = new ProfessionalModel(form, null);

        return professional;
    }

    public ProfessionalManagerOut createProfessionalManagerOut(){
        AddressFormDTO addressForm = new AddressFormDTO("Street", 123, "neighborhood", "City", "State", "Zip");
        Address address = new Address(addressForm);
        List<UUID> specialityIds = new ArrayList<>();
        specialityIds.add(UUID.randomUUID());
        List<SpecialityModel> specialities = new ArrayList<>();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", new ArrayList<>()), new ArrayList<>());
        specialities.add(speciality);
        ProfessionalManagerOut professional = new ProfessionalManagerOut(new ProfessionalModel(new ProfessionalCreateForm("Name", "123", addressForm, ProfessionalType.DOCTOR, specialityIds), null));

        return professional;
    }

    public ProfessionalCreateForm createProfessionalForm(String document, String name){
        AddressFormDTO addressForm = new AddressFormDTO("Street", 123, "neighborhood", "City", "State", "Zip");
        List<UUID> specialityIds = new ArrayList<>();
        specialityIds.add(UUID.randomUUID());
        List<SpecialityModel> specialities = new ArrayList<>();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", new ArrayList<>()), new ArrayList<>());
        specialities.add(speciality);

        return new ProfessionalCreateForm(name, document,  addressForm, ProfessionalType.DOCTOR, specialityIds);
    }
}
