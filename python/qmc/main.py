import asyncio

from aiohttp import web

from qiskit import QuantumCircuit

from qmc.backend import SimulatorBackend, QPUBackend
from qmc.synthesize import synthesize
from qmc.convert import convert

routes = web.RouteTableDef()

@routes.get('/')
async def hello(request):
    return web.Response(text="Hello, Minecraft")

@routes.post('/compile')
async def compile(request):
    body = await request.json()
    qubit_map = {j: i for (i, j) in enumerate(body['qubits'])}
    qc = convert(body, qubit_map)
    print(qc.qasm())
    backend = SimulatorBackend() if body['backend'] == 'simulator' else QPUBackend()
    task = asyncio.create_task(backend.schedule_compilation(qc))
    await task
    bit_pattern = task.result()
    reverse_qubit_map = {v: int(k) for k, v in qubit_map.items()}
    qubit_values = {reverse_qubit_map[qubit]: bool(int(bit)) for qubit, bit in enumerate(bit_pattern)}
    return web.json_response({'circuit_id': body['circuit_id'], 'qubit_values': qubit_values})

@routes.post('/synthesize')
async def compile(request):
    body = await request.json()
    qasm = body['qasm']
    task = asyncio.create_task(synthesize(qasm, body['id']))
    await task
    return web.json_response({'qasm': task.result(), 'id': body['id']})

def main(argv):
    app = web.Application()
    app.add_routes(routes)
    return app