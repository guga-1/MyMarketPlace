package com.example.MyMarketPlace.Service;
import com.example.MyMarketPlace.Dto.ItemDtoList;
import com.example.MyMarketPlace.Model.Item;
import com.example.MyMarketPlace.Repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.val;
import com.example.MyMarketPlace.Dto.ItemDto;
import com.example.MyMarketPlace.Util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketPlaceService {
    private static final String PHOTO_PREFIX = "-photo";
    private final ItemRepository repo;
    private final DtoConverter converter;
    @Autowired
    public MarketPlaceService(ItemRepository repo, DtoConverter converter){
        this.repo=repo;
        this.converter=converter;
    }
    public ItemDto getItem(String name){
        val item= (val) repo.findByName(name).orElseThrow(() -> new EntityNotFoundException("Item was not found"));
        return converter.convert((Item) item);
    }
    public boolean checkItem(String name) {
        try {
            val item = (val) repo.findByName(name)
                    .orElseThrow(() -> new Exception("Item not found with name: " + name));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    public ItemDtoList getItemsByPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 6);
        val item = (val) repo.findAll(pageable).stream().toList();
        return converter.convert((List<Item>) item);
    }
    public ItemDto addItem(ItemDto itemDto){
        repo.save(converter.convert(itemDto));
        return itemDto;
    }
    public Long getQuantity(){
        return repo.count();
    }
    public ItemDto addPhotoToItem(String name) {
        Item item = repo.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Item entity with name " + name + " not found"));
        item.setPhoto(name+PHOTO_PREFIX);
        return converter.convert(repo.save(item));
    }
}
