package de.bahr;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import de.bahr.order.Item;
import de.bahr.order.Volumes;
import de.bahr.order.VolumesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by michaelbahr on 18/07/16.
 */
@Component
public class DataStore {

    @Autowired
    private VolumesRepository repository;

    private static final String URL = "https://crest-tq.eveonline.com/";

    private HashMap<String, Item> data = new HashMap<>();
    private HashMap<String, String> itemUrls = new HashMap<>();

    private boolean DEV_MODE = false;

    private boolean initialized = false;

    public void init() throws Exception {
        String inventoryTypesUrl = getInventoryTypeUrl();

        BasicDBList itemList = new BasicDBList();
        BasicDBObject itemResult = Http.asJson(inventoryTypesUrl, "GET");
        itemList.addAll((Collection<?>) itemResult.get("items"));

        if (!DEV_MODE) {
            while (hasNext(itemResult)) {
                inventoryTypesUrl = (String) ((BasicDBObject) itemResult.get("next")).get("href");
                System.out.println(inventoryTypesUrl);
                itemResult = Http.asJson(inventoryTypesUrl, "GET");
                itemList.addAll((Collection<?>) itemResult.get("items"));
            }
        }

        for (int i = 0; i < itemList.size(); i++) {

            BasicDBObject obj = (BasicDBObject) itemList.get(i);
            String name = (String) obj.get("name");
            String itemHref = (String) obj.get("href");

            itemUrls.put(name, itemHref);


            if (i % 2500 == 0) {
                System.out.println(i + "/" + itemList.size());
            }
        }

        System.out.println("Loading complete.");
    }

    private String getInventoryTypeUrl() throws Exception {
        BasicDBObject root = Http.asJson(URL, "GET");
        BasicDBObject inventory = (BasicDBObject) root.get("itemTypes");
        return (String) inventory.get("href");
    }

    private boolean hasNext(BasicDBObject itemResult) {
        Object next = itemResult.get("next");
        return next != null;
    }

    private void add(Item item) {
        data.put(item.getName(), item);
    }

    private Item load(String itemName) {

        String href = itemUrls.get(itemName);
        if (href == null) {
            return null;
        }
        BasicDBObject itemData;
        try {
            itemData = Http.asJson(href, "GET");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // todo: when repackaged volume is available through crest, use that
        Long typeId = itemData.getLong("id");

        List<Volumes> volumes = repository.findAll().stream().filter(item -> item.getTypeid().equals(typeId)).collect(Collectors.toList());

        // volumes out of repository have priority over volumes from crest
        Double volume;
        if (volumes.isEmpty() || volumes.get(0) == null) {
            // if we didn't find anything above, take the crest data
            volume = Double.valueOf(itemData.get("volume").toString());
        } else {
            volume = volumes.get(0).getVolume();
        }

        Item item = new Item(itemName, volume);
        add(item);

        return item;
    }

    public Item find(String itemName) throws Exception {
        // todo: move this somewhere else later (or put it in the database somehow (through another service))
        if (!initialized) {
            init();
            initialized = true;
        }

        Item result = data.get(itemName);
        if (null != result) {
            return result;
        } else {
            return load(itemName);
        }
    }
}
