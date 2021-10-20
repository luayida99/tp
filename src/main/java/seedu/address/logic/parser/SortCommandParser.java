package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_VISIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT;
import static seedu.address.logic.parser.SortComparator.SORT_BY_LAST_VISIT;
import static seedu.address.logic.parser.SortComparator.SORT_BY_NEXT_VISIT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_VISIT, PREFIX_LAST_VISIT);

        try {
            if (argMultimap.isAllPresent(PREFIX_LAST_VISIT, PREFIX_VISIT)) {
                throw new ParseException(SortCommand.MESSAGE_INVALID_FLAG);
            } else if (argMultimap.getValue(PREFIX_LAST_VISIT).isPresent()) {
                return new SortCommand(SORT_BY_LAST_VISIT, false);
            } else if (argMultimap.getValue(PREFIX_VISIT).isPresent()) {
                return new SortCommand(SORT_BY_NEXT_VISIT, true);
            } else {
                throw new ParseException(SortCommand.MESSAGE_INVALID_FLAG);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), pe);
        }
    }

}
