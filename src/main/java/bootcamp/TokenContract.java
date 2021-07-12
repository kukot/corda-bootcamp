package bootcamp;

import net.corda.core.contracts.Command;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.identity.Party;
import net.corda.core.transactions.LedgerTransaction;

import static net.corda.core.contracts.ContractsDSL.requireSingleCommand;
import static net.corda.core.contracts.ContractsDSL.requireThat;

import java.security.PublicKey;
import java.util.List;

/* Our contract, governing how our state will evolve over time.
 * See src/main/java/examples/ArtContract.java for an example. */
public class TokenContract implements Contract {
    public static String ID = "bootcamp.TokenContract";


    public void verify(LedgerTransaction tx) throws IllegalArgumentException {
        if (tx.getInputs().size() != 0) {
            throw new IllegalArgumentException("Token command must have no input");
        }
        if (tx.getOutputs().size() != 1) {
            throw new IllegalArgumentException("Token contract must have exact one output");
        }
        if (tx.getCommands().size() != 1) {
            throw new IllegalArgumentException("Token contract requires one command");
        }
        if (!(tx.getOutput(0) instanceof TokenState)) {
            throw new IllegalArgumentException("Output of token contract must be  a Token State");
        }
        if (((TokenState) tx.getOutput(0)).getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction output of the contract must be possitive amount");
        }
        Command<?> command = tx.getCommand(0);
        if (!(command.getValue() instanceof Commands.Issue)) {
            throw new IllegalArgumentException("Transaction command must be issue command");
        }
        List<PublicKey> requiredSigners = command.getSigners();
        Party issuer = ((TokenState) tx.getOutput(0)).getIssuer();
        if (!(requiredSigners.contains((issuer.getOwningKey())))) {
            throw new IllegalArgumentException("Issuer must sign the Token contract");
        }
    }


    public interface Commands extends CommandData {
        class Issue implements Commands {
        }
    }
}
