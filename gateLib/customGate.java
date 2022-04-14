package gateLib;

import java.util.List;
import java.util.ArrayList;

//subclass of Gate used to link existing gates together
public class customGate extends Gate {

  private Gate outputGate;
  private int inputCount;
  private int outputGateIndex;
  private int[][] inputReference;
  private InputMatrix gateLinkReference;
  private List<Gate> gates;
  public customGate(String name, List<Gate> gates, int inputCount, int outputGateIndex, int[][] inputReference, InputMatrix gateLinkReference) {
    super(name, inputCount, new GateUpdater(){
      public void update(Gate gate) {
        customGate cast = (customGate)gate;//gate will always be custom
        for (int i = 0; i < cast.gates.size(); i++) {
          Gate g = cast.gates.get(i);
          int hold = g.getState();
          g.update();

          if (hold != g.getState()) {
            i = -1;//go back and update every gate again
          } 
        }
      }
    }, true);//the 'true' is for saving the gate to the Manager
    
    this.inputCount = inputCount;
    this.inputReference = inputReference;
    this.gateLinkReference = gateLinkReference;
    this.outputGateIndex = outputGateIndex;
    this.gates = gates;
    outputGate = gates.get(outputGateIndex);
    outputPin = outputGate.getOutput();
    for (int i = 0; i < gateLinkReference.size(); i++) {
      gates.get(gateLinkReference.getInputGates().get(i)).setPin(gateLinkReference.getInputPins().get(i), gates.get(gateLinkReference.getOutputGates().get(i)).getOutput());//link inputs and outputs part 1
    }
    for (int i = 0; i < inputReference.length; i++) {
      Gate gat = gates.get(inputReference[i][0]);
      gat.setPin(inputReference[i][1], inputPins.get(i));//link inputs and outputs part 2
    }
  }

  private customGate(customGate toCopy) {
    super(toCopy.getName(), toCopy.getInputCount(), toCopy.getUpdater(), false);
    inputCount = toCopy.getInputCount();
    pins = inputPins;
    outputPin = new Pin();
    gates = new ArrayList<Gate>();
    for (Gate gate : toCopy.getSubGates()) {
      gates.add(gate.clone());
    } 
    outputGateIndex = toCopy.getOutputGateIndex();
    outputGate = gates.get(getOutputGateIndex());
    outputPin = outputGate.getOutput();
    inputReference = toCopy.getInputReference();
    gateLinkReference = toCopy.getGateLinkReference();
    updater = toCopy.getUpdater();
    for (int i = 0; i < gateLinkReference.size(); i++) {
    //link pins for copy  part 1
gates.get(gateLinkReference.getInputGates().get(i)).setPin(gateLinkReference.getInputPins().get(i), gates.get(gateLinkReference.getOutputGates().get(i)).getOutput());
    }
    for (int i = 0; i < inputReference.length; i++) {
      Gate gat = gates.get(inputReference[i][0]);
      gat.setPin(inputReference[i][1], inputPins.get(i));//link pints for copy part 2
    }
  }

  public int getInputCount() {
    return inputCount;
  }

  public List<Gate> getSubGates() {
    return gates;
  }

  public int getOutputGateIndex() {
    return outputGateIndex;
  }

  @Override
  public customGate clone() {
    return new customGate(this);
  } 

  @Override
    public void setPin(int index, Pin pin) {
    inputPins.set(index, pin);
    Gate gate = gates.get(inputReference[index][0]);//grab gate holding pin at index
    gate.setPin(inputReference[index][1], inputPins.get(index));//assign gate new pin
    }

  public InputMatrix getGateLinkReference() {
    return gateLinkReference;
  }

  public int[][] getInputReference() {
    return inputReference;
  }
}
 
