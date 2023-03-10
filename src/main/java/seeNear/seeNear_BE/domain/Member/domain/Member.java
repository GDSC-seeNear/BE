package seeNear.seeNear_BE.domain.Member.domain;

import java.sql.Date;
import java.util.List;

public interface Member {
    int id = 0;

    String phoneNumber = null;
    String name = null;
    Date birth = null;
    double addressLati = 0;
    double addressLongi = 0;
    String addressDetail = null;
    Boolean isConnect = null;
    Integer guardianId = null;

    List<String> emergencyPhoneNumber = null;

    int getId();

    String getName();
}
