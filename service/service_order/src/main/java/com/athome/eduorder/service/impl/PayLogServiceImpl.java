package com.athome.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.athome.eduorder.entity.Order;
import com.athome.eduorder.entity.PayLog;
import com.athome.eduorder.mapper.PayLogMapper;
import com.athome.eduorder.service.OrderService;
import com.athome.eduorder.service.PayLogService;
import com.athome.eduorder.utils.HttpClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-07-03
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    @Override
    public Map createNative(String orderNo) {
        try {
            //1 根据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            Order order = orderService.getOne(wrapper);

            //2 使用map设置生成二维码需要参数
            Map m = new HashMap();
            m.put("appid","wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle()); //课程标题
            m.put("out_trade_no", orderNo); //订单号
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");

            //3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            //执行post请求发送
            client.post();

            //4 得到发送请求返回结果
            //返回内容，是使用xml格式返回
            String xml = client.getContent();

            //把xml格式转换map集合，把map集合返回
            Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);

            //最终返回数据 的封装
            Map map = new HashMap();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
            map.put("code_url", resultMap.get("code_url"));        //二维码地址

            return map;
        }catch(Exception e) {
            throw new RuntimeException("生成二维码失败");
        }

    }

    @Override
    public Map<String, String> checkPayState(String orderNo) {
        Map<String,String> payInfo = null;

        Map payStatus = new HashMap();
        payStatus.put("appid", "wx74862e0dfcf69954");
        payStatus.put("mch_id", "1558950191");
        payStatus.put("out_trade_no", orderNo);
        payStatus.put("nonce_str", WXPayUtil.generateNonceStr());

        try {
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(payStatus,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            String xml = client.getContent();
            payInfo = WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            throw new RuntimeException("支付出错了");
        }

        return payInfo;

    }

    @Override
    public void updatePayState(Map<String, String> payInfo) {
        String orderNo = payInfo.get("out_trade_no");
        QueryWrapper<Order> orderWrapper = new QueryWrapper<>();
        orderWrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(orderWrapper);
        if(order.getStatus().intValue() == 1) {
            return;
        }
        order.setStatus(1);
        orderService.updateById(order);

        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);  //订单号
        payLog.setPayTime(new Date()); //订单完成时间
        payLog.setPayType(1);//支付类型 1微信
        payLog.setTotalFee(order.getTotalFee());//总金额(分)

        payLog.setTradeState(payInfo.get("trade_state"));//支付状态
        payLog.setTransactionId(payInfo.get("transaction_id")); //流水号
        payLog.setAttr(JSONObject.toJSONString(payInfo));
        baseMapper.insert(payLog);
    }
}