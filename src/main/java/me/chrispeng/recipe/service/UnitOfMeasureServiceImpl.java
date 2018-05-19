package me.chrispeng.recipe.service;


import me.chrispeng.recipe.commands.UnitOfMeasureCommand;
import me.chrispeng.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import me.chrispeng.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private UnitOfMeasureRepository uomRepository;

	private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

	@Autowired
	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		this.uomRepository = uomRepository;
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
	}


	@Override
	public Set<UnitOfMeasureCommand> listAll() {
		return StreamSupport.stream(uomRepository.findAll().spliterator(), false)
				.map(unitOfMeasureToUnitOfMeasureCommand::convert)
				.collect(Collectors.toSet());
	}
}
