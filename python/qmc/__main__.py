import sys

from aiohttp import web

from qmc.main import main

web.run_app(main(sys.argv))