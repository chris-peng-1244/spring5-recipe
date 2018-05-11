package me.chrispeng.recipe.converters;

import lombok.Synchronized;
import me.chrispeng.recipe.commands.NotesCommand;
import me.chrispeng.recipe.domain.Note;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Note, NotesCommand> {

	@Synchronized
	@Nullable
	@Override
	public NotesCommand convert(Note source) {
		if (null == source) {
			return null;
		}

		final NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(source.getId());
		notesCommand.setRecipeNotes(source.getRecipeNotes());
		return notesCommand;
	}
}
