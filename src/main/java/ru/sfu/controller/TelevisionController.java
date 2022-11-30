package ru.sfu.controller;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sfu.entity.Television;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import ru.sfu.service.TelevisionService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Controller for Television CRUD operations
 * @author Agapchenko V.V.
 */
@Controller
@RequestMapping("/tvs")
public class TelevisionController {

    private final TelevisionService service;

    /**
     * Repository dependency injection for data access
     * @param service Television Service
     */
    @Autowired
    public TelevisionController(TelevisionService service) {
        this.service = service;
    }

    // REST GET One/All Televisions

    /**
     * REST GET Television in JSON format
     * @param id Identification number
     * @return Television JSON
     */
    @GetMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public Television getTelevisionJson(
            @PathVariable int id
    ) {
        return service.getById(id);
    }

    /**
     * REST GET Television in HTML format
     * @param id Identification number
     * @param model Model object
     * @return Television HTML view
     */
    @GetMapping(value = "/{id}", headers = "Accept=text/html")
    public String getTelevisionHtml(
            @PathVariable int id,
            Model model
    ) {
        model.addAttribute(
                "tvs",
                Collections.singleton(
                        service.getById(id)
                )
        );
        return "tvs/show";
    }

    /**
     * REST GET Televisions in JSON format
     * @return Televisions JSON
     */
    @GetMapping(headers = "Accept=application/json")
    @ResponseBody
    public List<Television> getTelevisionsJson() {
        return service.getNotSold();
    }

    /**
     * REST GET Televisions in HTML format
     * @param model Model object
     * @return Televisions HTML view
     */
    @GetMapping(headers = "Accept=text/html")
    public String getTelevisionsHtml(Model model) {
        model.addAttribute("tvs", service.getNotSold());
        return "tvs/show";
    }

    // REST POST New Television

    /**
     * REST POST New Television in JSON format
     * @param tv Television JSON
     * @param bindingResult Validation result
     * @param response HTTP Response Servlet
     * @return Television JSON
     * @throws BindException Validation error
     */
    @PostMapping(headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Television postTelevisionJson(
            @Valid @RequestBody Television tv,
            BindingResult bindingResult,
            HttpServletResponse response
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        if (!service.create(tv)) {
            throw new EntityExistsException(String.valueOf(tv.getId()));
        }
        response.setHeader("Location", "/tvs/" + tv.getId());
        return tv;
    }

    /**
     * REST POST New Television in HTML format
     * @param tv Television HTML
     * @param bindingResult Validation result
     * @return Television HTML view
     * @throws BindException Validation error
     */
    @PostMapping(headers = "Accept=html/text")
    @ResponseStatus(HttpStatus.CREATED)
    public String postTelevisionHtml(
            @Valid @RequestBody Television tv,
            BindingResult bindingResult
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        if (!service.create(tv)) {
            throw new EntityExistsException(String.valueOf(tv.getId()));
        }
        return "redirect:/tvs/" + tv.getId();
    }

    /**
     * REST POST New Television in HTML FORM format
     * @param tv Television FORM
     * @param bindingResult Validation result
     * @param response HTTP Response Servlet
     * @return Television HTML view
     * @throws IOException Response redirect error
     */
    @PostMapping(consumes = "application/x-www-form-urlencoded")
    @ResponseStatus(HttpStatus.CREATED)
    public String postTelevisionForm(
            @Valid @ModelAttribute("tv") Television tv,
            BindingResult bindingResult,
            HttpServletResponse response
    ) throws IOException {
        if (!bindingResult.hasErrors()) {
            service.create(tv);
            response.sendRedirect("/tvs/" + tv.getId());
        }
        return "tvs/new";
    }

    // REST PUT Television

    /**
     * REST PUT Television in JSON format
     * @param id Identification number
     * @param tv Television JSON
     * @param bindingResult Validation result
     * @throws BindException Validation error
     */
    @PutMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putTelevisionJson(
            @PathVariable int id,
            @Valid @RequestBody Television tv,
            BindingResult bindingResult
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        service.update(tv);
    }

    /**
     * REST PUT Television in HTML format
     * @param id Identification number
     * @param tv Television HTML
     * @param bindingResult Validation result
     * @return Television HTML view
     */
    @PutMapping(value = "/{id}", headers = "Accept=text/html")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String putTelevisionHtml(
            @PathVariable int id,
            @Valid @RequestBody Television tv,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors())
            return "tvs/edit";

        service.update(tv);
        return "redirect:/tvs";
    }

