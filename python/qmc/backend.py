from qiskit import Aer

from qiskit_ionq_provider import IonQProvider 

import asyncio
from pathlib import Path

from qmc.utils import to_thread

with open(Path(__file__).parent / "API_KEY") as key_file:
    key = key_file.read().strip()

provider = IonQProvider(token=key)

class Backend:
    """The base Backend class"""

def _run_ionq_simulator(qc):
    backend = provider.get_backend("ionq_simulator")
    job = backend.run(qc, shots=1000)
    job_id_bell = job.job_id()
    result = job.result()
    return result

class SimulatorBackend(Backend):
    async def schedule_compilation(self, qc):
        result = await to_thread(_run_ionq_simulator, qc)
        return result

def _run_ionq_qpu(qc):
    backend = provider.get_backend("ionq_qpu")
    job = backend.run(qc)
    job_id_bell = job.job_id()
    result = job.result()
    return result

class QPUBackend(Backend):
    async def schedule_compilation(self, qc):
        result = await to_thread(_run_ionq_qpu, qc)
        return result