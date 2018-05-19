package me.chrispeng.recipe.service;

import me.chrispeng.recipe.commands.UnitOfMeasureCommand;
import me.chrispeng.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import me.chrispeng.recipe.domain.UnitOfMeasure;
import me.chrispeng.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.configuration.MockAnnotationProcessor;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceImplTest {

	@Mock
	private UnitOfMeasureRepository unitOfMeasureRepository;

	private UnitOfMeasureService unitOfMeasureService;

	private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

	public UnitOfMeasureServiceImplTest() {
		this.unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,
				unitOfMeasureToUnitOfMeasureCommand);
	}

	@Test
	public void listAll() {
		// given
		Set<UnitOfMeasure> uoms = new HashSet<>();
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom1.setId(2L);
		uoms.add(uom1);
		uoms.add(uom2);

		// when
		when(unitOfMeasureRepository.findAll()).thenReturn(uoms);

		Set<UnitOfMeasureCommand> uomList = unitOfMeasureService.listAll();
		assertEquals(2, uomList.size());
		verify(unitOfMeasureRepository, times(1)).findAll();
	}
}