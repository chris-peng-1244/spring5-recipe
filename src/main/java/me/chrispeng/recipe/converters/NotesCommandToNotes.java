package me.chrispeng.recipe.converters;

import lombok.Synchronized;
import me.chrispeng.recipe.commands.NotesCommand;
import me.chrispeng.recipe.domain.Note;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Note> {

	@Synchronized
	@Nullable
	@Override
	public Note convert(NotesCommand source) {
		if(source == null) {
			return null;
		}

		final Note notes = new Note();
		notes.setId(source.getId());
		notes.setRecipeNotes(source.getRecipeNotes());
		return notes;
	}
}
