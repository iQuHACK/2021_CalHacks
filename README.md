By Team CalHacks

# Abstract
BloQ is a Minecraft mod that is intended to help students explore the field of quantum computing in a fun and engaging way. You will use craft qubits and quantum gates, which you will assemble into a circuit.

## Motivation
Quantum Computing is a fast-growing and important field with the potential to make breakthroughs in many different areas. It is important to get students interested in the field, so we decided to create an interesting way to learn more about quantum computing through a Minecraft mod. Minecraft is a popular game with crafting mechanics that we think are valuable in teaching the basic concepts of quantum computing.


## Installation
First, you need to install Fabric, a modding tool for Minecraft. Please refer to the [Fabric Tutorial Page](https://fabricmc.net/wiki/install) for set up instructions.  Follow the instructions to set up with their sample project, except use our project instead.

Once you have that installed, it is time to install the Python webserver that handles quantum compilation requests from the Minecraft Mod.

 1. Enter the `python` directory.
 2. Make a venv using `python3 -m venv /path/to/new/virtual/environment` and activate it
 3. Download the wheel file (ends in .whl) from https://ionq.com/links/qiskit-provider
 4. Install the wheel via `pip install ./qiskit-ionq..etc.whl`
 5. Install the other dependenices via: `pip install -r requirements.txt`
 6. Run with `python -m aiohttp.web -H localhost -P 8080 qmc.main:main`
 7. You can now query the API at `localhost:8080`

The python server needs to be running on the computer that is hosting the minecraft server.  Note that you will need to add your own API Key as API_KEY in the qmc folder After that, you are ready to start using the mod.

## Getting Started
You can craft qubits from redstone and ender pearls, and then use qubits to craft various gates by taking your qubit and connecting it into the shape of the desired gate letter with redstone dust. For example, to craft an X gate you would put the qubit in the center with red stone dust on each corner to create an X shape.  You can make a variable rotation block from a gate and a comparator, such as an Rx gate.

Once you have crafted gates, you will measure the result using the yellow measure block, which is crafted from a qubit and a comparator.

(You can also get all of these items from the Quantum Computing tab of the creative inventory)

To make a quantum circuit, you can add a qubit to the top of a gate block, and it will register the gate and transfer the qubit to the bottom.  You can use hoppers and other built-in Minecraft mechanisms to build automated circuits.

Once you have created a circuit, you can deposit the relevant qubits in measure blocks and the Minecraft server will work with the Python code to send your circuit to IonQ.  The result from IonQ will then be stored on the qubit, and will be available as redstone output from a redstone comparator reading the measure block.

The variable gates (such as Rx) are controllable via redstone.  The Rx gate applies a variable-angle X gate from 0 to 15/32  * pi, based on the strength of the redstone input.

The redstone compatibility allows for quantum-classical algorithms to be created, leveraging Minecraft's built-in redstone system for classical programming.  


## Our Process
We use a Python webserver to manage the quantum side of our code, and a Java Minecraft mod to handle the Minecraft portion.

Minecraft items can store arbitrary dat in NBT tags, which are similar to JSON.  Every time a qubit item passes through a gate block, NBT data registering that gate is added to the qubit.  The qubit maintains a list of gates it has passed through until it is measured.

When a measurement occurs, the circuit data stored on the qubit(s) is sent to the Python webserver, where the NBT data is parsed and reformatted into a qiskit circuit.  This circuit is run on the IonQ server, and the result is recieved, parsed, and sent back to Java, where it is stored on the qubit item.  The measure block reads the value from the qubit item, and outputs a corresponding redstone signal, which can be read by a redstone comparator.

We chose this method for building circuits because it takes advantage of built-in minecraft game mechanics for transferring qubits between gates and for classical programming, resulting in a tighter integration.



## Future Directions
* Our minimal proof-of-concept only supports single-qubit circuits, although we have a plan for supporting 2-qubit gates, which is currently partially implemented.  The Control block is in-game, but it currently can't actually be used in making circuits.
* We want to support more gates such as H, T, S, Y, and Ry gates.
* We are considering including quantum gate synthesis in order to optimize longer circuits and to support gates that may not be natively available on the quantum backend.
* The GUI for the gate blocks is currently rough and somewhat buggy, and we would like to improve it.
* Creating a wiki with tutorials and informative articles
