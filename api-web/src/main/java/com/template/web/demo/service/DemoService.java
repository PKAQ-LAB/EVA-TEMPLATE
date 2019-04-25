package com.template.web.demo.service;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.template.web.demo.entity.DemoEntity;
import com.template.web.demo.mapper.DemoMapper;
import io.nerv.core.mvc.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * CRUD示例
 */
@Service
public class DemoService extends BaseService<DemoMapper, DemoEntity> {

    public String aes(String demoText) {
        // 密钥
        String sec = "fe0e3ff747ee587a512954073481dd70";

        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, HexUtil.decodeHex(sec));
        // 加密
        return aes.encryptHex(demoText);
    }
}
