package pl.annawyszomirskaszmyd.farmerspring.admin.panel.models.mappers;
import pl.annawyszomirskaszmyd.farmerspring.admin.panel.models.forms.AddAnimalForm;
import pl.annawyszomirskaszmyd.farmerspring.admin.panel.models.entities.AnimalEntity;

public class AddAnimalFormToAnimalEntityMapper implements Mapper<AddAnimalForm, AnimalEntity> {

    @Override
    public AnimalEntity map(AddAnimalForm addAnimalForm) {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setType(addAnimalForm.getType());
        animalEntity.setAge(addAnimalForm.getAge());
        animalEntity.setVaccinated(isVaccinatedMapper(addAnimalForm));
        animalEntity.setBarnId(addAnimalForm.getBarnId());

        return animalEntity;
    }

    private boolean isVaccinatedMapper(AddAnimalForm addAnimalForm){
        String isVaccinated = addAnimalForm.getIsVaccinated();

        return isVaccinated.equals("tak");
    }
}
