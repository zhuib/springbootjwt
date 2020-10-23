package com.aron;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.HashMap;

//@SpringBootTest
class SpringbootjwtApplicationTests {

	@Test
	void contextLoads() {

		HashMap<String, Object> map = new HashMap<>();

		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.SECOND, 100);
		String token = JWT.create()
				.withHeader(map)  // header eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
				.withClaim("userId", 12)  //payload
				.withClaim("username", "aron")
				.withExpiresAt(instance.getTime())  // 指定令牌过期时间
				.sign(Algorithm.HMAC256("!Q@W#E$R"));  //utV8xCc1LYEgbYenHuUefYoR6kN-MBk4b70ztvmYVu0

		System.out.println(token);
	}

	@Test
	public void test() {
		// 创建验证对象
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("!Q@W#E$R")).build();
		// 根据token进行验证
		DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDMzODU2NzksInVzZXJJZCI6MTIsInVzZXJuYW1lIjoiYXJvbiJ9.3RiodzH0KdP2ReQT-NbH7zFUo3Wod_-0PSylS5EFJCY\n");
		// 进行解码，获取相应的值 根据对应的数据类型取值
		System.out.println(verify.getClaim("userId").asInt());
		System.out.println(verify.getClaim("username").asString());
		System.out.println(verify.getExpiresAt());
		// 可以取多个值
//		System.out.println(verify.getClaims().get("userId").asInt());
//		System.out.println(verify.getClaims().get("username").asString());
	}
}
