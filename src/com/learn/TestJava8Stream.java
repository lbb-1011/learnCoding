package com.learn;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * java8 新特性 stream-流
 *
 * @author 刘背背【豆芽:18053498】
 * @date 2019/12/6 16:58
 */
public class TestJava8Stream {

    public static void main(String[] args) {
        //准备数据
        List<VipCustomer> vipCustomerList = ListData.getListData();
        //====================================================================================
        //数据流在管道中传输，每个管道节点对流进行处理（筛选、排序、聚合等），最后得到最终结果
        //List<Integer> transactionsIds =
        //        widgets.stream()
        //                .filter(b -> b.getColor() == RED)
        //                .sorted((x,y) -> x.getWeight() - y.getWeight())
        //                .mapToInt(Widget::getWeight)
        //                .sum();
        //+--------------------+       +------+   +------+   +---+   +-------+
        //| stream of elements +-----> |filter+-> |sorted+-> |map+-> |collect|
        //+--------------------+       +------+   +------+   +---+   +-------+
        //====================================================================================
        //创建流Stream 的集中方式
        //Collections系列集合通过提供了
        //      stream - 为集合创建串行流
        //      parallelStream - 为集合创建并行流
        //====================================================================================
        //forEach() 效率比较    lambda for >   stream for
        // 数据量较少时  普通for效率高于 前两种
        // 数据量巨大时  普通for效率低于 前两种
        // 数据量巨大时 使用parallelStream for
        testForeach();
        //====================================================================================
        //filter()
        List<VipCustomer> list = vipCustomerList.stream().filter(vipCustomer ->
                "001".equals(vipCustomer.getCustNo())
        ).collect(Collectors.toList());
        System.out.println("用filter过滤出custNo为001的用户：" + JSON.toJSONString(list));
        //====================================================================================
        //sorted()
        testSorted(vipCustomerList);
        //====================================================================================
        //reduce
        //====================================================================================
        //find
        //====================================================================================
        //match
        //====================================================================================
        //map
        //====================================================================================
        //collect Collectors
        //====================================================================================
        //summaryStatistics()
        //====================================================================================
        //

    }

    public static void testForeach() {
        System.out.println("=============for循环耗时比较↓========================");
        List<String> strList = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            strList.add(String.valueOf(i));
        }
        long normal_startTime = System.currentTimeMillis();
        for (String str : strList) {
            str.concat("===");
        }
        System.out.println("普通for循环耗时：" + (System.currentTimeMillis() - normal_startTime) + "毫秒");
        long stream_forEach_startTime = System.currentTimeMillis();
        strList.stream().forEach(c -> {
            c.concat("===");
        });
        System.out.println("Stream_ForEach耗时：" + (System.currentTimeMillis() - stream_forEach_startTime) + "毫秒");
        long parallelStream_forEach_startTime = System.currentTimeMillis();
        strList.parallelStream().forEach(c -> {
            c.concat("===");
        });
        System.out.println("Parallel_Stream_ForEach耗时：" + (System.currentTimeMillis() - parallelStream_forEach_startTime) + "毫秒");
        long lambda_startTime = System.currentTimeMillis();
        strList.forEach(c -> {
            c.concat("===");
        });
        System.out.println("Lambda_ForEach耗时：" + (System.currentTimeMillis() - lambda_startTime) + "毫秒");
        System.out.println("=============for循环耗时比较↑========================");
    }

    public static void testSorted(List<VipCustomer> vipCustomerList) {
        System.out.println("=============stream的一些排序方式↓========================");
        Integer[] intNums = new Integer[]{1, 3, 8, 5, 7, 4};
        List<Integer> list = Arrays.asList(intNums);
        System.out.println("【排序】排序之前:" + JSON.toJSONString(list));
        List<Integer> rlt1 = list.stream().sorted().collect(Collectors.toList());
        System.out.println("【排序】默认升序:" + JSON.toJSONString(rlt1));
        List<Integer> rlt2 = list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println("【排序】倒叙之后:" + JSON.toJSONString(rlt2));
        List<VipCustomer> rlt3 = vipCustomerList.stream().sorted(Comparator.comparing(VipCustomer::getCustNo)).collect(Collectors.toList());
        System.out.println("【排序】按对象的某个属性默认升序：" + JSON.toJSONString(rlt3));
        List<VipCustomer> rlt4 = vipCustomerList.stream().sorted(Comparator.comparing(VipCustomer::getCustNo).reversed()).collect(Collectors.toList());
        System.out.println("【排序】按对象的某个属性倒叙排列：" + JSON.toJSONString(rlt4));
        System.out.println("=============stream的一些排序方式↑========================");
    }
}

//数据准备
class ListData {
    private static List<VipCustomer> listData;

    static {
        VipCustomer customer1 = new VipCustomer("001", "张三", "18600010203", "1", null);
        VipCustomer customer4 = new VipCustomer("001", "张弛有度", "18600010203", "1", null);
        VipCustomer customer2 = new VipCustomer("002", "李四", "18600020304", "2", 2);
        VipCustomer customer3 = new VipCustomer("003", "王五", "18600010405", "3", 8);
        listData = asList(customer1, customer2, customer3, customer4);
    }

    public static List<VipCustomer> getListData() {
        return listData;
    }


}

@Setter
@Getter
class VipCustomer {
    /**
     * 会员编码
     */
    private String custNo;
    /**
     * 会员名称
     */
    private String custName;
    /**
     * 会员电话
     */
    private String custPhone;
    /**
     * 会员类型
     * 1-普通会员
     * 2-vip会员
     */
    private String custType;
    /**
     * 会员等级 1、2、3……
     */
    private Integer vipLevel;

    VipCustomer(String custNo, String custName, String custPhone, String custType, Integer vipLevel) {
        this.custNo = custNo;
        this.custName = custName;
        this.custPhone = custPhone;
        this.custType = custType;
        this.vipLevel = vipLevel;
    }
}
