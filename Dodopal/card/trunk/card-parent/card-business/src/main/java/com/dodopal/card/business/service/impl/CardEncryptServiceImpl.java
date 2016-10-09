package com.dodopal.card.business.service.impl;

import org.springframework.stereotype.Service;

import com.dodopal.card.business.service.CardEncryptService;
import com.dodopal.card.business.util.SecretUtils;

@Service
public class CardEncryptServiceImpl implements CardEncryptService {

    @Override
    public byte[] encryptMode(byte[] src) {
        return SecretUtils.encryptMode(src);
    }

    @Override
    public byte[] decryptMode(byte[] src) {
        return SecretUtils.decryptMode(src);
    }

}
