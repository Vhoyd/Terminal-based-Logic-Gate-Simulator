import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import gateLib.Gate;
import gateLib.customGate;
import gateLib.GateUpdater;
import gateLib.InputMatrix;
import gateLib.Manager;
import gateLib.Pin;

class Main {
  
  private static Scanner input = new Scanner(System.in);
  
  @SuppressWarnings("unused")//exists for and, or, and not gate variables
  public static void main(String[] args) {

    //and gate
    GateUpdater ANDUpdater = new GateUpdater(){
      public void update(Gate gate) {
        int state = gate.getPin(0).getState() * gate.getPin(1).getState(); //will only == 1 if both 0 and 1 are 1
        gate.getOutput().setState(state);
      }
    }; 
    
    Gate AND = new Gate("AND", 2, ANDUpdater, true);
    
    //or gate
    GateUpdater ORUpdater = new GateUpdater(){
      public void update(Gate gate) {
        int state = (gate.getPin(0).getState() == 1 || gate.getPin(1).getState() == 1? 1 : 0);//will be 1 if either condition is 1

        gate.getOutput().setState(state);
      }
    }; 
    Gate OR = new Gate("OR", 2, ORUpdater, true);

    //not gate
    GateUpdater NOTUpdater = new GateUpdater(){
      public void update(Gate gate) {
        int state = ((gate.getPin(0).getState()+1)%2);
        gate.getOutput().setState(state);//toggle between 1 and 0
      }
    };
    Gate NOT = new Gate("NOT", 1, NOTUpdater, true);  
    
    while (true) {
      System.out.println("\nFor info on how logic gates work, read the README file.\n\n");
      String check = getInput("Create a gate, run a gate, or remove a gate? (create/run/remove)"); 
      
      if (check.equals("run")) {
        System.out.println("Which gate do you want to run?");
        System.out.print("Existing gates: ");
        for (Gate gate : Manager.getGates()) {
          System.out.print(gate.getName() + " ");
        }
        String name = getInput("");
        Gate toRun = Manager.findFromName(name.toUpperCase());
        if (toRun == null) {
          System.out.println("I don't recognize that gate name.");
          continue;
        }
        int pins = toRun.getInputPins().size();
        System.out.println("This gate has "+String.valueOf(pins)+" inputs. Enter either a 0 or 1 for the sate of each input when running the simulation. For example: ");
        Random rand = new Random();
        for (int i = 0; i < pins; i++) {
          System.out.print(Math.abs(rand.nextInt()) %2);//randomly generate 0 or 1 for each input example
          System.out.print(" ");
        }
        String inputs = getInput("\nType your inputs:");
        List<Integer> inputHandler = new ArrayList<Integer>();
        for (int i = 0; i < inputs.length(); i++) {
          char currentNumber = inputs.charAt(i);
          if (currentNumber == '1' || currentNumber == '0') {
            inputHandler.add(currentNumber - 48);// 0 ascii is 48, 1 is 49
          } 
        }
        if (inputHandler.size() < pins) {
          System.out.println("You didn't give enough inputs!");
          continue;//skip simulating gates to prevent getting stuck or errors
        }
        for (int i = 0; i < pins; i++) {
          //assign pin states to given inputs
          toRun.getPin(i).setState(inputHandler.get(i));
        }
        toRun.update();
        printPins(toRun);
      } else if (check.equals("create")) {
        String name = "";
        System.out.println("When making a new gate, bear in mind it is easier and less confusing to make smaller gates rather than larger ones.");
        boolean validName = false;
        while (!validName) {
          name = getInput("Name this gate.").toUpperCase();
          validName = true;
          for (Gate gate : Manager.getGates()) {
            if (gate.getName().equals(name)) {
              System.out.println("That gate already exists.");
              validName = false;
              break;
            }
          }
        }
        List<Gate> builderGates = new ArrayList<Gate>();
        System.out.println("Now it is time to decide which gates it will be made up of. Type \"done\" when you are done adding gates, and type \"cancel\" to stop making this gate.");
        boolean loop = true;
        boolean cancel = false;
        while (loop) {
          System.out.print("You currently have " + String.valueOf(builderGates.size()) + " gates: ");
          for (Gate gate : builderGates) {
            //show selected gates
            System.out.print(gate.getName()+ " ");
          }
          System.out.print("\nAvailable gates: ");
          for (Gate gate : Manager.getGates()) {
            //show existing gates
            System.out.print(gate.getName() + " ");
          }
          String input = getInput("\nWhich gate would you like to add?");
          if (input.equals("done")) {
            loop = false;
          } else if (input.equals("cancel")) {
            loop = false;
            cancel = true;
          } else {
            Gate toAdd = Manager.findFromName
        (input.toUpperCase());
            if (toAdd == null) {//gate doesn't exist
              System.out.println("I don't recognize that gate name.");
            } else {
              builderGates.add(toAdd.clone());//clone the object so pin states aren't shared between circuits (in case remembering circuts are made)
            }
          }
        }
        if (cancel) continue;
        System.out.println("Great! Now it's time to hook the gates together.");
        int pin = 1;
        int out = 0;
        List<Character> pinMap = new ArrayList<Character>();
        List<Character> outputMap = new ArrayList<Character>();
        String alphabet = "ABCDEFGHIJLKMNOPQRSTUVWXYZ";
        for (Gate gate : builderGates) {
          // System.out.print(gate.getName() +": [");
          for (Pin p : gate.getInputPins()) {
            // System.out.print(pin);
            pinMap.add((char)(pin+48));//integer to ascii value
            // if (p != gate.getInputPins().get(gate.getInputPins().size() -1)) System.out.print(" ");//prevent 
            pin++;
          }
          // System.out.print(", out: ");
          // System.out.print(alphabet.charAt(out));
          outputMap.add(alphabet.charAt(out));
          out++;
          // System.out.print("] ");
        }
        printGates(builderGates, pinMap, outputMap);
        System.out.println("\nThese numbers you see here match the amount of inputs that each gate you selected has, separated by square brackets and labeled by the gate name. The letters represent the output of the gate paired to it. \nYou will control which gate outputs are connected to other gate inputs by changing the numbers to letters. \nSo for example, if a gate layout by default is \"NOT: [1, out: A] OR: [2 3, out: B]\", then you can change it to be \"NOT: [B, out: A] OR: [2 3, out: B]\" to make the NOT gate read the OR gate's output. \nOnce you are done, the amount of inputs that are still a number instead of a letter should correspond to the amount of inputs you want this gate to have. In the case of my example, the gate (Which is a NOR gate) will have 2 inputs. \nAgain, type \"done\" when done or \"cancel\" to stop.");
        loop = true;
        cancel = false;
        while (loop) {
          printGates(builderGates, pinMap, outputMap);
          String input = getInput("Enter a number matching the input you want to change.");
          if (input.equals("done")) {
            loop = false;
          } else if (input.equals("cancel")) {
            loop = false;
            cancel = true;
          } else {
            int position;
            try {
              position = Integer.valueOf(input);
            } catch (Exception e) {
              System.out.println("That isn't a valid number.");
              continue;
            }
            if (position > pinMap.size() || position < 1) {
              System.out.println("That input doesn't exist.");
              continue;
            } else {
              input = getInput("Now input a letter matching the output you want to link it to.");
              char output = input.toUpperCase().charAt(0);
              if (outputMap.indexOf(output) == -1) {
                System.out.println("That output doesn't exist.");
              } else {
                pinMap.set(position-1, output);
              }
            }
          }
        }
        if (cancel) break;
        System.out.print("One final thing, then your gate is all set. The gate needs a final output destination. Which output should be the one that is the final output of the gate? Enter the letter that matches the output.");
        char charAt = '1';
        boolean isValid = false;
        //keep getting a letter until the user inputs one that exists
        while (!isValid) {
          String charInput = getInput("").toUpperCase();
          charAt = charInput.charAt(0);
          if (outputMap.indexOf(charAt) == -1) {
            System.out.println("That isn't a valid output letter.");
          } else isValid = true;
        }
        
        int outputPinIndex = outputMap.indexOf(charAt);
        InputMatrix builderMatrix = new InputMatrix();
        int focus = 0;
        int inputPins = 0;
        for (int i = 0; i < builderGates.size(); i++) {
          for (int j = 0; j < builderGates.get(i).getInputPins().size(); j++) {
            //count number of inputs and format gates/pins
            if (Character.isDigit(pinMap.get(focus))) {
              inputPins++;
            } else {
              builderMatrix.addInputGateIndex(i);
              builderMatrix.addInputPinIndex(j);
              builderMatrix.addOutputGateIndex(outputMap.indexOf(pinMap.get(focus)));
            }
            focus++;
          }
        }
        focus = 0;
        int currentSpot = 0;
        int builderPins[][] = new int[inputPins][2];
        for (int i = 0; i < builderGates.size(); i++) {
          for (int j = 0; j < builderGates.get(i).getInputPins().size(); j++) {
            if (Character.isDigit(pinMap.get(focus))) {
              //link input pins to output pins
              builderPins[currentSpot][0] = i;
              builderPins[currentSpot][1] = j;
              currentSpot++;
            }
            focus++;
          }
        }
        
        customGate newGate = new customGate(name.toUpperCase(), builderGates, inputPins, outputPinIndex, builderPins, builderMatrix);
        System.out.println(newGate.getName() + " is now an available gate to run or use in building other gates!");
        
      } else if (check.equals("remove")) {
        
        System.out.print("Which gate do you want to remove? \nRemovable gates: ");
        for (int i = 3; i < Manager.getGates().size(); i++) {//start i at 3 to skip over primitive gates
          System.out.print(Manager.getGates().get(i).getName() + " ");
        } 
        System.out.print("\nType \"cancel\" to cancel removing any gates.");
        boolean loop = true;
        while (loop) {
          String toRemove = getInput("").toUpperCase();
          if (toRemove.equals("CANCEL")) {
            loop = false;
          } else if (Manager.findFromName(toRemove) != null) {
            if (Manager.getGates().indexOf(Manager.findFromName(toRemove)) > 2) {//cannot remove a primitive gate
              Manager.getGates().remove(Manager.getGates().indexOf(Manager.findFromName(toRemove)));
              loop = false;
            } else {
              System.out.print("You can't remove that gate!");
            }
          } else {
            System.out.print("I don't recognize that gate name.");
          }
        }
      }
    }
  }

  public static void printPins(Gate gate) {
    System.out.print("(inputs: ");
    for (int i = 0; i < gate.getInputPins().size(); i++) {
      System.out.print(gate.getPin(i).getState());
      System.out.print(", ");
    }
    System.out.print("output: ");
    System.out.print(gate.getState());
    System.out.println(")");
  }

  public static String getInput(String prompt) {
    System.out.println(prompt);
    String ret = input.nextLine();
    return ret.toLowerCase();
  }

  public static void printGates(List<Gate> gates, List<Character> pinMap, List<Character> outputMap) {
    int pin = 0;
    int out = 0;
    for (Gate gate : gates) {
      System.out.print(gate.getName() +": [");
      for (Pin p : gate.getInputPins()) {
        System.out.print(pinMap.get(pin));//print character representing pin
        if (p != gate.getInputPins().get(gate.getInputPins().size() -1)) System.out.print(" ");//prevent extraneous space from showing
        pin++;
      }
      System.out.print(", out: ");
      System.out.print(outputMap.get(out));
      System.out.print("] ");
      out++;
    }
    System.out.println();
  }
}
