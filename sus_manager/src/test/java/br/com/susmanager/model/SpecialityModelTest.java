package br.com.susmanager.model;

import br.com.susmanager.controller.dto.speciality.SpecialityForm;
import br.com.susmanager.model.ProfessionalModel;
import br.com.susmanager.model.SpecialityModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SpecialityModelTest {

    @Test
    void testConstructorWithForm() {
        SpecialityForm form = new SpecialityForm("Cardiologia", List.of(UUID.randomUUID()));
        List<ProfessionalModel> professionals = new ArrayList<>();

        SpecialityModel speciality = new SpecialityModel(form, professionals);

        assertNotNull(speciality.getId());
        assertEquals("Cardiologia", speciality.getName());
        assertEquals(professionals, speciality.getProfessionals());
    }

    @Test
    void testMerge() {
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiologia", List.of(UUID.randomUUID())), new ArrayList<>());
        SpecialityForm newForm = new SpecialityForm("Dermatologia", List.of(UUID.randomUUID()));
        List<ProfessionalModel> newProfessionals = List.of(new ProfessionalModel());

        speciality.merge(newForm, newProfessionals);

        assertEquals("Dermatologia", speciality.getName());
        assertEquals(newProfessionals, speciality.getProfessionals());
    }

    @Test
    void testGetProfessionalsNames_empty() {
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiologia",List.of(UUID.randomUUID())), new ArrayList<>());

        List<String> professionalNames = speciality.getProfessionalsNames();

        assertTrue(professionalNames.isEmpty());
    }

    @Test
    void testGetProfessionalsNames_populated() {
        ProfessionalModel professional1 = new ProfessionalModel(UUID.randomUUID(), "Dr. Smith", "123", null, null, null, null);
        ProfessionalModel professional2 = new ProfessionalModel(UUID.randomUUID(), "Dr. Jones",  "456", null, null, null, null);
        List<ProfessionalModel> professionals = List.of(professional1, professional2);
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiologia",List.of(UUID.randomUUID())), professionals);

        List<String> professionalNames = speciality.getProfessionalsNames();

        assertEquals(2, professionalNames.size());
        assertTrue(professionalNames.contains("Dr. Smith"));
        assertTrue(professionalNames.contains("Dr. Jones"));
    }

    @Test
    void testAddProfessional() {
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiologia",List.of(UUID.randomUUID())), new ArrayList<>());
        ProfessionalModel professional = new ProfessionalModel(UUID.randomUUID(), "Dr. Smith",  "123", null, null, null, null);

        speciality.addProfessional(professional);

        assertEquals(1, speciality.getProfessionals().size());
        assertTrue(speciality.getProfessionals().contains(professional));
    }



    @Test
    void testNoArgsConstructor() {
        SpecialityModel speciality = new SpecialityModel();
        assertNotNull(speciality);
    }

    @Test
    void testGetId() {
        SpecialityForm form = new SpecialityForm("Cardiologia",List.of(UUID.randomUUID()));
        List<ProfessionalModel> professionals = new ArrayList<>();

        SpecialityModel speciality = new SpecialityModel(form, professionals);

        assertNotNull(speciality.getId());
    }

    @Test
    void testGetName() {
        SpecialityForm form = new SpecialityForm("Cardiologia",List.of(UUID.randomUUID()));
        List<ProfessionalModel> professionals = new ArrayList<>();

        SpecialityModel speciality = new SpecialityModel(form, professionals);

        assertEquals("Cardiologia", speciality.getName());
    }

    @Test
    void testGetProfessionals() {
        ProfessionalModel professional1 = new ProfessionalModel(UUID.randomUUID(), "Dr. Smith",  "123", null, null, null, null);
        List<ProfessionalModel> professionals = List.of(professional1);
        SpecialityModel speciality = new SpecialityModel(new SpecialityForm("Cardiologia",List.of(UUID.randomUUID())), professionals);

        assertEquals(professionals, speciality.getProfessionals());
    }
}