from qiskit import QuantumCircuit
import numpy as np


def convert(json, qubit_map):
    num_qubits = len(json['qubits'])
    qc = QuantumCircuit(num_qubits, num_qubits)
    for gate in json['circuit']:
        qubits = [qubit_map[qubit] for qubit in gate['qubit_indices']]
        if gate['parameter'] is not None:
            getattr(qc, gate['gate_name'])(int(gate['parameter']) * np.pi/32, *qubits)
        else:
            getattr(qc, gate['gate_name'])(*qubits)
    qc.measure(range(num_qubits), range(num_qubits))
    return qc