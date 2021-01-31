package me.oxe.bloq;

import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

public class QuantumWatcher {
    private static QuantumWatcher single_instance = null;

    private QuantumWatcher() {

    }

    public static QuantumWatcher getInstance() {
        if (single_instance == null)
            single_instance = new QuantumWatcher();

        return single_instance;
    }


    public static void appendQubitGate(ItemStack qubit_item, String gatename, int parameter) {
        CompoundTag tag = qubit_item.getOrCreateSubTag("bloq:quantum_data");
        ListTag circuit = tag.getList("circuit", 10);

        CompoundTag newOperation = new CompoundTag();
        newOperation.putString("gatename", gatename);
        newOperation.putInt("parameter", parameter);
        newOperation.putInt("qubit_index", 0);
        newOperation.putInt("num_qubits", 1);
        newOperation.putUuid("UUID", UUID.randomUUID());
    
        circuit.add(newOperation);

        tag.put("circuit", circuit);
    }

    public static void appendTwoQubitGate(ItemStack qubit_0, ItemStack qubit_1, String gatename, int parameter) {
        CompoundTag tag_0 = qubit_0.getOrCreateSubTag("bloq:quantum_data");
        ListTag circuit_0 = tag_0.getList("circuit", 10);
        CompoundTag tag_1 = qubit_1.getOrCreateSubTag("bloq:quantum_data");
        ListTag circuit_1 = tag_1.getList("circuit", 10);

        UUID gate_uuid = UUID.randomUUID();

        CompoundTag newOperation_0 = new CompoundTag();
        newOperation_0.putString("gatename", gatename);
        newOperation_0.putInt("parameter", parameter);
        newOperation_0.putInt("qubit_index", 0);
        newOperation_0.putInt("num_qubits", 2);
        newOperation_0.putUuid("UUID", gate_uuid);

        CompoundTag newOperation_1 = new CompoundTag();
        newOperation_1.putString("gatename", gatename);
        newOperation_1.putInt("parameter", parameter);
        newOperation_1.putInt("qubit_index", 1);
        newOperation_1.putInt("num_qubits", 2);
        newOperation_1.putUuid("UUID", gate_uuid);

        circuit_0.add(newOperation_0);
        circuit_1.add(newOperation_1);

        tag_0.put("circuit", circuit_0);
        tag_1.put("circuit", circuit_1);
    }

    public static void wipeCircuit(ItemStack qubit) {
        qubit.removeSubTag("bloq:quantum_data");
    }
}