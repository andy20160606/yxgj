package cn.youguang.repository;

import cn.youguang.entity.Cp;
import cn.youguang.entity.Hy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CpDao extends JpaRepository<Cp, Long> {


    List<Cp> findByCpmcLike(String cpmc);

    Page<Cp> findByCpmcLike(String cpmc, Pageable pageable);
    List<Cp> findByCpmcLike(String cpmc, Sort s);

    Page<Cp> findByCpmcLikeAndHysIn(String cpmc, List<Hy> hys, Pageable pageable);

    Page<Cp> findByHysIn(List<Hy> hys, Pageable pagerequest);

    List<Cp> findByCpmcLikeAndHysIn(String cpmc, List<Hy> hys, Sort s);

    List<Cp> findByHysIn(List<Hy> hys, Sort s);

    Page<Cp> findByCplbLike(String cplb, Pageable pageable);


}
