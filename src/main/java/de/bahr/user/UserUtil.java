package de.bahr.user;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

/**
 * Created by michaelbahr on 26/11/16.
 */

public class UserUtil {

    public static User getUser(String auth, UserRepository userRepository) {
        String decoded = decode(auth.replace("Basic ", ""));
        String characterId = decoded.split(":")[0];
        return userRepository.findByCharacterId(Long.valueOf(characterId));
    }

    private static String decode(String s) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(s));
    }
}
