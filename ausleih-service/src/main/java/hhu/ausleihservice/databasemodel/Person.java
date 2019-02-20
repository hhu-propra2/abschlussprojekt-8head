package hhu.ausleihservice.databasemodel;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nachname;
	private String vorname;

	@EqualsAndHashCode.Include
	private String username;
	private String password;
	private Role role = Role.USER;

	private String email;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Item> items = new HashSet<>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Ausleihe> ausleihen = new HashSet<>();
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	private Set<Abholort> abholorte = new HashSet<>();

	public void addAusleihe(Ausleihe ausleihe) {
		if (ausleihe == null) {
			return;
		}
		ausleihen.add(ausleihe);
		ausleihe.setAusleiher(this);
	}

	public void removeAusleihe(Ausleihe ausleihe) {
		if (ausleihe == null) {
			return;
		}
		ausleihen.remove(ausleihe);
		ausleihe.setAusleiher(null);
	}

	public void addItem(Item item) {
		if (item == null) {
			return;
		}
		items.add(item);
		item.setBesitzer(this);
	}

	public void removeItem(Item item) {
		if (item == null) {
			return;
		}
		items.remove(item);
		item.setBesitzer(null);
	}

	public boolean isAdmin() {
		return this != null && this.getRole().equals(Role.ADMIN);
	}

	public boolean isOwner(Item artikel){
		return this.getId().equals(artikel.getBesitzer().getId());
	}
}
