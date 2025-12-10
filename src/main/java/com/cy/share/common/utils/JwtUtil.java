package com.cy.share.common.utils;

import com.cy.share.common.constant.Jwtconstant;
import io.jsonwebtoken.*;
import org.bouncycastle.util.encoders.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
public class JwtUtil {
    /**
     * 签发JWT
     * @param id
     * @param subject 可以是JSON数据 尽可能少
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        //加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //当前时间的毫秒值
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)  // 主体 一般是用户名
                .setIssuer("cyy")    // 签发者
                .setIssuedAt(now)     // 签发时间
                .signWith(signatureAlgorithm, secretKey); // 签名算法以及密钥
        if (ttlMillis != null) {
            long expMillis = nowMillis + ttlMillis; // 当前时间 + 有效时间
            Date expDate = new Date(expMillis);     // 过期时间
            builder.setExpiration(expDate);         // 设置过期时间
            // 如果不主动配置，Token 将永久有效（除非被主动吊销）
        }
        return builder.compact();
    }

    /**
     * 验证JWT
     * @param jwtStr
     * @return
     */
    public static JwtResult validateJWT(String jwtStr) {
        JwtResult checkResult = new JwtResult();
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {//过期异常
            checkResult.setErrCode(Jwtconstant.JWT_ERRCODE_EXPIRE);
            checkResult.setSuccess(false);
        } catch (SignatureException e) {//验证失败异常
            checkResult.setErrCode(Jwtconstant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrCode(Jwtconstant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    /**
     * 生成加密Key
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(Jwtconstant.JWT_SECRET);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");//第二个参数是密钥起始位置；第三个参数是密钥长度
        return key;
    }

    /**
     * 解析JWT字符串
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
