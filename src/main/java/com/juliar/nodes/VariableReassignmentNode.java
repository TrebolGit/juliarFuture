package com.juliar.nodes;

import java.util.Stack;

/**
 * Created by don on 3/25/17.
 */
@SuppressWarnings("serial")
public class VariableReassignmentNode extends NodeImpl {
    public VariableReassignmentNode(){
        System.out.println("variable reassignmnent");
    }

    public Node getRvalueType(){
        return instructions.get(2).getInstructions().get(0);
    }

    @Override
    public NodeType getType() {
        return NodeType.VariableReassignmentType;
    }

    @Override
    public void addInst(Node parent, Node instruction) {
        super.addInst(parent, instruction);
    }

    @Override
    public void addInst(Node instruction) {
        super.addInst(instruction);
    }

    @Override
    public void addInst(Stack<Node> contextStack, Node instruction) {
        super.addInst(contextStack, instruction);
    }
}
