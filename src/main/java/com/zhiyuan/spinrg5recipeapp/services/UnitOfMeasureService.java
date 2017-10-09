package com.zhiyuan.spinrg5recipeapp.services;

import com.zhiyuan.spinrg5recipeapp.commands.UnitOfMeasureCommand;

import java.util.List;
import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUom();
}
