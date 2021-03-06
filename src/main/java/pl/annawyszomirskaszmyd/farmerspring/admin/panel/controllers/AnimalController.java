package pl.annawyszomirskaszmyd.farmerspring.admin.panel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.annawyszomirskaszmyd.farmerspring.admin.panel.models.forms.AddAnimalForm;
import pl.annawyszomirskaszmyd.farmerspring.admin.panel.models.forms.RemoveAnimalForm;
import pl.annawyszomirskaszmyd.farmerspring.admin.panel.models.services.AnimalService;
import pl.annawyszomirskaszmyd.farmerspring.admin.panel.models.services.BarnService;

import javax.validation.Valid;

@Controller
public class AnimalController {
    private final AnimalService animalService;
    private final BarnService barnService;

    @Autowired
    public AnimalController(AnimalService animalService, BarnService barnService) {
        this.animalService = animalService;
        this.barnService = barnService;
    }

    @GetMapping("/admin-panel/add-animal")
    public String addAnimal(Model model){
        model.addAttribute("addAnimalForm", new AddAnimalForm());

        if(barnService.isBarnListEmpty()){
            model.addAttribute("noBarnInfo", "Na farmie nie ma żadnej stodoły. Dodaj stodołę aby móc dodać zwierzę!");
            return"add_animal";
        }

        return "add_animal";
    }

    @PostMapping("/admin-panel/add-animal")
    public String addAnimal(@ModelAttribute @Valid AddAnimalForm addAnimalForm, BindingResult bindingResult,
                            Model model){

        if (bindingResult.hasErrors() || !barnService.existsByIdAndFarmerId(addAnimalForm.getBarnId())) {
            return "add_animal";
        }

        model.addAttribute("animalAddedInfo", "Zwierzę zostało dodane do farmy!");
        animalService.addAnimal(addAnimalForm);

        return "add_animal";
    }

    @GetMapping("/admin-panel/remove-animal")
    public String removeAnimal(Model model){
        model.addAttribute("removeAnimalForm", new RemoveAnimalForm());

        if(animalService.isFarmerAnimalListEmpty()){
            model.addAttribute("emptyAnimalList", "Nie masz żadnych zwierząt na farmie!");

            return "remove_animal";
        }

        return "remove_animal";
    }

    @PostMapping("/admin-panel/remove-animal")
    public String removeAnimal(@ModelAttribute @Valid RemoveAnimalForm removeAnimalForm, BindingResult bindingResult,
                               Model model){
        if(bindingResult.hasErrors() ){
            return "remove_animal";
        }

        if(!animalService.existsByTypeAndFarmerId(removeAnimalForm.getType())){
            model.addAttribute("noAnimalTypeError", "Nie masz na farmie zwierząt o podanym typie!");
            return "remove_animal";
        }

        animalService.removeAnimal(removeAnimalForm.getType());
        model.addAttribute("removedAnimalInfo", "Zwierzę zostało usunięte z farmy");

        return "remove_animal";
    }

}
