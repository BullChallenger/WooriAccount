package io.woori.account.wooriaccount.encryption;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptImpl implements EncryptHelper{
    @Override
    public String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt()); // hash 할때 gensalt() 메소드로 같은 password 라도 Hash한 결과 다르게끔 (랜덤한 salt)
    }

    @Override
    public boolean isMatch(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

}
