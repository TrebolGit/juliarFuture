package com.juliar.nodes;

import com.juliar.interpreter.ActivationFrame;

/**
 * Created by Don on 1/15/2017.
 */
@SuppressWarnings("serial")
public class VariableNode extends NodeImpl {
    private static final long serialVersionUID = 321323217;
    private Node parent;
    public String variableName;
    public IntegralTypeNode integralTypeNode;


    public Node getParent(){
        return parent;
    }

    public void setParent( Node parentNode){
        parent = parentNode;
    }


    public VariableNode(String vName){
        variableName = vName;
    }

    public String getVariableName() {
        FinalNode finalNode = (FinalNode) getInstructions().get(0);
        byte[] bytes = finalNode.getFinalNodeBytes();

        return new String(bytes);
    }

    public void setIntegralTypeNode(IntegralTypeNode typeNode){
        integralTypeNode = typeNode;
    }

    @Override
    public NodeType getType() {
        return NodeType.VariableType;
    }


    public FinalNode getFinalNode(){
        return (FinalNode) this.getInstructions().get(0);
    }

    @Override
    public Object getRealValue(ActivationFrame frame) {
        String variableName =  (( FinalNode )getInstructions().get( 0 )).dataString();
        if ( frame.variableSet.containsKey( variableName ) ){
            return ((FinalNode)frame.variableSet.get ( variableName ).getInstructions().get ( 0 )).dataString();
        }

        return "";
    }
}
