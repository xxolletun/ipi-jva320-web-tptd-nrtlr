package com.ipi.jva320.Controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class SalarieController {

    @Autowired
    SalarieAideADomicileService salarieService;

    @GetMapping(value = "/salaries/{id}")
    public String getSalarie(final ModelMap model,
                             @PathVariable Long id) {
        SalarieAideADomicile salarie = salarieService.getSalarie(id);
        model.put("salarie", salarie);
        model.put("nbSalarie", salarieService.countSalaries());
        return "detail_Salarie";
    }

    @GetMapping(value = "/salaries/aide/new")
    public String addSalarie(final ModelMap model) {
        model.put("salarie", new SalarieAideADomicile());
        model.put("nbSalarie", salarieService.countSalaries());
        return "detail_Salarie";
    }

    @PostMapping(value = "/salaries/save")
    public String saveSalarie(SalarieAideADomicile salarie) throws SalarieException {
        salarieService.creerSalarieAideADomicile(salarie);
        return "redirect:/salaries/" + salarie.getId();
    }

    @GetMapping(value = "/salaries")
    public String listSalarie(final ModelMap model,
                              @RequestParam(name = "nom", required = false) String nom,
                              @RequestParam(name = "matricule", required = false) String name) {
        model.put("nbSalarie", salarieService.countSalaries());
        if (nom != null && !nom.isEmpty()) {
            model.put("salaries", salarieService.getSalaries(nom));
            return "list";
        }
        model.put("salaries", salarieService.getSalaries());
        return "list";
    }

    @PostMapping(value = "/salaries/{id}")
    public String editSalarie(SalarieAideADomicile salarie, @PathVariable Long id) throws SalarieException {
        SalarieAideADomicile compare = salarieService.getSalarie(id);
        if (Objects.equals(salarie.getId(), compare.getId())) {
            salarieService.updateSalarieAideADomicile(salarie);
        }
        return "redirect:/salaries/" + salarie.getId();
    }

    @GetMapping("/salaries/{id}/delete")
    public String deleteSalarie(@PathVariable Long id) throws SalarieException {
        salarieService.deleteSalarieAideADomicile(id);
        return "redirect:/salaries";
    }
}