
package cn.youguang.service;

import cn.youguang.dto.CpllDto;
import cn.youguang.dto.HdllDto;
import cn.youguang.dto.SjzlDto;
import cn.youguang.entity.*;
import cn.youguang.repository.*;
import cn.youguang.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.activation.ActivationGroup;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.summingInt;

//Spring Bean的标识.
@Service
@Transactional
public class SjrzService {


    @Autowired
    private ZfrzDao zfrzDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SjrzDao sjrzDao;

    @Autowired
    private CpDao cpDao;
    @Autowired
    private YhhdDao yhhdDao;

    public Sjrz save(Sjrz sjrz) {
        return sjrzDao.save(sjrz);
    }


    public Zfrz findById(Long id) {
        return zfrzDao.findOne(id);
    }


    public Sjrz findByCpAndYhhdAndSjrqAndSjxw(Cp cp, Yhhd yhhd, Date sjrq, String sjxw) {
        return sjrzDao.findByCpAndYhhdAndSjrqAndSjxw(cp, yhhd, sjrq, sjxw);
    }


    private Long getZlrs(List<Sjrz> sjrzs) {
        Long count = sjrzs.stream().filter(a -> "ll".equals(a.getSjxw())).collect(Collectors.summingLong(b -> b.getCount()));
        return count == null ? 0 : count;
    }

    private Long getHlrs(List<Sjrz> sjrzs) {
        Long count = sjrzs.stream().filter(a -> a.getYhhd() == null ? false : a.getYhhd().getId() == 1L).collect(Collectors.summingLong(b -> b.getCount()));
        return count == null ? 0 : count;
    }

    private Long getZfsl(List<Sjrz> sjrzs) {
        Long count = sjrzs.stream().filter(a -> a.getSjxw().equals("zf")).collect(Collectors.summingLong(b -> b.getCount()));
        return count == null ? 0 : count;
    }


