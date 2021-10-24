package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTH_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LANGUAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_VISIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCURRENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Frequency;
import seedu.address.model.person.Occurrence;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_LANGUAGE + "LANGUAGE "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_LAST_VISIT + "LAST_VISIT] "
            + "[" + PREFIX_VISIT + "VISIT] "
            + "[" + PREFIX_FREQUENCY + "FREQUENCY " + PREFIX_OCCURRENCE + "OCCURRENCE] "
            + "[" + PREFIX_HEALTH_CONDITION + "HEALTH_CONDITION]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_LANGUAGE + "English "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_LAST_VISIT + "2021-07-28 12:00 "
            + PREFIX_VISIT + "2021-11-30 18:30 "
            + PREFIX_HEALTH_CONDITION + "diabetes "
            + PREFIX_HEALTH_CONDITION + "dementia";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_INVALID_OPTIONAL_FREQUENCY_FLAG =
            "Frequency cannot be empty for multiple occurrence.";
    public static final String MESSAGE_INVALID_OPTIONAL_VISIT_FLAG =
            "Visit cannot be empty for non-empty frequency or multiple occurrence";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        // If visit field is not present, occurrence should not be more than one and frequency should be empty
        // If not, throw CommandException
        if (toAdd.hasInvalidFrequencyOccurrence()) {
            throw new CommandException(MESSAGE_INVALID_OPTIONAL_VISIT_FLAG);
        }

        Optional<Frequency> frequency = toAdd.getFrequency();
        Optional<Occurrence> occurrence = toAdd.getOccurrence();

        if (occurrence.get().isMoreThan(1) && frequency.get().equals(Frequency.EMPTY)) {
            throw new CommandException(MESSAGE_INVALID_OPTIONAL_FREQUENCY_FLAG);
        }


        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
