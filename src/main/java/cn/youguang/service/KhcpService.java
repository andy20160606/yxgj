
package cn.youguang.service;

import cn.youguang.entity.Kh;
import cn.youguang.entity.Khcp;
import cn.youguang.entity.Zt;
import cn.youguang.repository.HyDao;
import cn.youguang.repository.KhDao;
import cn.youguang.repository.KhcpDao;
import cn.youguang.repository.ZtDao;
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
public class KhcpService {


    @Autowired
    private HyDao hyDao;


    @Autowired
    private ZtDao ztDao;

    @Autowired
    private KhcpDao khcpDao;

    @Autowired
    private KhDao khDao;


    /**
     * 保存/更新客户产品
     *
     * @param khcp
     */
    public void save(Khcp khcp) {
        khcpDao.save(khcp);
    }


    public Khcp findById(Long id) {


        return khcpDao.findOne(id);
    }


    public List<Khcp> findList(Map<String, Object> condition) {
        return khcpDao.findAll();
    }

    public void deleteById(Long id) {
        khcpDao.delete(id);
    }

    public List<Khcp> findByKhId(Long khId) {
        Kh kh = khDao.findOne(khId);
        return khcpDao.findByKh(kh);
    }
}
