package bootcamp.flows;

import bootcamp.HouseContract;
import bootcamp.HouseState;
import net.corda.core.contracts.AttachmentResolutionException;
import net.corda.core.contracts.StateAndRef;
import net.corda.core.contracts.TransactionResolutionException;
import net.corda.core.contracts.TransactionVerificationException;
import net.corda.core.identity.Party;
import net.corda.core.node.ServiceHub;
import net.corda.core.transactions.TransactionBuilder;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;

public class TransactionBuilderWalkthrough {

    public static void main(String[] args) throws TransactionVerificationException, AttachmentResolutionException, TransactionResolutionException {
        StateAndRef inputState = null;
        Party newOwner = null;
        Party oldOwner = null;
        PublicKey newOwnerKey = newOwner.getOwningKey();
        List<PublicKey> requiredSigners = Arrays.asList(newOwnerKey);
        HouseContract houseContract = new HouseContract();
        HouseState outputState = new HouseState("26 Dong Bat, My Dinh 2", newOwner);
        TransactionBuilder txBuilder = new TransactionBuilder(oldOwner);
        Party notary = null;
        txBuilder.setNotary(notary);
        txBuilder
                .addInputState(inputState)
                .addOutputState(outputState, "bootcamp.HouseContract")
                .addCommand(new HouseContract.Register(), requiredSigners);
        ServiceHub hub = null;
        txBuilder.verify(hub);
    }
}
