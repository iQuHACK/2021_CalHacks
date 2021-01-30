# Python webserver

This is a Python webserver that handles quantum compilation requests from the Minecraft Mod.

# Get set up

0. Enter the `python` directory.
1. Make a venv and activate it
2. Download the wheel file (ends in .whl) from https://ionq.com/links/qiskit-provider
3. Install the wheel via `pip install ./qiskit-ionq..etc.whl
4. Install the other dependenices via: `pip install -r requirements.txt`
5. Run with `python -m aiohttp.web -H localhost -P 8080 qmc.main:main`
6. You can now query the API at `localhost:8080`