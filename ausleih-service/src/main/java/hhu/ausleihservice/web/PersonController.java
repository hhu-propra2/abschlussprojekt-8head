package hhu.ausleihservice.web;

import hhu.ausleihservice.databasemodel.Person;
import hhu.ausleihservice.validators.PersonValidator;
import hhu.ausleihservice.web.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class PersonController {
	private PersonService personService;
	private PersonValidator personValidator;

	PersonController(PersonService personService, PersonValidator personValidator) {
		this.personService = personService;
		this.personValidator = personValidator;
	}

	@GetMapping("/")
	public String startseite(Model model, Principal p) {
		model.addAttribute("user", personService.get(p));
		return "startseite";
	}

	@GetMapping("/profil/{id}")
	public String otherUser(Model model, @PathVariable Long id, Principal p) {
		model.addAttribute("benutzer", personService.findById(id));
		model.addAttribute("user", personService.get(p));
		return "profil";
	}

	@GetMapping("/profil")
	public String user(Model model, Principal p) {
		return otherUser(model, personService.get(p).getId(), p);
	}

	@PostMapping("/profil/{id}")
	public String editUser(Model model,
						   @PathVariable Long id,
						   Principal p,
						   @RequestParam(name = "editPerson", defaultValue = "false") final boolean changePerson,
						   @ModelAttribute("person") Person person
	) {
		System.out.println("Post triggered at /profil/" + id);
		if (changePerson) {
			System.out.println("Now updating..");
			personService.updateById(id, person);
		}
		model.addAttribute("benutzer", personService.findById(id));
		model.addAttribute("user", personService.get(p));
		return "profil";

	}

	@PostMapping("/profil")
	public String editUser(Model model,
						   Principal p,
						   @RequestParam(name = "editPerson", defaultValue = "false") final boolean changePerson
	) {
		return editUser(model, personService.get(p).getId(), p, changePerson, personService.get(p));
	}

	@GetMapping("/benutzersuche")
	public String benutzerSuche(Model model, Principal p) {
		model.addAttribute("user", personService.get(p));
		return "benutzerSuche";
	}

	@PostMapping("/benutzersuche")
	public String benutzerSuche(Model model,
								String query, //For nachname, vorname, username
								Principal p
	) {
		if (query != null) {
			query = query.trim();
		}
		List<Person> list = personService.searchByNames(query);
		model.addAttribute("benutzerListe", list);
		model.addAttribute("user", personService.get(p));
		return "benutzerListe";
	}

	@GetMapping("/register")
	public String register(Model model, Principal p) {
		if (personService.get(p) != null) {
			return "redirect:/";
		}
		Person userForm = new Person();
		model.addAttribute("usernameTaken", false);
		model.addAttribute("userForm", userForm);
		return "register";
	}

	@PostMapping("/register")
	public String added(Model model, Person userForm, BindingResult bindingResult) {
		personValidator.validate(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("userForm", userForm);
			model.addAttribute("usernameErrors", bindingResult.getFieldError("username"));
			model.addAttribute("vornameErrors", bindingResult.getFieldError("vorname"));
			model.addAttribute("nachnameErrors", bindingResult.getFieldError("nachname"));
			model.addAttribute("passwordErrors", bindingResult.getFieldError("password"));
			model.addAttribute("emailErrors", bindingResult.getFieldError("email"));
			return "register";
		}
		personService.save(userForm);
		return startseite(model, null);
	}

	@GetMapping("/admin")
	public String admin(Model model) {
		return "admin";
	}

}