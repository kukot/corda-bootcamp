package bootcamp;

import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HouseState implements ContractState {

    private String address;
    private Party owner;

    public static void main(String[] args) {
        Party kevin = null;
        HouseState myHouse = new HouseState("26 Dong Bat, My Dinh 2, Nam Tu Liem District, Hanoi", kevin);
    }

    public HouseState(String address, Party owner) {
        this.address = address;
        this.owner = owner;
    }

    public Party getOwner() {
        return owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    @NotNull
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(owner);
    }

}