    @SuppressWarnings(value = "all")
    public Map<String, List> getCpllByMonth(Date date) {
        Date var1start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -2));
        Date var1end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -2));
        Date var2start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -1));
        Date var2end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -1));
        Date var3start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, 0));
        Date var3end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, 0));


        List<Sjrz> sjrzs = sjrzDao.findBySjrqBetween(var1start, var3end);


        List<Sjrz> var1sjrzs = sjrzs.stream().filter(a -> var1start.compareTo(a.getSjrq()) <= 0).filter(a -> var1end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var2sjrzs = sjrzs.stream().filter(a -> var2start.compareTo(a.getSjrq()) <= 0).filter(a -> var2end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var3sjrzs = sjrzs.stream().filter(a -> var3start.compareTo(a.getSjrq()) <= 0).filter(a -> var3end.after(a.getSjrq())).collect(Collectors.toList());
        List<Cp> cps = cpDao.findAll();
        Map<Long, Long> var1cpll = var1sjrzs.stream().filter(b -> b.getCp() != null).collect(Collectors.groupingBy(a -> a.getCp().getId(), Collectors.summingLong(b -> b.getCount() == null ? 0L : b.getCount())));
        Map<Long, Long> var2cpll = var2sjrzs.stream().filter(b -> b.getCp() != null).collect(Collectors.groupingBy(a -> a.getCp().getId(), Collectors.summingLong(b -> b.getCount() == null ? 0L : b.getCount())));
        Map<Long, Long> var3cpll = var3sjrzs.stream().filter(b -> b.getCp() != null).collect(Collectors.groupingBy(a -> a.getCp().getId(), Collectors.summingLong(b -> b.getCount() == null ? 0L : b.getCount())));
        List<String> cpmc = new ArrayList<>();

        List<Long> data1 = new ArrayList<>();
        List<Long> data2 = new ArrayList<>();
        List<Long> data3 = new ArrayList<>();
        List<CpllDto> cpllDtos = new ArrayList<>();
        for (Cp cp : cps) {
//            cpmc.add(cp.getCpmc());
//            data1.add(var1cpll.get(cp.getId()) == null ? 0L : var1cpll.get(cp.getId()));
//            data2.add(var2cpll.get(cp.getId()) == null ? 0L : var2cpll.get(cp.getId()));
//            data3.add(var3cpll.get(cp.getId()) == null ? 0L : var3cpll.get(cp.getId()));

            CpllDto cpllDto = new CpllDto();
            cpllDto.setCpmc(cp.getCpmc());
            cpllDto.setCpId(cp.getId());
            cpllDto.setVar1(var1cpll.get(cp.getId()) == null ? 0L : var1cpll.get(cp.getId()));
            cpllDto.setVar2(var2cpll.get(cp.getId()) == null ? 0L : var2cpll.get(cp.getId()));
            cpllDto.setVar3(var3cpll.get(cp.getId()) == null ? 0L : var3cpll.get(cp.getId()));
            cpllDtos.add(cpllDto);

        }
        cpllDtos.sort(new Comparator<CpllDto>() {
            @Override
            public int compare(CpllDto o1, CpllDto o2) {
                if (o1.getVar3() >= o2.getVar3()) {
                    return 1;
                }
                return -1;

            }
        });

        cpmc = cpllDtos.stream().map(a -> a.getCpmc()).collect(Collectors.toList());
        data1 = cpllDtos.stream().map(a -> a.getVar1()).collect(Collectors.toList());
        data2 = cpllDtos.stream().map(a -> a.getVar2()).collect(Collectors.toList());
        data3 = cpllDtos.stream().map(a -> a.getVar3()).collect(Collectors.toList());

        List<String> months = new ArrayList<>();
        months.add(DateUtil.getYear(var1start) + "年" + (var1start.getMonth() + 1) + "月浏览人数");
        months.add(DateUtil.getYear(var2start) + "年" + (var2start.getMonth() + 1) + "月浏览人数");
        months.add(DateUtil.getYear(var3start) + "年" + (var3start.getMonth() + 1) + "月浏览人数");

        Map<String, List> cpll = new HashMap<>();
        cpll.put("months", months);
        cpll.put("cpmc", cpmc);
        cpll.put("data1", data1);
        cpll.put("data2", data2);
        cpll.put("data3", data3);


        return cpll;
    }


    @SuppressWarnings(value = "all")
    public Map<String, List> getHdllByMonth(Date date) {
        Date var1start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -2));
        Date var1end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -2));
        Date var2start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -1));
        Date var2end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -1));
        Date var3start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, 0));
        Date var3end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, 0));


        List<Sjrz> sjrzs = sjrzDao.findBySjrqBetween(var1start, var3end);


        List<Sjrz> var1sjrzs = sjrzs.stream().filter(a -> var1start.compareTo(a.getSjrq()) <= 0).filter(a -> var1end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var2sjrzs = sjrzs.stream().filter(a -> var2start.compareTo(a.getSjrq()) <= 0).filter(a -> var2end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var3sjrzs = sjrzs.stream().filter(a -> var3start.compareTo(a.getSjrq()) <= 0).filter(a -> var3end.after(a.getSjrq())).collect(Collectors.toList());
        List<Yhhd> yhhds = yhhdDao.findAll();
        Map<Long, Long> var1hdll = var1sjrzs.stream().filter(b -> b.getYhhd() != null).collect(Collectors.groupingBy(a -> a.getYhhd().getId(), Collectors.summingLong(b -> b.getCount() == null ? 0L : b.getCount())));
        Map<Long, Long> var2hdll = var2sjrzs.stream().filter(b -> b.getYhhd() != null).collect(Collectors.groupingBy(a -> a.getYhhd().getId(), Collectors.summingLong(b -> b.getCount() == null ? 0L : b.getCount())));
        Map<Long, Long> var3hdll = var3sjrzs.stream().filter(b -> b.getYhhd() != null).collect(Collectors.groupingBy(a -> a.getYhhd().getId(), Collectors.summingLong(b -> b.getCount() == null ? 0L : b.getCount())));
        List<String> hdmc = new ArrayList<>();

        List<Long> data1 = new ArrayList<>();
        List<Long> data2 = new ArrayList<>();
        List<Long> data3 = new ArrayList<>();
        List<HdllDto> hdllDtos = new ArrayList<>();
        for (Yhhd yhhd : yhhds) {
//            hdmc.add(yhhd.getHdmc());
//            data1.add(var1hdll.get(yhhd.getId()) == null ? 0L : var1hdll.get(yhhd.getId()));
//            data2.add(var1hdll.get(yhhd.getId()) == null ? 0L : var2hdll.get(yhhd.getId()));
//            data3.add(var1hdll.get(yhhd.getId()) == null ? 0L : var3hdll.get(yhhd.getId()));

            HdllDto hdllDto = new HdllDto();
            hdllDto.setHdmc(yhhd.getHdmc());
            hdllDto.setVar1(var1hdll.get(yhhd.getId()) == null ? 0L : var1hdll.get(yhhd.getId()));
            hdllDto.setVar2(var2hdll.get(yhhd.getId()) == null ? 0L : var2hdll.get(yhhd.getId()));
            hdllDto.setVar3(var3hdll.get(yhhd.getId()) == null ? 0L : var3hdll.get(yhhd.getId()));
            hdllDtos.add(hdllDto);
        }

        hdllDtos.sort(new Comparator<HdllDto>() {
            @Override
            public int compare(HdllDto o1, HdllDto o2) {
                if (o1.getVar3() >= o2.getVar3()) {
                    return 1;
                }
                return -1;
            }
        });

        hdmc = hdllDtos.stream().map(a -> a.getHdmc()).collect(Collectors.toList());
        data1 = hdllDtos.stream().map(a -> a.getVar1()).collect(Collectors.toList());
        data2 = hdllDtos.stream().map(a -> a.getVar2()).collect(Collectors.toList());
        data3 = hdllDtos.stream().map(a -> a.getVar3()).collect(Collectors.toList());

        List<String> months = new ArrayList<>();
        months.add(DateUtil.getYear(var1start) + "年" + (var1start.getMonth() + 1) + "月浏览人数");
        months.add(DateUtil.getYear(var2start) + "年" + (var2start.getMonth() + 1) + "月浏览人数");
        months.add(DateUtil.getYear(var3start) + "年" + (var3start.getMonth() + 1) + "月浏览人数");

        Map<String, List> hdll = new HashMap<>();
        hdll.put("months", months);
        hdll.put("hdmc", hdmc);
        hdll.put("data1", data1);
        hdll.put("data2", data2);
        hdll.put("data3", data3);


        return hdll;
    }


    @SuppressWarnings(value = "all")
    public Map<String, List> getZlrsByMonth(Date date) {

        Date var1start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -4));
        Date var1end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -4));
        Date var2start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -3));
        Date var2end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -3));
        Date var3start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -2));
        Date var3end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -2));
        Date var4start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -1));
        Date var4end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -1));
        Date var5start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -0));
        Date var5end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -0));


        List<Sjrz> sjrzs = sjrzDao.findBySjrqBetween(var1start, var5end);


        //     List<Sjrz> var1sjrzs = sjrzs.stream().filter(a -> var1start.compareTo(a.getSjrq()) <= 0).filter(a -> var1end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var2sjrzs = sjrzs.stream().filter(a -> var2start.compareTo(a.getSjrq()) <= 0).filter(a -> var2end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var3sjrzs = sjrzs.stream().filter(a -> var3start.compareTo(a.getSjrq()) <= 0).filter(a -> var3end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var4sjrzs = sjrzs.stream().filter(a -> var4start.compareTo(a.getSjrq()) <= 0).filter(a -> var4end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var5sjrzs = sjrzs.stream().filter(a -> var5start.compareTo(a.getSjrq()) <= 0).filter(a -> var5end.after(a.getSjrq())).collect(Collectors.toList());


        Map<String, List> zlrs = new HashMap<>();
        List<String> months = new ArrayList<>();
        //     months.add(DateUtil.getYear(var1start) + "年" + (var1start.getMonth() + 1) + "月");
        months.add(DateUtil.getYear(var2start) + "年" + (var2start.getMonth() + 1) + "月");
        months.add(DateUtil.getYear(var3start) + "年" + (var3start.getMonth() + 1) + "月");
        months.add(DateUtil.getYear(var4start) + "年" + (var4start.getMonth() + 1) + "月");
        months.add(DateUtil.getYear(var5start) + "年" + (var5start.getMonth() + 1) + "月");
        zlrs.put("months", months);

        List<Long> data = new ArrayList<>();
        //     data.add(getZlrs(var1sjrzs));
        data.add(getZlrs(var2sjrzs));
        data.add(getZlrs(var3sjrzs));
        data.add(getZlrs(var4sjrzs));
        data.add(getZlrs(var5sjrzs));
        zlrs.put("data", data);

        return zlrs;

    }


    @SuppressWarnings(value = "all")
    public Map<String, List> getHlrsByMonth(Date date) {

        Date var1start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -4));
        Date var1end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -4));
        Date var2start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -3));
        Date var2end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -3));
        Date var3start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -2));
        Date var3end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -2));
        Date var4start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -1));
        Date var4end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -1));
        Date var5start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -0));
        Date var5end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -0));


        List<Sjrz> sjrzs = sjrzDao.findBySjrqBetween(var1start, var5end);


        //     List<Sjrz> var1sjrzs = sjrzs.stream().filter(a -> var1start.compareTo(a.getSjrq()) <= 0).filter(a -> var1end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var2sjrzs = sjrzs.stream().filter(a -> var2start.compareTo(a.getSjrq()) <= 0).filter(a -> var2end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var3sjrzs = sjrzs.stream().filter(a -> var3start.compareTo(a.getSjrq()) <= 0).filter(a -> var3end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var4sjrzs = sjrzs.stream().filter(a -> var4start.compareTo(a.getSjrq()) <= 0).filter(a -> var4end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var5sjrzs = sjrzs.stream().filter(a -> var5start.compareTo(a.getSjrq()) <= 0).filter(a -> var5end.after(a.getSjrq())).collect(Collectors.toList());


        Map<String, List> zlrs = new HashMap<>();
        List<String> months = new ArrayList<>();
        //   months.add(DateUtil.getYear(var1start) + "年" + (var1start.getMonth() + 1) + "月");
        months.add(DateUtil.getYear(var2start) + "年" + (var2start.getMonth() + 1) + "月");
        months.add(DateUtil.getYear(var3start) + "年" + (var3start.getMonth() + 1) + "月");
        months.add(DateUtil.getYear(var4start) + "年" + (var4start.getMonth() + 1) + "月");
        months.add(DateUtil.getYear(var5start) + "年" + (var5start.getMonth() + 1) + "月");
        zlrs.put("months", months);

        List<Long> data = new ArrayList<>();
        //  data.add(getHlrs(var1sjrzs));
        data.add(getHlrs(var2sjrzs));
        data.add(getHlrs(var3sjrzs));
        data.add(getHlrs(var4sjrzs));
        data.add(getHlrs(var5sjrzs));
        zlrs.put("data", data);

        return zlrs;

    }


    @SuppressWarnings(value = "all")
    public Map<String, List> getZfslByMonth(Date date) {

        Date var1start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -4));
        Date var1end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -4));
        Date var2start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -3));
        Date var2end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -3));
        Date var3start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -2));
        Date var3end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -2));
        Date var4start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -1));
        Date var4end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -1));
        Date var5start = DateUtil.getTimesMonthmorning(DateUtil.addByMonth(date, -0));
        Date var5end = DateUtil.getTimesMonthnight(DateUtil.addByMonth(date, -0));


        List<Sjrz> sjrzs = sjrzDao.findBySjrqBetween(var1start, var5end);


        //   List<Sjrz> var1sjrzs = sjrzs.stream().filter(a -> var1start.compareTo(a.getSjrq()) <= 0).filter(a -> var1end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var2sjrzs = sjrzs.stream().filter(a -> var2start.compareTo(a.getSjrq()) <= 0).filter(a -> var2end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var3sjrzs = sjrzs.stream().filter(a -> var3start.compareTo(a.getSjrq()) <= 0).filter(a -> var3end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var4sjrzs = sjrzs.stream().filter(a -> var4start.compareTo(a.getSjrq()) <= 0).filter(a -> var4end.after(a.getSjrq())).collect(Collectors.toList());
        List<Sjrz> var5sjrzs = sjrzs.stream().filter(a -> var5start.compareTo(a.getSjrq()) <= 0).filter(a -> var5end.after(a.getSjrq())).collect(Collectors.toList());


        Map<String, List> zlrs = new HashMap<>();
        List<String> months = new ArrayList<>();

        // months.add(DateUtil.getYear(var1start) + "年" + (var1start.getMonth() + 1) + "月");
        months.add(DateUtil.getYear(var2start) + "年" + (var2start.getMonth() + 1) + "月");
        months.add(DateUtil.getYear(var3start) + "年" + (var3start.getMonth() + 1) + "月");
        months.add(DateUtil.getYear(var4start) + "年" + (var4start.getMonth() + 1) + "月");
        months.add(DateUtil.getYear(var5start) + "年" + (var5start.getMonth() + 1) + "月");
        zlrs.put("months", months);

        List<Long> data = new ArrayList<>();
        //    data.add(getZfsl(var1sjrzs));
        data.add(getZfsl(var2sjrzs));
        data.add(getZfsl(var3sjrzs));
        data.add(getZfsl(var4sjrzs));
        data.add(getZfsl(var5sjrzs));
        zlrs.put("data", data);

        return zlrs;

    }


}