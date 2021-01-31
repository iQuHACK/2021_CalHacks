import asyncio
import base64

from aiohttp import web

from qiskit import QuantumCircuit

from qmc.backend import SimulatorBackend, QPUBackend
from qmc.convert import convert

routes = web.RouteTableDef()

BACKEND = SimulatorBackend()

@routes.get('/')
async def hello(request):
    return web.Response(text="Hello, Minecraft")

@routes.post('/backend')
async def backend(request):
    global BACKEND
    body = await request.json()
    BACKEND = SimulatorBackend() if body['backend'] == 'simulator' else QPUBackend()
    return web.Response()

@routes.post('/execute')
async def execute(request):
    body = await request.json()
    circuit_data = base64.b64decode(body['circuit_data'])
    return web.Response(body='1')
    qubit_map = {j: i for (i, j) in enumerate(body['qubits'])}
    qc = convert(body, qubit_map)
    print(qc.qasm())
    task = asyncio.create_task(BACKEND.schedule_execution(qc))
    await task
    bit_pattern = task.result()
    reverse_qubit_map = {v: int(k) for k, v in qubit_map.items()}
    qubit_values = {reverse_qubit_map[qubit]: bool(int(bit)) for qubit, bit in enumerate(bit_pattern)}
    return web.json_response({'circuit_id': body['circuit_id'], 'qubit_values': qubit_values})


def main(argv):
    app = web.Application()
    app.add_routes(routes)
    return app