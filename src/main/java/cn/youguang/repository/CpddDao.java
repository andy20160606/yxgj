package cn.youguang.repository;

import cn.youguang.entity.Cpdd;
import cn.youguang.entity.User;
import cn.youguang.entity.Yhhd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public interface CpddDao extends JpaRepository<Cpdd, Long> {


    Page<Cpdd> findByDdlxAndUser(String ddlx, User user, Pageable pagerequest);

    Page<Cpdd> findByDdlx(String ddlx, Pageable pagerequest);

    List<Cpdd> findByDdlxAndUser(String ddlx, User user, Sort s);

    List<Cpdd> findByDdlx(String ddlx, Sort s);

    List<Cpdd> findByYhhd(Yhhd hdId, Sort s);

    List<Cpdd> findByCpIsNotNull(Sort jpaSort);

    List<Cpdd> findByYdzt(Integer ydzt);


    @Modifying
    @Query(value = "update Cpdd cpdd set cpdd.ydzt = 1 where cpdd.id in :ids")
    void updateYdztToOneUsingIds(@Param(value = "ids") List<Long> asList);


    @Modifying
    @Query(value = "update Cpdd cpdd set cpdd.ydzt = 1 where cpdd.ydzt = 0 and cpdd.yhhd.id = :hdId  ")
    void updateYdztToOneUsingHdId(@Param(value = "hdId") Long hdId);

    @Modifying
    @Query(value = "update Cpdd cpdd set cpdd.ydzt = 1 where cpdd.ydzt = 0 and cpdd.cp.id is not null ")
    void updateYdztToOneUsingCp();

    @Modifying
    @Query(value = "update Cpdd cpdd set cpdd.rydzt = 1 where cpdd.id in :ids")
    void updateRydztToOneUsingCp(@Param(value = "ids") List ids);


    List<Cpdd> findByYhhd(Yhhd yhhd);
}
