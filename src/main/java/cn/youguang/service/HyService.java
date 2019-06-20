
package cn.youguang.service;

import cn.youguang.entity.Hy;
import cn.youguang.repository.HyDao;
import cn.youguang.repository.UserDao;
import cn.youguang.util.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//Spring Bean的标识.
@Service
@Transactional
public class HyService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private HyDao hyDao;


    /**
     * 保存/更新行业
     *
     * @param hy
     */
    public void save(Hy hy) {
        hyDao.save(hy);
    }


    public Hy findById(Long id) {


        return hyDao.findOne(id);
    }


    public void delete(long id) {
        hyDao.delete(id);
    }


    public void findDataTables(PageInfo pageInfo) {

        Page<Hy> hys;

        String hymc = (String) pageInfo.getCondition().get("hymc");


        if (StringUtils.isNoneBlank(hymc)) {
            hys = hyDao.findByHymc(hymc, pageInfo.getPagerequest());
        } else {


            hys = hyDao.findAll(pageInfo.getPagerequest());
        }

        pageInfo.setRows(hys.getContent());
        pageInfo.setTotal(hys.getTotalElements());

    }

    public List<Hy> findList(Map<String, Object> condition) {
        String hymc = (String) condition.get("hymc");
        String hyfl = (String) condition.get("hyfl");
        if (StringUtils.isNoneBlank(hymc)) {
            return hyDao.findByHymcLike(hymc);
        }if (StringUtils.isNoneBlank(hyfl)) {
            return hyDao.findByHyfl(hyfl);
        }
        else {
            return hyDao.findAll();
        }


    }

    public List<Hy> findByIds(Long[] hys) {
        return hyDao.findByIdIn(Arrays.asList(hys));
    }
}
