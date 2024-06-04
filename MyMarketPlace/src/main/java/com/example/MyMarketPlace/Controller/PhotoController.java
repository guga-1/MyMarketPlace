package com.example.MyMarketPlace.Controller;
import com.example.MyMarketPlace.Dto.ItemDto;
import com.example.MyMarketPlace.Service.MarketPlaceService;
import com.example.MyMarketPlace.Service.PhotoService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/photo")
public class PhotoController {

    private final MarketPlaceService marketPlaceService;
    private final PhotoService photoService;

    @Autowired
    public PhotoController(MarketPlaceService marketPlaceService, PhotoService photoService) {
        this.marketPlaceService = marketPlaceService;
        this.photoService = photoService;
    }
    @PostMapping
    public ResponseEntity<?> uploadPhoto(@RequestParam("photo") MultipartFile photo,
                                         @RequestParam("name") String name) {
        try {
            ItemDto itemDto = marketPlaceService.addPhotoToItem(name);
            photoService.storePhoto(photo, itemDto.getPhoto());
            System.out.println("28xazze movida photocontroller");
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPhotos() {
        try {
            val photos = (val) photoService.getAllPhotos();
            return ResponseEntity.ok(photos);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}