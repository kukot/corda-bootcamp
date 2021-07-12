package bootcamp;

import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.Party;
import net.corda.core.transactions.LedgerTransaction;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.List;

public class HouseContract implements Contract {
    @Override
    public void verify(@NotNull LedgerTransaction tx) throws IllegalArgumentException {
// House contract accept only transaction with extract 1 command
        if (tx.getCommands().size() != 1) {
            throw new IllegalArgumentException("There must be exactly 1 command in house contract");
        }
        List<PublicKey> requiredSigners = tx.getCommands().get(0).getSigners();
        if (tx.getCommands().get(0).getValue() instanceof Register) {
            // TODO: 7/10/21 implement logic for registering house
            //shape constraint: like number of input, output
            if (tx.getInputs().size() != 0) {
                throw new IllegalArgumentException("Registering a house must have no input");
            }
            if (tx.getOutputs().size() != 1) {
                throw new IllegalArgumentException("Registering a house must have exact 1 output");
            }

            // content constraint
            ContractState outputState = tx.getOutput(0);
            if (!(outputState instanceof HouseState)) {
                throw new IllegalArgumentException("Output of transaction must be House State");
            }
            HouseState outputHouseState = (HouseState) outputState;
            if (outputHouseState.getAddress() == null || outputHouseState.getAddress().length() < 10) {
                throw new IllegalArgumentException("Invalid Output house address");
            }
            if ("Brazil".equals(outputHouseState.getOwner().getName().getCountry())) {
                throw new IllegalArgumentException("Cannot register Brazilian owner");
            }
            // required signer constraint
            if (!requiredSigners.contains(outputHouseState.getOwner().getOwningKey())) {
                throw new IllegalArgumentException("House owner must sign registration");
            }
        } else if (tx.getCommands().get(0).getValue() instanceof Transfer) {
            // TODO: 7/10/21 implement logic for transfering house
            // shape constraints
            if (tx.getInputs().size() != 1) {
                throw new IllegalArgumentException("House transfer must have exact 1 input");
            }
            if (tx.getOutputs().size() != 1) {
                throw new IllegalArgumentException("House transfer must have exact 1 output");
            }

            // content constraint
            if (!((tx.getInput(0)) instanceof HouseState)) {
                throw new IllegalArgumentException("Input must be House State");
            }
            if (!(tx.getOutput(0) instanceof HouseState)) {
                throw new IllegalArgumentException("Output must be House State");
            }
            HouseState inputState = (HouseState) tx.getInput(0);
            HouseState outputState = (HouseState) tx.getOutput(0);
            if (!inputState.getAddress().equals(outputState.getAddress())) {
                throw new IllegalArgumentException("Input and output must have the same address");
            }
            if (!(inputState.getOwner().equals(outputState.getOwner()))) {
                throw new IllegalArgumentException("Owner must change after transfer");
            }
            // signer constraint
            Party oldOwner = inputState.getOwner();
            Party newOwner = outputState.getOwner();
            if (!(requiredSigners.contains(oldOwner.getOwningKey()) && requiredSigners.contains(newOwner.getOwningKey()))) {
                throw new IllegalArgumentException("Both old owner & new owner must have signed the transfer");
            }
        } else {
            throw new IllegalArgumentException("Command type not supported");
        }
    }

    public static class Register implements CommandData {}
    public static class Transfer implements CommandData {}
}
