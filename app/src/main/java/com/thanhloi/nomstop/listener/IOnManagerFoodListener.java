package com.thanhloi.nomstop.listener;

import com.thanhloi.nomstop.model.Food;

public interface IOnManagerFoodListener {
    void onClickUpdateFood(Food food);
    void onClickDeleteFood(Food food);
}
