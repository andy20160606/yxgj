package cn.youguang.repository;


import cn.youguang.entity.Cp;
import cn.youguang.entity.Kh;
import cn.youguang.entity.Khcp;
import cn.youguang.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;


public interface KhcpDao extends JpaRepository<Khcp, Long> {

    List<Khcp> findByKh(Kh kh);



    List<Khcp> findByKhAndCpAndStarttimeBeforeAndStoptimeAfter(Kh kh, Cp cp, Date starttime, Date stoptime);
}
