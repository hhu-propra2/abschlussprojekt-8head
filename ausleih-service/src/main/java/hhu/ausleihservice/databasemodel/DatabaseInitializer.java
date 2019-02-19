package hhu.ausleihservice.databasemodel;

import hhu.ausleihservice.dataaccess.AbholortRepository;
import hhu.ausleihservice.dataaccess.ItemRepository;
import hhu.ausleihservice.dataaccess.PersonRepository;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseInitializer implements ServletContextInitializer {

	private PersonRepository personRepository;
	private ItemRepository itemRepository;
	private AbholortRepository abholortRepository;

	public DatabaseInitializer(PersonRepository perRepository,
							   ItemRepository iRepository,
							   AbholortRepository abhRepository) {
		this.personRepository = perRepository;
		this.itemRepository = iRepository;
		this.abholortRepository = abhRepository;
	}

	@Override
	public void onStartup(ServletContext servletContext) {
		System.out.println("Populating the database");

		Abholort ort1 = new Abholort();
		Abholort ort2 = new Abholort();
		Abholort ort3 = new Abholort();
		Abholort ort4 = new Abholort();

		ort1.setBeschreibung("Höhle");
		ort2.setBeschreibung("Garage");
		ort3.setBeschreibung("Haus");
		ort4.setBeschreibung("Verloren");

		ort1.setLatitude(51.227741);
		ort1.setLongitude(6.773456);

		ort2.setLatitude(51.227741);
		ort2.setLongitude(6.773456);

		ort3.setLatitude(51.227741);
		ort3.setLongitude(6.773456);

		ort4.setLatitude(51.227741);
		ort4.setLongitude(6.773456);

		Set<Abholort> orte1 = new HashSet<>();
		Set<Abholort> orte2 = new HashSet<>();
		Set<Abholort> orte3 = new HashSet<>();

		orte1.add(ort1);
		orte2.add(ort2);
		orte2.add(ort3);
		orte3.add(ort4);


		Person person1 = new Person();
		Person person2 = new Person();
		Person person3 = new Person();

		person1.setVorname("Gerold");
		person1.setNachname("Steiner");
		person2.setVorname("Volker");
		person2.setNachname("Racho");
		person3.setVorname("Wilma");
		person3.setNachname("Pause");

		person1.setUsername("Miner4lwasser");
		person2.setUsername("Kawumms");
		person3.setUsername("Kautschkartoffel3000");

		person1.setEmail("sleeping@home.com");
		person2.setEmail("notWorking@uni.com");
		person3.setEmail("screaming@computer.de");

		person1.setAbholorte(orte1);
		person2.setAbholorte(orte2);
		person3.setAbholorte(orte3);


		Item item1 = new Item();
		Item item2 = new Item();
		Item item3 = new Item();

		item1.setTitel("Stift");
		item2.setTitel("Fahrrad");
		item3.setTitel("Pfeil");

		item1.setBeschreibung("Zum stiften gehen");
		item2.setBeschreibung("Falls man sich radlos fühlt");
		item3.setBeschreibung("Wenn man den Bogen schon raus hat");

		item1.setTagessatz(3);
		item2.setTagessatz(8);
		item3.setTagessatz(100);

		item1.setKautionswert(34);
		item2.setKautionswert(1245);
		item3.setKautionswert(55);

		item1.setAbholort(ort1);
		item2.setAbholort(ort3);
		item3.setAbholort(ort4);

		item1.setAvailableFrom(LocalDate.now().plusDays(1));
		item2.setAvailableFrom(LocalDate.now().plusDays(3));
		item3.setAvailableFrom(LocalDate.now().plusDays(4));

		item1.setAvailableTill(LocalDate.now().plusDays(13));
		item2.setAvailableTill(LocalDate.now().plusDays(35));
		item3.setAvailableTill(LocalDate.now().plusDays(46));

		item1.setBesitzer(person1);
		item2.setBesitzer(person2);
		item3.setBesitzer(person3);

		this.abholortRepository.save(ort1);
		this.abholortRepository.save(ort2);
		this.abholortRepository.save(ort3);
		this.abholortRepository.save(ort4);

		this.personRepository.save(person1);
		this.personRepository.save(person2);
		this.personRepository.save(person3);

		this.itemRepository.save(item1);
		this.itemRepository.save(item2);
		this.itemRepository.save(item3);


	}
}
