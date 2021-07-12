package bootcamp;

import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;

import java.util.List;

public class ContainerState implements ContractState {

    private String content;
    private int width;
    private int height;
    private int depth;
    private Party owner;
    private Party carrier;

    public ContainerState(String content, int width, int height, int depth, Party owner, Party carrier) {
        this.content = content;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.owner = owner;
        this.carrier = carrier;
    }

    public static void main(String[] args) {
        Party fastLogistic = null;
        Party fastShip = null;
        ContainerState containerState = new ContainerState(
                "Corda training",
                200,
                100,
                500,
                fastLogistic,
                fastShip
        );
    }

    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(owner, carrier);
    }
}
