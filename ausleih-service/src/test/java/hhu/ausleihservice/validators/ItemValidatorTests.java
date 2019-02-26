package hhu.ausleihservice.validators;

import hhu.ausleihservice.databasemodel.Abholort;
import hhu.ausleihservice.databasemodel.Item;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class ItemValidatorTests {
	//Tests for supports(Class<?> clazz)
	@Test
	public void supportsValidClass() {
		ItemValidator itemValidator = new ItemValidator();
		assertTrue(itemValidator.supports(Item.class));
	}

	@Test
	public void supportsInvalidClassFalse() {
		ItemValidator itemValidator = new ItemValidator();
		assertFalse(itemValidator.supports(Integer.class));
	}


	//Tests for validate(Object target, Errors errors)
	@Test
	public void validateNullTagessatz() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("Frittiertes Fahrrad");
		item.setBeschreibung("Du hast doch ein Rad ab");
		item.setKautionswert(1337);
		item.setAvailableFrom(LocalDate.now().plusDays(1));
		item.setAvailableTill(LocalDate.now().plusDays(2));
		item.setAbholort(new Abholort());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		FieldError error = bindingResult.getFieldError("tagessatz");

		assertTrue(bindingResult.hasErrors());
		assertEquals(1, bindingResult.getErrorCount());
		assertNotNull(error);
		assertEquals(Messages.notEmpty, error.getCode());
	}

	@Test
	public void validateNullKautionswert() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("Frittiertes Fahrrad");
		item.setBeschreibung("Du hast doch ein Rad ab");
		item.setTagessatz(42);
		item.setAvailableFrom(LocalDate.now().plusDays(1));
		item.setAvailableTill(LocalDate.now().plusDays(2));
		item.setAbholort(new Abholort());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		FieldError error = bindingResult.getFieldError("kautionswert");

		assertTrue(bindingResult.hasErrors());
		assertEquals(1, bindingResult.getErrorCount());
		assertNotNull(error);
		assertEquals(Messages.notEmpty, error.getCode());
	}

	@Test
	public void validateNullAvailableFrom() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("Frittiertes Fahrrad");
		item.setBeschreibung("Du hast doch ein Rad ab");
		item.setTagessatz(42);
		item.setKautionswert(1337);
		item.setAvailableTill(LocalDate.now().plusDays(2));
		item.setAbholort(new Abholort());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		FieldError error1 = bindingResult.getFieldError("availableFrom");
		FieldError error2 = bindingResult.getFieldError("availableTill");

		assertTrue(bindingResult.hasErrors());
		assertEquals(2, bindingResult.getErrorCount());
		assertNotNull(error1);
		assertEquals(Messages.notEmpty, error1.getCode());
		assertNotNull(error2);
		assertEquals(Messages.notEmpty, error2.getCode());
	}

	@Test
	public void validateNullAvailableTill() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("Frittiertes Fahrrad");
		item.setBeschreibung("Du hast doch ein Rad ab");
		item.setTagessatz(42);
		item.setKautionswert(1337);
		item.setAvailableFrom(LocalDate.now().plusDays(1));
		item.setAbholort(new Abholort());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		FieldError error1 = bindingResult.getFieldError("availableFrom");
		FieldError error2 = bindingResult.getFieldError("availableTill");

		assertTrue(bindingResult.hasErrors());
		assertEquals(2, bindingResult.getErrorCount());
		assertNotNull(error1);
		assertEquals(Messages.notEmpty, error1.getCode());
		assertNotNull(error2);
		assertEquals(Messages.notEmpty, error2.getCode());
	}

	@Test
	public void validateEmptyTitel() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("");
		item.setBeschreibung("Du hast doch ein Rad ab");
		item.setTagessatz(42);
		item.setKautionswert(1337);
		item.setAvailableFrom(LocalDate.now().plusDays(1));
		item.setAvailableTill(LocalDate.now().plusDays(2));
		item.setAbholort(new Abholort());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		List<FieldError> errors = bindingResult.getFieldErrors("titel");

		assertTrue(bindingResult.hasErrors());
		assertEquals(2, bindingResult.getErrorCount());
		assertNotNull(errors);
		assertEquals(2, errors.size());
		assertEquals(Messages.notEmpty, errors.get(0).getCode());
		assertEquals(Messages.sizeTitle, errors.get(1).getCode());
	}

	@Test
	public void validateEmptyBeschreibung() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("Frittiertes Fahrrad");
		item.setBeschreibung("");
		item.setTagessatz(42);
		item.setKautionswert(1337);
		item.setAvailableFrom(LocalDate.now().plusDays(1));
		item.setAvailableTill(LocalDate.now().plusDays(2));
		item.setAbholort(new Abholort());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		List<FieldError> errors = bindingResult.getFieldErrors("beschreibung");

		assertTrue(bindingResult.hasErrors());
		assertEquals(2, bindingResult.getErrorCount());
		assertNotNull(errors);
		assertEquals(2, errors.size());
		assertEquals(Messages.notEmpty, errors.get(0).getCode());
		assertEquals(Messages.sizeItemDescription, errors.get(1).getCode());
	}

	@Test
	public void validateTitelTooShort() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("Fri");
		item.setBeschreibung("Du hast doch ein Rad ab");
		item.setTagessatz(42);
		item.setKautionswert(1337);
		item.setAvailableFrom(LocalDate.now().plusDays(1));
		item.setAvailableTill(LocalDate.now().plusDays(2));
		item.setAbholort(new Abholort());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		FieldError error = bindingResult.getFieldError("titel");

		assertTrue(bindingResult.hasErrors());
		assertEquals(1, bindingResult.getErrorCount());
		assertNotNull(error);
		assertEquals(Messages.sizeTitle, error.getCode());
	}

	@Test
	public void validateTitelTooLong() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("01234567890123456789012345678901234567890123456789");
		item.setBeschreibung("Du hast doch ein Rad ab");
		item.setTagessatz(42);
		item.setKautionswert(1337);
		item.setAvailableFrom(LocalDate.now().plusDays(1));
		item.setAvailableTill(LocalDate.now().plusDays(2));
		item.setAbholort(new Abholort());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		FieldError error = bindingResult.getFieldError("titel");

		assertTrue(bindingResult.hasErrors());
		assertEquals(1, bindingResult.getErrorCount());
		assertNotNull(error);
		assertEquals(Messages.sizeTitle, error.getCode());
	}

	@Test
	public void validateBeschreibungTooShort() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("Frittiertes Fahrrad");
		item.setBeschreibung("Du");
		item.setTagessatz(42);
		item.setKautionswert(1337);
		item.setAvailableFrom(LocalDate.now().plusDays(1));
		item.setAvailableTill(LocalDate.now().plusDays(2));
		item.setAbholort(new Abholort());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		FieldError error = bindingResult.getFieldError("beschreibung");

		assertTrue(bindingResult.hasErrors());
		assertEquals(1, bindingResult.getErrorCount());
		assertNotNull(error);
		assertEquals(Messages.sizeItemDescription, error.getCode());
	}

	@Test
	public void validateBeschreibungTooLong() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("Frittiertes Fahrrad");
		item.setTagessatz(42);
		item.setKautionswert(1337);
		item.setAvailableFrom(LocalDate.now().plusDays(1));
		item.setAvailableTill(LocalDate.now().plusDays(2));
		item.setAbholort(new Abholort());

		StringBuilder beschreibungBuilder = new StringBuilder();
		for (int i = 1; i <= 4001; i++) {
			beschreibungBuilder.append("E");
		}
		item.setBeschreibung(beschreibungBuilder.toString());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		FieldError error = bindingResult.getFieldError("beschreibung");

		assertTrue(bindingResult.hasErrors());
		assertEquals(1, bindingResult.getErrorCount());
		assertNotNull(error);
		assertEquals(Messages.sizeItemDescription, error.getCode());
	}

	@Test
	public void validateKautionswertTooSmall() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("Frittiertes Fahrrad");
		item.setBeschreibung("Du hast doch ein Rad ab");
		item.setTagessatz(42);
		item.setKautionswert(0);
		item.setAvailableFrom(LocalDate.now().plusDays(1));
		item.setAvailableTill(LocalDate.now().plusDays(2));
		item.setAbholort(new Abholort());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		FieldError error = bindingResult.getFieldError("kautionswert");

		assertTrue(bindingResult.hasErrors());
		assertEquals(1, bindingResult.getErrorCount());
		assertNotNull(error);
		assertEquals(Messages.negativeValue, error.getCode());
	}

	@Test
	public void validateTagessatzTooSmall() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("Frittiertes Fahrrad");
		item.setBeschreibung("Du hast doch ein Rad ab");
		item.setTagessatz(0);
		item.setKautionswert(1337);
		item.setAvailableFrom(LocalDate.now().plusDays(1));
		item.setAvailableTill(LocalDate.now().plusDays(2));
		item.setAbholort(new Abholort());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		FieldError error = bindingResult.getFieldError("tagessatz");

		assertTrue(bindingResult.hasErrors());
		assertEquals(1, bindingResult.getErrorCount());
		assertNotNull(error);
		assertEquals(Messages.negativeValue, error.getCode());
	}

	@Test
	public void validateAvailableFromAfterAvailableTill() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("Frittiertes Fahrrad");
		item.setBeschreibung("Du hast doch ein Rad ab");
		item.setTagessatz(42);
		item.setKautionswert(1337);
		item.setAvailableFrom(LocalDate.now().plusDays(2));
		item.setAvailableTill(LocalDate.now().plusDays(1));
		item.setAbholort(new Abholort());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		FieldError error = bindingResult.getFieldError("availableFrom");

		assertTrue(bindingResult.hasErrors());
		assertEquals(1, bindingResult.getErrorCount());
		assertNotNull(error);
		assertEquals(Messages.invalidPeriod, error.getCode());
	}

	@Test
	public void validateAvailableFromBeforeToday() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("Frittiertes Fahrrad");
		item.setBeschreibung("Du hast doch ein Rad ab");
		item.setTagessatz(42);
		item.setKautionswert(1337);
		item.setAvailableFrom(LocalDate.now().minusDays(1));
		item.setAvailableTill(LocalDate.now().plusDays(2));
		item.setAbholort(new Abholort());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		FieldError error = bindingResult.getFieldError("availableFrom");

		assertTrue(bindingResult.hasErrors());
		assertEquals(1, bindingResult.getErrorCount());
		assertNotNull(error);
		assertEquals(Messages.invalidAvailableFrom, error.getCode());
	}

	@Test
	public void validateValid() {
		ItemValidator itemValidator = new ItemValidator();
		Item item = new Item();

		item.setId(0L);
		item.setTitel("Frittiertes Fahrrad");
		item.setBeschreibung("Du hast doch ein Rad ab");
		item.setTagessatz(42);
		item.setKautionswert(1337);
		item.setAvailableFrom(LocalDate.now().plusDays(1));
		item.setAvailableTill(LocalDate.now().plusDays(2));
		item.setAbholort(new Abholort());

		DataBinder dataBinder = new DataBinder(item);
		dataBinder.setValidator(itemValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();

		assertFalse(bindingResult.hasErrors());
	}
}
