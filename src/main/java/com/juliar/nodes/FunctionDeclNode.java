package com.juliar.nodes;

import java.util.List;
import java.util.Stack;

/**
 * Created by Don on 1/12/2017.
 */
@SuppressWarnings("serial")
public class FunctionDeclNode extends NodeImpl implements IContextInfo {
    private String functionName;

    public FunctionDeclNode(){
        super();
        System.out.println( "creating functionDeclNode" );
    }


    public FunctionDeclNode(String funcName, List<Node> inst){
        this();
        functionName = funcName;
        instructions = inst;
    }
    public String getFunctionName(){
        return functionName;
    }

    @Override
    public NodeType getType() {
        return NodeType.FunctionDeclType;
    }

    @Override
    public void addInst(Node parent, Node instruction) {
        super.addInst(parent, instruction);
    }

    @Override
    public void addInst(Node instruction) {
        // Throw away assigned instructions since we already know the
        // function name
        if ( instruction instanceof StatementNode){
            super.addInst( instruction );
        }

        ((NodeImpl)instruction).SetParentNode( this );

        return;
    }

    @Override
    public void addInst(Stack<Node> contextStack, Node instruction) {
        super.addInst(contextStack, instruction);
    }
}
