package com.example.MyMarketPlace.Controller;
import com.example.MyMarketPlace.Dto.ItemDto;
import com.example.MyMarketPlace.Service.MarketPlaceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.val;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/market")
public class MarketPlaceController {
    private final MarketPlaceService marketPlaceService;

    @Autowired
    public MarketPlaceController(MarketPlaceService marketPlaceService) {
        this.marketPlaceService = marketPlaceService;
    }

    @GetMapping("/{page}")
    @ResponseBody
    public ResponseEntity<?> getpageItems(@PathVariable("page") Integer oldpage){
        try {
            int page=oldpage-1;
            val list= (val) marketPlaceService.getItemsByPage(page);
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getItem(@RequestParam("name") String name) {
        try {

            val res = (val) marketPlaceService.getItem(name);
            System.out.println("movida getitem 41 xazi");
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/total")
    @ResponseBody
    public ResponseEntity<?> totalItem() {
        try {
            Long total=marketPlaceService.getQuantity();
            return ResponseEntity.status(HttpStatus.OK).body(total);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<?> checkItem(@RequestParam("name") String name) {
        try {
            boolean result = marketPlaceService.checkItem(name);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> addItem(@RequestBody ItemDto itemDto){
        try {
            val res = (val) marketPlaceService.addItem(itemDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}