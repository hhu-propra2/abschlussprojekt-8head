package hhu.ausleihservice.web.service;

import hhu.ausleihservice.dataaccess.KaufItemRepository;
import hhu.ausleihservice.databasemodel.KaufItem;
import hhu.ausleihservice.web.responsestatus.ItemNichtVorhanden;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KaufItemService {

	private KaufItemRepository items;

	public KaufItemService(KaufItemRepository itemRep) {
		this.items = itemRep;
	}

	public KaufItem findById(long id) {
		Optional<KaufItem> item = items.findById(id);
		if (!item.isPresent()) {
			throw new ItemNichtVorhanden();
		}
		return item.get();
	}

	List<KaufItem> findAll() {
		return items.findAll();
	}

	public void save(KaufItem newItem) {
		items.save(newItem);
	}

	public void updateById(Long id, KaufItem newItem) {
		KaufItem toUpdate = (KaufItem) findById(id);
		System.out.println("Starting item update");
		toUpdate.setTitel(newItem.getTitel());
		toUpdate.setBeschreibung(newItem.getBeschreibung());
		toUpdate.getAbholort().setBeschreibung(newItem.getAbholort().getBeschreibung());
		toUpdate.setKaufpreis(newItem.getKaufpreis());
		items.save(toUpdate);
	}

	public List<KaufItem> simpleSearch(String query) {
		if (query == null || query.isEmpty()) return findAll();
		return items.simpleSearch(query);
	}

}
