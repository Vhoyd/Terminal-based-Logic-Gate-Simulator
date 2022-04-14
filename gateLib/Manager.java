package gateLib;

import java.util.List;
import java.util.ArrayList;

//static class for storing gates and pins
public class Manager {
  private static List<Gate> gates = new ArrayList<Gate>();
  private static List<Pin> pins = new ArrayList<Pin>();

  public static List<Gate> getGates() {
    return gates;
  }

  public static List<Pin> getPins() {
    return pins;
  }

  public static boolean addPin(Pin pin) {
    if (!pins.contains(pin)) {
      pins.add(pin);
      return true;
    }
    return false;
  }

  public static boolean removePin(Pin pin) {
    if (pins.contains(pin)) {
      pins.remove(pin);
      return true;
    }
    return false;
  }

  public static boolean addGate(Gate gate) {
    for (Gate g : gates) {
      if (g.getName().equals(gate.getName())) return false;
    }
    gates.add(gate);
    return true;
  }

  public static boolean removeGate(Gate gate) {
    if (gates.contains(gate)) {
      gates.remove(gate);
      return true;
    }
    return false;
  }

  public static Gate findFromName(String name) {
    for (Gate gate : gates) {
      if(gate.getName().equals(name)) {
        return gate;
      }
    }
    return null;
  }
}