package hhu.ausleihservice.validators;

import hhu.ausleihservice.databasemodel.Person;
import hhu.ausleihservice.web.service.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegisterValidator implements Validator {
	private PersonService personService;

	public RegisterValidator(PersonService personService) {
		this.personService = personService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", Messages.notEmpty);
		if (person.getUsername().length() < 4 || person.getUsername().length() > 32) {
			errors.rejectValue("username", Messages.usernameSize);
		}

		if (personService.existsByUsername(person.getUsername())) {
			errors.rejectValue("username", Messages.duplicateUsername);
		}


	}
}
