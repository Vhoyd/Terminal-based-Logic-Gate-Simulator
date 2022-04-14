package gateLib;

import java.util.ArrayList;
import java.util.List;

//represents a logic gate
public class Gate {
  protected String name;
  protected GateUpdater updater;
  protected List<Pin> pins;
  protected Pin outputPin;
  protected List<Pin> inputPins = new ArrayList<Pin>();

  public Gate(String name, int inputPinCount, GateUpdater updater, boolean registerToManager) {
    for (int i = 0; i < inputPinCount; i++) {
      this.inputPins.add(new Pin());
    }
    this.name = name;
    pins = inputPins;
    outputPin = new Pin();
    this.updater = updater;
    if (registerToManager) Manager.addGate(this);//makes gate visible to main class for cloning 
  }

  private Gate(Gate toCopy) {
    //new pins are created so as to not share pin states between gates
    for (int i = 0; i < toCopy.getInputPins().size(); i++) {
      inputPins.add(new Pin());
    }
    name = toCopy.getName();
    pins = inputPins;
    outputPin = new Pin();
    updater = toCopy.getUpdater();
  }

  public Pin getPin(int index) {
    return pins.get(index);
  }

  public void setPin(int index, Pin pin) {
    if (index == pins.size()) {
      pins.add(pin);
    } else {
      pins.set(index, pin);
    }
  }

  public int getState() {
    return outputPin.getState();
  }

  public void setState(int state) {
    outputPin.setState(state);
  }

  public Pin getOutput() {
    return outputPin;
  }

  public String getName() {
    return name;
  }

  public void update() {
    updater.update(this);
  }

  public List<Pin> getInputPins() {
    return inputPins;
  }

  public Gate clone() {
    return new Gate(this);
  }

  public GateUpdater getUpdater() {
    return updater;
  }
}