package com.juliar.nodes;

import java.util.Stack;

/**
 * Created by donreamey on 3/28/17.
 */
@SuppressWarnings("serial")
public class ExpressionNode extends NodeImpl{
    public ExpressionNode(){
        System.out.println( "ExpressionNode" );
    }

    @Override
    public NodeType getType() {
        return NodeType.ExpressionType;
    }

    public String getVariableName(){
        if ( instructions.size() >= 2 && instructions.get(0) instanceof VariableDeclarationNode){
            VariableDeclarationNode variableDeclarationNode = (VariableDeclarationNode)instructions.get(0);
            return variableDeclarationNode.getVariableNode().variableName;
        }

        throw new RuntimeException( "variable does not have a name");
    }

    @Override
    public void addInst(Node parent, Node instruction) {
        super.addInst(parent, instruction);
    }

    @Override
    public void addInst(Node instruction) {
        if ( instruction instanceof FinalNode )
        {
            return;
        }
        super.addInst(instruction);
    }

    @Override
    public void addInst(Stack<Node> contextStack, Node instruction) {
        super.addInst(contextStack, instruction);
    }
}
