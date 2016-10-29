package de.bahr.user;

/**
 * Created by michaelbahr on 23/10/16.
 */
public class User {

    private Long characterId;
    private String name;
    private String role;

    public User(Long characterId, String role, String name) {
        this.name = name;
        this.characterId = characterId;
        this.role = role;
    }


    public Long getCharacterId() {
        return characterId;
    }

    public String getRole() {
        return role;
    }

    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
