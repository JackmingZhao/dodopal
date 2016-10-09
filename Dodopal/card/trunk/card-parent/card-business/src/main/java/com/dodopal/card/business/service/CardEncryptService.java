package com.dodopal.card.business.service;

public interface CardEncryptService {
    public byte[] encryptMode(byte[] src);
    public byte[] decryptMode(byte[] src);
}
