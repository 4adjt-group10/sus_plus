package br.com.susmanager.helper;

import br.com.susmanager.controller.dto.professional.AddressFormDTO;
import br.com.susmanager.controller.dto.professional.ProfessionalCreateForm;
import br.com.susmanager.controller.dto.professional.ProfessionalManagerOut;
import br.com.susmanager.controller.dto.professional.ProfessionalType;
import br.com.susmanager.controller.dto.speciality.SpecialityForm;
import br.com.susmanager.model.AddressModel;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.model.SpecialityModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfessionalHelper {

    public ProfessionalModel createProfessionalModel(){
        AddressFormDTO addressForm = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        List<UUID> specialityIds = new ArrayList<>();
        specialityIds.add(UUID.randomUUID());
        List<SpecialityModel> specialities = new ArrayList<>();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", new ArrayList<>()), new ArrayList<>());
        specialities.add(speciality);
        List<LocalDateTime> availabilities = new ArrayList<>();
        availabilities.add(LocalDateTime.now());
        ProfessionalCreateForm form = new ProfessionalCreateForm("Name", "123456",  addressForm, ProfessionalType.DOCTOR, specialityIds, availabilities);
        ProfessionalModel professional = new ProfessionalModel(form, null);

        return professional;
    }

    public ProfessionalManagerOut createProfessionalManagerOut(){
        AddressFormDTO addressForm = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        AddressModel address = new AddressModel(addressForm);
        List<UUID> specialityIds = new ArrayList<>();
        specialityIds.add(UUID.randomUUID());
        List<SpecialityModel> specialities = new ArrayList<>();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", new ArrayList<>()), new ArrayList<>());
        specialities.add(speciality);
        List<LocalDateTime> availabilities = new ArrayList<>();
        availabilities.add(LocalDateTime.now());
        ProfessionalManagerOut professional = new ProfessionalManagerOut(new ProfessionalModel(new ProfessionalCreateForm("Name", "123", addressForm, ProfessionalType.DOCTOR, specialityIds, availabilities), null));

        return professional;
    }

    public ProfessionalCreateForm createProfessionalForm(String document, String name){
        AddressFormDTO addressForm = new AddressFormDTO("Street", 123, "City", "State", "Zip");
        List<UUID> specialityIds = new ArrayList<>();
        specialityIds.add(UUID.randomUUID());
        List<SpecialityModel> specialities = new ArrayList<>();
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiology", new ArrayList<>()), new ArrayList<>());
        specialities.add(speciality);
        List<LocalDateTime> availabilities = new ArrayList<>();
        availabilities.add(LocalDateTime.now());

        return new ProfessionalCreateForm(name, document,  addressForm, ProfessionalType.DOCTOR, specialityIds, availabilities);
    }
}
