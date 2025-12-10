package com.cy.share.common.constant;

public class Jwtconstant {
    /**
     * token
     */
    public static final int JWT_ERRCODE_NULL = 4000;        //Token不存在
    public static final int JWT_ERRCODE_EXPIRE = 4001;      //Token过期
    public static final int JWT_ERRCODE_FAIL = 4002;        //验证不通过

    /**
     * JWT
     */
    public static final String JWT_SECRET = "8677df7fc3a34e26a61c034d5ec8245d";    //密匙
    public static final long JWT_TTL = 30 * 60 * 1000;       //token有效时间30min
}
