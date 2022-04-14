package gateLib;

//class for representing an input or output for a logic gate
public class Pin {
  protected int state;

  public Pin() {
    state = 0;
    Manager.addPin(this);
  }

  public void setState(int state) {
    this.state = state;
  }

  public int getState() {
    return state;
  }

}