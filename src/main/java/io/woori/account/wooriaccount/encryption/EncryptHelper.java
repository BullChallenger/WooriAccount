package io.woori.account.wooriaccount.encryption;

public interface EncryptHelper {
    /**
     * 파라매타로 들어온 password를 암호화 하여 길이가 60인 string으로 반환
     * @param password
     * @return
     */
    String encrypt(String password);

    /**
     * 저장된 hash 값과 password가 Match되나 확인
     * @param password
     * @param hashed
     * @return
     */
    boolean isMatch(String password, String hashed);
}
