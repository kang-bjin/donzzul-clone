package com.ossproj.donjjul.controller;

import com.ossproj.donjjul.dto.CharacterResponse;
import com.ossproj.donjjul.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consumption")
public class ConsumptionController {
    private final CharacterService characterService;

    public ConsumptionController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping("/{userId}/good")
    public ResponseEntity<CharacterResponse> goodConsumption(@PathVariable Long userId) {
        CharacterResponse resp = characterService.processGoodConsumption(userId);
        return ResponseEntity.ok(resp);
    }
}
