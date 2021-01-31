import asyncio
import base64

from aiohttp import web

from qiskit import QuantumCircuit
import numpy as np

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
    parts = [i.split(':') for i in circuit_data.decode().split(',')]
    gates_and_params = [part[1].replace('"', '') for part in parts if len(part)==2 and  (part[0] == 'gatename' or part[0] == 'parameter')]
    data = list(zip(*[iter(gates_and_params)]*2))
    qc = QuantumCircuit(1, 1)
    for (gate, param) in data:
        print(gate, param)
        if gate == 'x':
            qc.x(0)
        else:
            qc.rx(float(param)* np.pi/32, 0)
    qc.measure(0,0)

    print(qc.qasm())
    task = asyncio.create_task(BACKEND.schedule_execution(qc))
    await task
    bit_pattern = task.result()
    """ reverse_qubit_map = {v: int(k) for k, v in qubit_map.items()}
    qubit_values = {reverse_qubit_map[qubit]: bool(int(bit)) for qubit, bit in enumerate(bit_pattern)}
    return web.json_response({'circuit_id': body['circuit_id'], 'qubit_values': qubit_values})
    """
    print(bit_pattern)
    return web.Response(body=bit_pattern)

def main(argv):
    app = web.Application()
    app.add_routes(routes)
    return app