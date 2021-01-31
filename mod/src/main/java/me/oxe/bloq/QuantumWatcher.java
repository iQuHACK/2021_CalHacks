package me.oxe.bloq;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

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

    public static void sendQubitData(ItemStack stack) {
        CompoundTag tag = stack.getSubTag("bloq:quantum_data");
        ListTag circuit = tag.getList("circuit", 10);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost request = new HttpPost("http://localhost:8080/execute");
            StringEntity params = new StringEntity(String.format("{\"circuit_data\": \"%s\" }", Base64.getEncoder().encodeToString(circuit.toString().getBytes())));
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpclient.execute(request);
            String str = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8.name());
            System.out.println(str);
            stack.putSubTag("result", StringTag.of(str));
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void wipeCircuit(ItemStack qubit) {
        qubit.removeSubTag("bloq:quantum_data");
    }
}