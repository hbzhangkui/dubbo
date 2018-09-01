package com.jt.order.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Update;

import com.jt.common.mapper.SysMapper;
import com.jt.dubbo.pojo.Order;

public interface OrderMapper extends SysMapper<Order>{

	Order findOrderById(String id);
	@Update("update tb_order set status = 6,updated = now() "
			+ "where status = 1 and created < #{dateAgo}")
	void updateStatus(Date dateAgo);
	
}