package hhu.ausleihservice.databasemodel;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ItemTests {
	@Test
	public void testGetPictureReturnsByteArrayWithSameLength() {
		Item item = new Item();
		byte[] picture = new byte[256];
		item.setPicture(picture);
		Assert.assertEquals(256, item.getPicture().length);
	}

	@Test
	public void testGetPictureReturnsCopyOfPicture() {
		Item item = new Item();
		byte[] picture = new byte[256];
		item.setPicture(picture);
		byte[] copy = item.getPicture();
		copy[255] = 42;
		Assert.assertEquals(0, item.getPicture()[255]);
	}

	@Test
	public void testGetPictureReturnsNullIfPictureIsNotSet() {
		Item item = new Item();
		Assert.assertNull(item.getPicture());
	}

	@Test
	public void testGetPictureReturnsEmptyByteArrayIfPictureIsEmpty() {
		Item item = new Item();
		item.setPicture(new byte[0]);
		Assert.assertEquals(0, item.getPicture().length);
	}

	@Test
	public void testSetPictureCopiesGivenByteArray() {
		Item item = new Item();
		byte[] original = new byte[256];
		item.setPicture(original);
		original[255] = 77;
		Assert.assertEquals(0, item.getPicture()[255]);
	}

	@Test
	public void testSetPictureNull() {
		Item item = new Item();
		item.setPicture(null);
		Assert.assertNull(item.getPicture());
	}

	@Test
	public void addAusleiheFahrrad() {
		Person burak = new Person();
		burak.setUsername("bumar100");
		Abholort ort = new Abholort();
		ort.setBeschreibung("Zuhause");
		Item fahrrad = new Item();
		fahrrad.setId(1L);
		fahrrad.setBeschreibung("Schnell");
		fahrrad.setTitel("Mountain-Bike");
		fahrrad.setAbholort(ort);

		Ausleihe ausleihe = new Ausleihe();
		ausleihe.setAusleiher(burak);
		fahrrad.addAusleihe(ausleihe);
		Assert.assertEquals(fahrrad, ausleihe.getItem());
		Assert.assertEquals("Mountain-Bike", ausleihe.getItem().getTitel());
		Assert.assertEquals("Schnell", ausleihe.getItem().getBeschreibung());
		Assert.assertEquals("Zuhause", fahrrad.getAbholort().getBeschreibung());

	}

	@Test
	public void addAusleiheNull() {
		Item fahrrad = new Item();
		Assert.assertEquals(0, fahrrad.getAusleihen().size());
		fahrrad.addAusleihe(null);
		Assert.assertEquals(0, fahrrad.getAusleihen().size());
	}

	@Test
	public void removeAusleihe() {
		Ausleihe ausleihe = new Ausleihe();
		ausleihe.setId(1L);
		Item item = new Item();
		item.setId(2L);
		item.addAusleihe(ausleihe);
		item.removeAusleihe(ausleihe);
		Assert.assertEquals(0, item.getAusleihen().size());
		Assert.assertNull(ausleihe.getItem());
	}


	//Tests for setTitel(String s)
	@Test
	public void setTitelToNull() {
		Item item = new Item();
		item.setTitel(null);

		assertEquals("", item.getTitel());
	}

	@Test
	public void setTitelWithNoWhiteSpace() {
		Item item = new Item();
		item.setTitel("Uganda");

		assertEquals("Uganda", item.getTitel());
	}

	@Test
	public void setTitelWithWhiteSpaceLeft() {
		Item item = new Item();
		item.setTitel("                          Uganda");

		assertEquals("Uganda", item.getTitel());
	}

	@Test
	public void setTitelWithWhiteSpaceRight() {
		Item item = new Item();
		item.setTitel("Uganda                            ");

		assertEquals("Uganda", item.getTitel());
	}

	@Test
	public void setTitelWithWhiteSpaceBothSides() {
		Item item = new Item();
		item.setTitel("                    Uganda                  ");

		assertEquals("Uganda", item.getTitel());
	}

	@Test
	public void setTitelWithWhiteSpaceInside() {
		Item item = new Item();
		item.setTitel("Uga nda");

		assertEquals("Uga nda", item.getTitel());
	}


	//Tests for setBeschreibung(String s)
	@Test
	public void setBeschreibungToNull() {
		Item item = new Item();
		item.setBeschreibung(null);

		assertEquals("", item.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithNoWhiteSpace() {
		Item item = new Item();
		item.setBeschreibung("Uganda");

		assertEquals("Uganda", item.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithWhiteSpaceLeft() {
		Item item = new Item();
		item.setBeschreibung("                          Uganda");

		assertEquals("Uganda", item.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithWhiteSpaceRight() {
		Item item = new Item();
		item.setBeschreibung("Uganda                            ");

		assertEquals("Uganda", item.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithWhiteSpaceBothSides() {
		Item item = new Item();
		item.setBeschreibung("                    Uganda                  ");

		assertEquals("Uganda", item.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithWhiteSpaceInside() {
		Item item = new Item();
		item.setBeschreibung("Uga nda");

		assertEquals("Uga nda", item.getBeschreibung());
	}


	//Tests for setAbholort(Abholort a)
	@Test
	public void setAbholortToNull() {
		Item item = new Item();
		item.setAbholort(null);

		assertEquals(new Abholort(), item.getAbholort());
	}
}
