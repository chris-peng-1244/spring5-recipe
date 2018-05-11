package me.chrispeng.recipe.converters;

import lombok.Synchronized;
import me.chrispeng.recipe.commands.UnitOfMeasureCommand;
import me.chrispeng.recipe.domain.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasureCommand convert(UnitOfMeasure source) {
		if (null == source) {
			return null;
		}

		final UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
		unitOfMeasureCommand.setId(source.getId());
		unitOfMeasureCommand.setDescription(source.getDescription());
		return unitOfMeasureCommand;
	}
}
