package com.juliar.nodes;

/**
 * Created by donreamey on 10/21/16.
 */
@SuppressWarnings("serial")
public class BinaryNode extends NodeImpl  {
    private IntegralTypeNode integralTypeNode;



    public BinaryNode(){
    }


    public IntegralTypeNode data(){return integralTypeNode;}

    @Override
    public NodeType getType() {
        return NodeType.BinaryType;
    }

}