    // REST DELETE Television

    /**
     * REST DELETE Television JSON
     * @param id Identification Number
     */
    @DeleteMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTelevisionJson(
            @PathVariable int id
    ) {
        service.deleteById(id);
    }

    /**
     * REST DELETE Television HTML
     * @param id Identification Number
     * @return Redirect to Televisions view
     */
    @DeleteMapping(value = "/{id}", headers = "Accept=text/html")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTelevisionHtml(
            @PathVariable int id
    ) {
        service.deleteById(id);
        return "redirect:/tvs";
    }

    // REST GET Television By Width & Height

    /**
     * REST GET Television JSON by Width + Height
     * @param width Width parameter
     * @param height Height parameter
     * @return Television JSON
     */
    @GetMapping(value = "/{width}/{height}", headers = "Accept=application/json")
    @ResponseBody
    public List<Television> getByWidthAndHeightJson(
            @PathVariable int width,
            @PathVariable int height
    ) {
        return service.getByWidthAndHeight(width, height);
    }

    /**
     * REST GET Television HTML by Width + Height
     * @param width Width parameter
     * @param height Height parameter
     * @param model Model object
     * @return Television HTML view
     */
    @GetMapping(value = "/{width}/{height}", headers = "Accept=text/html")
    public String getByWidthAndHeightHtml(
            @PathVariable int width,
            @PathVariable int height,
            Model model
    ) {
        model.addAttribute(
                "tvs",
                service.getByWidthAndHeight(width, height)
        );
        return "tvs/show";
    }

    // NON REST

    /**
     * GET New Television Form
     * @param model Model object
     * @return Television Form view
     */
    @GetMapping(value = "/new")
    public String newTelevision(Model model) {
        model.addAttribute("tv", new Television(service.getLastId()));
        return "tvs/new";
    }

    /**
     * GET Television Edit Form
     * @param id Identification number
     * @param model Model object
     * @return Television Edit Form view
     */
    @GetMapping(value = "/{id}/update")
    public String editTelevision(
            @PathVariable int id,
            Model model
    ) {
        Television tv = service.getById(id);
        model.addAttribute("tv", tv);
        model.addAttribute("id", id);
        if (tv == null)
            return "redirect:/tvs/" + id;
        return "tvs/edit";
    }

    /**
     * POST Update Television
     * @param tv Television
     * @param bindingResult Validation result
     * @return Redirect to Televisions view
     */
    @PostMapping(value = "/{id}/update")
    public String updateTelevision(
            @ModelAttribute("tv") @Valid Television tv,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors())
            return "tvs/edit";

        service.update(tv);
        return "redirect:/tvs";
    }

    /**
     * POST Delete Television
     * @param id Identification number
     * @param model Model object
     * @return Redirect to Televisions view
     */
    @PostMapping(value = "/{id}/delete", headers = "Accept=text/html")
    public String deleteTelevision(
            @PathVariable int id,
            Model model
    ) {
        model.addAttribute("reload", "");
        service.deleteById(id);
        return "redirect:/tvs";
    }

    /**
     * GET Width + Height Search Form
     * @return Search Form view
     */
    @GetMapping("/set-width-height")
    public String setWidthAndHeight() {
        return "tvs/set-width-and-height";
    }

    /**
     * GET Television by Width + Height
     * @param width Width query parameter
     * @param height Height query parameter
     * @return Redirect to search result
     */
    @GetMapping("/get-width-height")
    public String getByWidthAndHeight(
            @RequestParam int width,
            @RequestParam int height
    ) {
        return String.format("redirect:/tvs/%d/%d", width, height);
    }

    /**
     * POST Purchase Television
     * @param id Identification number
     * @return Redirect to Televisions view
     */
    @PostMapping("/{id}/purchase")
    public String purchaseTelevision(@PathVariable int id) {
        service.sell(id);
        return "redirect:/tvs";
    }

    /**
     * GET Television Menu
     * @return Menu view
     */
    @GetMapping(value = "/menu")
    public String menu() {
        return "tvs/menu";
    }
}
