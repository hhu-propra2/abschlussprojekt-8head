package hhu.ausleihservice.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import hhu.ausleihservice.databasemodel.Person;
import hhu.ausleihservice.databasemodel.Rolle;
import hhu.ausleihservice.web.PersonService;

@Component
public class PersonValidator implements Validator {

	PersonService personService;

	public PersonValidator(PersonService personService) {
		this.personService = personService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		if (personService.findByUsername(person.getUsername()) != null) {
			errors.rejectValue("username", "User schon vorhanden");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (person.getPassword().length() < 8 || person.getPassword().length() >= 255) {
			errors.rejectValue("password", "Passwort muss mehr als 8 und weniger als 255 Zeichen beinhalten");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
		//Email format Regular Expression from RFC 2822
//		if(!person.getEmail().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")){
//			errors.rejectValue("email", "Email ist nicht gueltig");
//		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vorname", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nachname", "NotEmpty");
	}
}