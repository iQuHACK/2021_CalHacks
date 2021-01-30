import asyncio

import qiskit
from qiskit import QuantumCircuit
from qsearch import Project, utils, assemblers

from qmc.utils import to_thread

backend = qiskit.BasicAer.get_backend('unitary_simulator')

def _synthesize(qasm, id):
    qc = QuantumCircuit.from_qasm_str(qasm)
    job = qiskit.execute(qc, backend)
    LE = job.result().get_unitary()
    BE = utils.endian_reverse(LE)
    with Project(f'ionq_{id}') as p:
        p.add_compilation(f'ionq_{id}', BE)
        p.run()
        res = p.assemble(f'ionq_{id}', assembler=assemblers.ASSEMBLER_IBMOPENQASM)
    return res

async def synthesize(qasm, id):
    res = await to_thread(_synthesize, qasm, id)
    return res

