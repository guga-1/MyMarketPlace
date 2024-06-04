package com.example.MyMarketPlace.Util;
import com.example.MyMarketPlace.Dto.ItemDto;
import com.example.MyMarketPlace.Dto.ItemDtoList;
import com.example.MyMarketPlace.Model.Item;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DtoConverter {
    public ItemDto convertToDto(Item item) {
        if (Objects.isNull(item)) {
            return null;
        }
        return ItemDto.builder()
                .name(item.getName())
                .price(item.getPrice())
                .description(item.getDescription())
                .submittionTime(item.getSubmittionTime())
                .photo(item.getPhoto())
                .build();
    }

    public Item convertToEntity(ItemDto itemDto) {
        if (Objects.isNull(itemDto)) {
            return null;
        }
        return Item.builder()
                .name(itemDto.getName())
                .price(itemDto.getPrice())
                .description(itemDto.getDescription())
                .submittionTime(itemDto.getSubmittionTime())
                .photo(itemDto.getPhoto())
                .build();
    }

    public ItemDtoList convertToDtoList(List<Item> itemList) {
        if (Objects.isNull(itemList)) {
            return ItemDtoList.builder().itemDtoList(List.of()).quantity(0).build();
        }
        return ItemDtoList.builder()
                .itemDtoList(itemList.stream().map(this::convertToDto).collect(Collectors.toList()))
                .quantity(itemList.size())
                .build();
    }
}