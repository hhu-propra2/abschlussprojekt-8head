package hhu.ausleihservice.databasemodel;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude="ausleihen")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String titel;
	private String beschreibung;
	private int tagessatz;
	private int kautionswert;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinColumn
	private Abholort abholort;

	private LocalDate availableFrom;
	private LocalDate availableTill;
	@ManyToOne
	private Person besitzer;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Ausleihe> ausleihen = new HashSet<>();
	@Lob
	private byte[] picture;

	//Getter and Setter are copying the array to prevent
	// data leaking outside by storing/giving the reference to the array
	@Lob
	public byte[] getPicture() {
		byte[] out = new byte[picture.length];
		System.arraycopy(picture, 0, out, 0, picture.length);
		return out;
	}

	public void setPicture(byte[] in) {
		picture = new byte[in.length];
		System.arraycopy(in, 0, picture, 0, in.length);
	}

	private boolean isInPeriod(LocalDate date, LocalDate start, LocalDate end) {
		return (!date.isBefore(start) && !date.isAfter(end));
	}

	public boolean isAvailable() {
		return isAvailable(LocalDate.now());
	}
	boolean isAvailable(LocalDate date) {
		if (!isInPeriod(date, availableFrom, availableTill)) {
			return false;
		}
		for (Ausleihe ausleihe : ausleihen) {
			LocalDate startDatum = ausleihe.getStartDatum();
			LocalDate endDatum = ausleihe.getEndDatum();
			if (isInPeriod(date, startDatum, endDatum)) {
				return false;
			}
		}
		return true;
	}

	//Format of input is "YYYY-MM-DD"
	public boolean isAvailableFromTill(String from, String till) {
		LocalDate temp = LocalDate.parse(from);
		LocalDate end = LocalDate.parse(till);
		while (!temp.equals(end.plusDays(1))) {
			if (!isAvailable(temp)) {
				return false;
			}
			temp = temp.plusDays(1);
		}
		return true;
	}

	public ArrayList<Period> getAvailablePeriods(){
		ArrayList<Period> out = new ArrayList<>();

		if(ausleihen.isEmpty()){
			out.add(new Period(availableFrom, availableTill));
			return out;
		}

		Ausleihe[] sortierteAusleihen = getSortierteAusleihen();
		int length = sortierteAusleihen.length;
		System.err.println(sortierteAusleihen[0]);

		LocalDate start = availableFrom;
		LocalDate end = sortierteAusleihen[0].getStartDatum();

		if(!start.equals(end)){
			out.add(new Period(start, end.minusDays(1)));
		}

		for(int i = 0; i < length-1; i++){

			start = sortierteAusleihen[i].getEndDatum();
			end = sortierteAusleihen[i+1].getStartDatum();

			if(!start.equals(end)){
				out.add(new Period(start.plusDays(1), end.minusDays(1)));
			}
		}

		start = sortierteAusleihen[length-1].getEndDatum();
		end = availableTill;

		if(!start.equals(end)){
			out.add(new Period(start.plusDays(1), end));
		}

		return out;
	}

	Ausleihe[] getSortierteAusleihen() {

		Ausleihe[] sortierteAusleihen = new Ausleihe[ausleihen.size()];
		List<Ausleihe> tempAusleihen = new ArrayList<>(ausleihen);

		for(int i = 0; i < ausleihen.size(); i++) {

			Ausleihe smallest = tempAusleihen.get(0);
			LocalDate smallestDate = smallest.getStartDatum();

			for (Ausleihe test : tempAusleihen) {
				LocalDate testDate = test.getStartDatum();
				if(testDate.isBefore(smallestDate)){
					smallest = test;
					smallestDate = smallest.getStartDatum();
				}
			}
			sortierteAusleihen[i] = smallest;
			tempAusleihen.remove(smallest);
		}

		return sortierteAusleihen;
	}

	void addAusleihe(Ausleihe ausleihe) {
		if(ausleihe == null){return;}
		ausleihen.add(ausleihe);
		ausleihe.setItem(this);
	}

	public void removeAusleihe(Ausleihe ausleihe) {
		if(ausleihe == null){return;}
		ausleihen.remove(ausleihe);
		ausleihe.setItem(null);
	}
}
