package cn.youguang.repository;


import cn.youguang.entity.Kh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface KhDao extends JpaRepository<Kh, Long> {
    List<Kh> findByCslxContainingOrKhmcContainingOrSjhmContainingOrKhbmContaining(String cslx, String khmc, String sjhm, String khbm);

    Page<Kh> findByKhmcContainingAndSjhmContaining(String khmc, String sjhm, Pageable pageable);

    Page<Kh> findByKhmcContaining(String khmc, Pageable pageable);

    Page<Kh> findBySjhmContaining(String sjhm, Pageable pageable);

    Kh findByLoginnameAndLoginpass(String loginname, String loginpass);

    Kh findByLoginname(String loginname);

    Page<Kh> findByKhmcContainingAndSjhmContainingAndType(String khmc, String sjhm, Integer type, Pageable pageable);
}
