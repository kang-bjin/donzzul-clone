package com.ossproj.donjjul.dto;

import com.ossproj.donjjul.enums.CharacterStage;

public class CharacterResponse {
    private int donationPoints;
    private CharacterStage characterStage;

    // Jackson 등 리플렉션용 기본 생성자
    public CharacterResponse() {}

    // 파라미터 생성자 추가
    public CharacterResponse(int donationPoints, CharacterStage characterStage) {
        this.donationPoints = donationPoints;
        this.characterStage = characterStage;
    }

    // getter만 있어도 JSON serialization 가능
    public int getDonationPoints() {
        return donationPoints;
    }

    public CharacterStage getCharacterStage() {
        return characterStage;
    }
}
