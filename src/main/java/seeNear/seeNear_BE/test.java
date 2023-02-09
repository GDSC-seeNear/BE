package seeNear.seeNear_BE;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class test {
    public static void main (String[] args){
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Date expiration = Date.from(issuedAt.plus(3, ChronoUnit.HOURS));
        System.out.println("fkfkfk"+ expiration);
    }
}
