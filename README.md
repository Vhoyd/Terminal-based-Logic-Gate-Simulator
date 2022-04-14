# Terminal-based-logic-gate-simulator
A simple, minimalistic program that allows you to test inputs for logic gates and even make your own.

# INFO (Read this if you don't know what logic gates are)

> What exactly are logic gates?

You've probably heard of logic gates before. Logic gates are the basic components of how any computer work, and you link them together to make fancier logic circuits. Think of it like legos, each piece is small and simple on its own but when put together they can create huge sets.

> How do they work?
 
Logic gates are simple. They take in inputs, and based on those inputs they give an output. Switches, on and off, 1s and 0s. They're like an if statement in programming.

> Examples?

Sure. Let's start with the basic 3 gates. AND, OR, and NOT. An AND gate will only give an output signal if both input signals are there. So an input of 1 and 1 will give an output of 1, but 0/0, 1/0, and 0/1 will give an output of 0, since one or both inputs is not on. An OR gate, on the other hand, only requires one of the inputs. 1/1, 0/1, and 1/0 all work and give an output of 1, but 0/0 still gives an output of 0. A NOT gate, also called an inverter, gives the opposite state of its singular input. 1 gives 0, 0 gives 1.

> How are these useful?

You can build them together! The output of one gate can be the input of another gate. We can make fancier logic gates, like NAND and NOR, out of the basic 3. An AND gate leading into a NOT gate makes a NAND gate, where the output is 1 unless both inputs are 1, and similarly an OR gate leading into a NOT gate gives a NOR gate, where both inputs must be off to get an output. You can combine these new gates in even more complex circuitry to create all sorts of systems, but I'm not here to give you an engineering lesson.

# INSTRUCTIONS
If the program asks you to pick from a selection of options, type one of those options and hit enter. Case does not matter.    
There will be a lot of reading at some points. Make sure to read it thoroughly unless you want to intentionally miss an important detail.    
Whenever you are in a cycle of some kind (i.e. picking smaller gates to add to a larger gate), type "done" and hit enter to move on from that cycle, or alternatively you can type "cancel" and hit enter to quit whatever you are doing.
