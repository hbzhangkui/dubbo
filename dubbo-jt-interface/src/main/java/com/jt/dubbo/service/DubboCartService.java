package com.jt.dubbo.service;

import java.util.List;

import com.jt.dubbo.pojo.Cart;

public interface DubboCartService {
	//通过userId查询购物车列表
	List<Cart> findCartListByUserId(Long userId);

	void saveCart(Cart cart);

	void updateCartNum(Cart cart);

	void deleteCart(Cart cart);
}
