import asyncio

from aiohttp import web

from qiskit import QuantumCircuit

from qmc.backend import SimulatorBackend, QPUBackend
from qmc.synthesize import synthesize

routes = web.RouteTableDef()

@routes.get('/')
async def hello(request):
    return web.Response(text="Hello, Minecraft")

@routes.post('/compile')
async def compile(request):
    body = await request.json()
    backend = SimulatorBackend() if body['backend'] == 'simulator' else QPUBackend()
    task = asyncio.create_task(backend.schedule_compilation(QuantumCircuit.from_qasm_str(body['qasm'])))
    await task
    return web.json_response(task.result().get_counts())

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