package com.linln.component.jwt.utlis;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.linln.common.exception.ResultException;
import com.linln.common.utils.HttpServletUtil;
import com.linln.common.utils.ToolUtil;
import com.linln.component.jwt.enums.JwtResultEnums;
import com.linln.modules.system.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 小懒虫
 * @date 2019/4/9
 */
public class JwtUtil {

    /**
     * 生成JwtToken
     * @param username 用户名
     * @param secret 秘钥
     * @param amount 过期天数
     */
    public static String getToken(String username, String secret, int amount){
        User user = new User();
        user.setUsername(username);
        return getToken(user, secret, amount);
    }

    /**
     * 生成JwtToken
     * @param user 用户对象
     * @param secret 秘钥
     * @param amount 过期天数
     */
    public static String getToken(User user, String secret, int amount){
        // 过期时间
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, amount);

        // 随机Claim
        String random = ToolUtil.getRandomString(6);

        // 创建JwtToken对象
        String token="";
        token= JWT.create()
                // 用户名
                .withSubject(user.getUsername())
                // 发布时间
                .withIssuedAt(new Date())
                // 过期时间
                .withExpiresAt(ca.getTime())
                // 自定义随机Claim
                .withClaim("ran", random)
                .sign(getSecret(secret, random));

        return token;
    }

    /**
     * 获取请求对象中的token数据
     */
    public static String getRequestToken(HttpServletRequest request){
        // 获取JwtTokens失败
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResultException(JwtResultEnums.TOKEN_ERROR);
        }
        return authorization.substring(7);
    }

    /**
     * 获取当前token中的用户名
     */
    public static String getSubject(){
        HttpServletRequest request = HttpServletUtil.getRequest();
        String token = getRequestToken(request);
        return JWT.decode(token).getSubject();
    }

    /**
     * 验证JwtToken
     * @param token JwtToken数据
     * @return true 验证通过
     * @exception TokenExpiredException Token过期
     * @exception JWTVerificationException 令牌无效（验证不通过）
     */
    public static void verifyToken(String token, String secret) throws JWTVerificationException {
        String ran = JWT.decode(token).getClaim("ran").asString();
        JWTVerifier jwtVerifier = JWT.require(getSecret(secret, ran)).build();
        jwtVerifier.verify(token);
    }

    /**
     * 生成Secret混淆数据
     */
    private static Algorithm getSecret(String secret, String random){
        String salt = "君不见黄河之水天上来，奔流到海不复回。君不见高堂明镜悲白发，朝如青丝暮成雪。";
        //String salt = "元嘉草草，封狼居胥，赢得仓皇北顾。四十三年，望中犹记，烽火扬州路。可堪回首，佛狸祠下，一片神鸦社鼓。凭谁问、廉颇老矣，尚能饭否？";
        //String salt = "安能摧眉折腰事权贵，使我不得开心颜。";
        //String salt = "大江东去，浪淘尽，千古风流人物。故垒西边，人道是，三国周郎赤壁。乱石穿空，惊涛拍岸，卷起千堆雪。江山如画，一时多少豪杰。";
        return Algorithm.HMAC256(secret + salt + "(ノ￣▽￣)ノ 皮一下" + random);
    }
}
