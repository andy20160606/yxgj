
package cn.youguang.service;

import cn.youguang.entity.*;
import cn.youguang.repository.*;
import cn.youguang.util.PageInfo;
import cn.youguang.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//Spring Bean的标识.
@Service
@Transactional
public class KhService {


    @Autowired
    private KhDao khDao;

    @Autowired
    private CpDao cpDao;

    @Autowired
    private KhcpDao khcpDao;


    /**
     * 保存/更新行业
     *
     * @param
     */
    public void save(Kh kh) {
        khDao.save(kh);
    }


    public List<Kh> findList(Map<String, Object> condition) {
        String keyWords = (String) condition.get("keyWords");

        if (StringUtils.isNoneBlank(keyWords)) {
            return khDao.findByCslxContainingOrKhmcContainingOrSjhmContainingOrKhbmContaining(keyWords, keyWords, keyWords, keyWords);
        } else {
            return khDao.findAll();
        }


    }

    public Kh findById(Long khId) {
        return khDao.findOne(khId);
    }

    public void findDataTables(PageInfo pageInfo) {
        String khmc = (String) pageInfo.getCondition().get("khmc");
        String sjhm = (String) pageInfo.getCondition().get("sjhm");
        Integer type = (Integer) pageInfo.getCondition().get("type");
        Page<Kh> khs;

        if (type != null && StringUtils.isNotBlank(khmc) && StringUtils.isNotBlank(sjhm)) {
            khs = khDao.findByKhmcContainingAndSjhmContainingAndType(khmc, sjhm, type, pageInfo.getPagerequest());
        } else if (type != null && StringUtils.isNotBlank(khmc)) {
            khs = khDao.findByKhmcContainingAndType(khmc, type, pageInfo.getPagerequest());
        } else if (type != null && StringUtils.isNotBlank(sjhm)) {
            khs = khDao.findBySjhmContainingAndType(sjhm, type, pageInfo.getPagerequest());
        } else if (type != null) {
            khs = khDao.findByType(type, pageInfo.getPagerequest());
        } else if (StringUtils.isNotBlank(sjhm) && StringUtils.isNotBlank(khmc)) {
            khs = khDao.findByKhmcContainingAndSjhmContaining(khmc, sjhm, pageInfo.getPagerequest());
        } else if (StringUtils.isNotBlank(khmc)) {
            khs = khDao.findByKhmcContaining(khmc, pageInfo.getPagerequest());
        } else if (StringUtils.isNotBlank(sjhm)) {
            khs = khDao.findBySjhmContaining(sjhm, pageInfo.getPagerequest());
        } else {
            khs = khDao.findAll(pageInfo.getPagerequest());
        }

        pageInfo.finishFromJpaPages(khs);


    }

//    private List<Kh> initKhsOtherCps(List<Kh> khs, List<Cp> cps) {
//
//
//        List<Kh> khList = new ArrayList<>();
//        for (Kh kh : khs) {
//            kh = initKhOtherCps(kh, cps);
//            khList.add(kh);
//        }
//        return khList;
//
//    }


    public List<Cp> getKhExistCps(Long khId) {
        Kh kh = khDao.findOne(khId);
        List<Khcp> khcps = khcpDao.findByKh(kh);
        List<Cp> khExistCps = new ArrayList<>();

        for (Khcp khcp : khcps) {
            if (khcp.getCp() != null) {
                khExistCps.add(khcp.getCp());
            }
        }
        return khExistCps;
    }


    public List<Cp> initKhOtherCps(Long khId) {
        List<Cp> cps = cpDao.findAll();
        List<Cp> khExistCps = getKhExistCps(khId);
        Map<Long, Boolean> map = khExistCps.stream().distinct().collect(Collectors.toMap(a -> a.getId(), a -> true));

        List<Cp> khOtherCps = cps.stream().filter(a -> (map.get(a.getId()) == null)).collect(Collectors.toList());
        return khOtherCps;

    }

    public Kh findByLoginnameAndLoginpass(String loginname, String loginpass) {
        return khDao.findByLoginnameAndLoginpass(loginname, loginpass);
    }

    public Kh findByLoginname(String loginname) {
        return khDao.findByLoginname(loginname);
    }

    public Kh findbyWybsAndLsrzm(@NotNull String khwybs, @NotNull String lsrzm) {

        return khDao.findByWybsAndLsrzm(khwybs, lsrzm);


    }

    public Kh findByWybs(String wybs) {
        return khDao.findByWybs(wybs);
    }
}
