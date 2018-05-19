package me.chrispeng.recipe.service;

import me.chrispeng.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
	Set<UnitOfMeasureCommand> listAll();
}
