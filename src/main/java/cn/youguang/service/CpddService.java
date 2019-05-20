
package cn.youguang.service;

import cn.youguang.entity.Cpdd;
import cn.youguang.entity.User;
import cn.youguang.entity.Yhhd;
import cn.youguang.repository.*;
import cn.youguang.util.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

//Spring Bean的标识.
@Service
@Transactional
public class CpddService {


    @Autowired
    private CpDao cpDao;


    @Autowired
    private HyDao hyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CpddDao cpddDao;

    @Autowired
    private YhhdDao yhhdDao;


    /**
     * 保存/更新产品
     *
     * @param
     */
    public Cpdd save(Cpdd cpdd) {
        return cpddDao.save(cpdd);
    }


    public void delete(long id) {
        cpddDao.delete(id);
    }


    public void findDataTables(PageInfo pageInfo) {

        Page<Cpdd> cpdds;

        String ddlx = (String) pageInfo.getCondition().get("ddlx");

        Long userId = (Long) pageInfo.getCondition().get("userId");

        Integer ddzt = (Integer) pageInfo.getCondition().get("ddzt");


        if (StringUtils.isNoneBlank(ddlx) && userId != null) {
            User user = userDao.findById(userId);
            cpdds = cpddDao.findByDdlxAndUser(ddlx, user, pageInfo.getPagerequest());

        } else if (StringUtils.isNoneBlank(ddlx)) {
            cpdds = cpddDao.findByDdlx(ddlx, pageInfo.getPagerequest());

        } else {

            cpdds = cpddDao.findAll(pageInfo.getPagerequest());

        }

        pageInfo.setRows(cpdds.getContent());
        pageInfo.setTotal(cpdds.getTotalElements());


    }


    /**
     * @param condition
     * @return
     */
    public List<Cpdd> findList(Map<String, Object> condition) {

        List<Cpdd> cpdds;
        String ddlx = (String) condition.get("ddlx");

        Long userId = (Long) condition.get("userId");

        Integer ddzt = (Integer) condition.get("ddzt");
        Long hdId = (Long) condition.get("hdId");

        String order = (String) condition.get("order");
        String sort = (String) condition.get("sort");
        Sort s = new Sort(sort.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, order);

        if (StringUtils.isNoneBlank(ddlx) && userId != null) {
            User user = userDao.findById(userId);
            cpdds = cpddDao.findByDdlxAndUser(ddlx, user, s);

        } else if (StringUtils.isNoneBlank(ddlx)) {
            cpdds = cpddDao.findByDdlx(ddlx, s);

        } else if (hdId != null) {
            Yhhd yhhd = yhhdDao.findOne(hdId);
            cpdds = cpddDao.findByYhhd(yhhd, s);
        } else {

            cpdds = cpddDao.findAll(s);

        }


        return cpdds;

    }

    public List<Cpdd> findListByHdId(Long hdId) {
        Yhhd yhhd = yhhdDao.findOne(hdId);
        return cpddDao.findByYhhd(yhhd);
    }


    public List<Cpdd> findListOnlyCps(PageInfo pageInfo) {
        return cpddDao.findByCpIsNotNull(pageInfo.getJpaSort());
    }

    public List<Cpdd> findListByDqzt(Integer ydzt) {
        return cpddDao.findByYdzt(ydzt);
    }

    public Cpdd findById(Long cpddId) {
        return cpddDao.findOne(cpddId);
    }

    public List<Map<String, String>> groupByYhhdAndCpUseYdzt(Integer ydzt) {


        List<Cpdd> cpdds = cpddDao.findByYdzt(ydzt);
        List<Yhhd> yhhds = yhhdDao.findAll();


        Long total = 0L;
        List<Map<String, String>> data = new ArrayList<>();
        for (Yhhd yhhd : yhhds) {
            Map<String, String> hdmp = new HashMap<>();
            Long count = cpdds.stream().filter(a -> a.getYhhd() != null).filter(b -> b.getYhhd().getId() == yhhd.getId()).count();
            hdmp.put("count", count + "");
            hdmp.put("hdId", yhhd.getId() + "");
            total += count;
            data.add(hdmp);
        }

        Long count = cpdds.stream().filter(a -> a.getCp() != null).count();
        Map<String, String> cpmp = new HashMap<>();
        cpmp.put("count", count + "");
        cpmp.put("hdId", "6");
        data.add(cpmp);

        Map<String, String> totalmp = new HashMap<>();
        total = total + count;
        totalmp.put("hdId", "0");
        totalmp.put("count", total + "");
        data.add(totalmp);

        return data;


    }


    public void updateYdztToOneUsingIds(Long[] ids) {
        cpddDao.updateYdztToOneUsingIds(Arrays.asList(ids));
    }

    public void updateYdztToOneUsingHdId(Long hdId) {

        cpddDao.updateYdztToOneUsingHdId(hdId);

    }


    public void updateYdztToOneUsingCp() {
        cpddDao.updateYdztToOneUsingCp();
    }

    public void updateRydztToOneUsingIds(Long[] ids) {
        cpddDao.updateRydztToOneUsingCp(Arrays.asList(ids));

    }


    public List<Cpdd> findListByHdIdAndPageInfo(Long hdId, PageInfo pageInfo) {
        Yhhd yhhd = yhhdDao.findOne(hdId);
        return cpddDao.findByYhhd(yhhd, pageInfo.getJpaSort());


    }
}
