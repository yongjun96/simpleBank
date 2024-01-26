package kimshop.kimcoding.config.jwt;

public interface JwtVO {

    // SECRET 노출되면 안된다. (클라우드AWS - 환경변수, 파일에 있는 것을 읽을 수도 있음.)
    // 리플래시 토큰 - 나중에 추가해 보자.
    public static final String SECRET = "비밀키"; // HS256
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 만료시간 7일

    public static final String TOKEN_PREFIX = "Bearer "; // 한칸 꼭! 띄어야 함.

    public static final String HEADER = "Authorization";
}
