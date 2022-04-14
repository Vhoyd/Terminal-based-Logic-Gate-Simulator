package gateLib;

import java.util.List;
import java.util.ArrayList;

//date class used to link inputs and outputs for custom gates
public class InputMatrix {
  private List<Integer> outputGates = new ArrayList<Integer>();
  private List<Integer> inputGates = new ArrayList<Integer>();
  private List<Integer> inputPins = new ArrayList<Integer>();
  
  public int size() {
    return outputGates.size();
  }

  public void addOutputGateIndex(int outputGateIndex) {
    outputGates.add(outputGateIndex);
  }

  public void addInputGateIndex(int inputGateIndex) {
    inputGates.add(inputGateIndex);
  }

  public void addInputPinIndex(int inputPinIndex) {
    inputPins.add(inputPinIndex);
  }

  public List<Integer> getOutputGates() {
    return outputGates;
  }
  
  public List<Integer> getInputGates() {
    return inputGates;
  }

  public List<Integer> getInputPins() {
    return inputPins;
  }

  @Override
  public String toString() {
    String out = "{";
    for (int i = 0; i < size(); i++) {
      out += "{"+ String.valueOf(outputGates.get(i)) + ","+String.valueOf(inputGates.get(i))+", "+String.valueOf(inputPins.get(i))+"}"+(i < size() -1 ? "," : "");
    }
    out += "}";
    return out;
  } 
}
