package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper  setmealMapper;
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //判断当前加入购物车的商品是否已经存在了

        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userid = BaseContext.getCurrentId();
        shoppingCart.setUserId(userid);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);


        //如果已经存在了，只需要将数量加一
        if(list != null && list.size() > 0){
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1 );
            shoppingCartMapper.updateById(cart);
        }else{
            Long dishId = shoppingCartDTO.getDishId();
            //如果不存在，需要插入一条购物车数据
            if(dishId != null){
                //加入的是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            }else{
                //加入的是套餐
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal = setmealMapper.getById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCart> list() {
        Long userid = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart
                .builder()
                .id(userid)
                .build();
        shoppingCartMapper.list(shoppingCart);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;

    }

    @Override
    public void clean() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.clean(userId);
    }

    @Override
    public void delete(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if(list.isEmpty()){
            return;
        }
        ShoppingCart item = list.get(0);
        if(item.getNumber() > 1){
            item.setNumber(item.getNumber() - 1);
            shoppingCartMapper.updateById(item);
        }else if(list.get(0).getNumber() > 0){
            shoppingCartMapper.delete(item);
        }
    }
}
